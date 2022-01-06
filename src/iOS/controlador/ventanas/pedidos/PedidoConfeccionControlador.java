package iOS.controlador.ventanas.pedidos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Material;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalleConfeccion;
import iOS.modelo.entidades.PedidoDetalleMaterial;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoConfeccionDetalle;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalleMaterial;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorMaterial;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.pedidos.TransaccionPedido;

public class PedidoConfeccionControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface,
		ClienteInterface, ProductoInterface, PropertyChangeListener, MaterialInterface {
	private TransaccionPedido ventana;
	private ModeloTablaPedidoConfeccionDetalle mtPedidoDetalle;
	private ModeloTablaPedidoDetalleMaterial mtDetalleMaterial;

	private PedidoDao dao;
	private Pedido pedido;
	private Producto producto;
	private PedidoDetalleConfeccion detalle;
	private List<PedidoDetalleConfeccion> detalles = new ArrayList<PedidoDetalleConfeccion>();

	private Material material;
	private PedidoDetalleMaterial pdMaterial;
	private List<PedidoDetalleMaterial> materiales = new ArrayList<PedidoDetalleMaterial>();

	private String accion;

	private Cliente cliente;

	public PedidoConfeccionControlador(TransaccionPedido ventana) {
		this.ventana = ventana;
		mtPedidoDetalle = new ModeloTablaPedidoConfeccionDetalle();
		ventana.getTable().setModel(mtPedidoDetalle);
		tableMenu(ventana.getTable());

		mtDetalleMaterial = new ModeloTablaPedidoDetalleMaterial();
		ventana.getTableMaterial().setModel(mtDetalleMaterial);

		dao = new PedidoDao();
		pedido = new Pedido();

		setUpEvents();
		formatoTabla();
	}

	private void setUpEvents() {
		ventana.getBtnAgregar().addActionListener(this);
		ventana.getBtnBuscarCliente().addActionListener(this);
		ventana.getBtnBuscarProducto().addActionListener(this);
		ventana.getBtnGuardar().addActionListener(this);
		ventana.getBtnQuitar().addActionListener(this);
		ventana.getBtnSalir().addActionListener(this);

		ventana.getTable().addMouseListener(this);
		ventana.getTable().addPropertyChangeListener(this);

		ventana.gettDescuentoPedido().addKeyListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlCliente());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlDireccion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMetros());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlPedido1());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlPedido2());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlProducto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSumatoria());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlValorPagar());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlVendedor());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDescuentoPedido());

		accion = null;
		cliente = null;
		detalle = null;
		material = null;
