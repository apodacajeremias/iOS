package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Representante;
import iOS.modelo.singleton.Sesion;

public class PedidoGenerico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7845171950774195159L;
	public String modulo = "PEDIDO";
	private JTable table;
	private MiBoton btnBuscarCliente;
	private MiBoton btnBuscarProducto;
	private MiBoton btnSalir;
	private JRadioButton rbGenerarPresupuesto;
	private CampoNumeroPersonalizado tDescuentoPedido;
	private MiBoton btnGuardar;
	private LabelPersonalizado lVendedor;
	private LabelPersonalizado lPedido2;
	private LabelPersonalizado lPedido1;
	private LabelPersonalizado lValorPagar;
	private LabelPersonalizado lSumatoria;
	private LabelPersonalizado lMetros;
	private JRadioButton rbContado;
	private JRadioButton rbCredito;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1_3;
	private LabelPersonalizado lDireccion;
	private LabelPersonalizado lCliente;
	private LabelPersonalizado lIdentificacion;
	private LabelPersonalizado lContacto;
	private LabelPersonalizado lProducto;
	private JTable tableMaterial;
	private MiBoton btnQuitar;
	private MiBoton btnAgregar;
	private CampoTextoPersonalizado tResponsable;
	private JRadioButton rbDetallado;
	private LabelPersonalizado lProduccion;
	private JComboBox<Representante> cbRepresentantes;
	private LabelPersonalizado lImagenes;
	private MiBoton btnAgregarImagen;

	public PedidoGenerico() {
		getContentPane().setEnabled(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		setType(Type.NORMAL);

		btnSalir = new MiBoton("Salir");
		btnSalir.setText("Salir");
		btnSalir.setActionCommand("Salir");
		btnSalir.setBounds(1154, 570, 100, 30);
		getContentPane().add(btnSalir);

		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new TitledBorder(null, "Informaci\u00F3n del Cliente", TitledBorder.LEFT,
				TitledBorder.TOP, null, null));
		panelCliente.setBounds(10, 355, 400, 245);
		getContentPane().add(panelCliente);
		panelCliente.setLayout(null);

		btnBuscarCliente = new MiBoton("...");
		btnBuscarCliente.setText("...");
		btnBuscarCliente.setBounds(340, 20, 50, 15);
		panelCliente.add(btnBuscarCliente);
		btnBuscarCliente.setActionCommand("BuscarCliente");

		lCliente = new LabelPersonalizado(0);
		lCliente.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lCliente.setFont(new Font("Tahoma", Font.BOLD, 15));
		lCliente.setBounds(20, 35, 350, 15);
		panelCliente.add(lCliente);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdEmisinDelPedido_2_1.setText("Documento");
		lblprsnlzdEmisinDelPedido_2_1.setBounds(20, 61, 100, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1);

		lIdentificacion = new LabelPersonalizado(0);
		lIdentificacion.setText("4563438");
		lIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lIdentificacion.setBounds(20, 76, 100, 15);
		panelCliente.add(lIdentificacion);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdEmisinDelPedido_2_1_1.setText("Contacto");
		lblprsnlzdEmisinDelPedido_2_1_1.setBounds(130, 61, 100, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1_1);

		lContacto = new LabelPersonalizado(0);
		lContacto.setText("0985300003");
		lContacto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lContacto.setBounds(130, 76, 100, 15);
		panelCliente.add(lContacto);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1_2 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1_2.setText("Nombre completo del cliente");
		lblprsnlzdEmisinDelPedido_2_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblprsnlzdEmisinDelPedido_2_1_2.setBounds(20, 20, 304, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1_2);

		lblprsnlzdEmisinDelPedido_2_1_3 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1_3.setText("Direccion");
		lblprsnlzdEmisinDelPedido_2_1_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdEmisinDelPedido_2_1_3.setBounds(240, 61, 100, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1_3);

		lDireccion = new LabelPersonalizado(0);
		lDireccion.setText("SDG");
		lDireccion.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lDireccion.setBounds(240, 76, 150, 15);
		panelCliente.add(lDireccion);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1_2_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1_2_1.setText("Informaci\u00F3n adicional sobre el representante");
		lblprsnlzdEmisinDelPedido_2_1_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblprsnlzdEmisinDelPedido_2_1_2_1.setBounds(20, 102, 350, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1_2_1);

		tResponsable = new CampoTextoPersonalizado();
		tResponsable.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tResponsable.setBounds(20, 155, 350, 25);
		panelCliente.add(tResponsable);

		cbRepresentantes = new JComboBox<Representante>();
		cbRepresentantes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cbRepresentantes.setBounds(20, 119, 350, 25);
		cbRepresentantes.setActionCommand("comboBoxChanged");
		panelCliente.add(cbRepresentantes);

		lImagenes = new LabelPersonalizado(0);
		lImagenes.setText("3 Imagenes");
		lImagenes.setFont(new Font("Tahoma", Font.BOLD, 18));
		lImagenes.setBounds(20, 191, 173, 20);
		panelCliente.add(lImagenes);

		btnAgregarImagen = new MiBoton((String) null);
		btnAgregarImagen.setActionCommand("AgregarImagen");
		btnAgregarImagen.setText("Agregar");
		btnAgregarImagen.setBounds(203, 191, 100, 20);
		panelCliente.add(btnAgregarImagen);
		if (EventosUtil.liberarAccesoSegunRol(Sesion.getInstance().getColaborador(), "ADMINISTRADOR")) {
			btnAgregarImagen.setEnabled(true);
		} else {
			btnAgregarImagen.setEnabled(false);
		}

		JPanel panelPedido = new JPanel();
		panelPedido.setBorder(new TitledBorder(null, "Informaci\u00F3n del Pedido", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelPedido.setBounds(10, 11, 400, 340);
		getContentPane().add(panelPedido);
		panelPedido.setLayout(null);

		lPedido1 = new LabelPersonalizado(0);
		lPedido1.setText("PEDIDO 1000 noviembre, 18 2021");
		lPedido1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lPedido1.setBounds(10, 89, 370, 15);
		panelPedido.add(lPedido1);

		lPedido2 = new LabelPersonalizado(0);
		lPedido2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lPedido2.setText("PEDIDO VIGENTE, GENERA DEUDA");
		lPedido2.setBounds(10, 109, 370, 15);
		panelPedido.add(lPedido2);

		LabelPersonalizado lblVendedor = new LabelPersonalizado(0);
		lblVendedor.setText("VENDEDOR");
		lblVendedor.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblVendedor.setBounds(10, 159, 370, 15);
		panelPedido.add(lblVendedor);

		rbGenerarPresupuesto = new JRadioButton("Presupuesto");
		rbGenerarPresupuesto.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbGenerarPresupuesto.setBounds(144, 20, 110, 20);
		panelPedido.add(rbGenerarPresupuesto);

		lVendedor = new LabelPersonalizado(0);
		lVendedor.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lVendedor.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lVendedor.setBounds(10, 179, 370, 15);
		panelPedido.add(lVendedor);

		LabelPersonalizado lblValorPagar = new LabelPersonalizado(0);
		lblValorPagar.setText("VALOR A PAGAR");
		lblValorPagar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValorPagar.setBounds(10, 209, 370, 15);
		panelPedido.add(lblValorPagar);

		lValorPagar = new LabelPersonalizado(0);
		lValorPagar.setText("350.000 Gs");
		lValorPagar.setFont(new Font("Tahoma", Font.BOLD, 15));
		lValorPagar.setBounds(10, 229, 370, 15);
		panelPedido.add(lValorPagar);

		LabelPersonalizado lblSumatoria = new LabelPersonalizado(0);
		lblSumatoria.setText("SUMATORIA");
		lblSumatoria.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSumatoria.setBounds(10, 249, 370, 15);
		panelPedido.add(lblSumatoria);

		lSumatoria = new LabelPersonalizado(20);
		lSumatoria.setBounds(10, 269, 370, 15);
		panelPedido.add(lSumatoria);
		lSumatoria.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lSumatoria.setText("350.000 Gs");

		LabelPersonalizado lblDescuento = new LabelPersonalizado(20);
		lblDescuento.setText("DESCUENTO");
		lblDescuento.setBounds(10, 289, 370, 15);
		panelPedido.add(lblDescuento);
		lblDescuento.setFont(new Font("Tahoma", Font.PLAIN, 15));

		lMetros = new LabelPersonalizado(20);
		lMetros.setText("METRAJE TOTAL: 3,5 m2");
		lMetros.setBounds(10, 129, 370, 15);
		panelPedido.add(lMetros);
		lMetros.setFont(new Font("Tahoma", Font.PLAIN, 15));

		tDescuentoPedido = new CampoNumeroPersonalizado();
		tDescuentoPedido.setBounds(10, 309, 185, 20);
		panelPedido.add(tDescuentoPedido);
		tDescuentoPedido.setFont(new Font("Tahoma", Font.PLAIN, 15));

		rbContado = new JRadioButton("Contado");
		rbContado.setSelected(true);
		buttonGroup.add(rbContado);
		rbContado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbContado.setBounds(17, 20, 110, 20);
		panelPedido.add(rbContado);

		rbCredito = new JRadioButton("Credito");
		buttonGroup.add(rbCredito);
		rbCredito.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbCredito.setBounds(17, 43, 110, 20);
		panelPedido.add(rbCredito);

		rbDetallado = new JRadioButton("Detallado");
		rbDetallado.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rbDetallado.setBounds(144, 43, 110, 23);
		panelPedido.add(rbDetallado);

		LabelPersonalizado lblprsnlzdProduccion = new LabelPersonalizado(0);
		lblprsnlzdProduccion.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblprsnlzdProduccion.setText("PRODUCCION");
		lblprsnlzdProduccion.setBounds(271, 20, 110, 20);
		panelPedido.add(lblprsnlzdProduccion);

		lProduccion = new LabelPersonalizado(0);
		lProduccion.setText("PRODUCCION");
		lProduccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lProduccion.setBounds(270, 43, 110, 23);
		panelPedido.add(lProduccion);

		btnGuardar = new MiBoton("Guardar");
		btnGuardar.setActionCommand("Guardar");
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(1044, 570, 100, 30);
		getContentPane().add(btnGuardar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(420, 52, 844, 250);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setToolTipText("Click para seleccionar");

		btnBuscarProducto = new MiBoton("Buscar");
		btnBuscarProducto.setBounds(430, 11, 100, 30);
		getContentPane().add(btnBuscarProducto);
		btnBuscarProducto.setText("Producto");
		btnBuscarProducto.setActionCommand("BuscarProducto");

		lProducto = new LabelPersonalizado(0);
		lProducto.setText("LONA IMPRESA");
		lProducto.setFont(new Font("Tahoma", Font.BOLD, 22));
		lProducto.setBounds(540, 11, 724, 30);
		getContentPane().add(lProducto);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setEnabled(false);
		scrollPane_1.setBounds(420, 354, 842, 205);
		getContentPane().add(scrollPane_1);

		tableMaterial = new JTable();
		scrollPane_1.setViewportView(tableMaterial);
		tableMaterial.setToolTipText("Click para seleccionar");
		tableMaterial.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableMaterial.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tableMaterial.setBackground(new Color(204, 204, 204));

		btnAgregar = new MiBoton("Nuevo");
		btnAgregar.setEnabled(false);
		btnAgregar.setText("Agregar");
		btnAgregar.setActionCommand("AgregarMaterial");
		btnAgregar.setBounds(430, 313, 100, 30);
		getContentPane().add(btnAgregar);

		LabelPersonalizado lblprsnlzdDetallesDelProducto = new LabelPersonalizado(0);
		lblprsnlzdDetallesDelProducto.setText("Detalles del producto seleccionado");
		lblprsnlzdDetallesDelProducto.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblprsnlzdDetallesDelProducto.setBounds(650, 313, 350, 30);
		getContentPane().add(lblprsnlzdDetallesDelProducto);

		btnQuitar = new MiBoton("Eliminar");
		btnQuitar.setEnabled(false);
		btnQuitar.setText("Quitar");
		btnQuitar.setActionCommand("QuitarMaterial");
		btnQuitar.setBounds(540, 313, 100, 30);
		getContentPane().add(btnQuitar);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public JTable getTable() {
		return table;
	}

	public MiBoton getBtnBuscarCliente() {
		return btnBuscarCliente;
	}

	public MiBoton getBtnBuscarProducto() {
		return btnBuscarProducto;
	}

	public MiBoton getBtnSalir() {
		return btnSalir;
	}

	public JRadioButton getRbGenerarPresupuesto() {
		return rbGenerarPresupuesto;
	}

	public CampoNumeroPersonalizado gettDescuentoPedido() {
		return tDescuentoPedido;
	}

	public MiBoton getBtnGuardar() {
		return btnGuardar;
	}

	public LabelPersonalizado getlVendedor() {
		return lVendedor;
	}

	public LabelPersonalizado getlPedido2() {
		return lPedido2;
	}

	public LabelPersonalizado getlPedido1() {
		return lPedido1;
	}

	public LabelPersonalizado getlValorPagar() {
		return lValorPagar;
	}

	public LabelPersonalizado getlSumatoria() {
		return lSumatoria;
	}

	public LabelPersonalizado getlMetros() {
		return lMetros;
	}

	public JRadioButton getRbContado() {
		return rbContado;
	}

	public JRadioButton getRbCredito() {
		return rbCredito;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public LabelPersonalizado getLblprsnlzdEmisinDelPedido_2_1_3() {
		return lblprsnlzdEmisinDelPedido_2_1_3;
	}

	public LabelPersonalizado getlDireccion() {
		return lDireccion;
	}

	public LabelPersonalizado getlCliente() {
		return lCliente;
	}

	public LabelPersonalizado getlIdentificacion() {
		return lIdentificacion;
	}

	public LabelPersonalizado getlContacto() {
		return lContacto;
	}

	public LabelPersonalizado getlProducto() {
		return lProducto;
	}

	public JTable getTableMaterial() {
		return tableMaterial;
	}

	public MiBoton getBtnQuitar() {
		return btnQuitar;
	}

	public MiBoton getBtnAgregar() {
		return btnAgregar;
	}

	public CampoTextoPersonalizado gettResponsable() {
		return tResponsable;
	}

	public JRadioButton getRbDetallado() {
		return rbDetallado;
	}

	public LabelPersonalizado getlProduccion() {
		return lProduccion;
	}

	public JComboBox<Representante> getCbRepresentantes() {
		return cbRepresentantes;
	}

	public LabelPersonalizado getlImagenes() {
		return lImagenes;
	}

	public MiBoton getBtnAgregarImagen() {
		return btnAgregarImagen;
	}

}
