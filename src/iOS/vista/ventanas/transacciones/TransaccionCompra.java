package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import iOS.controlador.ventanas.transacciones.TransaccionCompraControlador;
import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.entidades.Sector;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionCompra extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7515380163909231472L;

	private JTable table;
	private MiBoton btnBuscarProveedor;
	private MiBoton btnBuscarMaterial;
	private MiBoton btnSalir;

	private TransaccionCompraControlador controlador;
	private MiBoton btnAgregar;
	private LabelPersonalizado lFechaHora;
	private LabelPersonalizado lNroCompra;
	private LabelPersonalizado lProveedor;
	private LabelPersonalizado lIdentificacion;
	private LabelPersonalizado lContacto;
	private LabelPersonalizado lMaterial;
	private LabelPersonalizado lValorFactura;
	private MiBoton btnGuardar;
	private JDateChooser dtchFechaRealCompra;
	private LabelPersonalizado lValorPago;
	private CampoNumeroPersonalizado tValorNTCR;
	private CampoTextoPersonalizado tNroFactura;
	private CampoTextoPersonalizado tNroNTCR;
	private JComboBox<InformacionPago> cbInformacionPago;
	private JPanel panelCompra;
	private JComboBox<Sector> cbSector;

	public void setUpControlador() {
		controlador = new TransaccionCompraControlador(this);
	}

	public TransaccionCompra() {
		setResizable(false);
		setType(Type.POPUP);
		setFont(new Font("Tahoma", Font.BOLD, 12));

		getContentPane().setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(this);
		setModal(true);
		setTitle("Registro de Compras");
		getContentPane().setLayout(null);

		JPanel panelDetalle = new JPanel();
		panelDetalle.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Detalles de Compra",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelDetalle.setBounds(425, 11, 839, 618);
		getContentPane().add(panelDetalle);
		panelDetalle.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 53, 819, 554);
		panelDetalle.add(scrollPane);

		table = new JTable();
		table.setBackground(new Color(204, 204, 204));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setToolTipText("Click para seleccionar");

		btnBuscarMaterial = new MiBoton("Buscar");
		btnBuscarMaterial.setText("Material");
		btnBuscarMaterial.setBounds(10, 14, 100, 30);
		panelDetalle.add(btnBuscarMaterial);
		btnBuscarMaterial.setActionCommand("BuscarMaterial");

		btnAgregar = new MiBoton("Nuevo");
		btnAgregar.setVisible(false);
		btnAgregar.setText("Repetir material");
		btnAgregar.setActionCommand("Agregar");
		btnAgregar.setBounds(699, 14, 130, 30);
		panelDetalle.add(btnAgregar);

		lMaterial = new LabelPersonalizado(0);
		lMaterial.setHorizontalAlignment(SwingConstants.RIGHT);
		lMaterial.setFont(new Font("Tahoma", Font.BOLD, 20));
		lMaterial.setBounds(119, 17, 600, 25);
		panelDetalle.add(lMaterial);

		btnSalir = new MiBoton("Salir");
		btnSalir.setText("Salir");
		btnSalir.setActionCommand("Salir");
		btnSalir.setBounds(1139, 640, 110, 30);
		getContentPane().add(btnSalir);

		JPanel panelProveedor = new JPanel();
		panelProveedor.setBorder(new TitledBorder(null, "Informaci\u00F3n del Proveedor", TitledBorder.LEFT,
				TitledBorder.TOP, null, null));
		panelProveedor.setBounds(15, 362, 400, 308);
		getContentPane().add(panelProveedor);
		panelProveedor.setLayout(null);

		btnBuscarProveedor = new MiBoton("...");
		btnBuscarProveedor.setText("...");
		btnBuscarProveedor.setBounds(342, 18, 50, 20);
		panelProveedor.add(btnBuscarProveedor);
		btnBuscarProveedor.setActionCommand("BuscarProveedor");

		lProveedor = new LabelPersonalizado(0);
		lProveedor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lProveedor.setBounds(6, 18, 330, 20);
		panelProveedor.add(lProveedor);

		LabelPersonalizado lblprsnlzdEmisinDelCompra_2_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelCompra_2_1.setText("Identificaci\u00F3n");
		lblprsnlzdEmisinDelCompra_2_1.setBounds(10, 45, 185, 15);
		panelProveedor.add(lblprsnlzdEmisinDelCompra_2_1);

		lIdentificacion = new LabelPersonalizado(0);
		lIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lIdentificacion.setBounds(10, 60, 185, 15);
		panelProveedor.add(lIdentificacion);

		LabelPersonalizado lblprsnlzdEmisinDelCompra_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelCompra_2_1_1.setText("Contacto");
		lblprsnlzdEmisinDelCompra_2_1_1.setBounds(205, 49, 185, 15);
		panelProveedor.add(lblprsnlzdEmisinDelCompra_2_1_1);

		lContacto = new LabelPersonalizado(0);
		lContacto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lContacto.setBounds(205, 64, 185, 15);
		panelProveedor.add(lContacto);

		panelCompra = new JPanel();
		panelCompra.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Informaci\u00F3n de Compra",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCompra.setBounds(15, 11, 400, 340);
		getContentPane().add(panelCompra);
		panelCompra.setLayout(null);

		lNroCompra = new LabelPersonalizado(0);
		lNroCompra.setFont(new Font("Tahoma", Font.BOLD, 15));
		lNroCompra.setBounds(10, 30, 185, 15);
		panelCompra.add(lNroCompra);

		LabelPersonalizado lblprsnlzdEmisinDelCompra_2 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelCompra_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblprsnlzdEmisinDelCompra_2.setText("Registro de Compra");
		lblprsnlzdEmisinDelCompra_2.setBounds(10, 55, 185, 15);
		panelCompra.add(lblprsnlzdEmisinDelCompra_2);

		lFechaHora = new LabelPersonalizado(0);
		lFechaHora.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lFechaHora.setBounds(9, 74, 185, 15);
		panelCompra.add(lFechaHora);

		LabelPersonalizado lblprsnlzdEmisinDelCompra_2_2 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelCompra_2_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdEmisinDelCompra_2_2.setText("Valor de Factura");
		lblprsnlzdEmisinDelCompra_2_2.setBounds(10, 242, 185, 15);
		panelCompra.add(lblprsnlzdEmisinDelCompra_2_2);

		lValorFactura = new LabelPersonalizado(0);
		lValorFactura.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lValorFactura.setBounds(10, 258, 185, 15);
		panelCompra.add(lValorFactura);

		LabelPersonalizado lblprsnlzdMetrosDelCompra = new LabelPersonalizado(0);
		lblprsnlzdMetrosDelCompra.setText("Valor NTCR");
		lblprsnlzdMetrosDelCompra.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdMetrosDelCompra.setBounds(10, 284, 185, 15);
		panelCompra.add(lblprsnlzdMetrosDelCompra);

		LabelPersonalizado lblprsnlzdEmisinDelCompra_2_3 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelCompra_2_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblprsnlzdEmisinDelCompra_2_3.setText("Fecha real de Compra");
		lblprsnlzdEmisinDelCompra_2_3.setBounds(10, 101, 185, 15);
		panelCompra.add(lblprsnlzdEmisinDelCompra_2_3);

		dtchFechaRealCompra = new JDateChooser();
		dtchFechaRealCompra.setBounds(10, 117, 185, 20);
		panelCompra.add(dtchFechaRealCompra);

		LabelPersonalizado lblprsnlzdValorPago = new LabelPersonalizado(0);
		lblprsnlzdValorPago.setText("Valor Pago");
		lblprsnlzdValorPago.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdValorPago.setBounds(10, 200, 185, 15);
		panelCompra.add(lblprsnlzdValorPago);

		lValorPago = new LabelPersonalizado(0);
		lValorPago.setFont(new Font("Tahoma", Font.BOLD, 15));
		lValorPago.setBounds(10, 216, 185, 15);
		panelCompra.add(lValorPago);

		tValorNTCR = new CampoNumeroPersonalizado();
		tValorNTCR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tValorNTCR.setBounds(10, 300, 185, 20);
		panelCompra.add(tValorNTCR);

		LabelPersonalizado lblprsnlzdFacturaDbito = new LabelPersonalizado(0);
		lblprsnlzdFacturaDbito.setText("Factura D\u00E9bito");
		lblprsnlzdFacturaDbito.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdFacturaDbito.setBounds(205, 15, 185, 15);
		panelCompra.add(lblprsnlzdFacturaDbito);

		tNroFactura = new CampoTextoPersonalizado();
		tNroFactura.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tNroFactura.setBounds(205, 31, 185, 20);
		panelCompra.add(tNroFactura);

		LabelPersonalizado lblprsnlzdMetrosDelCompra_2 = new LabelPersonalizado(0);
		lblprsnlzdMetrosDelCompra_2.setText("Nota de Cr\u00E9dito");
		lblprsnlzdMetrosDelCompra_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdMetrosDelCompra_2.setBounds(205, 57, 185, 15);
		panelCompra.add(lblprsnlzdMetrosDelCompra_2);

		tNroNTCR = new CampoTextoPersonalizado();
		tNroNTCR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tNroNTCR.setBounds(205, 73, 185, 20);
		panelCompra.add(tNroNTCR);

		LabelPersonalizado lblprsnlzdCompra_1 = new LabelPersonalizado(0);
		lblprsnlzdCompra_1.setText("ID Compra");
		lblprsnlzdCompra_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblprsnlzdCompra_1.setBounds(10, 15, 185, 15);
		panelCompra.add(lblprsnlzdCompra_1);

		LabelPersonalizado lblprsnlzdMetrosDelCompra_2_1 = new LabelPersonalizado(0);
		lblprsnlzdMetrosDelCompra_2_1.setText("Informaci\u00F3n de Pago");
		lblprsnlzdMetrosDelCompra_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdMetrosDelCompra_2_1.setBounds(205, 99, 185, 15);
		panelCompra.add(lblprsnlzdMetrosDelCompra_2_1);

		cbInformacionPago = new JComboBox<InformacionPago>();
		cbInformacionPago.setBounds(205, 115, 185, 20);
		panelCompra.add(cbInformacionPago);

		LabelPersonalizado lblprsnlzdMetrosDelCompra_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdMetrosDelCompra_2_1_1.setText("Sector de Compra");
		lblprsnlzdMetrosDelCompra_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdMetrosDelCompra_2_1_1.setBounds(10, 153, 185, 15);
		panelCompra.add(lblprsnlzdMetrosDelCompra_2_1_1);

		cbSector = new JComboBox<Sector>();
		cbSector.setBounds(10, 169, 185, 20);
		panelCompra.add(cbSector);

		btnGuardar = new MiBoton("Guardar");
		btnGuardar.setActionCommand("Guardar");
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(1029, 640, 100, 30);
		getContentPane().add(btnGuardar);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTable() {
		return table;
	}

	public MiBoton getBtnBuscarProveedor() {
		return btnBuscarProveedor;
	}

	public MiBoton getBtnBuscarMaterial() {
		return btnBuscarMaterial;
	}

	public MiBoton getBtnSalir() {
		return btnSalir;
	}

	public TransaccionCompraControlador getControlador() {
		return controlador;
	}

	public MiBoton getBtnAgregar() {
		return btnAgregar;
	}

	public LabelPersonalizado getlFechaHora() {
		return lFechaHora;
	}

	public LabelPersonalizado getlNroCompra() {
		return lNroCompra;
	}

	public LabelPersonalizado getlProveedor() {
		return lProveedor;
	}

	public LabelPersonalizado getlIdentificacion() {
		return lIdentificacion;
	}

	public LabelPersonalizado getlContacto() {
		return lContacto;
	}

	public LabelPersonalizado getlMaterial() {
		return lMaterial;
	}

	public LabelPersonalizado getlValorFactura() {
		return lValorFactura;
	}

	public MiBoton getBtnGuardar() {
		return btnGuardar;
	}

	public JDateChooser getDtchFechaRealCompra() {
		return dtchFechaRealCompra;
	}

	public LabelPersonalizado getlValorPago() {
		return lValorPago;
	}

	public CampoNumeroPersonalizado gettValorNTCR() {
		return tValorNTCR;
	}

	public CampoTextoPersonalizado gettNroFactura() {
		return tNroFactura;
	}

	public CampoTextoPersonalizado gettNroNTCR() {
		return tNroNTCR;
	}

	public JComboBox<InformacionPago> getCbInformacionPago() {
		return cbInformacionPago;
	}

	public JPanel getPanelCompra() {
		return panelCompra;
	}

	public JComboBox<Sector> getCbSector() {
		return cbSector;
	}

}
