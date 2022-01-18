package iOS.vista.ventanas;

import java.awt.Font;

import iOS.controlador.ventanas.VentanaMaquinaControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * @author 59598
 *
 */
public class VentanaMaquina extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4569012102467509839L;
	public String modulo = "MAQUINA";
	private CampoTextoPersonalizado tNombreCompleto;
	private VentanaMaquinaControlador controlador;
	private LabelPersonalizado lblFechaRegistro;
	private LabelPersonalizado lblColaborador;
	private LabelPersonalizado lblID;
	private LabelPersonalizado lblEstado;
	private JComboBox<String> cbTipoMaquina;

	public void setUpControlador() {
		controlador = new VentanaMaquinaControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaMaquina() {
		setTitle("Formulario de Cliente");

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreCompleto.setBounds(20, 40, 432, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreCompleto.setText("Nombre descriptivo de la maquina");
		lblprsnlzdNombreCompleto.setBounds(20, 20, 307, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1.setText("Tipo de maquina");
		lblprsnlzdIdentificacion_1.setBounds(20, 80, 354, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		lblID = new LabelPersonalizado(0);
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblID.setText("ID 000");
		lblID.setBounds(20, 141, 432, 20);
		getPanelFormulario().add(lblID);

		lblFechaRegistro = new LabelPersonalizado(0);
		lblFechaRegistro.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblFechaRegistro.setText("Registrado en diciembre 12, 2021");
		lblFechaRegistro.setBounds(20, 201, 432, 20);
		getPanelFormulario().add(lblFechaRegistro);
		
		lblColaborador = new LabelPersonalizado(0);
		lblColaborador.setText("Registrado por ADMIN ROOT");
		lblColaborador.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblColaborador.setBounds(20, 170, 432, 20);
		getPanelFormulario().add(lblColaborador);
		
		lblEstado = new LabelPersonalizado(0);
		lblEstado.setText("MAQUINA ACTIVA");
		lblEstado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEstado.setBounds(20, 232, 432, 20);
		getPanelFormulario().add(lblEstado);
		
		cbTipoMaquina = new JComboBox<String>();
		cbTipoMaquina.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbTipoMaquina.setModel(new DefaultComboBoxModel<String>(new String[] {"Impresora", "Costura"}));
		cbTipoMaquina.setBounds(20, 100, 432, 30);
		getPanelFormulario().add(cbTipoMaquina);
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

	public VentanaMaquinaControlador getControlador() {
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

	public JComboBox<String> getCbTipoMaquina() {
		return cbTipoMaquina;
	}
	
	
}
