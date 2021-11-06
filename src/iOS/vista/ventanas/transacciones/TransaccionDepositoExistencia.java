package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.WindowConstants;

import iOS.controlador.ventanas.transacciones.TransaccionDepositoExistenciaControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionDepositoExistencia extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1587814772802813768L;
	private CampoTextoPersonalizado tReferenciaMaterial;
	private MiBoton btnBuscarExistencia;
	private LabelPersonalizado lMensaje;
	private MiBoton btnTransferir;
	@SuppressWarnings("rawtypes")
	private JComboBox cbDepositos;
	private TransaccionDepositoExistenciaControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					TransaccionDepositoExistencia dialog = new TransaccionDepositoExistencia();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setUpControlador() {
		controlador = new TransaccionDepositoExistenciaControlador(this);
		
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public TransaccionDepositoExistencia() {
		setResizable(false);
		setTitle("Transferir deposito");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 500, 170);
		
		tReferenciaMaterial = new CampoTextoPersonalizado();
		tReferenciaMaterial.soloNumerosEnteros();
		tReferenciaMaterial.avisar();
		tReferenciaMaterial.setBounds(10, 66, 230, 30);
		getContentPane().add(tReferenciaMaterial);
		
		LabelPersonalizado lblprsnlzdReferenciaDelMaterial = new LabelPersonalizado(0);
		lblprsnlzdReferenciaDelMaterial.setText("Referencia del material");
		lblprsnlzdReferenciaDelMaterial.setBounds(10, 50, 139, 15);
		getContentPane().add(lblprsnlzdReferenciaDelMaterial);
		
		btnBuscarExistencia = new MiBoton("Buscar");
		btnBuscarExistencia.setActionCommand("BuscarExistencia");
		btnBuscarExistencia.setText("");
		btnBuscarExistencia.setBounds(244, 66, 50, 30);
		getContentPane().add(btnBuscarExistencia);
		
		lMensaje = new LabelPersonalizado(0);
		lMensaje.setForeground(Color.YELLOW);
		lMensaje.setBounds(10, 115, 474, 15);
		getContentPane().add(lMensaje);
		
		btnTransferir = new MiBoton("Finalizar");
		btnTransferir.setActionCommand("Guardar");
		btnTransferir.setText("Transferir");
		btnTransferir.setBounds(370, 66, 100, 30);
		getContentPane().add(btnTransferir);
		
		cbDepositos = new JComboBox();
		cbDepositos.setBounds(10, 11, 350, 25);
		getContentPane().add(cbDepositos);
		
		LabelPersonalizado lblprsnlzdDeposito = new LabelPersonalizado(0);
		lblprsnlzdDeposito.setText("Deposito");
		lblprsnlzdDeposito.setBounds(370, 21, 55, 15);
		getContentPane().add(lblprsnlzdDeposito);
		
	}

	public CampoTextoPersonalizado gettReferenciaMaterial() {
		return tReferenciaMaterial;
	}

	public void settReferenciaMaterial(CampoTextoPersonalizado tReferenciaMaterial) {
		this.tReferenciaMaterial = tReferenciaMaterial;
	}

	public MiBoton getBtnBuscarExistencia() {
		return btnBuscarExistencia;
	}

	public void setBtnBuscarExistencia(MiBoton btnBuscarExistencia) {
		this.btnBuscarExistencia = btnBuscarExistencia;
	}

	public LabelPersonalizado getlMensaje() {
		return lMensaje;
	}

	public void setlMensaje(LabelPersonalizado lMensaje) {
		this.lMensaje = lMensaje;
	}

	public MiBoton getBtnTransferir() {
		return btnTransferir;
	}

	public void setBtnTransferir(MiBoton btnTransferir) {
		this.btnTransferir = btnTransferir;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getCbDepositos() {
		return cbDepositos;
	}

	public void setCbDepositos(@SuppressWarnings("rawtypes") JComboBox cbDepositos) {
		this.cbDepositos = cbDepositos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TransaccionDepositoExistenciaControlador getControlador() {
		return controlador;
	}
	
	
}
