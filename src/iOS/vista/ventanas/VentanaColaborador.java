package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import iOS.controlador.ventanas.VentanaColaboradorControlador;
import iOS.vista.componentes.CampoNumeroPersonalizado;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaColaborador extends VentanaGenerica {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9055021089711321287L;
	public String modulo = "COLABORADOR";
	private JPasswordField tPassword;
	@SuppressWarnings("rawtypes")
	private JComboBox cbRol;
	private CampoTextoPersonalizado tNombreCompleto;
	private CampoTextoPersonalizado tIdentificacion;
	private CampoTextoPersonalizado tContacto;
	private JRadioButton rdActivarAcceso;
	private CampoTextoPersonalizado tUsuario;
	@SuppressWarnings("rawtypes")
	private JComboBox cbSector;
	private JRadioButton rdEsEncargado;
	private JRadioButton rdDesvinculado;
	private LabelPersonalizado lFechaVinculacion;
	private LabelPersonalizado lFechaDesvinculacion;
	@SuppressWarnings("rawtypes")
	private JComboBox cbTipoSalario;
	private CampoNumeroPersonalizado tValorSalario;

	private VentanaColaboradorControlador controlador;
	private JPanel pAcceso;
	private JPanel pLaboral;
	private JPanel pSalario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					VentanaColaborador dialog = new VentanaColaborador();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void setUpControlador() {
		controlador = new VentanaColaboradorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public VentanaColaborador() {
		getPanelFormulario().setBorder(
				new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getPanelFormulario().setBounds(18, 10, 320, 230);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setText("Nombre Completo");
		lblprsnlzdNombreCompleto.setBounds(10, 15, 110, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setBounds(10, 30, 300, 25);
		getPanelFormulario().add(tNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacin = new LabelPersonalizado(0);
		lblprsnlzdIdentificacin.setText("Identificaci\u00F3n");
		lblprsnlzdIdentificacin.setBounds(10, 66, 110, 15);
		getPanelFormulario().add(lblprsnlzdIdentificacin);

		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setBounds(10, 81, 300, 25);
		getPanelFormulario().add(tIdentificacion);

		LabelPersonalizado lblprsnlzdNombreCompleto_1_1 = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto_1_1.setText("Contacto");
		lblprsnlzdNombreCompleto_1_1.setBounds(10, 117, 110, 15);
		getPanelFormulario().add(lblprsnlzdNombreCompleto_1_1);

		tContacto = new CampoTextoPersonalizado();
		tContacto.setBounds(10, 131, 300, 25);
		getPanelFormulario().add(tContacto);
		getlMensaje().setSize(674, 20);
		getMiToolBar().setSize(674, 40);
		getlMensaje().setLocation(10, 490);
		getMiToolBar().setLocation(10, 520);
		setResizable(false);
		setBounds(100, 100, 700, 600);
		getContentPane().setLayout(null);

		pAcceso = new JPanel();
		pAcceso.setBorder(new TitledBorder(null, "Acceso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pAcceso.setBackground(Color.WHITE);
		pAcceso.setBounds(18, 251, 320, 230);
		getContentPane().add(pAcceso);
		pAcceso.setLayout(null);

		LabelPersonalizado lblprsnlzdUsuario = new LabelPersonalizado(0);
		lblprsnlzdUsuario.setText("Usuario");
		lblprsnlzdUsuario.setBounds(10, 52, 110, 15);
		pAcceso.add(lblprsnlzdUsuario);

		tUsuario = new CampoTextoPersonalizado();
		tUsuario.setBounds(10, 66, 300, 25);
		pAcceso.add(tUsuario);

		LabelPersonalizado lblprsnlzdContrasea = new LabelPersonalizado(0);
		lblprsnlzdContrasea.setText("Contrase\u00F1a");
		lblprsnlzdContrasea.setBounds(10, 102, 110, 15);
		pAcceso.add(lblprsnlzdContrasea);

		LabelPersonalizado lblprsnlzdNombreCompleto_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto_1_1_1.setText("Rol");
		lblprsnlzdNombreCompleto_1_1_1.setBounds(10, 152, 110, 15);
		pAcceso.add(lblprsnlzdNombreCompleto_1_1_1);

		tPassword = new JPasswordField();
		tPassword.setBounds(10, 116, 300, 25);
		pAcceso.add(tPassword);

		cbRol = new JComboBox();
		cbRol.setBounds(10, 165, 300, 25);
		pAcceso.add(cbRol);

		rdActivarAcceso = new JRadioButton("\u00BFActivar acceso?");
		rdActivarAcceso.setBackground(Color.WHITE);
		rdActivarAcceso.setActionCommand("ActivarAcceso");
		rdActivarAcceso.setBounds(10, 20, 300, 25);
		pAcceso.add(rdActivarAcceso);

		pLaboral = new JPanel();
		pLaboral.setBorder(
				new TitledBorder(null, "Informaci\u00F3n Laboral", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pLaboral.setBackground(Color.WHITE);
		pLaboral.setBounds(356, 10, 320, 230);
		getContentPane().add(pLaboral);
		pLaboral.setLayout(null);

		rdEsEncargado = new JRadioButton("\u00BFEs encargado?");
		rdEsEncargado.setBackground(Color.WHITE);
		rdEsEncargado.setBounds(10, 68, 300, 25);
		pLaboral.add(rdEsEncargado);

		LabelPersonalizado lblprsnlzdSector = new LabelPersonalizado(0);
		lblprsnlzdSector.setText("Sector");
		lblprsnlzdSector.setBounds(10, 20, 110, 15);
		pLaboral.add(lblprsnlzdSector);

		cbSector = new JComboBox();
		cbSector.setBounds(10, 36, 300, 25);
		pLaboral.add(cbSector);

		rdDesvinculado = new JRadioButton("\u00BFDesvinculado?");
		rdDesvinculado.setBackground(Color.WHITE);
		rdDesvinculado.setBounds(10, 102, 300, 25);
		pLaboral.add(rdDesvinculado);

		lFechaVinculacion = new LabelPersonalizado(0);
		lFechaVinculacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lFechaVinculacion.setBounds(10, 134, 300, 15);
		pLaboral.add(lFechaVinculacion);

		lFechaDesvinculacion = new LabelPersonalizado(0);
		lFechaDesvinculacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lFechaDesvinculacion.setBounds(10, 160, 300, 15);
		pLaboral.add(lFechaDesvinculacion);

		pSalario = new JPanel();
		pSalario.setLayout(null);
		pSalario.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Salario", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSalario.setBackground(Color.WHITE);
		pSalario.setBounds(356, 251, 320, 230);
		getContentPane().add(pSalario);

		LabelPersonalizado lblprsnlzdTipo = new LabelPersonalizado(0);
		lblprsnlzdTipo.setText("Tipo");
		lblprsnlzdTipo.setBounds(10, 20, 110, 15);
		pSalario.add(lblprsnlzdTipo);

		cbTipoSalario = new JComboBox();
		cbTipoSalario.setModel(new DefaultComboBoxModel(new String[] { "MINIMO", "COMISION", "DIFERENCIAL" }));
		cbTipoSalario.setBounds(10, 36, 300, 25);
		pSalario.add(cbTipoSalario);

		LabelPersonalizado lblprsnlzdValor = new LabelPersonalizado(0);
		lblprsnlzdValor.setText("Valor");
		lblprsnlzdValor.setBounds(10, 72, 100, 15);
		pSalario.add(lblprsnlzdValor);

		tValorSalario = new CampoNumeroPersonalizado();
		tValorSalario.setBounds(10, 86, 300, 25);
		pSalario.add(tValorSalario);
	}

	public JPasswordField gettPassword() {
		return tPassword;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getCbRol() {
		return cbRol;
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

	public JRadioButton getRdActivarAcceso() {
		return rdActivarAcceso;
	}

	public CampoTextoPersonalizado gettUsuario() {
		return tUsuario;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getCbSector() {
		return cbSector;
	}

	public JRadioButton getRdEsEncargado() {
		return rdEsEncargado;
	}

	public JRadioButton getRdDesvinculado() {
		return rdDesvinculado;
	}

	public LabelPersonalizado getlFechaVinculacion() {
		return lFechaVinculacion;
	}

	public LabelPersonalizado getlFechaDesvinculacion() {
		return lFechaDesvinculacion;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getCbTipoSalario() {
		return cbTipoSalario;
	}

	public CampoNumeroPersonalizado gettValorSalario() {
		return tValorSalario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public VentanaColaboradorControlador getControlador() {
		return controlador;
	}

	public JPanel getpAcceso() {
		return pAcceso;
	}

	public JPanel getpLaboral() {
		return pLaboral;
	}

	public JPanel getpSalario() {
		return pSalario;
	}
}
