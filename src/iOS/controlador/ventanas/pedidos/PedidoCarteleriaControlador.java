package iOS.controlador.ventanas.pedidos;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.PedidoDao;
import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Imagen;
import iOS.modelo.entidades.Material;
import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalleMaterial;
import iOS.modelo.entidades.PedidoDetalles;
import iOS.modelo.entidades.Producto;
import iOS.modelo.entidades.Representante;
import iOS.modelo.interfaces.ClienteInterface;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.interfaces.PedidoInterface;
import iOS.modelo.interfaces.ProductoInterface;
import iOS.modelo.singleton.Metodos;
import iOS.modelo.singleton.Sesion;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalle;
import iOS.vista.modelotabla.ModeloTablaPedidoDetalleMaterial;
import iOS.vista.ventanas.buscadores.BuscadorCliente;
import iOS.vista.ventanas.buscadores.BuscadorMaterial;
import iOS.vista.ventanas.buscadores.BuscadorProducto;
import iOS.vista.ventanas.pedidos.TransaccionPedido;

public class PedidoCarteleriaControlador implements ActionListener, MouseListener, KeyListener, PedidoInterface,
		ClienteInterface, ProductoInterface, PropertyChangeListener, MaterialInterface {
	private TransaccionPedido ventana;
	private ModeloTablaPedidoDetalle mtPedidoDetalle;
	private ModeloTablaPedidoDetalleMaterial mtDetalleMaterial;
	private PedidoDao dao;
	private Pedido pedido;
	private Producto producto;
	private PedidoDetalles detalle;
	private List<PedidoDetalles> detalles = new ArrayList<PedidoDetalles>();

	private PedidoDetalleMaterial material;
	private List<PedidoDetalleMaterial> materiales = new ArrayList<PedidoDetalleMaterial>();

	private Imagen imagen;
	private List<Imagen> imagenes = new ArrayList<Imagen>();

	private String accion;

	private Cliente cliente;

	private byte[] bytesIMG;
	private File archivo;
	private JFileChooser selectorArchivos = new JFileChooser();
	private FileFilter png = new FileNameExtensionFilter("PNG", "png");
	private FileFilter jpg = new FileNameExtensionFilter("JPG", "jpg");
	private FileFilter jpeg = new FileNameExtensionFilter("JPEG", "jpeg");

	public PedidoCarteleriaControlador(TransaccionPedido ventana) {
		this.ventana = ventana;
		mtPedidoDetalle = new ModeloTablaPedidoDetalle();
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
		ventana.getBtnAgregarImagen().addActionListener(this);
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
		EventosUtil.limpiarCampoPersonalizado(ventana.getlProduccion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlImagenes());
		
		ventana.getlImagenes().setText("Agregar Imagen");
		
		accion = null;
		cliente = null;
		detalle = null;
		producto = null;
		vaciarTablaDetalle();
		vaciarTablaMaterial();
		vaciarImagenes();
	}

	private void formatoTabla() {
		ventana.getTable().getColumnModel().getColumn(0).setPreferredWidth(200);
		ventana.getTable().getColumnModel().getColumn(1).setPreferredWidth(200);
	}

	private void vaciarTablaDetalle() {
		detalles = new ArrayList<PedidoDetalles>();
		mtPedidoDetalle.setDetalle(detalles);
	}

	private void vaciarImagenes() {
		imagenes = new ArrayList<Imagen>();
	}

	private void vaciarTablaMaterial() {
		materiales = new ArrayList<PedidoDetalleMaterial>();
		mtDetalleMaterial.setMateriales(materiales);
	}

	private void abrirBuscadorImagenes() {
		selectorArchivos.setFileFilter(png);
		selectorArchivos.setFileFilter(jpg);
		selectorArchivos.setFileFilter(jpeg);
		if (selectorArchivos.showDialog(ventana, "Seleccionar") == JFileChooser.APPROVE_OPTION) {
			archivo = selectorArchivos.getSelectedFile();
			if (archivo.canRead()) {
				if (archivo.getName().endsWith("png") || archivo.getName().endsWith("jpg")
						|| archivo.getName().endsWith("jpeg")) {
					agregarImagen(archivo);
				} else {
					JOptionPane.showMessageDialog(ventana, "Seleccione una imagen");
				}
			}
		}
	}

	private void agregarImagen(File archivo) {
		bytesIMG = EventosUtil.entradaImagen(archivo);
		imagen = new Imagen();
		imagen.setImagen(bytesIMG);
		imagen.setColaborador(Sesion.getInstance().getColaborador());
//		imagen.setImagen(bytesIMG);
		imagen.setNombreImagen(archivo.getName());
		imagen.setRutaImagen(archivo.getAbsolutePath());
		imagenes.add(imagen);
		ventana.getlImagenes().setText(imagenes.size() + " imagen/es");
		System.out.println(bytesIMG);
	}

	private void quitarImagen(int posicion) {
		if (posicion < 0) {
			return;
		}
		imagenes.remove(posicion);
		ventana.getlImagenes().setText(imagenes.size() + " imagen/es");
	}

//	private void guardarImagen(File archivo, byte[] bytesIMG) throws IOException {
//		File directorio = new File("/sistema/backup_imagenes");
//		if (!directorio.exists()) {
//			if (directorio.mkdirs()) {
//				System.out.println("Directorio creado");
//			} else {
//				System.out.println("Error al crear directorio");
//			}
//		}
//		System.out.println(directorio.getPath());
//		EventosUtil.salidaImagen(archivo, bytesIMG);
//
//	}

	// Se agregar el producto al detalle pero con los valores por defecto
	private void agregarDetalle(Producto p) {
		if (p == null) {
			return;
		}
		if (p.isProductoCostura()) {
			Toolkit.getDefaultToolkit().beep();
			// JOptionPane.showMessageDialog(ventana, "Este producto es de confeccion.");
			return;
		}
		if (!limitarItems(detalles)) {
			return;
		}

		ventana.getlProducto().setText(producto.getDescripcion());

		try {
			detalle = new PedidoDetalles();
			detalle.setArchivo("Sin indicaciones");
			detalle.setCantidadDetalle(1);
			detalle.setColaborador(Sesion.getInstance().getColaborador());
			detalle.setFechaModificado(new Date());
			detalle.setFechaUltimoRegistroProduccion(new Date());
			detalle.setGananciaDetalle(0);
			detalle.setMedidaAlto(100);
			detalle.setMedidaAncho(100);
			detalle.setMedidaDetalle(1);
			detalle.setPorcentajeSobreCosto(0);
			detalle.setCosto(0);
			detalle.setPrecioDetalle(0);
			detalle.setPrecioProducto(0);
			detalle.setProduccionFinalizada(false);
			detalle.setProducto(p);
			ajustarDetalle(detalle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Se recibe un detalle y se hace el formateo
	private void ajustarDetalle(PedidoDetalles detalle) {
		producto = detalle.getProducto();
		// Medidas fijas
		if (producto.isTieneMedidaFija()) {
			detalle.setMedidaAlto(producto.getAltoProducto());
			detalle.setMedidaAncho(producto.getAnchoProducto());
			detalle.setMedidaDetalle(calcularArea(detalle.getMedidaAlto(), detalle.getMedidaAncho()));
		}

		asociarMaterial(detalle);

		if (detalle.getMateriales().size() <= 0) {
			detalle.setCosto(0);
			detalle.setPorcentajeSobreCosto(0);
			detalle.setPrecioDetalle(detalle.getProducto().getPrecioMaximo());
			detalle.setPrecioProducto(detalle.getProducto().getPrecioMaximo());
		} else {
			detalle.setCosto(costoProducto(detalle));
			detalle.setPorcentajeSobreCosto(detalle.getProducto().getPorcentajeSobreCosto());
			detalle.setPrecioDetalle(precioDetalle(detalle));
			detalle.setPrecioProducto(precioProducto(detalle));
		}

		// Para la tabla que se muestra
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
	private double costoProducto(PedidoDetalles detalle) {
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

	private double precioProducto(PedidoDetalles detalle) {
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

	private double precioDetalle(PedidoDetalles detalle) {
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
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroC = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO CUADRADO") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());
			costoMetroL = (detalle.getMateriales().stream()
					.filter(m -> m.isEstado() == true
							&& m.getMaterial().getMaterial().getTipoCobro().equalsIgnoreCase("METRO LINEAL") == true)
					.mapToDouble(m -> m.getSubtotal()).sum());

			costoUnidad = (costoUnidad * porcentajeSobreCosto) * detalle.getCantidadDetalle();
			costoMetroC = (costoMetroC * porcentajeSobreCosto) * detalle.getCantidadDetalle()
					* detalle.getMedidaDetalle();
			costoMetroL = (costoMetroL * porcentajeSobreCosto) * detalle.getCantidadDetalle()
					* detalle.getMedidaDetalle();
			precioDetalle = costoUnidad + costoMetroC + costoMetroL;
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("precioDetalle");
		System.out.println("costoUnidad " + costoUnidad);
		System.out.println("costoMetroC " + costoMetroC);
		System.out.println("costoMetroL " + costoMetroL);
		System.out.println("precioDetalle " + precioDetalle);

		if (precioDetalle < 45000) {
			precioDetalle = 45000;
		}
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
//		materiales = detalle.getMateriales();

		if (detalle.getMateriales() != null) {
			materiales = detalle.getMateriales();
		} else {
			materiales = new ArrayList<PedidoDetalleMaterial>();
		}
		mtDetalleMaterial.setMateriales(materiales);
		System.out.println("materiales " + materiales.size());
	}

	private void asociarMaterial(PedidoDetalles detalle) {
		if (detalle == null) {
			return;
		}

		for (int i = 0; i < detalle.getProducto().getMateriales().size(); i++) {
			material = new PedidoDetalleMaterial();
			material.setCantidadDetalle(detalle.getProducto().getMateriales().get(i).getCantidad());
			material.setColaborador(Sesion.getInstance().getColaborador());
			material.setCosto(detalle.getProducto().getMateriales().get(i).getCosto());
			material.setDetalleCarteleria(detalle);
			material.setDetalleConfeccion(null);
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

	private void agregarMaterial(PedidoDetalles detalle, Material m) {
		if (detalle == null) {
			return;
		}

		for (int i = 0; i < detalle.getProducto().getMateriales().size(); i++) {
			material = new PedidoDetalleMaterial();
			material.setCantidadDetalle(1);
			material.setColaborador(Sesion.getInstance().getColaborador());
			material.setCosto(m.getCosto());
			material.setDetalleCarteleria(detalle);
			material.setDetalleConfeccion(null);
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

	private List<Double> valorarPedido() {
		double suma = 0;
		double diferencia = 0;
		List<Double> numeros = new ArrayList<>();
		double descuento = ventana.gettDescuentoPedido().getValue();

		try {
			suma = detalles.stream().filter(o -> o.isEstado() == true).mapToDouble(o -> o.getPrecioDetalle()).sum();
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

	// private void valorarDetalle() {
	// if (detalle == null) {
	// return;
	// }
	// double costo = 0;
	// for (int i = 0; i < detalle.getMateriales().size(); i++) {
	// switch (detalle.getMateriales().get(i).getMaterial().getTipoCobro()) {
	// case "UNIDAD":
	// costo += detalle.getMateriales().get(i).getMaterial().getPrecioMaximo();
	// case "METRO CUADRADO":
	// costo +=
	// detalle.getMateriales().get(i).getMaterial().getPrecioMaximo()*detalle.getMedidaDetalle();
	// default:
	// break;
	// }
	// }
	//
	// detalle.setPrecioProducto((int) ((int)
	// costo*detalle.getProducto().getPorcentajeSobreCosto()));
	// detalle.setPrecioDetalle((int) ((int)
	// (costo*detalle.getProducto().getPorcentajeSobreCosto())*detalle.getMedidaDetalle()));
	// mtPedidoDetalle.fireTableDataChanged();
	// }

	private void realizarCalculos() {
		sumarMedidaDetalle();
		valorarPedido();
		// valorarDetalle();
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
		ventana.getlVendedor().setText(Sesion.getInstance().getColaborador().toString());
		ventana.getlPedido1().setText("PEDIDO NUEVO");
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
			pedido.setPedidoCostura(false);
			pedido.setPedidoCarteleria(true);
			pedido.setCliente(cliente);
		}

		pedido.setDescuentoTotal(Integer.parseInt(ventana.gettDescuentoPedido().getText()));
		pedido.setEsPresupuesto(ventana.getRbGenerarPresupuesto().isSelected());
		pedido.setConDetalle(ventana.getRbDetallado().isSelected());
		pedido.setMetrosTotal(sumarMedidaDetalle());
		pedido.setTipoPagoPedido(ventana.getRbContado().isSelected() ? "CONTADO" : "CREDITO");
		pedido.setSumatoriaPrecio(valorarPedido().get(0));
		pedido.setPrecioPagar(valorarPedido().get(1));
		pedido.setPedidosConfecciones(null);
		pedido.setPedidoDetalles(detalles);

		try {
			pedido.setRepresentante((Representante) ventana.getCbRepresentantes().getSelectedItem());
			pedido.setInformacionResponsable(ventana.gettResponsable().getText());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int i = 0; i < detalles.size(); i++) {
			detalles.get(i).setPedido(pedido);
			detalles.get(i).setFechaModificado(new Date());
			for (int j = 0; j < detalles.get(i).getMateriales().size(); j++) {
				detalles.get(i).getMateriales().get(j).setDetalleCarteleria(detalles.get(i));
				detalles.get(i).getMateriales().get(j).setDetalleConfeccion(null);
			}
		}

		for (int i = 0; i < imagenes.size(); i++) {
			imagenes.get(i).setPedido(pedido);
		}
		pedido.setImagenes(imagenes);

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(pedido);

			} else {
				dao.modificar(pedido);
			}
			dao.commit();
			modificar();
			setPedido(pedido);
			Metodos.getInstance().imprimirPedidoCarteleriaIndividual(pedido);
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

		if (e.getSource() == ventana.getTableMaterial()) {
			try {
				detalle.setCosto(costoProducto(detalle));
				detalle.setPrecioProducto(precioProducto(detalle));
				detalle.setPrecioDetalle(precioDetalle(detalle));
				mtPedidoDetalle.fireTableDataChanged();
				mtDetalleMaterial.fireTableDataChanged();
				realizarCalculos();
			} catch (Exception e1) {
				System.err.println("propertyChange");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "AgregarImagen":
			abrirBuscadorImagenes();
			break;
		case "comboBoxChanged":
			ventana.gettResponsable().setText(null);
			try {
				Representante r = (Representante) ventana.getCbRepresentantes().getSelectedItem();
				String s = r.getRepresentante().getNombreCompleto() + ", " + r.getRepresentante().getContacto();
				System.out.println(s);
				ventana.gettResponsable().setText(s);
			} catch (Exception e1) {
				System.err.println("comboBoxChanged");
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
		buscador.setTitle(buscador.getTitle().concat(": CARTELERIA"));
		buscador.getControlador().setInterfaz(this);
		buscador.getControlador().recuperarTodo(false, false);
		buscador.setLocationRelativeTo(ventana);
		buscador.setVisible(true);
	}

	private void abrirBuscadorMaterial() {
		if (detalle == null) {
			return;
		}
		BuscadorMaterial buscador = new BuscadorMaterial();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setLocationRelativeTo(ventana);
		buscador.setVisible(true);
	}

	private void abrirBuscadorCliente() {
		BuscadorCliente buscador = new BuscadorCliente();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setLocationRelativeTo(ventana);
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
		ventana.getCbRepresentantes().removeAllItems();
		try {
			representantes = cliente.getRepresentantes();
			for (int i = 0; i < representantes.size(); i++) {
				ventana.getCbRepresentantes().addItem(representantes.get(i));
			}
			try {
				ventana.gettResponsable().setText(representantes.get(0).getRepresentante().getNombreCompleto() + ", "
						+ representantes.get(0).getRepresentante().getContacto());
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
		ventana.getlProduccion().setText(!pedido.isProduccionFinalizada() ? "NO FINALIZADO" : "CONCLUIDO");
		ventana.getlImagenes().setText(pedido.getImagenes().size() + " imagen/es");

		imagenes = pedido.getImagenes();
		try {
			ventana.getCbRepresentantes().getModel().setSelectedItem(pedido.getRepresentante());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Detalles del pedido
		detalles = pedido.getPedidoDetalles();
		mtPedidoDetalle.setDetalle(detalles);
		mtPedidoDetalle.fireTableDataChanged();
	}

	@Override
	public void setMaterial(Material m) {
		if (m == null) {
			return;
		}
		vaciarTablaMaterial();
		agregarMaterial(detalle, m);
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

}
