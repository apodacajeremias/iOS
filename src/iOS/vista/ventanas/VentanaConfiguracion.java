package iOS.vista.ventanas;



import java.awt.Font;

import iOS.controlador.ventanas.VentanaConfiguracionControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaConfiguracion extends VentanaGenerica {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3326623937564321138L;
	public String modulo = "CONFIGURACION";
	private VentanaConfiguracionControlador Controlador;
	private CampoTextoPersonalizado tNombreEmpresa;
	private CampoTextoPersonalizado tContacto;
	private CampoTextoPersonalizado tContribuyente;
	private CampoTextoPersonalizado tRegistroP;
	private CampoTextoPersonalizado tTitular;
	private LabelPersonalizado lblAccion;
	private CampoTextoPersonalizado tCedula;
	private CampoTextoPersonalizado tUbicacion;
	

	/**
	 * Launch the application.
	 */  
	public void setUpControlador() {
		Controlador = new VentanaConfiguracionControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaConfiguracion() {
		setTitle("Configuraci\u00F3n");
		
		lblAccion = new LabelPersonalizado(10);
		lblAccion.setBounds(200, 305, 74, 15);
		getContentPane().add(lblAccion);
		setLocationRelativeTo(this);
		
		LabelPersonalizado lblNombreEmpresa = new LabelPersonalizado(16);
		lblNombreEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNombreEmpresa.setBounds(5, 6, 195, 20);
		getPanelFormulario().add(lblNombreEmpresa);
		lblNombreEmpresa.setText("Nombre de la empresa");
		tNombreEmpresa = new CampoTextoPersonalizado();
		tNombreEmpresa.setBounds(5, 32, 264, 20);
		getPanelFormulario().add(tNombreEmpresa);
		
		LabelPersonalizado lblContacto = new LabelPersonalizado(16);
		lblContacto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContacto.setBounds(5, 58, 180, 20);
		getPanelFormulario().add(lblContacto);
		lblContacto.setText("Telefono de contacto");
		tContacto = new CampoTextoPersonalizado();
		tContacto.setBounds(5, 84, 180, 20);
		getPanelFormulario().add(tContacto);
		
		LabelPersonalizado lblContribuyente = new LabelPersonalizado(16);
		lblContribuyente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblContribuyente.setBounds(5, 110, 245, 20);
		getPanelFormulario().add(lblContribuyente);
		lblContribuyente.setText("Registro Unico Contribuyente");
		tContribuyente = new CampoTextoPersonalizado();
		tContribuyente.setBounds(5, 136, 180, 20);
		getPanelFormulario().add(tContribuyente);
		
		LabelPersonalizado lblTitular = new LabelPersonalizado(16);
		lblTitular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitular.setBounds(5, 162, 175, 20);
		getPanelFormulario().add(lblTitular);
		lblTitular.setText("Titular de la empresa");
		tTitular = new CampoTextoPersonalizado();
		tTitular.setBounds(5, 188, 264, 20);
		getPanelFormulario().add(tTitular);
		
		LabelPersonalizado lblRegistro = new LabelPersonalizado(16);
		lblRegistro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegistro.setBounds(5, 214, 166, 20);
		getPanelFormulario().add(lblRegistro);
		lblRegistro.setText("Registro Profesional");
		tRegistroP = new CampoTextoPersonalizado();
		tRegistroP.setBounds(5, 240, 180, 20);
		getPanelFormulario().add(tRegistroP);
		
		LabelPersonalizado lblCedula = new LabelPersonalizado( 16);
		lblCedula.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCedula.setBounds(5, 266, 149, 20);
		getPanelFormulario().add(lblCedula);
		lblCedula.setText("C\u00E9dula del titular");
		tCedula = new CampoTextoPersonalizado();
		tCedula.setBounds(5, 292, 180, 20);
		getPanelFormulario().add(tCedula);
		
		LabelPersonalizado lblUbicacion = new LabelPersonalizado(16);
		lblUbicacion.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUbicacion.setBounds(5, 318, 180, 20);
		getPanelFormulario().add(lblUbicacion);
		lblUbicacion.setText("Ubicaci\u00F3n de empresa");
		
		tUbicacion = new CampoTextoPersonalizado();
		tUbicacion.setBounds(5, 344, 180, 20);
		getPanelFormulario().add(tUbicacion);
		tCedula.soloNumerosEnteros();
		tTitular.soloLetras();
		tNombreEmpresa.mayusculas();
	
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public VentanaConfiguracionControlador getControlador() {
		return Controlador;
	}

	public CampoTextoPersonalizado gettNombreEmpresa() {
		return tNombreEmpresa;
	}

	public CampoTextoPersonalizado gettContacto() {
		return tContacto;
	}

	public CampoTextoPersonalizado gettContribuyente() {
		return tContribuyente;
	}

	public CampoTextoPersonalizado gettRegistroP() {
		return tRegistroP;
	}

	public CampoTextoPersonalizado gettTitular() {
		return tTitular;
	}

	public LabelPersonalizado getLblAccion() {
		return lblAccion;
	}

	public CampoTextoPersonalizado gettCedula() {
		return tCedula;
	}

	public CampoTextoPersonalizado gettUbicacion() {
		return tUbicacion;
	}	
	
	
}

