package iOS.vista.ventanas;

import iOS.controlador.ventanas.VentanaClienteControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;
import java.awt.Font;

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

	public void setUpControlador() {
		controlador = new VentanaClienteControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCliente() {
		setTitle("Formulario de Cliente");

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreCompleto.setBounds(20, 40, 432, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);

		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tIdentificacion.setBounds(20, 100, 200, 30);
		tIdentificacion.mayusculas();
		tIdentificacion.limite(12);
		getPanelFormulario().add(tIdentificacion);

		tContacto = new CampoTextoPersonalizado();
		tContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tContacto.setBounds(20, 160, 200, 30);
		tContacto.limite(12);
		getPanelFormulario().add(tContacto);

		tDireccion = new CampoTextoPersonalizado();
		tDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tDireccion.setBounds(20, 220, 450, 30);
		tDireccion.limite(80);
		tDireccion.mayusculas();
		getPanelFormulario().add(tDireccion);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdNombreCompleto.setText("Nombre completo del cliente");
		lblprsnlzdNombreCompleto.setBounds(20, 20, 258, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdIdentificacion_1.setText("Nro. CI / RUC / CPF / RG / DNI");
		lblprsnlzdIdentificacion_1.setBounds(20, 81, 280, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		LabelPersonalizado lblprsnlzdContacto_1 = new LabelPersonalizado(0);
		lblprsnlzdContacto_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdContacto_1.setText("Tel\u00E9fono para contacto");
		lblprsnlzdContacto_1.setBounds(20, 141, 209, 20);
		getPanelFormulario().add(lblprsnlzdContacto_1);

		LabelPersonalizado lblprsnlzdDireccion = new LabelPersonalizado(0);
		lblprsnlzdDireccion.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdDireccion.setText("Direccion de vivienda");
		lblprsnlzdDireccion.setBounds(20, 201, 230, 20);
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
