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
import iOS.modelo.entidades.DetalleMaterial;
import iOS.modelo.entidades.Material;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaDetalleMaterial;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.pedidos.TransaccionPedido;

public class PedidoCarteleriaControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface,
		ClienteInterface, ProductoInterface, PropertyChangeListener {
	private TransaccionPedido ventana;

	private PedidoDao dao;
	private Pedido pedido;

	private Producto producto;
	private PedidoDetalles detalle;
	private List<PedidoDetalles> detalles = new ArrayList<PedidoDetalles>();
	private ModeloTablaPedidoDetalle mtPedidoDetalle;

	private DetalleMaterial material;
	private List<DetalleMaterial> materiales = new ArrayList<DetalleMaterial>();
	private ModeloTablaDetalleMaterial mtDetalleMaterial;

	private String accion;

	private Cliente cliente;

	public PedidoCarteleriaControlador(TransaccionPedido ventana) {
		this.ventana = ventana;
		mtPedidoDetalle = new ModeloTablaPedidoDetalle();
		ventana.getTable().setModel(mtPedidoDetalle);
		tableMenu(ventana.getTable());

		mtDetalleMaterial = new ModeloTablaDetalleMaterial();
		ventana.getTableMaterial().setModel(mtDetalleMaterial);

		dao = new PedidoDao();
		pedido = new Pedido();

		nuevo();
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
		
		cliente = null;
		producto = null;
		detalle = null;
		
		vaciarTablaDetalle();
		vaciarTablaMaterial();
	}

	private void formatoTabla() {
		ventana.getTable().getColumnModel().getColumn(4).setPreferredWidth(0);
		ventana.getTable().getColumnModel().getColumn(4).setMaxWidth(0);
		ventana.getTable().getColumnModel().getColumn(4).setMinWidth(0);

	}

	private void vaciarTablaDetalle() {
		detalles = new ArrayList<PedidoDetalles>();
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();

	}

	private void vaciarTablaMaterial() {
		materiales = new ArrayList<>();
		mtDetalleMaterial.setLista(materiales);
		mtDetalleMaterial.fireTableDataChanged();

	}

	private void agregarDetalle(Producto p) {
		if (!limitarItems(detalles)) {
			return;
		}
		detalle = new PedidoDetalles();
		detalle.setColaborador(Sesion.getInstance().getColaborador());
		detalle.setArchivo("Sin indicaciones");

		// Medidas
		if (producto.isTieneMedidaFija()) {
			detalle.setMedidaAlto(producto.getAltoProducto());
			detalle.setMedidaAncho(producto.getAnchoProducto());
			detalle.setMedidaDetalle((double)(producto.getAltoProducto() * producto.getAnchoProducto()) / 10000);
		} else {
			detalle.setMedidaAlto(0);
			detalle.setMedidaAncho(0);
			detalle.setMedidaDetalle(0.01);
		}
		// Cantidad
		detalle.setCantidadDetalle(1);

		detalle.setProducto(producto);
		detalle.setPrecioProducto((int) producto.getPrecioMaximo());

		try {
			switch (producto.getTipoCobro()) {
			case "METRO CUADRADO":
				detalle.setPrecioDetalle((int) (detalle.getPrecioProducto() * detalle.getMedidaDetalle()
						* detalle.getCantidadDetalle()));
				break;
			case "METRO LINEAL":
				detalle.setPrecioDetalle((int) (detalle.getPrecioProducto() * detalle.getMedidaDetalle()
						* detalle.getCantidadDetalle()));
				break;
			case "UNIDAD":
				detalle.setPrecioDetalle(detalle.getPrecioProducto() * detalle.getCantidadDetalle());
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// Materiales
		materiales = new ArrayList<DetalleMaterial>();
		if (producto.getMateriales() != null) {
			for (int i = 0; i < producto.getMateriales().size(); i++) {
				material = new DetalleMaterial();
				material.setDetalleCarteleria(detalle);
				material.setColaboradorQueRegistra(Sesion.getInstance().getColaborador());
				material.setMaterial(producto.getMateriales().get(i).getMaterial());
				material.setPrecioMaterial(producto.getMateriales().get(i).getMaterial().getPrecioMaximo());
				materiales.add(material);
			}
		}

		detalle.setMateriales(materiales);

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

		int opcion = JOptionPane.showConfirmDialog(null, "┐Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
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
		material = new DetalleMaterial();
		material.setDetalleCarteleria(detalle);
		material.setMaterial(m);
		material.setPrecioMaterial(m.getPrecioMaximo());
		materiales.add(material);
		mtDetalleMaterial.setLista(materiales);
		mtDetalleMaterial.fireTableDataChanged();

	}

	private void quitarMaterial(int posicion) {
		if (posicion < 0) {
			return;
		}
		int opcion = JOptionPane.showConfirmDialog(null, "┐Desasociar caracteristica de producto?", "Confirmar",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			materiales.remove(posicion);
			mtDetalleMaterial.setLista(materiales);
			mtDetalleMaterial.fireTableDataChanged();
		}
	}

	private void seleccionarMaterial(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		material = materiales.get(posicion);
		System.out.println(material);

	}

	private boolean limitarItems(List<PedidoDetalles> lista) {
		if (lista.size() < 10) {
			return true;
		}
		JOptionPane.showMessageDialog(ventana, "Solo se permiten 10 items por pedido.");
		return false;

	}

	private double sumarMedidaDetalle() {
		double suma = 0;

		try {
			suma = detalles.stream().filter(o -> o.isEstado() == true).mapToDouble(o -> o.getMedidaDetalle()).sum();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		ventana.getlMetros().setText("METRAJE DEL PEDIDO: " + EventosUtil.separadorDecimales(suma));
		return suma;
	}

	private List<Integer> valorarPedido() {
		int suma = 0;
		int diferencia = 0;
		List<Integer> numeros = new ArrayList<>();
		Double descuento = ventana.gettDescuentoPedido().getValue();

		try {
			suma = detalles.stream().filter(o -> o.isEstado() == true).mapToInt(o -> o.getPrecioDetalle()).sum();
			diferencia = (int) (suma - descuento);
		} catch (Exception e) {
			// TODO: handle exception
		}

		numeros.add(suma);
		numeros.add(diferencia);

		ventana.getlSumatoria().setText(EventosUtil.separadorMiles((double) suma) + " Gs.");
		ventana.getlValorPagar().setText(EventosUtil.separadorMiles((double) diferencia) + " Gs.");

		return numeros;
	}

	private void realizarCalculos() {
		sumarMedidaDetalle();
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
		accion = "NUEVO";
		estadoInicial(true);

		ventana.getlVendedor().setText(Sesion.getInstance().getColaborador().toString());
		ventana.getlPedido1().setText("PEDIDO NUEVO");
		ventana.getlPedido2().setText(EventosUtil.formatoFecha(new Date()));

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
			pedido.setMetrosFechaEmision(0d);
			pedido.setPedidoCarteleria(true);
		}

		pedido.setCliente(cliente);
		pedido.setConsiderarMetraje(ventana.getRbConsiderarMetraje().isSelected());
		pedido.setDescuentoTotal(Integer.parseInt(ventana.gettDescuentoPedido().getText()));
		pedido.setEsPresupuesto(ventana.getRbGenerarPresupuesto().isSelected());
		pedido.setCostoTotal(0);
		pedido.setMetrosTotal(sumarMedidaDetalle());
		pedido.setTipoPagoPedido(ventana.getRbContado().isSelected() ? "CONTADO" : "CREDITO");

		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));

		pedido.setPedidoDetalles(detalles);

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
			Metodos.getInstance().imprimirPedidoCarteleriaIndividual(pedido, ventana);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getSource() == ventana.gettDescuentoPedido() && e.getKeyCode() == KeyEvent.VK_ENTER) {
			realizarCalculos();
			System.out.println("keyPressed");
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

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
			agregarMaterial(null);
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
		buscador.setUpControlador(false);
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

//	private void abrirBuscadorMaterial() {
//		BuscadorMaterial buscador = new BuscadorMaterial();
//		buscador.setUpControlador(false);
//		buscador.getControlador().setInterfaz(this);
//		buscador.setVisible(true);
//	}

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
	}

	@Override
	public void setPedido(Pedido pedido) {
		estadoInicial(false);
		this.pedido = pedido;
		gestionarPedido();

	}

	private void gestionarPedido() {
		if (pedido == null) {
			return;
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
		ventana.gettDescuentoPedido().setValue((double) pedido.getDescuentoTotal());

		// Detalles del pedido
		detalles = pedido.getPedidoDetalles();
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();

		accion = "MODIFICAR";
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
		JMenuItem imprimirItem = new JMenuItem("Quitar item");
		imprimirItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quitarDetalle(row);

			}
		});
		popup.add(imprimirItem);
		return popup;
	}

}