//		pedido = null;
		producto = null;
		vaciarTablaDetalle();
		vaciarTablaMaterial();
	}

	private void formatoTabla() {
		ventana.getTable().getColumnModel().getColumn(0).setPreferredWidth(200);
		ventana.getTable().getColumnModel().getColumn(1).setPreferredWidth(200);
	}

	private void vaciarTablaDetalle() {
		detalles = new ArrayList<PedidoDetalleConfeccion>();
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();

	}

	private void vaciarTablaMaterial() {
		materiales = new ArrayList<PedidoDetalleMaterial>();
		mtDetalleMaterial.setMateriales(materiales);
		mtDetalleMaterial.fireTableDataChanged();

	}

	private void agregarDetalle(Producto p) {
		if (producto.getTipoCobro().equalsIgnoreCase("METRO CUADRADO")) {
			JOptionPane.showMessageDialog(ventana, "Este producto es por METRO CUADRADO, no se agregará el producto.");
			return;
		}
		if (!limitarItems(detalles)) {
			return;
		}
		detalle = new PedidoDetalleConfeccion();
		detalle.setColaborador(Sesion.getInstance().getColaborador());
		detalle.setArchivo("Sin observación");
		detalle.setTamano("Sin tamaño");
		detalle.setMolde("Sin molde");

		// Cantidad
		detalle.setCantidadDetalle(1);

		// Herencias
		detalle.setProducto(producto);
		detalle.setPrecioProducto(producto.getPrecioMaximo());
		detalle.setPrecioDetalle(producto.getPrecioMaximo());

		// Para la tabla que se muestra
		detalles.add(detalle);
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();
		ventana.getBtnBuscarProducto().requestFocus();

		// Metodos referentes
		realizarCalculos();
	}

	private void quitarDetalle(int posicion) {
		if (posicion < 0) {
			return;
		}

		int opcion = JOptionPane.showConfirmDialog(null, "¿Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			detalles.remove(ventana.getTable().getSelectedRow());
			mtPedidoDetalle.setDetalle(detalles);
		} else {
			return;
		}
		mtPedidoDetalle.fireTableDataChanged();
		realizarCalculos();
	}

	private void seleccionarDetalle(int posicion) {
		if (posicion < 0) {
		}

		detalle = detalles.get(posicion);
		System.out.println(detalle);

	}

	private void agregarMaterial(Material m) {
		if (detalle == null) {
			return;
		}
		pdMaterial = new PedidoDetalleMaterial();
		pdMaterial.setColaborador(Sesion.getInstance().getColaborador());
		pdMaterial.setDetalleConfeccion(detalle);
		pdMaterial.setMaterial(m);
		pdMaterial.setPrecio(m.getPrecioMaximo());
		materiales.add(pdMaterial);
		detalle.setMateriales(materiales);

		mtDetalleMaterial.setMateriales(materiales);
	}

	private void quitarMaterial(int posicion) {
		if (posicion < 0) {
			return;
		}

		int opcion = JOptionPane.showConfirmDialog(null, "¿Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			detalles.remove(ventana.getTable().getSelectedRow());
			mtPedidoDetalle.setDetalle(detalles);
		} else {
			return;
		}
		mtPedidoDetalle.fireTableDataChanged();
		realizarCalculos();
	}

	private void seleccionarMaterial(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}

	}

	private boolean limitarItems(List<PedidoDetalleConfeccion> lista) {
		if (lista.size() < 10) {
			return true;
		}
		JOptionPane.showMessageDialog(ventana, "Solo se permiten 10 items por pedido.");
		return false;

	}

	private List<Integer> valorarPedido() {
		double suma = 0;
		int diferencia = 0;
		List<Integer> numeros = new ArrayList<>();
		Double descuento = ventana.gettDescuentoPedido().getValue();

		try {
			suma = detalles.stream().filter(o -> o.isEstado() == true).mapToDouble(o -> o.getPrecioDetalle()).sum();
			diferencia = (int) (suma - descuento);
		} catch (Exception e) {
			// TODO: handle exception
		}

		numeros.add((int) suma);
		numeros.add(diferencia);

		ventana.getlSumatoria().setText(EventosUtil.separadorMiles((double) suma) + " Gs.");
		ventana.getlValorPagar().setText(EventosUtil.separadorMiles((double) diferencia) + " Gs.");

		return numeros;
	}

	private void realizarCalculos() {
		valorarPedido();
	}

	private boolean validarFormulario() {
		if (cliente == null) {
			JOptionPane.showMessageDialog(ventana, "Indique el cliente");
			return false;
		}
		if (detalles.size() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Cargue productos al pedido");
			return false;
		}

		return true;
	}

	public void nuevo() {
		estadoInicial(true);
		accion = "NUEVO";
		pedido = null;
		ventana.getlPedido1().setText("PEDIDO NUEVO");
		ventana.getlVendedor().setText(Sesion.getInstance().getColaborador().toString());
		ventana.getlPedido2().setText(EventosUtil.formatoFecha(new Date()));
	}

	public void modificar() {
		estadoInicial(true);
		accion = "MODIFICAR";
	}

	private void salir() {
		ventana.dispose();
	}

	private void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			pedido = new Pedido();
			pedido.setColaborador(Sesion.getInstance().getColaborador());
			pedido.setConsiderarMetraje(ventana.getRbConsiderarMetraje().isSelected());
			pedido.setCostoTotal(0);
			pedido.setDeudaPaga(false);
			pedido.setGananciaTotal(0);
			pedido.setGeneraDeuda(false);
			pedido.setMetrosFechaEmision(0d);
			pedido.setPedidoCostura(true);
			pedido.setPedidoCarteleria(false);
			pedido.setCliente(cliente);
		}

		pedido.setDescuentoTotal(Integer.parseInt(ventana.gettDescuentoPedido().getText()));
		pedido.setEsPresupuesto(ventana.getRbGenerarPresupuesto().isSelected());
		pedido.setInformacionResponsable(ventana.gettResponsable().getText());
		pedido.setMetrosTotal(0d);
		pedido.setTipoPagoPedido(ventana.getRbContado().isSelected() ? "CONTADO" : "CREDITO");

		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));

		pedido.setPedidosConfecciones(detalles);
		pedido.setPedidoDetalles(null);

		for (int i = 0; i < detalles.size(); i++) {
			detalles.get(i).setPedido(pedido);
			detalles.get(i).setFechaModificado(new Date());
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(pedido);
			} else {
				dao.modificar(pedido);
			}
			dao.commit();
			Metodos.getInstance().imprimirPedidoConfeccionIndividual(pedido);
			modificar();
			setPedido(pedido);
		} catch (Exception e) {
			dao.rollBack();
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == ventana.gettDescuentoPedido() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			realizarCalculos();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventana.getTable()) {
			seleccionarDetalle(ventana.getTable().getSelectedRow());
		}

		if (e.getSource() == ventana.getTableMaterial()) {
			seleccionarMaterial(ventana.getTableMaterial().getSelectedRow());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == ventana.getTable()) {
			seleccionarDetalle(ventana.getTable().getSelectedRow());
		}

		if (e.getSource() == ventana.getTableMaterial()) {
			seleccionarMaterial(ventana.getTableMaterial().getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getSource() == ventana.getTable()) {
			seleccionarDetalle(ventana.getTable().getSelectedRow());
		}

		if (e.getSource() == ventana.getTableMaterial()) {
			seleccionarMaterial(ventana.getTableMaterial().getSelectedRow());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == ventana.getTable()) {
			realizarCalculos();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "BuscarCliente":
			abrirBuscadorCliente();
			break;
		case "BuscarProducto":
			abrirBuscadorProducto();
			break;
		case "Salir":
			salir();
			break;
		case "Guardar":
			guardar();
			break;
		case "AgregarMaterial":
			abrirBuscadorMaterial();
			break;
		case "QuitarMaterial":
			quitarMaterial(0);
			break;
		default:
			break;
		}
	}

	private void abrirBuscadorProducto() {
		BuscadorProducto buscador = new BuscadorProducto();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	private void abrirBuscadorMaterial() {
		if (detalle == null) {
			return;
		}
		BuscadorMaterial buscador = new BuscadorMaterial();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	@Override
	public void setProducto(Producto producto) {
		this.producto = producto;
		gestionarProducto();
	}

	private void gestionarProducto() {
		if (producto == null) {
			return;
		}
		ventana.getlProducto().setText(producto.getDescripcion());
		agregarDetalle(producto);
	}

	@Override
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		gestionarCliente();
	}

	private void gestionarCliente() {
		if (cliente == null) {
			return;
		}
		ventana.getlCliente().setText(cliente.getNombreCompleto());
		ventana.getlIdentificacion().setText(cliente.getIdentificacion());
		ventana.getlContacto().setText(cliente.getContacto());
		ventana.getlDireccion().setText(cliente.getDireccion());
		ventana.gettResponsable().setText(cliente.getNombreCompleto() + ", " + cliente.getContacto());
	}

	@Override
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
		gestionarPedido();

	}

	private void gestionarPedido() {
		if (pedido == null) {
			return;
		}

		if (pedido.isGeneraDeuda()) {
			EventosUtil.estadosCampoPersonalizado(ventana, false);
		}

		if (pedido.isProduccionFinalizada()) {
			EventosUtil.estadosCampoPersonalizado(ventana, false);
		}

		if (pedido.getSumaPagos() > 0) {
			EventosUtil.estadosCampoPersonalizado(ventana, false);
		}

		// Datos del cliente
		cliente = pedido.getCliente();
		setCliente(cliente);

		// Datos del pedido
		ventana.getlMetros().setText("METRAJE DEL PEDIDO: " + EventosUtil.separadorDecimales(pedido.getMetrosTotal()));
		ventana.getlPedido1().setText(pedido.isEsPresupuesto()
				? "PRESUPUESTO"
						.concat(" " + pedido.getId() + " - " + EventosUtil.formatoFecha(pedido.getFechaRegistro()))
				: "PEDIDO".concat(" " + pedido.getId() + " - " + EventosUtil.formatoFecha(pedido.getFechaRegistro())));
		ventana.getlPedido2().setText(pedido.isEstado() ? "VIGENTE" : "ANULADO");
		ventana.getlSumatoria().setText(EventosUtil.separadorMiles((double) pedido.getSumatoriaPrecio()));
		ventana.getlValorPagar().setText(EventosUtil.separadorMiles((double) pedido.getPrecioPagar()));
		ventana.getlVendedor().setText(pedido.getColaborador().toString());
		ventana.gettDescuentoPedido().setValue((double) pedido.getDescuentoTotal());
		ventana.gettResponsable().setText(pedido.getInformacionResponsable());

		// Detalles del pedido
		detalles = pedido.getPedidosConfecciones();
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();
	}

	private void tableMenu(final JTable table) {
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				} else {
					table.clearSelection();
				}

				int rowindex = table.getSelectedRow();
				if (rowindex < 0) {
					return;
				}
				if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
					JPopupMenu popup = tablePopup(table, rowindex);
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	private JPopupMenu tablePopup(final JTable table, final int row) {
		JPopupMenu popup = new JPopupMenu("Popup");
		JMenuItem quitarItem = new JMenuItem("Quitar item");
		quitarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarDetalle(row);

			}
		});
		JMenuItem duplicarItem = new JMenuItem("Duplicar item");
		duplicarItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setProducto(detalles.get(row).getProducto());
			}
		});
		popup.add(quitarItem);
		popup.add(duplicarItem);
		return popup;
	}

	@Override
	public void setMaterial(Material m) {
		this.material = m;

		if (material == null) {
			return;
		}
		System.out.println(material);
		agregarMaterial(material);

	}
}
