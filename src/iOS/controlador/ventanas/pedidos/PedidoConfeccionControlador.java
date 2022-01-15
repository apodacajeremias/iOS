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

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
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
import iOS.modelo.entidades.Representante;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.componentes.CeldaRenderer;
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

	private PedidoDetalleMaterial material;
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
		ventana.getCbRepresentantes().addActionListener(this);

		ventana.getTable().addMouseListener(this);
		ventana.getTable().addPropertyChangeListener(this);
		ventana.getTableMaterial().addMouseListener(this);
		ventana.getTableMaterial().addPropertyChangeListener(this);

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
		EventosUtil.limpiarCampoPersonalizado(ventana.gettResponsable());
		accion = null;
		cliente = null;
		detalle = null;
		producto = null;
		vaciarTablaDetalle();
		vaciarTablaMaterial();
	}

	private void formatoTabla() {
		ventana.getTable().getColumnModel().getColumn(0).setPreferredWidth(200);
		ventana.getTable().getColumnModel().getColumn(1).setPreferredWidth(200);

		ventana.getTableMaterial().getColumnModel().getColumn(1).setPreferredWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(1).setMaxWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(1).setMinWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(1).setResizable(false);

		ventana.getTableMaterial().getColumnModel().getColumn(2).setPreferredWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(2).setMaxWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(2).setMinWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(2).setResizable(false);

		ventana.getTableMaterial().getColumnModel().getColumn(3).setPreferredWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(3).setMaxWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(3).setMinWidth(0);
		ventana.getTableMaterial().getColumnModel().getColumn(3).setResizable(false);
	}

	private void crearComboBoxTabla() {
		// Combo y valores
		JComboBox<String> comboBoxMolde = new JComboBox<String>();
		JComboBox<String> comboBoxTamano = new JComboBox<String>();

		comboBoxMolde.addItem("MASCULINO");
		comboBoxMolde.addItem("FEMENINO");
		comboBoxMolde.addItem("INFANTIL");

		comboBoxTamano.addItem("PP");
		comboBoxTamano.addItem("P");
		comboBoxTamano.addItem("M");
		comboBoxTamano.addItem("G");
		comboBoxTamano.addItem("GG");
		comboBoxTamano.addItem("GGG");

		ventana.getTable().getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(comboBoxTamano));
		ventana.getTable().setDefaultRenderer(String.class, new CeldaRenderer(3, "ComboBox"));

		ventana.getTable().getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(comboBoxMolde));
		ventana.getTable().setDefaultRenderer(String.class, new CeldaRenderer(4, "ComboBox"));

		// se agrega model al JTable
//		transaccionCompra.getTable().setRowHeight(22);// altura de filas
		// se indica que columna tendra el JComboBox
