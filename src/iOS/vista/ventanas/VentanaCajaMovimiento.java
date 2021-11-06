package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaCajaMovimientoControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaCajaMovimiento extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 628264997096470372L;
	public String modulo = "CAJA";
	private CampoNumeroPersonalizado tValorGs;
	private CampoNumeroPersonalizado tValorRs;
	private JTextArea txtObservacion;
	private CampoNumeroPersonalizado tValorUs;
	private VentanaCajaMovimientoControlador controlador;
	private LabelPersonalizado lDatosCriticos;
	private LabelPersonalizado lDatosCriticos2;
	private JRadioButton rdCliente;
	private JRadioButton rdColaborador;
	private JRadioButton rdIngreso;
	private JRadioButton rdRetiro;
	private JRadioButton rdEfectivo;
	private JRadioButton rdTarjeta;
	private JRadioButton rdTransferencia;
	private JRadioButton rdCheque;
	private JRadioButton rdGiro;
	private JRadioButton rd1;
	private JRadioButton rd2;
	
	public void setUpControlador(boolean esIngreso, String operacion) {
		controlador = new VentanaCajaMovimientoControlador(this, esIngreso, operacion);

	}

	/**
	 * Create the dialog.
	 */

	public VentanaCajaMovimiento() {		
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		getContentPane().setLayout(null);
		
		getPanelFormulario().setLocation(10, 132);
		getPanelFormulario().setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos cr\u00EDticos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getPanelFormulario().setSize(474, 100);
		
		lDatosCriticos = new LabelPersonalizado(0);
		lDatosCriticos.setBounds(130, 20, 334, 20);
		getPanelFormulario().add(lDatosCriticos);
		
		lDatosCriticos2 = new LabelPersonalizado(0);
		lDatosCriticos2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lDatosCriticos2.setBounds(130, 46, 334, 15);
		getPanelFormulario().add(lDatosCriticos2);
		
		rdCliente = new JRadioButton("Cliente");
		rdCliente.setSelected(true);
		rdCliente.setBackground(Color.WHITE);
		rdCliente.setBounds(6, 20, 109, 23);
		getPanelFormulario().add(rdCliente);
		
		rdColaborador = new JRadioButton("Colaborador");
		rdColaborador.setBackground(Color.WHITE);
		rdColaborador.setBounds(6, 46, 109, 23);
		getPanelFormulario().add(rdColaborador);
	
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Valor del movimiento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 243, 474, 166);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setText("Gs.");
		lblprsnlzdGs.setBounds(20, 27, 20, 15);
		panel_1.add(lblprsnlzdGs);
		
		tValorGs = new CampoNumeroPersonalizado();
		tValorGs.setBounds(46, 22, 150, 25);
		panel_1.add(tValorGs);
		
		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setText("Rs.");
		lblprsnlzdRs.setBounds(21, 74, 20, 15);
		panel_1.add(lblprsnlzdRs);
		
		tValorRs = new CampoNumeroPersonalizado();
		tValorRs.setBounds(46, 69, 150, 25);
		panel_1.add(tValorRs);
		
		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setText("Us.");
		lblprsnlzdUs.setBounds(20, 121, 20, 15);
		panel_1.add(lblprsnlzdUs);
		
		tValorUs = new CampoNumeroPersonalizado();
		tValorUs.setBounds(46, 116, 150, 25);
		panel_1.add(tValorUs);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(206, 11, 258, 144);
		panel_1.add(scrollPane);
		
		txtObservacion = new JTextArea();
		txtObservacion = new JTextArea();
		txtObservacion.setLineWrap(true);
		txtObservacion.setWrapStyleWord(true);
		txtObservacion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Character c = evt.getKeyChar();
				if (Character.isLetter(c)) {
					evt.setKeyChar(Character.toUpperCase(c));
				}
				if (txtObservacion.getText().length() > 235) {
					evt.consume();
				}
			}
		});
		txtObservacion.setFont(new Font("Monospaced", Font.PLAIN, 15));
		txtObservacion.setToolTipText("Observaciones relevantes");
		txtObservacion.setBounds(20, 189, 420, 200);
		scrollPane.setViewportView(txtObservacion);
		
		rdIngreso = new JRadioButton("Ingreso");
		rdIngreso.setSelected(true);
		rdIngreso.setBackground(Color.WHITE);
		rdIngreso.setBounds(10, 13, 109, 20);
		getContentPane().add(rdIngreso);
		
		rdRetiro = new JRadioButton("Retiro");
		rdRetiro.setBackground(Color.WHITE);
		rdRetiro.setBounds(10, 39, 109, 20);
		getContentPane().add(rdRetiro);
		
		rdEfectivo = new JRadioButton("Efectivo");
		rdEfectivo.setSelected(true);
		rdEfectivo.setBackground(Color.WHITE);
		rdEfectivo.setBounds(121, 13, 109, 20);
		getContentPane().add(rdEfectivo);
		
		rdTarjeta = new JRadioButton("Tarjeta");
		rdTarjeta.setBackground(Color.WHITE);
		rdTarjeta.setBounds(121, 36, 109, 20);
		getContentPane().add(rdTarjeta);
		
		rdTransferencia = new JRadioButton("Transferencia");
		rdTransferencia.setBackground(Color.WHITE);
		rdTransferencia.setBounds(121, 59, 109, 20);
		getContentPane().add(rdTransferencia);
		
		rdCheque = new JRadioButton("Cheque");
		rdCheque.setBackground(Color.WHITE);
		rdCheque.setBounds(121, 82, 109, 20);
		getContentPane().add(rdCheque);
		
		rdGiro = new JRadioButton("Giro");
		rdGiro.setBackground(Color.WHITE);
		rdGiro.setBounds(121, 105, 109, 20);
		getContentPane().add(rdGiro);
		
		rd1 = new JRadioButton("1");
		rd1.setBackground(Color.WHITE);
		rd1.setBounds(232, 13, 109, 23);
		getContentPane().add(rd1);
		
		rd2 = new JRadioButton("2");
		rd2.setBackground(Color.WHITE);
		rd2.setBounds(232, 39, 109, 23);
		getContentPane().add(rd2);
		
		ButtonGroup grupo1 = new ButtonGroup();
		grupo1.add(rdCliente);
		grupo1.add(rdColaborador);
		
		ButtonGroup grupo2 = new ButtonGroup();
		grupo2.add(rdIngreso);
		grupo2.add(rdRetiro);
		
		ButtonGroup grupo3 = new ButtonGroup();
		grupo3.add(rdEfectivo);
		grupo3.add(rdTarjeta);
		grupo3.add(rdTransferencia);
		grupo3.add(rdCheque);
		grupo3.add(rdGiro);
		
		ButtonGroup grupo4 = new ButtonGroup();
		grupo4.add(rd1);
		grupo4.add(rd2);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getModulo() {
		return modulo;
	}

	public CampoNumeroPersonalizado gettValorGs() {
		return tValorGs;
	}

	public CampoNumeroPersonalizado gettValorRs() {
		return tValorRs;
	}

	public JTextArea getTxtObservacion() {
		return txtObservacion;
	}

	public CampoNumeroPersonalizado gettValorUs() {
		return tValorUs;
	}

	public VentanaCajaMovimientoControlador getControlador() {
		return controlador;
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

	public JRadioButton getRdIngreso() {
		return rdIngreso;
	}

	public JRadioButton getRdRetiro() {
		return rdRetiro;
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

	public JRadioButton getRd1() {
		return rd1;
	}

	public JRadioButton getRd2() {
		return rd2;
	}
	
	
}
