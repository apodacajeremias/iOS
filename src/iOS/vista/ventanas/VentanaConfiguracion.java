package iOS.vista.ventanas;



import javax.swing.JDialog;

import iOS.controlador.ventanas.VentanaConfiguracionControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.TextPrompt;

public class VentanaConfiguracion extends JDialog {
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
	private MiBoton btGuardar;
	private MiBoton btCancelar;
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
		setAlwaysOnTop(true);
		setTitle("Configuraci\u00F3n");
		setBounds(100, 100, 300, 500);
		getContentPane().setLayout(null);
		
		lblAccion = new LabelPersonalizado(10);
		lblAccion.setBounds(200, 305, 74, 15);
		getContentPane().add(lblAccion);
		
		btGuardar = new MiBoton("Guardar");
		btGuardar.setBounds(10, 425, 100, 30);
		getContentPane().add(btGuardar);
		
		btCancelar = new MiBoton("Cancelar");
		btCancelar.setBounds(140, 418, 100, 30);
		getContentPane().add(btCancelar);
		
		LabelPersonalizado lblNombreEmpresa = new LabelPersonalizado(16);
		lblNombreEmpresa.setText("Nombre de la empresa");
		lblNombreEmpresa.setBounds(10, 5, 195, 20);
		getContentPane().add(lblNombreEmpresa);
		tNombreEmpresa = new CampoTextoPersonalizado();
		tNombreEmpresa.setBounds(10, 30, 264, 30);
		tNombreEmpresa.mayusculas();
		getContentPane().add(tNombreEmpresa);
		
		LabelPersonalizado lblContacto = new LabelPersonalizado(16);
		lblContacto.setText("Telefono de contacto");
		lblContacto.setBounds(10, 65, 180, 20);
		getContentPane().add(lblContacto);
		tContacto = new CampoTextoPersonalizado();
		tContacto.setBounds(10, 90, 180, 30);
		getContentPane().add(tContacto);
		
		LabelPersonalizado lblContribuyente = new LabelPersonalizado(16);
		lblContribuyente.setText("Registro Unico Contribuyente");
		lblContribuyente.setBounds(10, 125, 245, 20);
		getContentPane().add(lblContribuyente);
		tContribuyente = new CampoTextoPersonalizado();
		tContribuyente.setBounds(10, 150, 180, 30);
		getContentPane().add(tContribuyente);
		setLocationRelativeTo(this);
		
		LabelPersonalizado lblTitular = new LabelPersonalizado(16);
		lblTitular.setText("Titular de la empresa");
		lblTitular.setBounds(10, 185, 175, 20);
		getContentPane().add(lblTitular);
		tTitular = new CampoTextoPersonalizado();
		tTitular.soloLetras();
		tTitular.setBounds(10, 210, 264, 30);
		getContentPane().add(tTitular);
		
		LabelPersonalizado lblRegistro = new LabelPersonalizado(16);
		lblRegistro.setText("Registro Profesional");
		lblRegistro.setBounds(10, 245, 166, 20);
		getContentPane().add(lblRegistro);
		tRegistroP = new CampoTextoPersonalizado();
		tRegistroP.setBounds(10, 270, 180, 30);
		getContentPane().add(tRegistroP);
		
		LabelPersonalizado lblCedula = new LabelPersonalizado( 16);
		lblCedula.setText("C\u00E9dula del titular");
		lblCedula.setBounds(10, 305, 149, 20);
		getContentPane().add(lblCedula);
		tCedula = new CampoTextoPersonalizado();
		tCedula.setBounds(10, 330, 180, 30);
		tCedula.soloNumerosEnteros();
		getContentPane().add(tCedula);
		
		LabelPersonalizado lblUbicacion = new LabelPersonalizado(16);
		lblUbicacion.setText("Ubicaci\u00F3n de empresa");
		lblUbicacion.setBounds(10, 365, 180, 20);
		getContentPane().add(lblUbicacion);
		
		tUbicacion = new CampoTextoPersonalizado();
		tUbicacion.setBounds(10, 390, 180, 30);
		getContentPane().add(tUbicacion);

		@SuppressWarnings("unused")
		TextPrompt tempresa = new TextPrompt("Nombre de la empresa", tNombreEmpresa);
		@SuppressWarnings("unused")
		TextPrompt ttelefono = new TextPrompt("Contacto de la empresa", tContacto);
		@SuppressWarnings("unused")
		TextPrompt tContriyente = new TextPrompt("RUC de la empresa", tContribuyente);
		@SuppressWarnings("unused")
		TextPrompt ttitular = new TextPrompt("Titular de la empresa", tTitular);
		@SuppressWarnings("unused")
		TextPrompt treg = new TextPrompt("Registro Profesional", tRegistroP);
		@SuppressWarnings("unused")
		TextPrompt tcedula = new TextPrompt("Cédula de titular", tCedula);
		@SuppressWarnings("unused")
		TextPrompt tubicacion = new TextPrompt("Ciudad", tUbicacion);
		
		
	
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public MiBoton getBtGuardar() {
		return btGuardar;
	}

	public MiBoton getBtCancelar() {
		return btCancelar;
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

