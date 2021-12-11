package iOS.vista.ventanas;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
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

	private CampoTextoPersonalizado tNombreCompleto;
	private CampoTextoPersonalizado tIdentificacion;
	private CampoTextoPersonalizado tContacto;
	private JRadioButton rdActivarAcceso;
	private CampoTextoPersonalizado tUsuario;

	private JRadioButton rdEsEncargado;
	private JRadioButton rdDesvinculado;
	private LabelPersonalizado lFechaVinculacion;
	private LabelPersonalizado lFechaDesvinculacion;

	private JComboBox<String> cbTipoSalario;

	private JComboBox<Object> cbSector;

	private JComboBox<Object> cbRol;
	private CampoNumeroPersonalizado tValorSalario;

	private VentanaColaboradorControlador controlador;
	private JPanel pAcceso;
	private JPanel pLaboral;
	private JPanel pSalario;

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
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdNombreCompleto.setText("Nombre Completo");
		lblprsnlzdNombreCompleto.setBounds(10, 20, 300, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreCompleto.setBounds(10, 40, 300, 25);
		getPanelFormulario().add(tNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacin = new LabelPersonalizado(0);
		lblprsnlzdIdentificacin.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdIdentificacin.setText("Identificaci\u00F3n");
		lblprsnlzdIdentificacin.setBounds(10, 76, 300, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacin);

		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tIdentificacion.setBounds(10, 95, 300, 25);
		getPanelFormulario().add(tIdentificacion);

		LabelPersonalizado lblprsnlzdNombreCompleto_1_1 = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdNombreCompleto_1_1.setText("Contacto");
		lblprsnlzdNombreCompleto_1_1.setBounds(10, 131, 300, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto_1_1);

		tContacto = new CampoTextoPersonalizado();
		tContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tContacto.setBounds(10, 151, 300, 25);
		getPanelFormulario().add(tContacto);
		getlMensaje().setSize(674, 20);
		getMiToolBar().setSize(674, 40);
		getlMensaje().setLocation(10, 490);
		getMiToolBar().setLocation(10, 520);
		setResizable(false);
		setBounds(100, 100, 700, 600);
		getContentPane().setLayout(null);

		pAcceso = new JPanel();
		pAcceso.setVisible(false);
		pAcceso.setBorder(new TitledBorder(null, "Acceso", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pAcceso.setBackground(Color.WHITE);
		pAcceso.setBounds(18, 281, 320, 200);
		getContentPane().add(pAcceso);
		pAcceso.setLayout(null);

		LabelPersonalizado lblprsnlzdUsuario = new LabelPersonalizado(0);
		lblprsnlzdUsuario.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdUsuario.setText("Usuario");
		lblprsnlzdUsuario.setBounds(10, 20, 300, 20);
		pAcceso.add(lblprsnlzdUsuario);

		tUsuario = new CampoTextoPersonalizado();
		tUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tUsuario.setBounds(10, 40, 300, 25);
		pAcceso.add(tUsuario);

		LabelPersonalizado lblprsnlzdContrasea = new LabelPersonalizado(0);
		lblprsnlzdContrasea.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdContrasea.setText("Contrase\u00F1a");
		lblprsnlzdContrasea.setBounds(10, 72, 300, 20);
		pAcceso.add(lblprsnlzdContrasea);

		LabelPersonalizado lblprsnlzdNombreCompleto_1_1_1 = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdNombreCompleto_1_1_1.setText("Rol");
		lblprsnlzdNombreCompleto_1_1_1.setBounds(10, 135, 300, 20);
		pAcceso.add(lblprsnlzdNombreCompleto_1_1_1);

		tPassword = new JPasswordField();
		tPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tPassword.setBounds(9, 92, 300, 25);
		pAcceso.add(tPassword);

		cbRol = new JComboBox();
		cbRol.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbRol.setBounds(10, 164, 300, 25);
		pAcceso.add(cbRol);

		pLaboral = new JPanel();
		pLaboral.setBorder(
				new TitledBorder(null, "Informaci\u00F3n Laboral", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pLaboral.setBackground(Color.WHITE);
		pLaboral.setBounds(356, 10, 320, 230);
		getContentPane().add(pLaboral);
		pLaboral.setLayout(null);

		rdEsEncargado = new JRadioButton("\u00BFEs encargado?");
		rdEsEncargado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdEsEncargado.setBackground(Color.WHITE);
		rdEsEncargado.setBounds(10, 90, 300, 25);
		pLaboral.add(rdEsEncargado);

		LabelPersonalizado lblprsnlzdSector = new LabelPersonalizado(0);
		lblprsnlzdSector.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdSector.setText("Sector");
		lblprsnlzdSector.setBounds(10, 20, 300, 20);
		pLaboral.add(lblprsnlzdSector);

		cbSector = new JComboBox();
		cbSector.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbSector.setBounds(10, 50, 300, 25);
		pLaboral.add(cbSector);

		rdDesvinculado = new JRadioButton("\u00BFDesvinculado?");
		rdDesvinculado.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdDesvinculado.setBackground(Color.WHITE);
		rdDesvinculado.setBounds(10, 130, 300, 25);
		pLaboral.add(rdDesvinculado);

		lFechaVinculacion = new LabelPersonalizado(0);
		lFechaVinculacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lFechaVinculacion.setBounds(10, 170, 300, 15);
		pLaboral.add(lFechaVinculacion);

		lFechaDesvinculacion = new LabelPersonalizado(0);
		lFechaDesvinculacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lFechaDesvinculacion.setBounds(10, 200, 300, 15);
		pLaboral.add(lFechaDesvinculacion);

		pSalario = new JPanel();
		pSalario.setLayout(null);
		pSalario.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Salario", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		pSalario.setBackground(Color.WHITE);
		pSalario.setBounds(356, 251, 320, 230);
		getContentPane().add(pSalario);

		LabelPersonalizado lblprsnlzdTipo = new LabelPersonalizado(0);
		lblprsnlzdTipo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdTipo.setText("Tipo");
		lblprsnlzdTipo.setBounds(10, 28, 300, 20);
		pSalario.add(lblprsnlzdTipo);

		cbTipoSalario = new JComboBox();
		cbTipoSalario.setModel(new DefaultComboBoxModel(new String[] { "MINIMO", "COMISION", "DIFERENCIAL" }));
		cbTipoSalario.setBounds(10, 50, 300, 25);
		pSalario.add(cbTipoSalario);

		LabelPersonalizado lblprsnlzdValor = new LabelPersonalizado(0);
		lblprsnlzdValor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdValor.setText("Valor");
		lblprsnlzdValor.setBounds(10, 86, 300, 20);
		pSalario.add(lblprsnlzdValor);

		tValorSalario = new CampoNumeroPersonalizado();
		tValorSalario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tValorSalario.setBounds(10, 105, 300, 25);
		pSalario.add(tValorSalario);

		rdActivarAcceso = new JRadioButton("\u00BFActivar acceso?");
		rdActivarAcceso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rdActivarAcceso.isSelected()) {
					pAcceso.setVisible(true);
				} else {
					pAcceso.setVisible(false);
				}
			}
		});
		rdActivarAcceso.setBounds(38, 251, 300, 25);
		getContentPane().add(rdActivarAcceso);
		rdActivarAcceso.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdActivarAcceso.setBackground(Color.WHITE);
		rdActivarAcceso.setActionCommand("ActivarAcceso");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getModulo() {
		return modulo;
	}

	public JPasswordField gettPassword() {
		return tPassword;
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

	public JComboBox<String> getCbTipoSalario() {
		return cbTipoSalario;
	}

	public JComboBox<Object> getCbSector() {
		return cbSector;
	}

	public JComboBox<Object> getCbRol() {
		return cbRol;
	}

	public CampoNumeroPersonalizado gettValorSalario() {
		return tValorSalario;
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
