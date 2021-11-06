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
import javax.swing.DefaultComboBoxModel;
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

public class TransaccionCompraControlador implements ActionListener, MouseListener, KeyListener, CompraInterface, ProveedorInterface, MaterialInterface, PropertyChangeListener, MarcaInterface {
	private TransaccionCompra transaccionCompra;
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
		this.transaccionCompra = transaccionCompra;
		this.mtCompraDetalle = new ModeloTablaCompraDetalle();
		this.transaccionCompra.getTable().setModel(mtCompraDetalle);

		dao = new CompraDao();
		daoMarca = new MarcaDao();

		estadoInicial(true);
		setUpEvents();
		nuevo();

		recuperarMarcas();
	}


	private void estadoInicial(boolean b) {
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlContacto());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlFechaHora());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlIdentificacion());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlMaterial());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlNroCompra());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlProveedor());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlValorFactura());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getlValorPago());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.gettNroFactura());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.gettNroNTCR());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.gettValorNTCR());
		EventosUtil.limpiarCampoPersonalizado(transaccionCompra.getCbInformacionPago());
		EventosUtil.estadosCampoPersonalizado(transaccionCompra.getPanelCompra(), false);

		transaccionCompra.gettValorNTCR().setText("0");
		transaccionCompra.getlValorFactura().setText("0");
		transaccionCompra.getlValorPago().setText("0");
		transaccionCompra.gettNroFactura().setText("N/A");
		transaccionCompra.gettNroNTCR().setText("N/A");
		transaccionCompra.getlFechaHora().setText(sdf.format(new Date()));

		detalles = new ArrayList<>();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();

		//Botones que se mantienen disponibles si o si
		EventosUtil.estadosBotones(transaccionCompra.getBtnBuscarProveedor(), true);
		EventosUtil.estadosBotones(transaccionCompra.getBtnSalir(), true);

		//Cuando setProveedor, se activa
		EventosUtil.estadosBotones(transaccionCompra.getBtnBuscarMaterial(), false);

		//Cuando setMaterial, se activa
		EventosUtil.estadosBotones(transaccionCompra.getBtnAgregar(), false);

		//Si se selecciona un detalle
		//		EventosUtil.estadosBotones(transaccionCompra.getBtnAnular(), false);


		//Si es presupuesto
		EventosUtil.estadosBotones(transaccionCompra.getBtnGuardar(), false);

		transaccionCompra.getBtnBuscarProveedor().requestFocus();

	}


	private void setUpEvents() {
		//MOUSE LISTENER

		//ACTION LISTENER
		this.transaccionCompra.getBtnBuscarProveedor().addActionListener(this);
		this.transaccionCompra.getBtnBuscarMaterial().addActionListener(this);
		this.transaccionCompra.getBtnGuardar().addActionListener(this);

		//KEY LISTENER

		//PROPERTY CHANGES
		this.transaccionCompra.getTable().addPropertyChangeListener(this);
		this.transaccionCompra.gettValorNTCR().addPropertyChangeListener(this);
	}

	private String limitarDouble(double valor) {
		String v = "";
		DecimalFormat df = new DecimalFormat("#.###");

		//se redondea el valor recibido 
		//Math.ceil(valor)

		//luego se acorta
		v = df.format(valor);

		return v;

	}


	private void agregarItem() {
		if (material == null) {
			return;
		}	

		//Nueva fila
		detalle = new CompraDetalle();
		detalle.setMaterial(material);
		detalle.setMarca(marca);



		//Medidas
		detalle.setMedidaAlto(100);
		detalle.setMedidaAncho(100);
		detalle.setMedidaDetalle(1d);


		//Detalle sobre el material
		detalle.setCantidadDetalle(1);
		detalle.setCostoMaterial(1000);



		//Minorista
		detalle.setPrecioMinorista(1500);
		detalle.setPorcentajeMinorista(50d);


		//Mayorista
		detalle.setPrecioMayorista5(1350);
		detalle.setPrecioMayorista10(1350);
		detalle.setPrecioMayorista50(1350);
		detalle.setPrecioMayorista100(1350);
		detalle.setPrecioMayorista200(1350);
		detalle.setPrecioMayorista300(1350);
		detalle.setPorcentajeMayorista(35d);

		//Se añade a la tabla
		detalles.add(detalle);
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();

		transaccionCompra.getBtnBuscarMaterial().requestFocus();
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

			//Se obttienen las medidas para calcular el area
			double alto = detalles.get(i).getMedidaAlto();
			double ancho = detalles.get(i).getMedidaAncho();
			int cantidad = detalles.get(i).getCantidadDetalle();

			//calculo de area
			double area = alto*ancho;		

			//Como las medidas son en cm pasamos a M2			
			double metros = (area/10000)*cantidad;

			//y pasamos los metros m2 resultantes limitandos los decimales a dos digitos
			String metro = limitarDouble(metros);

			transaccionCompra.getTable().setValueAt(metro, i, 5);
			mtCompraDetalle.fireTableDataChanged();
		}
	}

	private void totalCompra() {
		total = 0;
		dif = 0;
		descuento = Integer.parseInt(transaccionCompra.gettValorNTCR().getText());
		for (int i = 0; i < detalles.size(); i++) {
			total += (detalles.get(i).getCostoMaterial() * detalles.get(i).getCantidadDetalle());
		}
		dif = total - descuento;
		transaccionCompra.getlValorFactura().setText(formatter.format(total));
		transaccionCompra.getlValorPago().setText(formatter.format(dif));
	}

	private boolean validarFormulario(){
		if (proveedor == null) {
			transaccionCompra.getBtnBuscarProveedor().requestFocus();
			return false;
		}
		if (detalles == null) {
			transaccionCompra.getBtnBuscarMaterial().requestFocus();
			return false;
		}
		if (Integer.parseInt(transaccionCompra.gettValorNTCR().getText()) > 0 && transaccionCompra.gettNroNTCR().getText().isEmpty()) {
			transaccionCompra.gettNroNTCR().requestFocus();
			return false;
		}

		if (transaccionCompra.gettNroFactura().getText().isEmpty()) {
			transaccionCompra.gettNroFactura().requestFocus();
			return false;
		}
		if (transaccionCompra.gettNroNTCR().getText().isEmpty()) {
			transaccionCompra.gettNroNTCR().requestFocus();
			return false;
		}
		if (transaccionCompra.gettValorNTCR().getText().isEmpty()) {
			transaccionCompra.gettValorNTCR().requestFocus();
			return false;
		}
		if (transaccionCompra.getCbInformacionPago().getSelectedIndex() == 0) {
			transaccionCompra.getCbInformacionPago().requestFocus();
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
		compra.setNroFactura(transaccionCompra.gettNroFactura().getText());
		compra.setNroNTCR(transaccionCompra.gettNroNTCR().getText());

		informacionPago = (InformacionPago) transaccionCompra.getCbInformacionPago().getSelectedItem();
		compra.setInformacionPago(informacionPago);

		for (int i = 0; i < detalles.size(); i++) {
			detalles.get(i).setCompra(compra);
		}

		compra.setCompraDetalles(detalles);

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
		if (evt.getSource() == transaccionCompra.getTable()){
			calcularMedidaDetalle();
			totalCompra();
		}
		if (evt.getSource() == transaccionCompra.gettValorNTCR()) {
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
		if (e.getSource() == transaccionCompra.getTable()) {
			obtenerColumna(transaccionCompra.getTable().getSelectedRow());
			obtenerFila(transaccionCompra.getTable().getSelectedColumn());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void gestionarProveedor() {
		transaccionCompra.getCbInformacionPago().setModel(new DefaultComboBoxModel(new String[] {"Seleccionar"}));
		transaccionCompra.getCbInformacionPago().setSelectedIndex(0);

		transaccionCompra.getlProveedor().setText(proveedor.getNombreCompleto());
		transaccionCompra.getlIdentificacion().setText(proveedor.getIdentificacion());
		transaccionCompra.getlContacto().setText(proveedor.getListaContactos().toString());

		for (int i = 0; i < proveedor.getInformacionesPago().size(); i++) {
			transaccionCompra.getCbInformacionPago().addItem(proveedor.getInformacionesPago().get(i));
		}

		EventosUtil.estadosCampoPersonalizado(transaccionCompra.getPanelCompra(), true);
		EventosUtil.estadosBotones(transaccionCompra.getBtnBuscarMaterial(), true);
	}

	private void abrirBuscadorProveedor() {
		BuscadorProveedor buscador = new BuscadorProveedor();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
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
		transaccionCompra.getlMaterial().setText(material.getDescripcion());
		EventosUtil.estadosBotones(transaccionCompra.getBtnAgregar(), true);
		EventosUtil.estadosBotones(transaccionCompra.getBtnGuardar(), true);
	}

	private void abrirBuscadorMaterial() {
		BuscadorMaterial buscador = new BuscadorMaterial();
		buscador.setUpControlador();
		buscador.getControlador().setInterfaz(this);
		buscador.setVisible(true);
	}

	//	private void abrirBuscadorMarca() {
	//		BuscadorMarca buscador = new BuscadorMarca();
	//		buscador.setUpControlador();
	//		buscador.getControlador().setInterfaz(this);
	//		buscador.setVisible(true);
	//	}


	private void crearTablaCombo() {
		//Combo y valores
		JComboBox<Marca> comboBox = new JComboBox<Marca>();

		try {
			for (int i = 0; i < marcas.size(); i++) {
				comboBox.addItem(marcas.get(i));
			}
		} catch (Exception e) {
			comboBox.addItem(null);
			e.printStackTrace();
		}

		//se agrega model al JTable
		transaccionCompra.getTable().setRowHeight(22);//altura de filas
		//se indica que columna tendra el JComboBox
		transaccionCompra.getTable().getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(comboBox));        
		transaccionCompra.getTable().setDefaultRenderer(Object.class, new CeldaRenderer(1, "ComboBox"));
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
		//Informacion de la compra
		transaccionCompra.getlNroCompra().setText(String.valueOf(compra.getId()));
		transaccionCompra.getlFechaHora().setText(sdf.format(compra.getFechaRegistroCompra()));
		transaccionCompra.getDtchFechaRealCompra().setDate(compra.getFechaCompra());
		transaccionCompra.getlValorFactura().setText(formatter.format(compra.getValorCompra()));
		transaccionCompra.getlValorPago().setText(formatter.format(compra.getValorPago()));
		transaccionCompra.gettValorNTCR().setText(formatter.format(compra.getValorNTCR()));
		transaccionCompra.gettNroFactura().setText(compra.getNroFactura());
		transaccionCompra.gettNroNTCR().setText(compra.getNroNTCR());


		//Informacion proveedor
		transaccionCompra.getlProveedor().setText(compra.getProveedor().getNombreCompleto());
		transaccionCompra.getlIdentificacion().setText(compra.getProveedor().getIdentificacion());
		transaccionCompra.getlContacto().setText(compra.getProveedor().getListaContactos().get(0).getNumeroTelefono());

		//Detalles de la compra
		detalles = compra.getCompraDetalles();
		mtCompraDetalle.setDetalle(detalles);
		mtCompraDetalle.fireTableDataChanged();
	}


	@Override
	public void setMarca(Marca marca) {
		this.marca = marca;

	}

}