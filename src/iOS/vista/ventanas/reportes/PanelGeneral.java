package iOS.vista.ventanas.reportes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import iOS.vista.componentes.LabelPersonalizado;

public class PanelGeneral extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3334371747289644272L;
	private LabelPersonalizado lInfo2;
	private LabelPersonalizado lInfo1;
	private JRadioButton rdTodo;
	private JRadioButton rdAlgunos;
	private JRadioButton rdHoy;
	private JRadioButton rdMes;
	private JRadioButton rdAnho;
	private JMonthChooser monthChooser;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JYearChooser yearChooser;
	private JRadioButton rdInactivo;
	private JRadioButton rdActivo;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private LabelPersonalizado lblrdTodo;
	private LabelPersonalizado lblrdAlgunos;
	private LabelPersonalizado lblrdHoy;
	private LabelPersonalizado lblrdMes;
	private LabelPersonalizado lblrdAnho;
	private LabelPersonalizado lblrdActivo;
	private LabelPersonalizado lblrdInactivo;

	/**
	 * Create the panel.
	 */
	public PanelGeneral() {
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
		lblprsnlzdFiltrar.setText("Indique los criterios para filtro");

		rdTodo = new JRadioButton("Encontrar y Mostrar Todo");
		rdTodo.setBounds(0, 78, 400, 20);
		add(rdTodo);
		rdTodo.setSelected(true);
		rdTodo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdTodo = new LabelPersonalizado(0);
		lblrdTodo.setBounds(25, 98, 390, 15);
		add(lblrdTodo);
		lblrdTodo.setText("Busca todos los registros relacionados a tu perfil en el sistema");
		lblrdTodo.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdAlgunos = new JRadioButton("Encontrar y Mostrar Solo Vales");
		rdAlgunos.setBounds(0, 124, 400, 20);
		add(rdAlgunos);
		rdAlgunos.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdAlgunos = new LabelPersonalizado(0);
		lblrdAlgunos.setBounds(25, 144, 390, 15);
		add(lblrdAlgunos);
		lblrdAlgunos.setText("Busca todos los registros de vales relacionados a tu perfil en el sistema");
		lblrdAlgunos.setFont(new Font("Tahoma", Font.PLAIN, 12));

		LabelPersonalizado lblprsnlzdIndiqueElPeriodo = new LabelPersonalizado(0);
		lblprsnlzdIndiqueElPeriodo.setBounds(0, 170, 400, 20);
		add(lblprsnlzdIndiqueElPeriodo);
		lblprsnlzdIndiqueElPeriodo.setText("Indique el periodo de tiempo");
		lblprsnlzdIndiqueElPeriodo.setFont(new Font("Tahoma", Font.BOLD, 18));

		rdHoy = new JRadioButton("Registros de Hoy");
		rdHoy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthChooser.setVisible(false);
				yearChooser.setVisible(false);
			}
		});
		rdHoy.setBounds(0, 200, 400, 20);
		add(rdHoy);
		rdHoy.setSelected(true);
		buttonGroup.add(rdHoy);
		rdHoy.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdHoy = new LabelPersonalizado(0);
		lblrdHoy.setBounds(25, 220, 390, 15);
		add(lblrdHoy);
		lblrdHoy.setText("Buscar los registros relacionados tu perfil generados HOY");
		lblrdHoy.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdMes = new JRadioButton("Registros del Mes");
		rdMes.setBounds(0, 246, 400, 20);
		add(rdMes);
		rdMes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthChooser.setVisible(true);
				yearChooser.setVisible(true);
			}
		});
		buttonGroup.add(rdMes);
		rdMes.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdMes = new LabelPersonalizado(0);
		lblrdMes.setBounds(25, 266, 390, 15);
		add(lblrdMes);
		lblrdMes.setText("Buscar los registros relacionados tu perfil generados en el MES");
		lblrdMes.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdAnho = new JRadioButton("Registros del A\u00F1o");
		rdAnho.setBounds(0, 292, 400, 20);
		add(rdAnho);
		rdAnho.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				monthChooser.setVisible(true);
				yearChooser.setVisible(true);
			}
		});
		buttonGroup.add(rdAnho);
		rdAnho.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdAnho = new LabelPersonalizado(0);
		lblrdAnho.setBounds(25, 312, 390, 15);
		add(lblrdAnho);
		lblrdAnho.setText("Buscar los registros relacionados tu perfil generados en el A\u00D1O");
		lblrdAnho.setFont(new Font("Tahoma", Font.PLAIN, 12));

		monthChooser = new JMonthChooser();
		monthChooser.setBounds(25, 338, 100, 30);
		add(monthChooser);
		monthChooser.setVisible(false);
		monthChooser.setMonth(12);

		yearChooser = new JYearChooser();
		yearChooser.setBounds(135, 338, 47, 30);
		add(yearChooser);

		LabelPersonalizado lblprsnlzdIndiqueElEstado = new LabelPersonalizado(0);
		lblprsnlzdIndiqueElEstado.setBounds(10, 379, 400, 20);
		add(lblprsnlzdIndiqueElEstado);
		lblprsnlzdIndiqueElEstado.setText("Indique el estado de los registros");
		lblprsnlzdIndiqueElEstado.setFont(new Font("Tahoma", Font.BOLD, 18));

		rdActivo = new JRadioButton("Registro con estado activo");
		rdActivo.setBounds(10, 409, 400, 20);
		add(rdActivo);
		buttonGroup_2.add(rdActivo);
		rdActivo.setSelected(true);
		rdActivo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdActivo = new LabelPersonalizado(0);
		lblrdActivo.setBounds(35, 429, 390, 15);
		add(lblrdActivo);
		lblrdActivo.setText("Registros con estado valido/activo/vigente.");
		lblrdActivo.setFont(new Font("Tahoma", Font.PLAIN, 12));

		rdInactivo = new JRadioButton("Registros con estado inactivo");
		rdInactivo.setBounds(10, 455, 400, 20);
		add(rdInactivo);
		buttonGroup_2.add(rdInactivo);
		rdInactivo.setFont(new Font("Tahoma", Font.PLAIN, 18));

		lblrdInactivo = new LabelPersonalizado(0);
		lblrdInactivo.setBounds(35, 475, 390, 15);
		add(lblrdInactivo);
		lblrdInactivo.setText("Registros con estado invalido/inactivo/no vigente");
		lblrdInactivo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		yearChooser.setVisible(false);

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

	public JRadioButton getRdTodo() {
		return rdTodo;
	}

	public JRadioButton getRdAlgunos() {
		return rdAlgunos;
	}

	public JRadioButton getRdHoy() {
		return rdHoy;
	}

	public JRadioButton getRdMes() {
		return rdMes;
	}

	public JRadioButton getRdAnho() {
		return rdAnho;
	}

	public JMonthChooser getMonthChooser() {
		return monthChooser;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

	public JYearChooser getYearChooser() {
		return yearChooser;
	}

	public JRadioButton getRdInactivo() {
		return rdInactivo;
	}

	public JRadioButton getRdActivo() {
		return rdActivo;
	}

	public ButtonGroup getButtonGroup_2() {
		return buttonGroup_2;
	}

	public LabelPersonalizado getLblrdTodo() {
		return lblrdTodo;
	}

	public LabelPersonalizado getLblrdAlgunos() {
		return lblrdAlgunos;
	}

	public LabelPersonalizado getLblrdHoy() {
		return lblrdHoy;
	}

	public LabelPersonalizado getLblrdMes() {
		return lblrdMes;
	}

	public LabelPersonalizado getLblrdAnho() {
		return lblrdAnho;
	}

	public LabelPersonalizado getLblrdActivo() {
		return lblrdActivo;
	}

	public LabelPersonalizado getLblrdInactivo() {
		return lblrdInactivo;
	}

}
