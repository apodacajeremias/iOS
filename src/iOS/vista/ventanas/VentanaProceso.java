package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import iOS.controlador.ventanas.VentanaProcesoControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;
import iOS.vista.ventanas.principales.VentanaAcceso;

/**
 * @author 59598
 *
 */
public class VentanaProceso extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569012102467509839L;
	public String modulo = "PROCESO";
	private CampoTextoPersonalizado tNombreCompleto;
	private VentanaProcesoControlador controlador;
	private LabelPersonalizado lblFechaRegistro;
	private LabelPersonalizado lblColaborador;
	private LabelPersonalizado lblID;
	private LabelPersonalizado lblEstado;
	private JLabel lIcono;
	private MiBoton btnAbrirArchivos;

	public void setUpControlador() {
		controlador = new VentanaProcesoControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaProceso() {
		setTitle("Formulario de Cliente");

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreCompleto.setBounds(10, 35, 432, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdNombreCompleto.setText("Nombre del proceso");
		lblprsnlzdNombreCompleto.setBounds(10, 10, 307, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		lblID = new LabelPersonalizado(0);
		lblID.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblID.setText("ID 000");
		lblID.setBounds(20, 141, 432, 20);
		getPanelFormulario().add(lblID);

		lblFechaRegistro = new LabelPersonalizado(0);
		lblFechaRegistro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFechaRegistro.setText("Registrado en diciembre 12, 2021");
		lblFechaRegistro.setBounds(20, 201, 432, 20);
		getPanelFormulario().add(lblFechaRegistro);

		lblColaborador = new LabelPersonalizado(0);
		lblColaborador.setText("Registrado por ADMIN ROOT");
		lblColaborador.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblColaborador.setBounds(20, 170, 432, 20);
		getPanelFormulario().add(lblColaborador);

		lblEstado = new LabelPersonalizado(0);
		lblEstado.setText("MAQUINA ACTIVA");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblEstado.setBounds(20, 232, 432, 20);
		getPanelFormulario().add(lblEstado);

		lIcono = new JLabel("");
		lIcono.setBounds(161, 76, 65, 65);
		getPanelFormulario().add(lIcono);
		lIcono.setIcon(new ImageIcon(VentanaAcceso.class.getResource("/img/NoDisponible.png")));
		lIcono.setForeground(Color.DARK_GRAY);
		lIcono.setHorizontalAlignment(SwingConstants.CENTER);

		LabelPersonalizado lblprsnlzdAgregarIcono = new LabelPersonalizado(0);
		lblprsnlzdAgregarIcono.setText("Agregar Icono");
		lblprsnlzdAgregarIcono.setBounds(236, 80, 100, 15);
		getPanelFormulario().add(lblprsnlzdAgregarIcono);

		LabelPersonalizado lblprsnlzdpxpx = new LabelPersonalizado(0);
		lblprsnlzdpxpx.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblprsnlzdpxpx.setText("64px * 64px");
		lblprsnlzdpxpx.setBounds(236, 98, 100, 10);
		getPanelFormulario().add(lblprsnlzdpxpx);

		btnAbrirArchivos = new MiBoton((String) null);
		btnAbrirArchivos.setActionCommand("AbrirArchivos");
		btnAbrirArchivos.setText("...");
		btnAbrirArchivos.setBounds(236, 110, 100, 20);
		getPanelFormulario().add(btnAbrirArchivos);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public CampoTextoPersonalizado gettNombreCompleto() {
		return tNombreCompleto;
	}

	public VentanaProcesoControlador getControlador() {
		return controlador;
	}

	public LabelPersonalizado getLblFechaRegistro() {
		return lblFechaRegistro;
	}

	public LabelPersonalizado getLblColaborador() {
		return lblColaborador;
	}

	public LabelPersonalizado getLblID() {
		return lblID;
	}

	public LabelPersonalizado getLblEstado() {
		return lblEstado;
	}

	public JLabel getlIcono() {
		return lIcono;
	}

	public MiBoton getBtnAbrirArchivos() {
		return btnAbrirArchivos;
	}

}
