package iOS.vista.ventanas;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaCajaCierreControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class VentanaCajaCierre extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811886757850643774L;
	private CampoNumeroPersonalizado tSaldoDeclaradoUS;
	private CampoNumeroPersonalizado tSaldoDeclaradoRS;
	private CampoNumeroPersonalizado tSaldoDeclaradoGS;
	private MiBoton btnCerrarCaja;
	private VentanaCajaCierreControlador controlador;
	private CampoNumeroPersonalizado tValorEntregadoRS;
	private CampoNumeroPersonalizado tValorEntregadoGS;
	private CampoNumeroPersonalizado tValorEntregadoUS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			VentanaCajaCierre dialog = new VentanaCajaCierre();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setUpControlador();
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setUpControlador() {
		controlador = new VentanaCajaCierreControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCajaCierre() {
		setModal(true);
		setResizable(false);
		setTitle("Cierre Caja");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 200);

		btnCerrarCaja = new MiBoton((String) null);
		btnCerrarCaja.setActionCommand("CerrarCaja");
		btnCerrarCaja.setText("Cerrar Caja");
		btnCerrarCaja.setBounds(384, 131, 100, 30);
		getContentPane().add(btnCerrarCaja);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Valores en Caja", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 9, 474, 52);
		getContentPane().add(panel);
		panel.setLayout(null);

		LabelPersonalizado lblprsnlzdGs = new LabelPersonalizado(0);
		lblprsnlzdGs.setBounds(40, 26, 18, 15);
		panel.add(lblprsnlzdGs);
		lblprsnlzdGs.setText("Gs.");

		tSaldoDeclaradoGS = new CampoNumeroPersonalizado();
		tSaldoDeclaradoGS.setBounds(68, 16, 100, 25);
		panel.add(tSaldoDeclaradoGS);

		LabelPersonalizado lblprsnlzdRs = new LabelPersonalizado(0);
		lblprsnlzdRs.setBounds(186, 26, 20, 15);
		panel.add(lblprsnlzdRs);
		lblprsnlzdRs.setText("Rs.");

		tSaldoDeclaradoRS = new CampoNumeroPersonalizado();
		tSaldoDeclaradoRS.setBounds(216, 16, 100, 25);
		panel.add(tSaldoDeclaradoRS);

		LabelPersonalizado lblprsnlzdUs = new LabelPersonalizado(0);
		lblprsnlzdUs.setBounds(336, 26, 18, 15);
		panel.add(lblprsnlzdUs);
		lblprsnlzdUs.setText("Us.");

		tSaldoDeclaradoUS = new CampoNumeroPersonalizado();
		tSaldoDeclaradoUS.setBounds(364, 16, 100, 25);
		panel.add(tSaldoDeclaradoUS);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Valor entregado", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 70, 474, 52);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		LabelPersonalizado lblprsnlzdGs_1 = new LabelPersonalizado(0);
		lblprsnlzdGs_1.setText("Gs.");
		lblprsnlzdGs_1.setBounds(40, 26, 18, 15);
		panel_1.add(lblprsnlzdGs_1);

		tValorEntregadoGS = new CampoNumeroPersonalizado();
		tValorEntregadoGS.setBounds(68, 16, 100, 25);
		panel_1.add(tValorEntregadoGS);

		LabelPersonalizado lblprsnlzdRs_1 = new LabelPersonalizado(0);
		lblprsnlzdRs_1.setText("Rs.");
		lblprsnlzdRs_1.setBounds(186, 26, 20, 15);
		panel_1.add(lblprsnlzdRs_1);

		tValorEntregadoRS = new CampoNumeroPersonalizado();
		tValorEntregadoRS.setBounds(216, 16, 100, 25);
		panel_1.add(tValorEntregadoRS);

		LabelPersonalizado lblprsnlzdUs_1 = new LabelPersonalizado(0);
		lblprsnlzdUs_1.setText("Us.");
		lblprsnlzdUs_1.setBounds(336, 26, 18, 15);
		panel_1.add(lblprsnlzdUs_1);

		tValorEntregadoUS = new CampoNumeroPersonalizado();
		tValorEntregadoUS.setBounds(364, 16, 100, 25);
		panel_1.add(tValorEntregadoUS);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoNumeroPersonalizado gettSaldoDeclaradoUS() {
		return tSaldoDeclaradoUS;
	}

	public CampoNumeroPersonalizado gettSaldoDeclaradoRS() {
		return tSaldoDeclaradoRS;
	}

	public CampoNumeroPersonalizado gettSaldoDeclaradoGS() {
		return tSaldoDeclaradoGS;
	}

	public MiBoton getBtnCerrarCaja() {
		return btnCerrarCaja;
	}

	public VentanaCajaCierreControlador getControlador() {
		return controlador;
	}

	public CampoNumeroPersonalizado gettValorEntregadoRS() {
		return tValorEntregadoRS;
	}

	public CampoNumeroPersonalizado gettValorEntregadoGS() {
		return tValorEntregadoGS;
	}

	public CampoNumeroPersonalizado gettValorEntregadoUS() {
		return tValorEntregadoUS;
	}


	
}
