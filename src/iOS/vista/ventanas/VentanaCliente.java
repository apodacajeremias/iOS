package iOS.vista.ventanas;

import java.awt.EventQueue;

import javax.swing.JDialog;

import iOS.controlador.ventanas.VentanaClienteControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

/**
 * @author 59598
 *
 */
public class VentanaCliente extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569012102467509839L;
	public String modulo = "CLIENTE";
	private CampoTextoPersonalizado tNombreCompleto;
	private CampoTextoPersonalizado tIdentificacion;
	private CampoTextoPersonalizado tContacto;
	private CampoTextoPersonalizado tDireccion;
	private VentanaClienteControlador controlador;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCliente dialog = new VentanaCliente();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void setUpControlador() {
		controlador = new VentanaClienteControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCliente() {
		setTitle("Formulario de Cliente");
		
		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setBounds(12, 22, 450, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);
		
		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setBounds(12, 76, 200, 30);
		tIdentificacion.mayusculas();
		tIdentificacion.limite(12);
		getPanelFormulario().add(tIdentificacion);
		
		tContacto = new CampoTextoPersonalizado();
		tContacto.setBounds(12, 131, 200, 30);
		tContacto.limite(12);
		getPanelFormulario().add(tContacto);
		
		tDireccion = new CampoTextoPersonalizado();
		tDireccion.setBounds(12, 186, 450, 30);
		tDireccion.limite(80);
		tDireccion.mayusculas();
		getPanelFormulario().add(tDireccion);
		
		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Nombre del cliente");
		lblprsnlzdNombreCompleto.setBounds(12, 8, 118, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);
		
		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setText("Identificacion");
		lblprsnlzdIdentificacion_1.setBounds(12, 63, 85, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);
		
		LabelPersonalizado lblprsnlzdContacto_1 = new LabelPersonalizado(0);
		lblprsnlzdContacto_1.setText("Contacto");
		lblprsnlzdContacto_1.setBounds(12, 117, 60, 15);
		getPanelFormulario().add(lblprsnlzdContacto_1);
		
		LabelPersonalizado lblprsnlzdDireccion = new LabelPersonalizado(0);
		lblprsnlzdDireccion.setText("Direccion");
		lblprsnlzdDireccion.setBounds(12, 172, 60, 15);
		getPanelFormulario().add(lblprsnlzdDireccion);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoTextoPersonalizado gettNombreCompleto() {
		return tNombreCompleto;
	}

	public CampoTextoPersonalizado gettIdentificacion() {
		return tIdentificacion;
	}

	public CampoTextoPersonalizado gettContacto() {
		return tContacto;
	}

	public CampoTextoPersonalizado gettDireccion() {
		return tDireccion;
	}

	public VentanaClienteControlador getControlador() {
		return controlador;
	}
	
	
}
