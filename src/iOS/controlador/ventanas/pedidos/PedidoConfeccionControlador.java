package iOS.controlador.ventanas.pedidos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import iOS.controlador.util.ConexionReporte;
import iOS.controlador.util.EventosUtil;
import iOS.controlador.util.Impresiones;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalleConfeccion;
import iOS.modelo.entidades.Producto;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoConfeccionDetalle;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.pedidos.PedidoConfeccion;
import net.sf.jasperreports.engine.JRException;

public class PedidoConfeccionControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface,
		ClienteInterface, ProductoInterface, PropertyChangeListener {
	private PedidoConfeccion ventana;

	private ModeloTablaPedidoConfeccionDetalle modeloTabla;
	private PedidoDao dao;
	private String accion;
	private Pedido pedido;
	private Cliente cliente;
	private List<PedidoDetalleConfeccion> items = new ArrayList<PedidoDetalleConfeccion>();
	private Producto producto;
	private PedidoDetalleConfeccion detalle;
	private List<Pedido> pedidos = new ArrayList<Pedido>();

	public PedidoConfeccionControlador(PedidoConfeccion ventana) {
		this.ventana = ventana;
		modeloTabla = new ModeloTablaPedidoConfeccionDetalle();
		ventana.getTable().setModel(modeloTabla);

		dao = new PedidoDao();
		pedido = new Pedido();

		nuevo();
		setUpEvents();
		formatoTabla();
	}

	private void setUpEvents() {
		this.ventana.getBtnAgregar().addActionListener(this);
		this.ventana.getBtnAnular().addActionListener(this);
		this.ventana.getBtnBuscarCliente().addActionListener(this);
		this.ventana.getBtnBuscarProducto().addActionListener(this);
		this.ventana.getBtnSalir().addActionListener(this);

		this.ventana.getBtnGuardar().addActionListener(this);

		this.ventana.getTable().addMouseListener(this);
		this.ventana.getTable().addPropertyChangeListener(this);
		this.ventana.getRbConsiderarMetraje().addPropertyChangeListener(this);
		this.ventana.getRbGenerarPresupuesto().addPropertyChangeListener(this);
		this.ventana.getRbConsiderarMetraje().addMouseListener(this);
		this.ventana.getRbGenerarPresupuesto().addMouseListener(this);

		this.ventana.gettDescuentoPedido().addKeyListener(this);
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlCliente());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlEstadoPedido());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFechaHora());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlGanancia());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlProducto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMetrosFechaEmision());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMetrosPedido());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlNroPedido());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlSumatoria());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlTotalPagar());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettDescuentoPedido());
		EventosUtil.estadosCampoPersonalizado(ventana.gettDescuentoPedido(), !b);

		ventana.getRbConsiderarMetraje().setSelected(false);
		ventana.getlMetrosFechaEmision().setForeground(Color.RED);
		ventana.getRbGenerarPresupuesto().setSelected(false);

		// Botones que se mantienen disponibles si o si
		EventosUtil.estadosBotones(ventana.getBtnBuscarCliente(), true);
		EventosUtil.estadosBotones(ventana.getBtnSalir(), true);

		// Cuando setCliente, se activa
		EventosUtil.estadosBotones(ventana.getBtnBuscarProducto(), !b);

		// Cuando setProducto, se activa
		EventosUtil.estadosBotones(ventana.getBtnAgregar(), false);

		// Si se selecciona un detalle
		EventosUtil.estadosBotones(ventana.getBtnAnular(), !b);

		EventosUtil.estadosBotones(ventana.getBtnGuardar(), false);

		vaciarTabla();

	}

	private void formatoTabla() {
		ventana.getTable().getColumnModel().getColumn(0).setPreferredWidth(250);
		ventana.getTable().getColumnModel().getColumn(1).setPreferredWidth(100);

	}

	private void vaciarTabla() {
		items = new ArrayList<PedidoDetalleConfeccion>();
		modeloTabla.setDetalle(items);
		modeloTabla.fireTableDataChanged();

	}

	private void agregarItem() {
		if (producto == null) {
			return;
		}
		if (producto.getTipoCobro().equalsIgnoreCase("METRO CUADRADO")) {
			JOptionPane.showMessageDialog(ventana, "Este producto es por METRO CUADRADO, no se agregará el producto.");
			return;
		}
		if (!limitarItems(items)) {
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
		items.add(detalle);
		modeloTabla.setDetalle(items);
		modeloTabla.fireTableDataChanged();
		ventana.getBtnBuscarProducto().requestFocus();

		// Metodos referentes
		realizarCalculos();
	}

	private boolean limitarItems(List<PedidoDetalleConfeccion> lista) {
		if (lista.size() < 10) {
			return true;
		}
		return false;

	}

	private void quitarItem() {
		if (ventana.getTable().getSelectedRow() < 0) {
			return;
		}

		int opcion = JOptionPane.showConfirmDialog(null, "¿Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			items.remove(ventana.getTable().getSelectedRow());
			modeloTabla.setDetalle(items);
		} else {
			return;
		}
		modeloTabla.fireTableDataChanged();
		realizarCalculos();
	}

	private boolean considerarMetraje() {
		boolean considerarMetraje = ventana.getRbConsiderarMetraje().isSelected();
		if (considerarMetraje == true) {
			ventana.getlMetrosFechaEmision().setForeground(Color.YELLOW);
		} else {
			ventana.getlMetrosFechaEmision().setForeground(Color.RED);
		}
		System.out.println(considerarMetraje + " considerarMetraje");
		return considerarMetraje;

	}

	private boolean generarPresupuesto() {
		boolean esPresupuesto = ventana.getRbGenerarPresupuesto().isSelected();
		return esPresupuesto;

	}

	private void calcularSubtotalDetalle() {
		double precio = 0;
		double cantidad = 0;
		double total = 0;

		for (int i = 0; i < items.size(); i++) {
			precio = items.get(i).getPrecioProducto();
			cantidad = items.get(i).getCantidadDetalle();
			switch (items.get(i).getProducto().getTipoCobro()) {
			case "UNIDAD":
				total = (int) ((precio * cantidad));
				items.get(i).setPrecioDetalle(total);
				modeloTabla.setDetalle(items);
				modeloTabla.fireTableDataChanged();
				break;
			default:
				break;
			}
		}
	}

	private List<Integer> valorarPedido() {
		int suma = 0;
		int diferencia = 0;
		List<Integer> numeros = new ArrayList<>();
		double descuento = ventana.gettDescuentoPedido().getValue();
		for (int i = 0; i < items.size(); i++) {
			suma += items.get(i).getPrecioDetalle();
		}
		if (suma > descuento) {
			diferencia = (int) (suma - descuento);
		} else {
			return null;
		}

		numeros.add(suma);
		numeros.add(diferencia);

		ventana.getlSumatoria().setText(EventosUtil.separadorMiles((double) suma) + " Gs.");
		ventana.getlTotalPagar().setText(EventosUtil.separadorMiles((double) diferencia) + " Gs.");

		return numeros;
	}

	private void realizarCalculos() { // Si mi items está vacio entonces no ejecuta
		calcularSubtotalDetalle();
		valorarPedido();

	}

	private boolean validarFormulario() {
		if (cliente == null) {
			JOptionPane.showMessageDialog(ventana, "Indique el cliente");
			return false;
		}
		if (items.size() <= 0) {
			JOptionPane.showMessageDialog(ventana, "Cargue productos al pedido");
			return false;
		}

		return true;
	}

	private String esPresupuesto(Pedido p) {
		if (p.isEsPresupuesto()) {
			return "PRESUPUESTO";
		} else {
			return "PEDIDO";
		}
	}

	private void imprimir(Pedido p) {
		if (p == null) {
			return;
		}
		HashMap<String, Object> parametros = new HashMap<>();
		parametros.put("nombreEmpresa", "iOS Comunicacion Visual");
		parametros.put("esPresupuesto", esPresupuesto(p));

		pedidos.clear();

		pedidos.add((p));

		// Creando reportes
		ConexionReporte<Pedido> conexionReporte = new ConexionReporte<Pedido>();
		try {
			conexionReporte.generarReporte(pedidos, parametros, "PedidoImpreso3");
			conexionReporte.ventanaReporte.setLocationRelativeTo(ventana);
			conexionReporte.ventanaReporte.setVisible(true);
			ventana.dispose();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void nuevo() {
		accion = "NUEVO";
		System.out.println(accion);
		estadoInicial(false);

	}

	private void cancelar() {
		estadoInicial(true);
	}

	private void guardar() {
		if (!validarFormulario()) {
			return;
		}

		if (accion.equals("NUEVO")) {
			pedido = new Pedido();
			pedido.setColaborador(Sesion.getInstance().getColaborador());
			pedido.setPedidoCostura(true);
		}

		pedido.setCliente(cliente);
		pedido.setDescuentoTotal(Integer.parseInt(ventana.gettDescuentoPedido().getText()));
		pedido.setEsPresupuesto(ventana.getRbGenerarPresupuesto().isSelected());
		pedido.setTipoPagoPedido(ventana.getLstTipoPago().getSelectedValue().toString());
		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));

		pedido.setPedidosConfecciones(items);

		for (int i = 0; i < items.size(); i++) {
			items.get(i).setPedido(pedido);
			items.get(i).setFechaModificado(new Date());
		}
		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(pedido);
			} else {
				dao.modificar(pedido);
			}
			dao.commit();
			Impresiones.getInstance().imprimirPedidoConfeccionIndividual(pedido, ventana);
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
			ventana.getTable().isCellEditable(ventana.getTable().getSelectedRow(),
					ventana.getTable().getSelectedColumn());

		}
		if (e.getSource() == ventana.getRbConsiderarMetraje()) {
			considerarMetraje();
		}

		if (e.getSource() == ventana.getRbGenerarPresupuesto()) {
			generarPresupuesto();
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

		if (e.getSource() == ventana.getRbConsiderarMetraje()) {
			considerarMetraje();
		}
		if (e.getSource() == ventana.getRbGenerarPresupuesto()) {
			generarPresupuesto();
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
		case "Nuevo":
			nuevo();
			break;
		case "Cancelar":
			cancelar();
			break;
		case "Guardar":
			guardar();
			break;
		case "Agregar":
			agregarItem();
			break;
		case "Quitar":
			quitarItem();
			break;
		case "Imprimir":
			imprimir(pedido);
			break;
		default:
			break;
		}
	}

	@Override
	public void setProducto(Producto producto) {
		this.producto = producto;
		gestionarProducto();
	}

	private void gestionarProducto() {
		ventana.getlProducto().setText(producto.getDescripcion());
		agregarItem();
		EventosUtil.estadosBotones(ventana.getBtnAgregar(), true);
		EventosUtil.estadosBotones(ventana.getBtnGuardar(), true);
	}

	private void abrirBuscadorProducto() {
		BuscadorProducto buscador = new BuscadorProducto();
		buscador.setUpControlador(false);
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
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
	}

	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		if (EventosUtil.liberarAcceso(Sesion.getInstance().getColaborador(), buscador.modulo, "BUSCAR")) {
			buscador.setUpControlador();
			buscador.getControlador().setInterfaz(this);
			buscador.setVisible(true);
		}
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

		// No se puede modificar el cliente de un pedido generado
		EventosUtil.estadosBotones(ventana.getBtnBuscarCliente(), false);
		EventosUtil.estadosBotones(ventana.getBtnAgregar(), true);
		EventosUtil.estadosBotones(ventana.getBtnAnular(), true);
		EventosUtil.estadosBotones(ventana.getBtnBuscarProducto(), true);
		EventosUtil.estadosBotones(ventana.getBtnGuardar(), true);

		// Datos del cliente
		cliente = pedido.getCliente();
		ventana.getlCliente().setText(cliente.getNombreCompleto());
		ventana.getlContacto().setText(cliente.getContacto());
		ventana.getlIdentificacion().setText(cliente.getIdentificacion());
		ventana.getlMetrosFechaEmision().setText(EventosUtil.separadorDecimales(pedido.getMetrosFechaEmision()));

		// Datos del pedido
		ventana.getlEstadoPedido().setText(EventosUtil.verificarEstado(pedido.isEstado()));
		ventana.getlGanancia().setText(EventosUtil.separadorMiles((double) pedido.getGananciaTotal()));
		ventana.getlMetrosPedido().setText(EventosUtil.separadorDecimales(pedido.getMetrosTotal()));
		ventana.getlNroPedido().setText((EventosUtil.separadorMiles((double) pedido.getId())));
		ventana.getlSumatoria().setText((EventosUtil.separadorMiles((double) pedido.getSumatoriaPrecio())));
		ventana.getLstTipoPago().setSelectedValue(pedido.getTipoPagoPedido(), true);
		ventana.getlTotalPagar().setText((EventosUtil.separadorMiles((double) pedido.getPrecioPagar())));
		ventana.gettDescuentoPedido().setValue((double) pedido.getDescuentoTotal());
		ventana.getRbConsiderarMetraje().setSelected(pedido.isConsiderarMetraje());
		ventana.getRbGenerarPresupuesto().setSelected(pedido.isEsPresupuesto());

		// Detalles del pedido
		items = pedido.getPedidosConfecciones();
		modeloTabla.setDetalle(items);
		modeloTabla.fireTableDataChanged();

		accion = "MODIFICAR";
	}
}