//		transaccionCompra.getTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
//		transaccionCompra.getTable().setDefaultRenderer(Object.class, new CeldaRenderer(1, "ComboBox"));
	}

	private void vaciarTablaDetalle() {
		detalles = new ArrayList<PedidoDetalleConfeccion>();
		mtPedidoDetalle.setDetalle(detalles);
		System.out.println("vaciarTablaDetalle");
	}

	private void vaciarTablaMaterial() {
		materiales = new ArrayList<PedidoDetalleMaterial>();
		mtDetalleMaterial.setMateriales(materiales);
		System.out.println("vaciarTablaMaterial");
		System.out.println("size " + materiales.size());
	}

	private void agregarDetalle(Producto p) {
		if (p == null) {
			return;
		}
		if (p.isProductoCarteleria()) {
			JOptionPane.showMessageDialog(ventana, "Este producto es de carteleria.");
			return;
		}
		if (producto.getTipoCobro().equalsIgnoreCase("METRO CUADRADO")) {
			JOptionPane.showMessageDialog(ventana, "Este producto es por METRO CUADRADO, no se agregará el producto.");
			return;
		}
		if (!limitarItems(detalles)) {
			return;
		}
		try {
			detalle = new PedidoDetalleConfeccion();
			detalle.setArchivo("Sin indicaciones.");
			detalle.setCantidadDetalle(1);
			detalle.setColaborador(Sesion.getInstance().getColaborador());
			detalle.setCosto(0);
			detalle.setFechaModificado(new Date());
			detalle.setFechaUltimoRegistroProduccion(new Date());
			detalle.setGananciaDetalle(0);
			detalle.setMolde("MASCULINO");
			detalle.setPorcentajeSobreCosto(50);
			detalle.setPrecioDetalle(0);
			detalle.setPrecioProducto(0);
			detalle.setProduccionFinalizada(false);
			detalle.setProducto(p);
			detalle.setTamano("P");
			ajustarDetalle(detalle);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void ajustarDetalle(PedidoDetalleConfeccion detalle) {
		producto = detalle.getProducto();

		asociarMaterial(detalle);

		detalle.setCosto(costoProducto(detalle));
		detalle.setPorcentajeSobreCosto(detalle.getProducto().getPorcentajeSobreCosto());
		detalle.setPrecioDetalle(precioDetalle(detalle));
		detalle.setPrecioProducto(precioProducto(detalle));

		// Para la tabla que se muestra
		crearComboBoxTabla();
		detalles.add(detalle);
		mtPedidoDetalle.setDetalle(detalles);
		ventana.getBtnBuscarProducto().requestFocus();

		// Metodos referentes
		realizarCalculos();
	}

	private double calcularArea(double alto, double ancho) {
		double area = 0;
		try {
			area = (alto * ancho) / 10000;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return area;
	}

	// Se hace el calculo de costo del detalle recibido
	private double costoProducto(PedidoDetalleConfeccion detalle) {
		double costoUnidad = 0;
		double costoMetroC = 0;
		double costoMetroL = 0;
		double costoTotal = 0;
		try {
			costoUnidad = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("UNIDAD") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroC = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO CUADRADO") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroL = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO LINEAL") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoTotal = costoUnidad + costoMetroC + costoMetroL;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return costoTotal;
	}

	private double precioProducto(PedidoDetalleConfeccion detalle) {
		double costoUnidad = 0;
		double costoMetroC = 0;
		double costoMetroL = 0;
		double precioProducto = 0;
		double porcentajeSobreCosto = 0;
		try {
			porcentajeSobreCosto = (detalle.getProducto().getPorcentajeSobreCosto() + 100) / 100;
			costoUnidad = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("UNIDAD") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroC = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO CUADRADO") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroL = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO LINEAL") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoUnidad = (costoUnidad * porcentajeSobreCosto);
			costoMetroC = (costoMetroC * porcentajeSobreCosto);
			costoMetroL = (costoMetroL * porcentajeSobreCosto);
			precioProducto = costoUnidad + costoMetroC + costoMetroL;
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("precioProducto");
		System.out.println("costoUnidad " + costoUnidad);
		System.out.println("costoMetroC " + costoMetroC);
		System.out.println("costoMetroL " + costoMetroL);
		System.out.println("precioProducto " + precioProducto);
		return precioProducto;
	}

	private double precioDetalle(PedidoDetalleConfeccion detalle) {
		double costoUnidad = 0;
		double costoMetroC = 0;
		double costoMetroL = 0;
		double porcentajeSobreCosto = 0;
		double precioDetalle = 0;
		try {
			porcentajeSobreCosto = (detalle.getProducto().getPorcentajeSobreCosto() + 100) / 100;

			costoUnidad = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("UNIDAD") == true)
					.mapToDouble(m -> m.getSubtotal() * m.getMedidaDetalle()).sum());
			costoMetroC = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO CUADRADO") == true)
					.mapToDouble(m -> m.getSubtotal() * m.getMedidaDetalle()).sum());
			costoMetroL = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO LINEAL") == true)
					.mapToDouble(m -> m.getSubtotal() * m.getMedidaDetalle()).sum());

			costoUnidad = (costoUnidad * porcentajeSobreCosto) * detalle.getCantidadDetalle();
			costoMetroC = (costoMetroC * porcentajeSobreCosto) * detalle.getCantidadDetalle();
			costoMetroL = (costoMetroL * porcentajeSobreCosto) * detalle.getCantidadDetalle();
			precioDetalle = costoUnidad + costoMetroC + costoMetroL;
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("precioDetalle");
		System.out.println("costoUnidad " + costoUnidad);
		System.out.println("costoMetroC " + costoMetroC);
		System.out.println("costoMetroL " + costoMetroL);
		System.out.println("precioDetalle " + precioDetalle);
		return precioDetalle;
	}

	private void quitarDetalle(int posicion) {
		if (posicion < 0) {
			return;
		}

		int opcion = JOptionPane.showConfirmDialog(null, "¿Retirar item?", "Confirmar", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (opcion == JOptionPane.OK_OPTION) {
			try {
				if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
					// Si la deuda = valor - pagos > al valor del detalle a retirar
					if ((detalles.get(posicion).getPedido().getPrecioPagar()
							- detalles.get(posicion).getPedido().getSumaPagos()) <= detalles.get(posicion)
									.getPrecioDetalle()) {
						JOptionPane.showMessageDialog(ventana,
								"El valor del item retirado supera el valor de la deuda pendiente de este pedido.");
						return;
					}
				} else if ((!EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(),
						"ADMINISTRADOR"))) {
					if (detalles.get(posicion).getPedido().getSumaPagos() > 0) {
						JOptionPane.showMessageDialog(ventana,
								"No se puede modificar ningun pago que cuente con algun pago, solicite a un administrador");
						return;
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

//			System.out.println("SumaPagos " + detalles.get(posicion).getPedido().getPrecioPagar());
//			System.out.println("PrecioPagar " + detalles.get(posicion).getPedido().getSumaPagos());
//			System.out.println("PrecioPagar - SumaPagos " + (detalles.get(posicion).getPedido().getPrecioPagar()
//					- detalles.get(posicion).getPedido().getSumaPagos()));
//			System.out.println("ValorItem " + detalles.get(posicion).getPrecioDetalle());

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
			detalle = null;
			return;
		}

		vaciarTablaMaterial();

		detalle = detalles.get(posicion);
		materiales = detalle.getMateriales();

		if (detalle.getMateriales() != null) {
			materiales = detalle.getMateriales();
		} else {
			materiales = new ArrayList<PedidoDetalleMaterial>();
		}
		mtDetalleMaterial.setMateriales(materiales);

		System.out.println("materiales " + materiales.size());

	}

	private void asociarMaterial(PedidoDetalleConfeccion detalle) {
		if (detalle == null) {
			return;
		}

		for (int i = 0; i < detalle.getProducto().getMateriales().size(); i++) {
			material = new PedidoDetalleMaterial();
			material.setCantidadDetalle(detalle.getProducto().getMateriales().get(i).getCantidad());
			material.setColaborador(Sesion.getInstance().getColaborador());
			material.setCosto(detalle.getProducto().getMateriales().get(i).getCosto());
			material.setDetalleCarteleria(null);
			material.setDetalleConfeccion(detalle);
			material.setMaterial(detalle.getProducto().getMateriales().get(i));
			material.setMedidaAlto(detalle.getProducto().getMateriales().get(i).getAlto());
			material.setMedidaAncho(detalle.getProducto().getMateriales().get(i).getAncho());
			material.setMedidaDetalle(calcularArea(detalle.getProducto().getMateriales().get(i).getAlto(),
					detalle.getProducto().getMateriales().get(i).getAncho()));
			material.setSubtotal(detalle.getProducto().getMateriales().get(i).getSubtotal());
			materiales.add(material);
		}

		detalle.setMateriales(materiales);

		mtDetalleMaterial.setMateriales(materiales);
	}

	private void agregarMaterial(PedidoDetalleConfeccion detalle, Material m) {
		if (detalle == null) {
			return;
		}

		for (int i = 0; i < detalle.getProducto().getMateriales().size(); i++) {
			material = new PedidoDetalleMaterial();
			material.setCantidadDetalle(1);
			material.setColaborador(Sesion.getInstance().getColaborador());
			material.setCosto(m.getCosto());
			material.setDetalleCarteleria(null);
			material.setDetalleConfeccion(detalle);
			material.setMaterial(detalle.getProducto().getMateriales().get(i));
			material.setMedidaAlto(detalle.getProducto().getMateriales().get(i).getAlto());
			material.setMedidaAlto(detalle.getProducto().getMateriales().get(i).getAncho());
			material.setMedidaDetalle(detalle.getProducto().getMateriales().get(i).getAncho());
			material.setSubtotal(detalle.getProducto().getMateriales().get(i).getSubtotal());
			materiales.add(material);
		}

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
		detalle.setMateriales(materiales);
		realizarCalculos();
	}

	private void seleccionarMaterial(int posicion) {
		// TODO Auto-generated method stub
		if (posicion < 0) {
			return;
		}
		material = materiales.get(posicion);

		System.out.println(material);

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
		if ((Representante) ventana.getCbRepresentantes().getSelectedItem() == null) {
			JOptionPane.showMessageDialog(ventana,
					"Debe haber un representante para el pedido, actualice la informacion del cliente");
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
			pedido.setConsiderarMetraje(false);
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
		pedido.setConDetalle(ventana.getRbDetallado().isSelected());
		pedido.setMetrosTotal(0d);
		pedido.setTipoPagoPedido(ventana.getRbContado().isSelected() ? "CONTADO" : "CREDITO");
		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));

		pedido.setPedidosConfecciones(detalles);
		pedido.setPedidoDetalles(null);

		try {
			pedido.setRepresentante((Representante) ventana.getCbRepresentantes().getSelectedItem());
			pedido.setInformacionResponsable(pedido.getRepresentante().getCliente().getNombreCompleto() + ", "
					+ pedido.getRepresentante().getCliente().getContacto());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < detalles.size(); i++) {
			detalles.get(i).setPedido(pedido);
			detalles.get(i).setFechaModificado(new Date());
			for (int j = 0; j < detalles.get(i).getMateriales().size(); j++) {
				detalles.get(i).getMateriales().get(j).setDetalleCarteleria(null);
				detalles.get(i).getMateriales().get(j).setDetalleConfeccion(detalles.get(i));
			}
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
		case "comboBoxChanged":
			ventana.gettResponsable().setText(null);
			try {
				Representante r = (Representante) ventana.getCbRepresentantes().getSelectedItem();
				String s = r.getRepresentante().getNombreCompleto() + ", " + r.getRepresentante().getContacto();
				System.out.println(s);
				ventana.gettResponsable().setText(s);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
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
		buscador.setTitle(buscador.getTitle().concat(": CONFECCION"));
		buscador.getControlador().recuperarTodo(false, true);
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
		vaciarTablaMaterial();
		ventana.getlProducto().setText(producto.getDescripcion());
		agregarDetalle(producto);
	}

	@Override
	public void setRepresentante(Cliente representate) {
		// TODO Auto-generated method stub

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

		List<Representante> representantes = new ArrayList<Representante>();
		try {
			representantes = cliente.getRepresentantes();
			for (int i = 0; i < representantes.size(); i++) {
				ventana.getCbRepresentantes().addItem(representantes.get(i));
			}
			try {
				ventana.gettResponsable().setText(representantes.get(0).getCliente().getNombreCompleto() + ", "
						+ representantes.get(0).getCliente().getContacto());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (Exception e) {
			ventana.getCbRepresentantes().addItem(null);
			e.printStackTrace();
		}
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

		ventana.getlProduccion().setText(pedido.isProduccionFinalizada() ? "NO FINALIZADO" : "CONCLUIDO");
		ventana.getCbRepresentantes().getModel().setSelectedItem(pedido.getRepresentante());
		
		ventana.getRbDetallado().setSelected(pedido.isConDetalle());
		ventana.getRbGenerarPresupuesto().setSelected(pedido.isEsPresupuesto());

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
		if (m == null) {
			return;
		}
		vaciarTablaMaterial();
		agregarMaterial(detalle, m);
	}
}
