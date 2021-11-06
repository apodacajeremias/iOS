package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;

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
	private MiBoton btnAbrirCaja;
	private VentanaCajaAperturaControlador controlador;
	
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
		setBounds(100, 100, 500, 170);
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setType(Type.NORMAL);

		lColaborador = new LabelPersonalizado(0);
		lColaborador.setFont(new Font("Tahoma", Font.BOLD, 20));
		lColaborador.setBounds(18, 11, 454, 25);
		getContentPane().add(lColaborador);

		tSaldoGS = new CampoNumeroPersonalizado();
		tSaldoGS.setBounds(18, 91, 100, 30);
		getContentPane().add(tSaldoGS);

		tSaldoRS = new CampoNumeroPersonalizado();
		tSaldoRS.setBounds(136, 91, 100, 30);
		getContentPane().add(tSaldoRS);

		tSaldoUS = new CampoNumeroPersonalizado();
		tSaldoUS.setBounds(254, 91, 100, 30);
		getContentPane().add(tSaldoUS);

		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setText("Guaranies - GS");
		lblprsnlzdGs.setBounds(18, 74, 100, 15);
		getContentPane().add(lblprsnlzdGs);

		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setText("Reales - RS");
		lblprsnlzdRs.setBounds(136, 74, 100, 15);
		getContentPane().add(lblprsnlzdRs);

		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setText("Dolares - US");
		lblprsnlzdUs.setBounds(254, 74, 100, 15);
		getContentPane().add(lblprsnlzdUs);

		LabelPersonalizado lblprsnlzdFecha = new LabelPersonalizado(0);
		lblprsnlzdFecha.setText("Fecha");
		lblprsnlzdFecha.setBounds(18, 47, 34, 20);
		getContentPane().add(lblprsnlzdFecha);

		lFecha = new LabelPersonalizado(0);
		lFecha.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lFecha.setBounds(62, 43, 150, 20);
		getContentPane().add(lFecha);

		btnAbrirCaja = new MiBoton((String) null);
		btnAbrirCaja.setActionCommand("AbrirCaja");
		btnAbrirCaja.setText("Abrir Caja");
		btnAbrirCaja.setBounds(372, 91, 100, 30);
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

	public MiBoton getBtnAbrirCaja() {
		return btnAbrirCaja;
	}

	public VentanaCajaAperturaControlador getControlador() {
		return controlador;
	}
	
}
