package iOS.vista.ventanas.reportes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import iOS.modelo.entidades.Colaborador;
import iOS.vista.componentes.LabelPersonalizado;

public class PanelEspecifico extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334371747289644272L;
	private LabelPersonalizado lInfo2;
	private LabelPersonalizado lInfo1;
	private JRadioButton rdTodoColaborador;
	private JRadioButton rdColaboradorEspecifico;
	private JRadioButton rdTipo1;
	private JRadioButton rdTipo2;
	private JRadioButton rdTipo3;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JComboBox<Colaborador> cbColaborador;

	/**
	 * Create the panel.
	 */
	public PanelEspecifico() {
		setLayout(null);

		lInfo1 = new LabelPersonalizado(0);
		lInfo1.setBounds(0, 0, 400, 20);
		add(lInfo1);
		lInfo1.setText("Reporte de Caja");
		lInfo1.setFont(new Font("Tahoma", Font.BOLD, 18));

		lInfo2 = new LabelPersonalizado(0);
		lInfo2.setBounds(10, 22, 390, 15);
		add(lInfo2);
		lInfo2.setText("Se han encontrado 1089 registros");
		lInfo2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		LabelPersonalizado lblprsnlzdFiltrar = new LabelPersonalizado(0);
		lblprsnlzdFiltrar.setBounds(0, 48, 400, 20);
		add(lblprsnlzdFiltrar);
		lblprsnlzdFiltrar.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblprsnlzdFiltrar.setText("Indique criterios de seleccion");

		rdTodoColaborador = new JRadioButton("Encontrar y Mostrar Todo de Todos");
		rdTodoColaborador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbColaborador.setVisible(false);
			}
		});
		rdTodoColaborador.setBounds(0, 78, 400, 20);
		add(rdTodoColaborador);
		rdTodoColaborador.setSelected(true);
		rdTodoColaborador.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdBuscaTodosLos = new LabelPersonalizado(0);
		lblprsnlzdBuscaTodosLos.setBounds(25, 98, 390, 15);
		add(lblprsnlzdBuscaTodosLos);
		lblprsnlzdBuscaTodosLos.setText("Busca todos los registros en el sistema");
		lblprsnlzdBuscaTodosLos.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdColaboradorEspecifico = new JRadioButton("Encontrar y Mostrar Todo de otro colaborador");
		rdColaboradorEspecifico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbColaborador.setVisible(true);
			}
		});
		rdColaboradorEspecifico.setBounds(0, 124, 400, 20);
		add(rdColaboradorEspecifico);
		rdColaboradorEspecifico.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdBuscaTodosLos_2 = new LabelPersonalizado(0);
		lblprsnlzdBuscaTodosLos_2.setBounds(25, 144, 390, 15);
		add(lblprsnlzdBuscaTodosLos_2);
		lblprsnlzdBuscaTodosLos_2.setText("Busca los registros del colaborador indicado en las opciones abajo\r\n");
		lblprsnlzdBuscaTodosLos_2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		LabelPersonalizado lblprsnlzdIndiqueElPeriodo = new LabelPersonalizado(0);
		lblprsnlzdIndiqueElPeriodo.setBounds(0, 211, 400, 20);
		add(lblprsnlzdIndiqueElPeriodo);
		lblprsnlzdIndiqueElPeriodo.setText("Indique tipo de registro");
		lblprsnlzdIndiqueElPeriodo.setFont(new Font("Tahoma", Font.BOLD, 18));

		rdTipo1 = new JRadioButton("Tipo 1");
		rdTipo1.setBounds(0, 241, 400, 20);
		add(rdTipo1);
		rdTipo1.setSelected(true);
		buttonGroup.add(rdTipo1);
		rdTipo1.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdBuscarLosRegistros = new LabelPersonalizado(0);
		lblprsnlzdBuscarLosRegistros.setBounds(25, 261, 390, 15);
		add(lblprsnlzdBuscarLosRegistros);
		lblprsnlzdBuscarLosRegistros.setText("Buscar registros de este tipo");
		lblprsnlzdBuscarLosRegistros.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdTipo2 = new JRadioButton("Tipo 2");
		rdTipo2.setBounds(0, 287, 400, 20);
		add(rdTipo2);
		buttonGroup.add(rdTipo2);
		rdTipo2.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdBuscaTodosLos_2_1 = new LabelPersonalizado(0);
		lblprsnlzdBuscaTodosLos_2_1.setBounds(25, 307, 390, 15);
		add(lblprsnlzdBuscaTodosLos_2_1);
		lblprsnlzdBuscaTodosLos_2_1.setText("Buscar registros de este tipo");
		lblprsnlzdBuscaTodosLos_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdTipo3 = new JRadioButton("Tipo 3");
		rdTipo3.setBounds(0, 333, 400, 20);
		add(rdTipo3);
		buttonGroup.add(rdTipo3);
		rdTipo3.setFont(new Font("Tahoma", Font.PLAIN, 18));

		LabelPersonalizado lblprsnlzdBuscaTodosLos_2_1_1 = new LabelPersonalizado(0);
		lblprsnlzdBuscaTodosLos_2_1_1.setBounds(25, 353, 390, 15);
		add(lblprsnlzdBuscaTodosLos_2_1_1);
		lblprsnlzdBuscaTodosLos_2_1_1.setText("Buscar registros de este tipo");
		lblprsnlzdBuscaTodosLos_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));

		cbColaborador = new JComboBox<Colaborador>();
		cbColaborador.setVisible(false);
		cbColaborador.setBounds(25, 163, 390, 30);
		add(cbColaborador);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LabelPersonalizado getlInfo2() {
		return lInfo2;
	}

	public LabelPersonalizado getlInfo1() {
		return lInfo1;
	}

	public JRadioButton getRdTodoColaborador() {
		return rdTodoColaborador;
	}

	public JRadioButton getRdColaboradorEspecifico() {
		return rdColaboradorEspecifico;
	}

	public JRadioButton getRdTipo1() {
		return rdTipo1;
	}

	public JRadioButton getRdTipo2() {
		return rdTipo2;
	}

	public JRadioButton getRdTipo3() {
		return rdTipo3;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public ButtonGroup getButtonGroup_2() {
		return buttonGroup_2;
	}

	public JComboBox<Colaborador> getCbColaborador() {
		return cbColaborador;
	}

	

}
