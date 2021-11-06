package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.transacciones.TransaccionCajaControlador2;
import iOS.vista.componentes.CeldaRenderer;
import iOS.vista.componentes.ClockLabel;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionCaja2 extends JDialog {
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
	private TransaccionCajaControlador2 controlador;
	private MiBoton btnEstadoCaja;
	private JPanel panelSInicial;
	private JPanel panel_1;
	private JPanel panelSIngreso;
	private LabelPersonalizado lSaldoIngresoGS;
	private LabelPersonalizado lSaldoIngresoRS;
	private LabelPersonalizado lSaldoIngresoUS;
	private JPanel panelSRetiro;
	private LabelPersonalizado lSaldoRetiroGS;
	private LabelPersonalizado lSaldoRetiroRS;
	private LabelPersonalizado lSaldoRetiroUS;
	private MiBoton btnRetirarVale;
	private LabelPersonalizado lColaborador;
	private LabelPersonalizado lEstadoCaja;
	private MiBoton btnIngresarEntrega;
	private MiBoton btnRetirarGasto;
	
	public void setUpControlador() {
		controlador = new TransaccionCajaControlador2(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionCaja2() {
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
		
		btnIngresarPago = new MiBoton("Guardar");
		btnIngresarPago.callToAction();
		btnIngresarPago.setHorizontalAlignment(SwingConstants.LEFT);
		btnIngresarPago.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnIngresarPago.setActionCommand("IngresarPago");
		btnIngresarPago.setText("Ingresar Pago");
		btnIngresarPago.setBounds(10, 142, 200, 50);
		getContentPane().add(btnIngresarPago);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(220, 142, 1044, 450);
		getContentPane().add(scrollPane);
		
		tableMovimientos = new JTable();
		tableMovimientos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(tableMovimientos);
		
		btnEstadoCaja = new MiBoton("Salir");
		btnEstadoCaja.setHorizontalAlignment(SwingConstants.LEFT);
		btnEstadoCaja.setFont(new Font("SansSerif", Font.PLAIN, 18));
		btnEstadoCaja.setActionCommand("EstadoCaja");
		btnEstadoCaja.setText("EstadoCaja");
		btnEstadoCaja.setBounds(10, 542, 200, 50);
		getContentPane().add(btnEstadoCaja);
		
		panelSInicial = new JPanel();
		panelSInicial.setBorder(new TitledBorder(null, "Saldo Inicial", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSInicial.setBackground(Color.WHITE);
		panelSInicial.setBounds(383, 11, 150, 120);
		getContentPane().add(panelSInicial);
		panelSInicial.setLayout(null);
		
		lSaldoInicialGS = new LabelPersonalizado(0);
		lSaldoInicialGS.setBounds(10, 15, 110, 20);
		panelSInicial.add(lSaldoInicialGS);
		
		lSaldoInicialRS = new LabelPersonalizado(0);
		lSaldoInicialRS.setBounds(10, 43, 110, 20);
		panelSInicial.add(lSaldoInicialRS);
		
		lSaldoInicialUS = new LabelPersonalizado(0);
		lSaldoInicialUS.setBounds(10, 70, 110, 20);
		panelSInicial.add(lSaldoInicialUS);
		
		panelSIngreso = new JPanel();
		panelSIngreso.setLayout(null);
		panelSIngreso.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Ingreso", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSIngreso.setBackground(Color.WHITE);
		panelSIngreso.setBounds(721, 11, 150, 120);
		getContentPane().add(panelSIngreso);
		
		lSaldoIngresoGS = new LabelPersonalizado(0);
		lSaldoIngresoGS.setBounds(10, 15, 110, 20);
		panelSIngreso.add(lSaldoIngresoGS);
		
		lSaldoIngresoRS = new LabelPersonalizado(0);
		lSaldoIngresoRS.setBounds(10, 43, 110, 20);
		panelSIngreso.add(lSaldoIngresoRS);
		
		lSaldoIngresoUS = new LabelPersonalizado(0);
		lSaldoIngresoUS.setBounds(10, 70, 110, 20);
		panelSIngreso.add(lSaldoIngresoUS);
		
		panelSRetiro = new JPanel();
		panelSRetiro.setLayout(null);
		panelSRetiro.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Retiro", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelSRetiro.setBackground(Color.WHITE);
		panelSRetiro.setBounds(552, 11, 150, 120);
		getContentPane().add(panelSRetiro);
		
		lSaldoRetiroGS = new LabelPersonalizado(0);
		lSaldoRetiroGS.setBounds(10, 15, 110, 20);
		panelSRetiro.add(lSaldoRetiroGS);
		
		lSaldoRetiroRS = new LabelPersonalizado(0);
		lSaldoRetiroRS.setBounds(10, 43, 110, 20);
		panelSRetiro.add(lSaldoRetiroRS);
		
		lSaldoRetiroUS = new LabelPersonalizado(0);
		lSaldoRetiroUS.setBounds(10, 70, 110, 20);
		panelSRetiro.add(lSaldoRetiroUS);
		
		btnRetirarVale = new MiBoton("Cancelar");
		btnRetirarVale.setHorizontalAlignment(SwingConstants.LEFT);
		btnRetirarVale.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnRetirarVale.setText("Retirar Vale");
		btnRetirarVale.setActionCommand("RetirarVale");
		btnRetirarVale.setBounds(10, 264, 200, 50);
		getContentPane().add(btnRetirarVale);
		
		tableMovimientos.setDefaultRenderer(Object.class, new CeldaRenderer(0, "Color"));
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), " ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(890, 11, 364, 120);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		ClockLabel labelDia = new ClockLabel("dia");
		labelDia.setForeground(Color.BLACK);
		labelDia.setHorizontalAlignment(SwingConstants.CENTER);
		labelDia.setBounds(103, 90, 159, 20);
		panel_1.add(labelDia);
		
		ClockLabel labelHora = new ClockLabel("hora");
		labelHora.setForeground(Color.BLACK);
		labelHora.setBounds(10, 16, 344, 40);
		panel_1.add(labelHora);
		
		ClockLabel labelFeca = new ClockLabel("fecha");
		labelFeca.setHorizontalAlignment(SwingConstants.CENTER);
		labelFeca.setForeground(Color.BLACK);
		labelFeca.setBounds(10, 72, 344, 20);
		panel_1.add(labelFeca);
		
		JSeparator sep = new JSeparator();
		sep.setBounds(32, 65, 300, 2);
		panel_1.add(sep);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "Informacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(19, 11, 345, 120);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.BOLD, 18));
		lColaborador.setBounds(10, 22, 325, 20);
		panel.add(lColaborador);
		
		lEstadoCaja = new LabelPersonalizado(0);
		lEstadoCaja.setFont(new Font("Tahoma", Font.BOLD, 18));
		lEstadoCaja.setBounds(10, 53, 325, 20);
		panel.add(lEstadoCaja);
		
		btnIngresarEntrega = new MiBoton("Guardar");
		btnIngresarEntrega.setHorizontalAlignment(SwingConstants.LEFT);
		btnIngresarEntrega.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnIngresarEntrega.setText("Ingresar Entrega");
		btnIngresarEntrega.setActionCommand("IngresarEntrega");
		btnIngresarEntrega.setBounds(10, 203, 200, 50);
		getContentPane().add(btnIngresarEntrega);
		
		btnRetirarGasto = new MiBoton("Cancelar");
		btnRetirarGasto.setHorizontalAlignment(SwingConstants.LEFT);
		btnRetirarGasto.setText("Retirar Gasto");
		btnRetirarGasto.setFont(new Font("SansSerif", Font.BOLD, 18));
		btnRetirarGasto.setActionCommand("RetirarGasto");
		btnRetirarGasto.setBounds(10, 325, 200, 50);
		getContentPane().add(btnRetirarGasto);
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

	public TransaccionCajaControlador2 getControlador() {
		return controlador;
	}

	public MiBoton getBtnEstadoCaja() {
		return btnEstadoCaja;
	}

	public JPanel getPanelSInicial() {
		return panelSInicial;
	}

	public JPanel getPanel_1() {
		return panel_1;
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

	public LabelPersonalizado getlColaborador() {
		return lColaborador;
	}

	public LabelPersonalizado getlEstadoCaja() {
		return lEstadoCaja;
	}

	public MiBoton getBtnIngresarEntrega() {
		return btnIngresarEntrega;
	}

	public MiBoton getBtnRetirarGasto() {
		return btnRetirarGasto;
	}
	
	
	
}
