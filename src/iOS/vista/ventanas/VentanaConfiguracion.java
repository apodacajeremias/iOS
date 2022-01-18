package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import iOS.controlador.ventanas.VentanaConfiguracionControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;
import iOS.vista.ventanas.principales.VentanaAcceso;

public class VentanaConfiguracion extends VentanaGenerica {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3326623937564321138L;
	public String modulo = "CONFIGURACION";
	private VentanaConfiguracionControlador Controlador;
	private CampoTextoPersonalizado tNombreEmpresa;
	private LabelPersonalizado lblAccion;
	private CampoTextoPersonalizado tRUC;
	private CampoTextoPersonalizado tTelefono;
	private CampoTextoPersonalizado tSitioWeb;
	private CampoTextoPersonalizado tDireccion;
	private MiBoton btnAbrirArchivos;
	private JLabel lIcono;

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
		lblNombreEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreEmpresa.setBounds(12, 10, 450, 20);
		getPanelFormulario().add(lblNombreEmpresa);
		lblNombreEmpresa.setText("Nombre de la empresa");
		tNombreEmpresa = new CampoTextoPersonalizado();
		tNombreEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreEmpresa.setBounds(12, 32, 450, 30);
		getPanelFormulario().add(tNombreEmpresa);

		LabelPersonalizado lblprsnlzdRucDeLa = new LabelPersonalizado(16);
		lblprsnlzdRucDeLa.setText("RUC de la empresa");
		lblprsnlzdRucDeLa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdRucDeLa.setBounds(12, 73, 220, 20);
		getPanelFormulario().add(lblprsnlzdRucDeLa);

		tRUC = new CampoTextoPersonalizado();
		tRUC.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tRUC.setBounds(12, 95, 220, 30);
		getPanelFormulario().add(tRUC);

		LabelPersonalizado lblNombreEmpresa_1_1 = new LabelPersonalizado(16);
		lblNombreEmpresa_1_1.setText("Telefono de la empresa");
		lblNombreEmpresa_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreEmpresa_1_1.setBounds(12, 136, 220, 20);
		getPanelFormulario().add(lblNombreEmpresa_1_1);

		tTelefono = new CampoTextoPersonalizado();
		tTelefono.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tTelefono.setBounds(12, 158, 220, 30);
		getPanelFormulario().add(tTelefono);

		LabelPersonalizado lblNombreEmpresa_1_1_1 = new LabelPersonalizado(16);
		lblNombreEmpresa_1_1_1.setText("Sitio Web de la empresa");
		lblNombreEmpresa_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreEmpresa_1_1_1.setBounds(12, 199, 450, 20);
		getPanelFormulario().add(lblNombreEmpresa_1_1_1);

		tSitioWeb = new CampoTextoPersonalizado();
		tSitioWeb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tSitioWeb.setBounds(12, 221, 450, 30);
		getPanelFormulario().add(tSitioWeb);

		LabelPersonalizado lblNombreEmpresa_1_1_1_1 = new LabelPersonalizado(16);
		lblNombreEmpresa_1_1_1_1.setText("Ciudad y Direccion de la empresa");
		lblNombreEmpresa_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreEmpresa_1_1_1_1.setBounds(12, 262, 450, 20);
		getPanelFormulario().add(lblNombreEmpresa_1_1_1_1);

		tDireccion = new CampoTextoPersonalizado();
		tDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tDireccion.setBounds(12, 284, 450, 30);
		getPanelFormulario().add(tDireccion);

		LabelPersonalizado lblNombreEmpresa_1_1_1_1_1 = new LabelPersonalizado(16);
		lblNombreEmpresa_1_1_1_1_1.setText("Logo de la empresa");
		lblNombreEmpresa_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNombreEmpresa_1_1_1_1_1.setBounds(242, 73, 220, 20);
		getPanelFormulario().add(lblNombreEmpresa_1_1_1_1_1);

		LabelPersonalizado lblprsnlzdAgregarIcono = new LabelPersonalizado(0);
		lblprsnlzdAgregarIcono.setText("Agregar Icono");
		lblprsnlzdAgregarIcono.setBounds(349, 136, 100, 15);
		getPanelFormulario().add(lblprsnlzdAgregarIcono);

		LabelPersonalizado lblprsnlzdpxpx = new LabelPersonalizado(0);
		lblprsnlzdpxpx.setText("64px * 64px");
		lblprsnlzdpxpx.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprsnlzdpxpx.setBounds(349, 154, 100, 10);
		getPanelFormulario().add(lblprsnlzdpxpx);

		btnAbrirArchivos = new MiBoton((String) null);
		btnAbrirArchivos.setText("...");
		btnAbrirArchivos.setActionCommand("AbrirArchivos");
		btnAbrirArchivos.setBounds(349, 166, 100, 20);
		getPanelFormulario().add(btnAbrirArchivos);

		lIcono = new JLabel("");
		lIcono.setHorizontalAlignment(SwingConstants.CENTER);
		lIcono.setForeground(Color.DARK_GRAY);
		lIcono.setBounds(274, 95, 65, 65);
		lIcono.setIcon(new ImageIcon(VentanaAcceso.class.getResource("/img/NoDisponible.png")));
		getPanelFormulario().add(lIcono);

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

	public LabelPersonalizado getLblAccion() {
		return lblAccion;
	}

	public CampoTextoPersonalizado gettRUC() {
		return tRUC;
	}

	public CampoTextoPersonalizado gettTelefono() {
		return tTelefono;
	}

	public CampoTextoPersonalizado gettSitioWeb() {
		return tSitioWeb;
	}

	public CampoTextoPersonalizado gettDireccion() {
		return tDireccion;
	}

	public MiBoton getBtnAbrirArchivos() {
		return btnAbrirArchivos;
	}

	public JLabel getlIcono() {
		return lIcono;
	}

}
