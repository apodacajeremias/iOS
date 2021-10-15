package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JSeparator;

import iOS.controlador.ventanas.VentanaCajaAperturaControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class VentanaCajaApertura extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811886757850643774L;
	public String modulo = "CAJA";
	private LabelPersonalizado lColaborador;
	private CampoNumeroPersonalizado tSaldoUS;
	private CampoNumeroPersonalizado tSaldoRS;
	private CampoNumeroPersonalizado tSaldoGS;
	private LabelPersonalizado lFecha;
	private JSeparator separator;
	private MiBoton btnAbrirCaja;
	private VentanaCajaAperturaControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCajaApertura dialog = new VentanaCajaApertura();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setUpControlador();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setUpControlador() {
		controlador = new VentanaCajaAperturaControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCajaApertura() {
		setResizable(false);
		setTitle("Apertura Caja");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 200);

		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.BOLD, 20));
		lColaborador.setBounds(10, 18, 474, 25);
		getContentPane().add(lColaborador);

		tSaldoGS = new CampoNumeroPersonalizado();
		tSaldoGS.setBounds(48, 81, 100, 25);
		getContentPane().add(tSaldoGS);

		tSaldoRS = new CampoNumeroPersonalizado();
		tSaldoRS.setBounds(196, 81, 100, 25);
		getContentPane().add(tSaldoRS);

		tSaldoUS = new CampoNumeroPersonalizado();
		tSaldoUS.setBounds(344, 81, 100, 25);
		getContentPane().add(tSaldoUS);

		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setText("Gs.");
		lblprsnlzdGs.setBounds(20, 91, 18, 15);
		getContentPane().add(lblprsnlzdGs);

		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setText("Rs.");
		lblprsnlzdRs.setBounds(166, 91, 20, 15);
		getContentPane().add(lblprsnlzdRs);

		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setText("Us.");
		lblprsnlzdUs.setBounds(316, 91, 18, 15);
		getContentPane().add(lblprsnlzdUs);

		LabelPersonalizado lblprsnlzdFecha = new LabelPersonalizado(0);
		lblprsnlzdFecha.setText("Fecha");
		lblprsnlzdFecha.setBounds(10, 124, 34, 15);
		getContentPane().add(lblprsnlzdFecha);

		lFecha = new LabelPersonalizado(0);
		lFecha.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lFecha.setBounds(48, 124, 248, 25);
		getContentPane().add(lFecha);

		separator = new JSeparator();
		separator.setBounds(47, 61, 400, 2);
		getContentPane().add(separator);

		btnAbrirCaja = new MiBoton((String) null);
		btnAbrirCaja.setActionCommand("AbrirCaja");
		btnAbrirCaja.setText("Abrir Caja");
		btnAbrirCaja.setBounds(344, 130, 100, 30);
		getContentPane().add(btnAbrirCaja);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LabelPersonalizado getlColaborador() {
		return lColaborador;
	}

	public CampoNumeroPersonalizado gettSaldoUS() {
		return tSaldoUS;
	}

	public CampoNumeroPersonalizado gettSaldoRS() {
		return tSaldoRS;
	}

	public CampoNumeroPersonalizado gettSaldoGS() {
		return tSaldoGS;
	}

	public LabelPersonalizado getlFecha() {
		return lFecha;
	}

	public JSeparator getSeparator() {
		return separator;
	}

	public MiBoton getBtnAbrirCaja() {
		return btnAbrirCaja;
	}

	public VentanaCajaAperturaControlador getControlador() {
		return controlador;
	}
	
}
