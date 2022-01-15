package iOS.vista.ventanas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import iOS.controlador.ventanas.VentanaClienteControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;
import iOS.vista.componentes.VentanaGenerica;

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
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdB2C;
	private JRadioButton rdB2B;
	private JTable listaRepresentantes;
	private JPanel panel;
	private MiBoton btnBuscarRepresentante;

	public void setUpControlador() {
		controlador = new VentanaClienteControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public VentanaCliente() {
		getMiToolBar().setLocation(10, 420);
		getlMensaje().setLocation(20, 394);
		getPanelFormulario().setLocation(10, 6);
		getPanelFormulario().setSize(474, 250);
		setTitle("Formulario de Cliente");

		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tNombreCompleto.setBounds(8, 56, 432, 30);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);

		tIdentificacion = new CampoTextoPersonalizado();
		tIdentificacion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tIdentificacion.setBounds(8, 110, 200, 30);
		tIdentificacion.mayusculas();
		tIdentificacion.limite(12);
		getPanelFormulario().add(tIdentificacion);

		tContacto = new CampoTextoPersonalizado();
		tContacto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tContacto.setBounds(8, 164, 200, 30);
		tContacto.limite(12);
		getPanelFormulario().add(tContacto);

		tDireccion = new CampoTextoPersonalizado();
		tDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tDireccion.setBounds(8, 218, 450, 30);
		tDireccion.limite(80);
		tDireccion.mayusculas();
		getPanelFormulario().add(tDireccion);

		LabelPersonalizado lblprsnlzdNombreCompleto = new LabelPersonalizado(0);
		lblprsnlzdNombreCompleto.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdNombreCompleto.setText("Nombre completo del cliente");
		lblprsnlzdNombreCompleto.setBounds(8, 34, 258, 20);
		getPanelFormulario().add(lblprsnlzdNombreCompleto);

		LabelPersonalizado lblprsnlzdIdentificacion_1 = new LabelPersonalizado(0);
		lblprsnlzdIdentificacion_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdIdentificacion_1.setText("Documento de Identidad");
		lblprsnlzdIdentificacion_1.setBounds(8, 88, 280, 20);
		getPanelFormulario().add(lblprsnlzdIdentificacion_1);

		LabelPersonalizado lblprsnlzdContacto_1 = new LabelPersonalizado(0);
		lblprsnlzdContacto_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdContacto_1.setText("Tel\u00E9fono para contacto");
		lblprsnlzdContacto_1.setBounds(8, 142, 209, 20);
		getPanelFormulario().add(lblprsnlzdContacto_1);

		LabelPersonalizado lblprsnlzdDireccion = new LabelPersonalizado(0);
		lblprsnlzdDireccion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblprsnlzdDireccion.setText("Direccion de vivienda");
		lblprsnlzdDireccion.setBounds(8, 196, 230, 20);
		getPanelFormulario().add(lblprsnlzdDireccion);

		rdB2C = new JRadioButton("B2C - Persona F\u00EDsica");
		rdB2C.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel.setVisible(false);
			}
		});
		buttonGroup.add(rdB2C);
		rdB2C.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdB2C.setBounds(8, 2, 225, 30);
		getPanelFormulario().add(rdB2C);

		rdB2B = new JRadioButton("B2B - Persona Jur\u00EDdica");
		rdB2B.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setVisible(true);
			}
		});
		buttonGroup.add(rdB2B);
		rdB2B.setFont(new Font("Tahoma", Font.PLAIN, 18));
		rdB2B.setBounds(241, 2, 225, 30);
		getPanelFormulario().add(rdB2B);

		panel = new JPanel();
		panel.setVisible(false);
		panel.setBounds(20, 262, 450, 126);
		getContentPane().add(panel);
		panel.setLayout(null);

		LabelPersonalizado lblprsnlzdRepresentantesDeLa = new LabelPersonalizado(0);
		lblprsnlzdRepresentantesDeLa.setHorizontalAlignment(SwingConstants.CENTER);
		lblprsnlzdRepresentantesDeLa.setBounds(0, 3, 380, 20);
		panel.add(lblprsnlzdRepresentantesDeLa);
		lblprsnlzdRepresentantesDeLa.setText("Representantes de la empresa");
		lblprsnlzdRepresentantesDeLa.setFont(new Font("Tahoma", Font.PLAIN, 18));

		listaRepresentantes = new JTable();
		listaRepresentantes.setBounds(0, 26, 450, 100);
		panel.add(listaRepresentantes);

		btnBuscarRepresentante = new MiBoton("...");
		btnBuscarRepresentante.setActionCommand("Buscar");
		btnBuscarRepresentante.setText("...");
		btnBuscarRepresentante.setBounds(390, 3, 50, 20);
		panel.add(btnBuscarRepresentante);
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

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public JRadioButton getRdB2C() {
		return rdB2C;
	}

	public JRadioButton getRdB2B() {
		return rdB2B;
	}

	public JTable getListaRepresentantes() {
		return listaRepresentantes;
	}

	public JPanel getPanel() {
		return panel;
	}

	public MiBoton getBtnBuscarRepresentante() {
		return btnBuscarRepresentante;
	}

}
