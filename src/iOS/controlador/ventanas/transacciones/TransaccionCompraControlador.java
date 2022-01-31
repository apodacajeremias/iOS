package iOS.controlador.ventanas.transacciones;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.dao.CompraDao;
import iOS.modelo.dao.MarcaDao;
import iOS.modelo.entidades.Compra;
import iOS.modelo.entidades.CompraDetalle;
import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.entidades.Marca;
import iOS.modelo.entidades.Material;
import iOS.modelo.entidades.Proveedor;
import iOS.modelo.entidades.Sector;
import iOS.modelo.interfaces.CompraInterface;
import iOS.modelo.interfaces.MarcaInterface;
import iOS.modelo.interfaces.MaterialInterface;
import iOS.modelo.interfaces.ProveedorInterface;
import iOS.modelo.singleton.Sesion;
import iOS.vista.componentes.CeldaRenderer;
import iOS.vista.modelotabla.ModeloTablaCompraDetalle;
import iOS.vista.ventanas.buscadores.BuscadorMaterial;
import iOS.vista.ventanas.buscadores.BuscadorProveedor;
import iOS.vista.ventanas.transacciones.TransaccionCompra;

public class TransaccionCompraControlador implements ActionListener, MouseListener, KeyListener, CompraInterface,
		ProveedorInterface, MaterialInterface, PropertyChangeListener, MarcaInterface {
	private TransaccionCompra ventana;
	private ModeloTablaCompraDetalle mtCompraDetalle;
	private CompraDao dao;
	private Compra compra;
	private Proveedor proveedor;
	private List<CompraDetalle> detalles = new ArrayList<CompraDetalle>();
	private Material material;
	private CompraDetalle detalle;
	private MarcaDao daoMarca;
	private List<Marca> marcas = new ArrayList<Marca>();
	private Marca marca;
	private InformacionPago informacionPago;

	private String accion;
	SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
	DecimalFormat formatter = new DecimalFormat("#,###.##");
	private int total;
	private int dif;
	private int descuento;

	public TransaccionCompraControlador(TransaccionCompra transaccionCompra) {
		this.ventana = transaccionCompra;
		this.mtCompraDetalle = new ModeloTablaCompraDetalle();
		this.ventana.getTable().setModel(mtCompraDetalle);

		dao = new CompraDao();
		daoMarca = new MarcaDao();

		estadoInicial(true);
		setUpEvents();
		nuevo();

		recuperarMarcas();

		cargarSectores();
	}

	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(ventana.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlFechaHora());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlMaterial());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlNroCompra());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlProveedor());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlValorFactura());
		EventosUtil.limpiarCampoPersonalizado(ventana.getlValorPago());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNroFactura());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettNroNTCR());
		EventosUtil.limpiarCampoPersonalizado(ventana.gettValorNTCR());
		EventosUtil.limpiarCampoPersonalizado(ventana.getCbInformacionPago());
		EventosUtil.estadosCampoPersonalizado(ventana.getPanelCompra(), false);

		ventana.gettValorNTCR().setText("0");
		ventana.getlValorFactura().setText("0");
		ventana.getlValorPago().setText("0");
		ventana.gettNroFactura().setText("N/A");
		ventana.gettNroNTCR().setText("N/A");
		ventana.getlFechaHora().setText(sdf.format(new Date()));

		detalles = new ArrayList<>();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();

		// Botones que se mantienen disponibles si o si
		EventosUtil.estadosBotones(ventana.getBtnBuscarProveedor(), true);
		EventosUtil.estadosBotones(ventana.getBtnSalir(), true);

		// Cuando setProveedor, se activa
		EventosUtil.estadosBotones(ventana.getBtnBuscarMaterial(), false);

		// Cuando setMaterial, se activa
		EventosUtil.estadosBotones(ventana.getBtnAgregar(), false);

		// Si se selecciona un detalle
		// EventosUtil.estadosBotones(transaccionCompra.getBtnAnular(), false);

		// Si es presupuesto
		EventosUtil.estadosBotones(ventana.getBtnGuardar(), false);

		ventana.getBtnBuscarProveedor().requestFocus();

	}

	private void setUpEvents() {
		// MOUSE LISTENER

		// ACTION LISTENER
		this.ventana.getBtnBuscarProveedor().addActionListener(this);
		this.ventana.getBtnBuscarMaterial().addActionListener(this);
		this.ventana.getBtnGuardar().addActionListener(this);

		// KEY LISTENER

		// PROPERTY CHANGES
		this.ventana.getTable().addPropertyChangeListener(this);
		this.ventana.gettValorNTCR().addPropertyChangeListener(this);
	}

	private void cargarSectores() {
		ventana.getCbSector().removeAllItems();
		ventana.getCbSector().addItem(null);
		for (int i = 0; i < Sesion.getInstance().getSectores().size(); i++) {
			ventana.getCbSector().addItem(Sesion.getInstance().getSectores().get(i));
		}
	}

	private void cargarInformacionPago(List<InformacionPago> info) {
		ventana.getCbInformacionPago().removeAllItems();
		ventana.getCbInformacionPago().addItem(null);
		for (int i = 0; i < info.size(); i++) {
			ventana.getCbInformacionPago().addItem(info.get(i));
		}

	}

	private String limitarDouble(double valor) {
		String v = "";
		DecimalFormat df = new DecimalFormat("#.###");

		// se redondea el valor recibido
		// Math.ceil(valor)

		// luego se acorta
		v = df.format(valor);
		return v;
	}

	private void agregarItem() {
		if (material == null) {
			return;
		}

		// Nueva fila
		detalle = new CompraDetalle();
		detalle.setColaborador(Sesion.getInstance().getColaborador());
		detalle.setMaterial(material);
		detalle.setMarca(marca);

		// Medidas
		detalle.setMedidaAlto(100);
		detalle.setMedidaAncho(100);
		detalle.setMedidaDetalle(1d);

		// Detalle sobre el material
		detalle.setCantidadDetalle(1);
		detalle.setCostoMaterial(1000);

		// Minorista
		detalle.setPrecioMinorista(1500);
		detalle.setPorcentajeMinorista(50d);

		// Mayorista
		detalle.setPrecioMayorista5(1350);
		detalle.setPrecioMayorista10(1350);
		detalle.setPrecioMayorista50(1350);
		detalle.setPrecioMayorista100(1350);
		detalle.setPrecioMayorista200(1350);
		detalle.setPrecioMayorista300(1350);
		detalle.setPorcentajeMayorista(35d);

		// Se añade a la tabla
		detalles.add(detalle);
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();

		ventana.getBtnBuscarMaterial().requestFocus();
	}

	private int obtenerFila(int posicion) {
		if (posicion < 0) {
			posicion = -1;
		}
		return posicion;
	}

	private int obtenerColumna(int posicion) {
		if (posicion < 0) {
			posicion = -1;
		}
		return posicion;
	}

	private void calcularMedidaDetalle() {
		for (int i = 0; i < detalles.size(); i++) {

			// Se obttienen las medidas para calcular el area
			double alto = detalles.get(i).getMedidaAlto();
			double ancho = detalles.get(i).getMedidaAncho();
			int cantidad = detalles.get(i).getCantidadDetalle();

			// calculo de area
			double area = alto * ancho;

			// Como las medidas son en cm pasamos a M2
			double metros = (area / 10000) * cantidad;

			// y pasamos los metros m2 resultantes limitandos los decimales a dos digitos
			String metro = limitarDouble(metros);

			ventana.getTable().setValueAt(metro, i, 5);
			mtCompraDetalle.fireTableDataChanged();
		}
	}

	private void totalCompra() {
		total = 0;
		dif = 0;
		descuento = Integer.parseInt(ventana.gettValorNTCR().getText());
		for (int i = 0; i < detalles.size(); i++) {
			total += (detalles.get(i).getCostoMaterial() * detalles.get(i).getCantidadDetalle());
		}
		dif = total - descuento;
		ventana.getlValorFactura().setText(formatter.format(total));
		ventana.getlValorPago().setText(formatter.format(dif));
	}

	private boolean validarFormulario() {
		if (proveedor == null) {
			ventana.getBtnBuscarProveedor().requestFocus();
			return false;
		}
		if (detalles == null) {
			ventana.getBtnBuscarMaterial().requestFocus();
			return false;
		}
		if (Integer.parseInt(ventana.gettValorNTCR().getText()) > 0 && ventana.gettNroNTCR().getText().isEmpty()) {
			ventana.gettNroNTCR().requestFocus();
			return false;
		}

		if (ventana.gettNroFactura().getText().isEmpty()) {
			ventana.gettNroFactura().requestFocus();
			return false;
		}
		if (ventana.gettNroNTCR().getText().isEmpty()) {
			ventana.gettNroNTCR().requestFocus();
			return false;
		}
		if (ventana.gettValorNTCR().getText().isEmpty()) {
			ventana.gettValorNTCR().requestFocus();
			return false;
		}
		if (ventana.getCbInformacionPago().getSelectedIndex() == 0) {
			ventana.getCbInformacionPago().requestFocus();
			return false;
		}
		if (ventana.getCbSector().getSelectedIndex() == 0) {
			ventana.getCbSector().requestFocus();
			return false;
		}
		return true;
	}

	private void guardar() {
		if (!validarFormulario()) {
			return;
		}
		if (accion.equals("NUEVO")) {
			compra = new Compra();
			compra.setColaborador(Sesion.getInstance().getColaborador());
		}
		totalCompra();
		compra.setProveedor(proveedor);
		compra.setFechaRegistroCompra(new Date());
		compra.setValorCompra(total);
		compra.setValorNTCR(descuento);
		compra.setValorPago(dif);
		compra.setFechaCompra(new Date());
		compra.setNroFactura(ventana.gettNroFactura().getText());
		compra.setNroNTCR(ventana.gettNroNTCR().getText());

		informacionPago = (InformacionPago) ventana.getCbInformacionPago().getSelectedItem();
		compra.setInformacionPago(informacionPago);

		for (int i = 0; i < detalles.size(); i++) {
			detalles.get(i).setCompra(compra);
		}

		compra.setCompraDetalles(detalles);
		
		compra.setSector((Sector) ventana.getCbSector().getSelectedItem());

		try {
			if (accion.equals("NUEVO")) {
				dao.insertar(compra);
			} else {
				dao.modificar(compra);
			}
			dao.commit();
			estadoInicial(true);
		} catch (Exception e) {
			dao.rollBack();
			EventosUtil.formatException(e);
		}

	}

	public void nuevo() {
		accion = "NUEVO";
		estadoInicial(false);
	}

	public void modificar() {
		accion = "MODIFICAR";
		estadoInicial(false);
	}

	public void cancelar() {
		estadoInicial(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getSource() == ventana.getTable()) {
			calcularMedidaDetalle();
			totalCompra();
		}
		if (evt.getSource() == ventana.gettValorNTCR()) {
			totalCompra();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == ventana.getTable()) {
			obtenerColumna(ventana.getTable().getSelectedRow());
			obtenerFila(ventana.getTable().getSelectedColumn());
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

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "BuscarProveedor":
			abrirBuscadorProveedor();
			break;
		case "BuscarMaterial":
			abrirBuscadorMaterial();
			break;
		case "Guardar":
			guardar();
			break;
		default:
			break;
		}
	}

	@Override
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
		gestionarProveedor();
	}

	private void gestionarProveedor() {
		ventana.getlProveedor().setText(proveedor.getNombreCompleto());
		ventana.getlIdentificacion().setText(proveedor.getIdentificacion());
		ventana.getlContacto().setText(proveedor.getListaContactos().toString());

		cargarInformacionPago(proveedor.getInformacionesPago());

		EventosUtil.estadosCampoPersonalizado(ventana.getPanelCompra(), true);
		EventosUtil.estadosBotones(ventana.getBtnBuscarMaterial(), true);
	}

	private void abrirBuscadorProveedor() {
		BuscadorProveedor buscador = new BuscadorProveedor();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setLocationRelativeTo(ventana);
		buscador.setVisible(true);
	}

	@Override
	public void setMaterial(Material material) {
		this.material = material;
		gestionarMaterial();
		agregarItem();
		crearTablaCombo();
	}

	private void gestionarMaterial() {
		ventana.getlMaterial().setText(material.getDescripcion());
		EventosUtil.estadosBotones(ventana.getBtnAgregar(), true);
		EventosUtil.estadosBotones(ventana.getBtnGuardar(), true);
	}

	private void abrirBuscadorMaterial() {
		BuscadorMaterial buscador = new BuscadorMaterial();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setLocationRelativeTo(ventana);
		buscador.setVisible(true);
	}

	// private void abrirBuscadorMarca() {
	// BuscadorMarca buscador = new BuscadorMarca();
	// buscador.setUpControlador();
	// buscador.getControlador().setInterfaz(this);
	// buscador.setVisible(true);
	// }

	private void crearTablaCombo() {
		// Combo y valores
		JComboBox<Marca> comboBox = new JComboBox<Marca>();

		try {
			comboBox.addItem(null);
			for (int i = 0; i < marcas.size(); i++) {
				comboBox.addItem(marcas.get(i));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		// se agrega model al JTable
		ventana.getTable().setRowHeight(22);// altura de filas
		// se indica que columna tendra el JComboBox
		ventana.getTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));
		ventana.getTable().setDefaultRenderer(Object.class, new CeldaRenderer(1, "ComboBox"));
	}

	private void recuperarMarcas() {
		marcas = daoMarca.recuperarTodoOrdenadoPorNombre();
	}

	@Override
	public void setCompra(Compra compra) {
		this.compra = compra;

		gestionarCompra();
	}

	private void gestionarCompra() {
		// Informacion de la compra
		ventana.getlNroCompra().setText(String.valueOf(compra.getId()));
		ventana.getlFechaHora().setText(sdf.format(compra.getFechaRegistroCompra()));
		ventana.getDtchFechaRealCompra().setDate(compra.getFechaCompra());
		ventana.getlValorFactura().setText(formatter.format(compra.getValorCompra()));
		ventana.getlValorPago().setText(formatter.format(compra.getValorPago()));
		ventana.gettValorNTCR().setText(formatter.format(compra.getValorNTCR()));
		ventana.gettNroFactura().setText(compra.getNroFactura());
		ventana.gettNroNTCR().setText(compra.getNroNTCR());

		// Informacion proveedor
		setProveedor(compra.getProveedor());

		// CB
		ventana.getCbInformacionPago().getModel().setSelectedItem(compra.getInformacionPago());
		ventana.getCbSector().getModel().setSelectedItem(compra.getSector());

		// Detalles de la compra
		detalles = compra.getCompraDetalles();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();
	}

	@Override
	public void setMarca(Marca marca) {
		this.marca = marca;

	}

}