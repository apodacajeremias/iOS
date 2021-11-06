package iOS.vista.componentes;



import java.awt.Color;
import java.awt.Font;

import javax.swing.AbstractListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

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


	private MiBoton btnAgregar;
	private LabelPersonalizado lFechaHora;
	private LabelPersonalizado lNroPedido;
	private JRadioButton rbConsiderarMetraje;
	private JRadioButton rbGenerarPresupuesto;
	private LabelPersonalizado lTotalPagar;
	private LabelPersonalizado lGanancia;
	private LabelPersonalizado lCliente;
	private LabelPersonalizado lIdentificacion;
	private LabelPersonalizado lContacto;
	private LabelPersonalizado lMetrosFechaEmision;
	private LabelPersonalizado lProducto;
	private CampoNumeroPersonalizado tDescuentoPedido;
	private LabelPersonalizado lEstadoPedido;
	private MiBoton btnGuardar;
	private LabelPersonalizado lSumatoria;
	private MiBoton btnAnular;
	private LabelPersonalizado lMetrosPedido;
	private JList<?> lstTipoPago;
	private LabelPersonalizado lblprsnlzdMetrosDelPedido;


	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "serial" })
	public PedidoGenerico() {
		getContentPane().setEnabled(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Pedidos");
		getContentPane().setLayout(null);
		setType(Type.NORMAL);

		btnSalir = new MiBoton("Salir");
		btnSalir.setText("Salir");
		btnSalir.setActionCommand("Salir");
		btnSalir.setBounds(1154, 570, 110, 30);
		getContentPane().add(btnSalir);

		JPanel panelCliente = new JPanel();
		panelCliente.setBorder(new TitledBorder(null, "Informaci\u00F3n del Cliente", TitledBorder.LEFT,
				TitledBorder.TOP, null, null));
		panelCliente.setBounds(620, 11, 634, 100);
		getContentPane().add(panelCliente);
		panelCliente.setLayout(null);

		btnBuscarCliente = new MiBoton("Buscar");
		btnBuscarCliente.setText("Cliente");
		btnBuscarCliente.setBounds(524, 11, 100, 30);
		panelCliente.add(btnBuscarCliente);
		btnBuscarCliente.setActionCommand("BuscarCliente");

		lCliente = new LabelPersonalizado(0);
		lCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lCliente.setBounds(10, 13, 500, 25);
		panelCliente.add(lCliente);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1.setText("Identificaci\u00F3n");
		lblprsnlzdEmisinDelPedido_2_1.setBounds(10, 40, 83, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1);

		lIdentificacion = new LabelPersonalizado(0);
		lIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lIdentificacion.setBounds(10, 55, 110, 15);
		panelCliente.add(lIdentificacion);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_1_1.setText("Contacto");
		lblprsnlzdEmisinDelPedido_2_1_1.setBounds(134, 40, 57, 15);
		panelCliente.add(lblprsnlzdEmisinDelPedido_2_1_1);

		lContacto = new LabelPersonalizado(0);
		lContacto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lContacto.setBounds(134, 55, 120, 15);
		panelCliente.add(lContacto);

		LabelPersonalizado lblMetros = new LabelPersonalizado(0);
		lblMetros.setVisible(false);
		lblMetros.setText("Metros < 30 D\u00EDas");
		lblMetros.setBounds(264, 40, 110, 15);
		panelCliente.add(lblMetros);

		lMetrosFechaEmision = new LabelPersonalizado(0);
		lMetrosFechaEmision.setVisible(false);
		lMetrosFechaEmision.setFont(new Font("Tahoma", Font.BOLD, 18));
		lMetrosFechaEmision.setBounds(264, 55, 120, 25);
		panelCliente.add(lMetrosFechaEmision);

		LabelPersonalizado lblprsnlzdaFechaDe_1_1 = new LabelPersonalizado(0);
		lblprsnlzdaFechaDe_1_1.setVisible(false);
		lblprsnlzdaFechaDe_1_1.setText("*A fecha de emisi\u00F3n");
		lblprsnlzdaFechaDe_1_1.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblprsnlzdaFechaDe_1_1.setBounds(264, 81, 120, 9);
		panelCliente.add(lblprsnlzdaFechaDe_1_1);

		JPanel panelPedido = new JPanel();
		panelPedido.setBorder(new TitledBorder(null, "Informaci\u00F3n del Pedido", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelPedido.setBounds(10, 11, 600, 100);
		getContentPane().add(panelPedido);
		panelPedido.setLayout(null);

		LabelPersonalizado lblprsnlzdPedido = new LabelPersonalizado(0);
		lblprsnlzdPedido.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdPedido.setText("Pedido");
		lblprsnlzdPedido.setBounds(20, 20, 76, 15);
		panelPedido.add(lblprsnlzdPedido);

		lNroPedido = new LabelPersonalizado(0);
		lNroPedido.setFont(new Font("Tahoma", Font.BOLD, 20));
		lNroPedido.setBounds(20, 39, 110, 20);
		panelPedido.add(lNroPedido);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2.setText("Emisi\u00F3n del Pedido");
		lblprsnlzdEmisinDelPedido_2.setBounds(20, 59, 114, 15);
		panelPedido.add(lblprsnlzdEmisinDelPedido_2);

		lFechaHora = new LabelPersonalizado(0);
		lFechaHora.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lFechaHora.setBounds(20, 75, 114, 15);
		panelPedido.add(lFechaHora);

		lstTipoPago = new JList();
		lstTipoPago.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lstTipoPago.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstTipoPago.setModel(new AbstractListModel() {
			String[] values = new String[] { "CONTADO", "CREDITO" };

			@Override
			public int getSize() {
				return values.length;
			}

			@Override
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		lstTipoPago.setSelectedIndex(0);
		lstTipoPago
				.setBorder(new TitledBorder(null, "Tipo de Pago", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		lstTipoPago.setBounds(166, 17, 100, 73);
		panelPedido.add(lstTipoPago);

		rbConsiderarMetraje = new JRadioButton("\u00BFConsiderar metraje?");
		rbConsiderarMetraje.setEnabled(false);
		rbConsiderarMetraje.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbConsiderarMetraje.setBounds(294, 17, 139, 20);
		panelPedido.add(rbConsiderarMetraje);

		rbGenerarPresupuesto = new JRadioButton("Generar Presupuesto");
		rbGenerarPresupuesto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbGenerarPresupuesto.setBounds(294, 56, 140, 20);
		panelPedido.add(rbGenerarPresupuesto);

		LabelPersonalizado lblprsnlzdEmisinDelPedido_2_2 = new LabelPersonalizado(0);
		lblprsnlzdEmisinDelPedido_2_2.setText("Estado del Pedido");
		lblprsnlzdEmisinDelPedido_2_2.setBounds(439, 17, 114, 15);
		panelPedido.add(lblprsnlzdEmisinDelPedido_2_2);

		lEstadoPedido = new LabelPersonalizado(0);
		lEstadoPedido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lEstadoPedido.setBounds(439, 33, 140, 15);
		panelPedido.add(lEstadoPedido);

		LabelPersonalizado lblprsnlzdNoSeEnvia = new LabelPersonalizado(0);
		lblprsnlzdNoSeEnvia.setText("*No se envia a producci\u00F3n");
		lblprsnlzdNoSeEnvia.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblprsnlzdNoSeEnvia.setBounds(304, 75, 121, 15);
		panelPedido.add(lblprsnlzdNoSeEnvia);

		lblprsnlzdMetrosDelPedido = new LabelPersonalizado(0);
		lblprsnlzdMetrosDelPedido.setText("Metros del Pedido");
		lblprsnlzdMetrosDelPedido.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblprsnlzdMetrosDelPedido.setBounds(440, 59, 121, 15);
		panelPedido.add(lblprsnlzdMetrosDelPedido);

		lMetrosPedido = new LabelPersonalizado(0);
		lMetrosPedido.setFont(new Font("Tahoma", Font.BOLD, 12));
		lMetrosPedido.setBounds(440, 75, 110, 15);
		panelPedido.add(lMetrosPedido);

		btnGuardar = new MiBoton("Guardar");
		btnGuardar.setActionCommand("Guardar");
		btnGuardar.setText("Guardar");
		btnGuardar.setBounds(970, 570, 100, 30);
		getContentPane().add(btnGuardar);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 168, 950, 432);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 15));
		table.setBackground(new Color(204, 204, 204));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(table);
		table.setToolTipText("Click para seleccionar");

		btnBuscarProducto = new MiBoton("Buscar");
		btnBuscarProducto.setBounds(10, 127, 100, 30);
		getContentPane().add(btnBuscarProducto);
		btnBuscarProducto.setText("Producto");
		btnBuscarProducto.setActionCommand("BuscarProducto");

		btnAgregar = new MiBoton("Nuevo");
		btnAgregar.setBounds(120, 127, 130, 30);
		getContentPane().add(btnAgregar);
		btnAgregar.setText("Repetir producto");
		btnAgregar.setActionCommand("Agregar");

		lProducto = new LabelPersonalizado(0);
		lProducto.setBounds(260, 130, 590, 25);
		getContentPane().add(lProducto);
		lProducto.setFont(new Font("Tahoma", Font.BOLD, 20));

		LabelPersonalizado lblprsnlzdTotalAPagar = new LabelPersonalizado(20);
		lblprsnlzdTotalAPagar.setHorizontalAlignment(SwingConstants.CENTER);
		lblprsnlzdTotalAPagar.setBounds(970, 168, 284, 25);
		getContentPane().add(lblprsnlzdTotalAPagar);
		lblprsnlzdTotalAPagar.setFont(new Font("Dialog", Font.BOLD, 20));
		lblprsnlzdTotalAPagar.setText("Total a Pagar");

		lTotalPagar = new LabelPersonalizado(20);
		lTotalPagar.setHorizontalAlignment(SwingConstants.CENTER);
		lTotalPagar.setFont(new Font("Dialog", Font.BOLD, 20));
		lTotalPagar.setBounds(970, 194, 284, 25);
		getContentPane().add(lTotalPagar);

		LabelPersonalizado lblprsnlzdGanancia = new LabelPersonalizado(20);
		lblprsnlzdGanancia.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblprsnlzdGanancia.setBounds(970, 346, 284, 25);
		getContentPane().add(lblprsnlzdGanancia);
		lblprsnlzdGanancia.setVisible(false);
		lblprsnlzdGanancia.setText("Ganancia");

		lGanancia = new LabelPersonalizado(20);
		lGanancia.setBounds(970, 368, 284, 25);
		getContentPane().add(lGanancia);
		lGanancia.setVisible(false);
		lGanancia.setFont(new Font("Dialog", Font.PLAIN, 20));

		LabelPersonalizado lblprsnlzdDescuento = new LabelPersonalizado(20);
		lblprsnlzdDescuento.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblprsnlzdDescuento.setBounds(970, 288, 284, 25);
		getContentPane().add(lblprsnlzdDescuento);
		lblprsnlzdDescuento.setText("Descuento");

		tDescuentoPedido = new CampoNumeroPersonalizado();
		tDescuentoPedido.setBounds(970, 310, 120, 25);
		getContentPane().add(tDescuentoPedido);
		tDescuentoPedido.setFont(new Font("Tahoma", Font.PLAIN, 12));

		LabelPersonalizado lblprsnlzdSumatoria = new LabelPersonalizado(20);
		lblprsnlzdSumatoria.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblprsnlzdSumatoria.setBounds(970, 230, 284, 25);
		getContentPane().add(lblprsnlzdSumatoria);
		lblprsnlzdSumatoria.setText("Sumatoria");

		lSumatoria = new LabelPersonalizado(20);
		lSumatoria.setBounds(970, 252, 284, 25);
		getContentPane().add(lSumatoria);
		lSumatoria.setFont(new Font("Dialog", Font.PLAIN, 20));

		btnAnular = new MiBoton("Eliminar");
		btnAnular.setBounds(860, 127, 100, 30);
		getContentPane().add(btnAnular);
		btnAnular.setText("Anular");
		btnAnular.setActionCommand("Quitar");
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
	public MiBoton getBtnAgregar() {
		return btnAgregar;
	}


	public LabelPersonalizado getlFechaHora() {
		return lFechaHora;
	}


	public LabelPersonalizado getlNroPedido() {
		return lNroPedido;
	}


	public JRadioButton getRbConsiderarMetraje() {
		return rbConsiderarMetraje;
	}


	public JRadioButton getRbGenerarPresupuesto() {
		return rbGenerarPresupuesto;
	}


	public LabelPersonalizado getlTotalPagar() {
		return lTotalPagar;
	}


	public LabelPersonalizado getlGanancia() {
		return lGanancia;
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


	public LabelPersonalizado getlMetrosFechaEmision() {
		return lMetrosFechaEmision;
	}


	public LabelPersonalizado getlProducto() {
		return lProducto;
	}


	public CampoNumeroPersonalizado gettDescuentoPedido() {
		return tDescuentoPedido;
	}


	public LabelPersonalizado getlEstadoPedido() {
		return lEstadoPedido;
	}


	public MiBoton getBtnGuardar() {
		return btnGuardar;
	}


	public LabelPersonalizado getlSumatoria() {
		return lSumatoria;
	}


	public MiBoton getBtnAnular() {
		return btnAnular;
	}


	public LabelPersonalizado getlMetrosPedido() {
		return lMetrosPedido;
	}


	public JList<?> getLstTipoPago() {
		return lstTipoPago;
	}

	public LabelPersonalizado getLblprsnlzdMetrosDelPedido() {
		return lblprsnlzdMetrosDelPedido;
	}
	
	
	
}
