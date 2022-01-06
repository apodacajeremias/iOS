package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.transacciones.TransaccionCajaControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CeldaRenderer;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.TextPrompt;

public class TransaccionCaja extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6998066400600940031L;
	public String modulo = "CAJA";
	private LabelPersonalizado lSaldoInicialGS;
	private LabelPersonalizado lSaldoInicialRS;
	private LabelPersonalizado lSaldoInicialUS;
	private JTable tableMovimientos;
	private MiBoton btnIngresarPago;
	private TransaccionCajaControlador controlador;
	private MiBoton btnEstadoCaja;
	private JPanel panelSInicial;
	private JPanel panelSIngreso;
	private LabelPersonalizado lSaldoIngresoGS;
	private LabelPersonalizado lSaldoIngresoRS;
	private LabelPersonalizado lSaldoIngresoUS;
	private JPanel panelSRetiro;
	private LabelPersonalizado lSaldoRetiroGS;
	private LabelPersonalizado lSaldoRetiroRS;
	private LabelPersonalizado lSaldoRetiroUS;
	private MiBoton btnRetirarVale;
	private MiBoton btnIngresarEntrega;
	private MiBoton btnRetirarGasto;
	private JRadioButton rdEfectivo;
	private JRadioButton rdTarjeta;
	private JRadioButton rdTransferencia;
	private JRadioButton rdCheque;
	private JRadioButton rdGiro;
	private LabelPersonalizado lDatosCriticos;
	private LabelPersonalizado lDatosCriticos2;
	private JRadioButton rdCliente;
	private JRadioButton rdColaborador;
	private MiBoton btnConfirmar;
	private MiBoton btnCancelar;
	private JRadioButton rdOtro;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JPanel panel_1;
	private JPanel panel_2;
	private LabelPersonalizado lDatosCriticos4;
	private LabelPersonalizado lDatosCriticos3;
	private JPanel panelValor1;
	private JTextArea tObservacion;
	private CampoNumeroPersonalizado tValorGs;
	private CampoNumeroPersonalizado tValorRs;
	private CampoNumeroPersonalizado tValorUs;
	private LabelPersonalizado lTitulo;
	private JPanel panelGeneral;
	private JPanel panelValor2;
	private CampoNumeroPersonalizado tSaldoEntregadoGS;
	private CampoNumeroPersonalizado tSaldoEntregadoRS;
	private CampoNumeroPersonalizado tSaldoEntregadoUS;
	private JPanel panelCotizacion;
	private LabelPersonalizado lCotizacionGS;
	private LabelPersonalizado lCotizacionRS;
	private LabelPersonalizado lCotizacionUS;

	public void setUpControlador() {
		controlador = new TransaccionCajaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionCaja() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Registro de Pagos");
		getContentPane().setLayout(null);
		setType(Type.NORMAL);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(20, 10, 200, 150);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		btnIngresarPago = new MiBoton("Guardar");
		btnIngresarPago.setBounds(0, 0, 200, 30);
		panel_2.add(btnIngresarPago);
		btnIngresarPago.callToAction();
		btnIngresarPago.setHorizontalAlignment(SwingConstants.LEFT);
		btnIngresarPago.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnIngresarPago.setActionCommand("IngresarPago");
		btnIngresarPago.setText("Ingresar Pago");

		btnRetirarVale = new MiBoton("Cancelar");
		btnRetirarVale.setBounds(0, 80, 200, 30);
		panel_2.add(btnRetirarVale);
		btnRetirarVale.setHorizontalAlignment(SwingConstants.LEFT);
		btnRetirarVale.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnRetirarVale.setText("Retirar Vale");
		btnRetirarVale.setActionCommand("RetirarVale");

		btnIngresarEntrega = new MiBoton("Guardar");
		btnIngresarEntrega.setBounds(0, 40, 200, 30);
		panel_2.add(btnIngresarEntrega);
		btnIngresarEntrega.setHorizontalAlignment(SwingConstants.LEFT);
		btnIngresarEntrega.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnIngresarEntrega.setText("Ingresar Entrega");
		btnIngresarEntrega.setActionCommand("IngresarEntrega");

		btnRetirarGasto = new MiBoton("Cancelar");
		btnRetirarGasto.setBounds(0, 120, 200, 30);
		panel_2.add(btnRetirarGasto);
		btnRetirarGasto.setHorizontalAlignment(SwingConstants.LEFT);
		btnRetirarGasto.setText("Retirar Gasto");
		btnRetirarGasto.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnRetirarGasto.setActionCommand("RetirarGasto");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(465, 121, 797, 469);
		getContentPane().add(scrollPane);

		tableMovimientos = new JTable();
		tableMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableMovimientos);

		btnEstadoCaja = new MiBoton("Salir");
		btnEstadoCaja.setHorizontalAlignment(SwingConstants.LEFT);
		btnEstadoCaja.setFont(new Font("SansSerif", Font.PLAIN, 12));
		btnEstadoCaja.setActionCommand("EstadoCaja");
		btnEstadoCaja.setText("EstadoCaja");
		btnEstadoCaja.setBounds(305, 130, 150, 30);
		getContentPane().add(btnEstadoCaja);
		
		panelCotizacion = new JPanel();
		panelCotizacion.setLayout(null);
		panelCotizacion.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cotizacion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelCotizacion.setBackground(Color.WHITE);
		panelCotizacion.setBounds(235, 10, 220, 100);
		getContentPane().add(panelCotizacion);
		
		lCotizacionGS = new LabelPersonalizado(0);
		lCotizacionGS.setBounds(10, 15, 200, 20);
		panelCotizacion.add(lCotizacionGS);
		
		lCotizacionRS = new LabelPersonalizado(0);
		lCotizacionRS.setBounds(10, 43, 200, 20);
		panelCotizacion.add(lCotizacionRS);
		
		lCotizacionUS = new LabelPersonalizado(0);
		lCotizacionUS.setBounds(10, 70, 200, 20);
		panelCotizacion.add(lCotizacionUS);

		panelSInicial = new JPanel();
		panelSInicial
				.setBorder(new TitledBorder(null, "Saldo Inicial", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSInicial.setBackground(Color.WHITE);
		panelSInicial.setBounds(465, 10, 220, 100);
		getContentPane().add(panelSInicial);
		panelSInicial.setLayout(null);

		lSaldoInicialGS = new LabelPersonalizado(0);
		lSaldoInicialGS.setBounds(10, 15, 200, 20);
		panelSInicial.add(lSaldoInicialGS);

		lSaldoInicialRS = new LabelPersonalizado(0);
		lSaldoInicialRS.setBounds(10, 43, 200, 20);
		panelSInicial.add(lSaldoInicialRS);

		lSaldoInicialUS = new LabelPersonalizado(0);
		lSaldoInicialUS.setBounds(10, 70, 200, 20);
		panelSInicial.add(lSaldoInicialUS);

		panelSIngreso = new JPanel();
		panelSIngreso.setLayout(null);
		panelSIngreso.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingreso",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSIngreso.setBackground(Color.WHITE);
		panelSIngreso.setBounds(1042, 10, 220, 100);
		getContentPane().add(panelSIngreso);

		lSaldoIngresoGS = new LabelPersonalizado(0);
		lSaldoIngresoGS.setBounds(10, 15, 200, 20);
		panelSIngreso.add(lSaldoIngresoGS);

		lSaldoIngresoRS = new LabelPersonalizado(0);
		lSaldoIngresoRS.setBounds(10, 43, 200, 20);
		panelSIngreso.add(lSaldoIngresoRS);

		lSaldoIngresoUS = new LabelPersonalizado(0);
		lSaldoIngresoUS.setBounds(10, 70, 200, 20);
		panelSIngreso.add(lSaldoIngresoUS);

		panelSRetiro = new JPanel();
		panelSRetiro.setLayout(null);
		panelSRetiro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Retiro",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSRetiro.setBackground(Color.WHITE);
		panelSRetiro.setBounds(753, 10, 220, 100);
		getContentPane().add(panelSRetiro);

		lSaldoRetiroGS = new LabelPersonalizado(0);
		lSaldoRetiroGS.setBounds(10, 15, 200, 20);
		panelSRetiro.add(lSaldoRetiroGS);

		lSaldoRetiroRS = new LabelPersonalizado(0);
		lSaldoRetiroRS.setBounds(10, 43, 200, 20);
		panelSRetiro.add(lSaldoRetiroRS);

		lSaldoRetiroUS = new LabelPersonalizado(0);
		lSaldoRetiroUS.setBounds(10, 70, 200, 20);
		panelSRetiro.add(lSaldoRetiroUS);

		tableMovimientos.setDefaultRenderer(Object.class, new CeldaRenderer(0, "Color"));

		panelGeneral = new JPanel();
		panelGeneral.setBounds(20, 172, 435, 364);
		getContentPane().add(panelGeneral);
		panelGeneral.setLayout(null);

		panel_1 = new JPanel();
		panel_1.setBounds(0, 3, 435, 81);
		panelGeneral.add(panel_1);
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);

		rdEfectivo = new JRadioButton("Efectivo");
		rdEfectivo.setBounds(151, 0, 150, 25);
		panel_1.add(rdEfectivo);
		buttonGroup_1.add(rdEfectivo);
		rdEfectivo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdEfectivo.setSelected(true);
		rdEfectivo.setBackground(Color.WHITE);

		rdTarjeta = new JRadioButton("Tarjeta");
		rdTarjeta.setBounds(151, 28, 150, 25);
		panel_1.add(rdTarjeta);
		buttonGroup_1.add(rdTarjeta);
		rdTarjeta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdTarjeta.setBackground(Color.WHITE);

		rdTransferencia = new JRadioButton("Transferencia");
		rdTransferencia.setBounds(151, 56, 150, 25);
		panel_1.add(rdTransferencia);
		buttonGroup_1.add(rdTransferencia);
		rdTransferencia.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdTransferencia.setBackground(Color.WHITE);

		rdCheque = new JRadioButton("Cheque");
		rdCheque.setBounds(303, 0, 126, 25);
		panel_1.add(rdCheque);
		buttonGroup_1.add(rdCheque);
		rdCheque.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdCheque.setBackground(Color.WHITE);

		rdGiro = new JRadioButton("Giro");
		rdGiro.setBounds(303, 28, 126, 25);
		panel_1.add(rdGiro);
		buttonGroup_1.add(rdGiro);
		rdGiro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdGiro.setBackground(Color.WHITE);

		rdCliente = new JRadioButton("Cliente");
		rdCliente.setBounds(0, 28, 150, 25);
		panel_1.add(rdCliente);
		buttonGroup_2.add(rdCliente);
		rdCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		rdCliente.setSelected(true);
		rdCliente.setBackground(Color.WHITE);

		rdColaborador = new JRadioButton("Colaborador");
		rdColaborador.setBounds(0, 56, 150, 25);
		panel_1.add(rdColaborador);
		buttonGroup_2.add(rdColaborador);
		rdColaborador.setFont(new Font("Tahoma", Font.BOLD, 18));
		rdColaborador.setBackground(Color.WHITE);

		rdOtro = new JRadioButton("Otro medio");
		rdOtro.setBounds(303, 56, 126, 25);
		panel_1.add(rdOtro);
		buttonGroup_1.add(rdOtro);
		rdOtro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdOtro.setBackground(Color.WHITE);

		LabelPersonalizado lblprsnlzdAsociarPago = new LabelPersonalizado(0);
		lblprsnlzdAsociarPago.setBounds(0, 0, 110, 25);
		panel_1.add(lblprsnlzdAsociarPago);
		lblprsnlzdAsociarPago.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdAsociarPago.setText("Asociar pago");

		JPanel panel = new JPanel();
		panel.setBounds(0, 87, 435, 93);
		panelGeneral.add(panel);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		lDatosCriticos = new LabelPersonalizado(0);
		lDatosCriticos.setBounds(0, 0, 435, 20);
		panel.add(lDatosCriticos);
		lDatosCriticos.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lDatosCriticos.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lDatosCriticos2 = new LabelPersonalizado(0);
		lDatosCriticos2.setBounds(0, 20, 435, 20);
		panel.add(lDatosCriticos2);
		lDatosCriticos2.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lDatosCriticos2.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lDatosCriticos3 = new LabelPersonalizado(0);
		lDatosCriticos3.setBounds(0, 51, 435, 20);
		panel.add(lDatosCriticos3);
		lDatosCriticos3.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lDatosCriticos3.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lDatosCriticos4 = new LabelPersonalizado(0);
		lDatosCriticos4.setBounds(0, 73, 435, 20);
		panel.add(lDatosCriticos4);
		lDatosCriticos4.setText("ENZO JEREMIAS APODACA GONZALEZ");
		lDatosCriticos4.setFont(new Font("Tahoma", Font.PLAIN, 12));

		panelValor1 = new JPanel();
		panelValor1.setBounds(0, 247, 435, 50);
		panelGeneral.add(panelValor1);
		panelValor1.setBackground(Color.WHITE);
		panelValor1.setLayout(null);

		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setBounds(14, 0, 92, 20);
		panelValor1.add(lblprsnlzdGs);
		lblprsnlzdGs.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdGs.setText("Guaranies");

		tValorGs = new CampoNumeroPersonalizado();
		tValorGs.setBounds(0, 20, 120, 30);
		panelValor1.add(tValorGs);
		tValorGs.setFont(new Font("Tahoma", Font.PLAIN, 18));

		TextPrompt placeholder = new TextPrompt("0.0", tValorGs);

		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setBounds(187, 0, 60, 20);
		panelValor1.add(lblprsnlzdRs);
		lblprsnlzdRs.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdRs.setText("Reales");

		tValorRs = new CampoNumeroPersonalizado();
		tValorRs.setBounds(157, 20, 120, 30);
		panelValor1.add(tValorRs);
		tValorRs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		placeholder = new TextPrompt("0,0", tValorRs);

		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setBounds(341, 0, 69, 20);
		panelValor1.add(lblprsnlzdUs);
		lblprsnlzdUs.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdUs.setText("D\u00F3lares");

		tValorUs = new CampoNumeroPersonalizado();
		tValorUs.setBounds(315, 20, 120, 30);
		panelValor1.add(tValorUs);
		tValorUs.setFont(new Font("Tahoma", Font.PLAIN, 18));
		placeholder = new TextPrompt("0,0", tValorUs);

		tObservacion = new JTextArea();
		tObservacion.setText("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		tObservacion.setBounds(0, 183, 434, 69);
		panelGeneral.add(tObservacion);
		tObservacion.setLineWrap(true);
		tObservacion.setWrapStyleWord(true);
		tObservacion.setFont(new Font("Tahoma", Font.PLAIN, 18));

		panelValor2 = new JPanel();
		panelValor2.setLayout(null);
		panelValor2.setBackground(Color.WHITE);
		panelValor2.setBounds(0, 308, 435, 50);
		panelGeneral.add(panelValor2);

		LabelPersonalizado lblprsnlzdGs_1 = new LabelPersonalizado(0);
		lblprsnlzdGs_1.setText("Entrega Gs");
		lblprsnlzdGs_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdGs_1.setBounds(0, 0, 120, 20);
		panelValor2.add(lblprsnlzdGs_1);

		tSaldoEntregadoGS = new CampoNumeroPersonalizado();
		tSaldoEntregadoGS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tSaldoEntregadoGS.setBounds(0, 20, 120, 30);
		panelValor2.add(tSaldoEntregadoGS);

		LabelPersonalizado lblprsnlzdRs_1 = new LabelPersonalizado(0);
		lblprsnlzdRs_1.setText("Entrega RS");
		lblprsnlzdRs_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdRs_1.setBounds(157, 0, 120, 20);
		panelValor2.add(lblprsnlzdRs_1);

		tSaldoEntregadoRS = new CampoNumeroPersonalizado();
		tSaldoEntregadoRS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tSaldoEntregadoRS.setBounds(157, 20, 120, 30);
		panelValor2.add(tSaldoEntregadoRS);

		LabelPersonalizado lblprsnlzdUs_1 = new LabelPersonalizado(0);
		lblprsnlzdUs_1.setText("Entrega US");
		lblprsnlzdUs_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdUs_1.setBounds(315, 0, 120, 20);
		panelValor2.add(lblprsnlzdUs_1);

		tSaldoEntregadoUS = new CampoNumeroPersonalizado();
		tSaldoEntregadoUS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tSaldoEntregadoUS.setBounds(315, 20, 120, 30);
		panelValor2.add(tSaldoEntregadoUS);
		placeholder.changeAlpha(0.75f);
		placeholder.changeStyle(Font.ITALIC);

		btnConfirmar = new MiBoton("Finalizar");
		btnConfirmar.setText("Confirmar");
		btnConfirmar.setHorizontalAlignment(SwingConstants.LEFT);
		btnConfirmar.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnConfirmar.setActionCommand("Confirmar");
		btnConfirmar.setBounds(20, 540, 200, 50);
		getContentPane().add(btnConfirmar);

		btnCancelar = new MiBoton("Cancelar");
		btnCancelar.setText("Cancelar");
		btnCancelar.setHorizontalAlignment(SwingConstants.LEFT);
		btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnCancelar.setActionCommand("Cancelar");
		btnCancelar.setBounds(255, 540, 200, 50);
		getContentPane().add(btnCancelar);

		lTitulo = new LabelPersonalizado(0);
		lTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lTitulo.setBounds(505, 135, 300, 20);
		getContentPane().add(lTitulo);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public LabelPersonalizado getlSaldoInicialGS() {
		return lSaldoInicialGS;
	}

	public LabelPersonalizado getlSaldoInicialRS() {
		return lSaldoInicialRS;
	}

	public LabelPersonalizado getlSaldoInicialUS() {
		return lSaldoInicialUS;
	}

	public JTable getTableMovimientos() {
		return tableMovimientos;
	}

	public MiBoton getBtnIngresarPago() {
		return btnIngresarPago;
	}

	public TransaccionCajaControlador getControlador() {
		return controlador;
	}

	public MiBoton getBtnEstadoCaja() {
		return btnEstadoCaja;
	}

	public JPanel getPanelSInicial() {
		return panelSInicial;
	}

	public JPanel getPanelSIngreso() {
		return panelSIngreso;
	}

	public LabelPersonalizado getlSaldoIngresoGS() {
		return lSaldoIngresoGS;
	}

	public LabelPersonalizado getlSaldoIngresoRS() {
		return lSaldoIngresoRS;
	}

	public LabelPersonalizado getlSaldoIngresoUS() {
		return lSaldoIngresoUS;
	}

	public JPanel getPanelSRetiro() {
		return panelSRetiro;
	}

	public LabelPersonalizado getlSaldoRetiroGS() {
		return lSaldoRetiroGS;
	}

	public LabelPersonalizado getlSaldoRetiroRS() {
		return lSaldoRetiroRS;
	}

	public LabelPersonalizado getlSaldoRetiroUS() {
		return lSaldoRetiroUS;
	}

	public MiBoton getBtnRetirarVale() {
		return btnRetirarVale;
	}

	public MiBoton getBtnIngresarEntrega() {
		return btnIngresarEntrega;
	}

	public MiBoton getBtnRetirarGasto() {
		return btnRetirarGasto;
	}

	public JRadioButton getRdEfectivo() {
		return rdEfectivo;
	}

	public JRadioButton getRdTarjeta() {
		return rdTarjeta;
	}

	public JRadioButton getRdTransferencia() {
		return rdTransferencia;
	}

	public JRadioButton getRdCheque() {
		return rdCheque;
	}

	public JRadioButton getRdGiro() {
		return rdGiro;
	}

	public LabelPersonalizado getlDatosCriticos() {
		return lDatosCriticos;
	}

	public LabelPersonalizado getlDatosCriticos2() {
		return lDatosCriticos2;
	}

	public JRadioButton getRdCliente() {
		return rdCliente;
	}

	public JRadioButton getRdColaborador() {
		return rdColaborador;
	}

	public MiBoton getBtnConfirmar() {
		return btnConfirmar;
	}

	public MiBoton getBtnCancelar() {
		return btnCancelar;
	}

	public JRadioButton getRdOtro() {
		return rdOtro;
	}

	public ButtonGroup getButtonGroup_1() {
		return buttonGroup_1;
	}

	public ButtonGroup getButtonGroup_2() {
		return buttonGroup_2;
	}

	public JPanel getPanel_1() {
		return panel_1;
	}

	public JPanel getPanel_2() {
		return panel_2;
	}

	public LabelPersonalizado getlDatosCriticos4() {
		return lDatosCriticos4;
	}

	public LabelPersonalizado getlDatosCriticos3() {
		return lDatosCriticos3;
	}

	public JPanel getPanelValor1() {
		return panelValor1;
	}

	public JTextArea gettObservacion() {
		return tObservacion;
	}

	public CampoNumeroPersonalizado gettValorGs() {
		return tValorGs;
	}

	public CampoNumeroPersonalizado gettValorRs() {
		return tValorRs;
	}

	public CampoNumeroPersonalizado gettValorUs() {
		return tValorUs;
	}

	public LabelPersonalizado getlTitulo() {
		return lTitulo;
	}

	public JPanel getPanelGeneral() {
		return panelGeneral;
	}

	public JPanel getPanelValor2() {
		return panelValor2;
	}

	public CampoNumeroPersonalizado gettSaldoEntregadoGS() {
		return tSaldoEntregadoGS;
	}

	public CampoNumeroPersonalizado gettSaldoEntregadoRS() {
		return tSaldoEntregadoRS;
	}

	public CampoNumeroPersonalizado gettSaldoEntregadoUS() {
		return tSaldoEntregadoUS;
	}

	public JPanel getPanelCotizacion() {
		return panelCotizacion;
	}

	public LabelPersonalizado getlCotizacionGS() {
		return lCotizacionGS;
	}

	public LabelPersonalizado getlCotizacionRS() {
		return lCotizacionRS;
	}

	public LabelPersonalizado getlCotizacionUS() {
		return lCotizacionUS;
	}

}
