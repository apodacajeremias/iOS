package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class ReporteGenerico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167363763266536167L;
	private JTable table;
	private JTable tableDetalle;
	private MiBoton btnFiltrar;
	private MiBoton btnImprimir;
	@SuppressWarnings("rawtypes")
	private JComboBox cbColaborador;
	private JMonthChooser dcMeses;
	private JYearChooser dcAnos;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private LabelPersonalizado l1;
	private JLabel l2;
	private LabelPersonalizado l3;
	private JLabel lblPorPeriodos;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JRadioButton rb3;
	private JRadioButton rb4;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public ReporteGenerico() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 900, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 148, 864, 225);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		l3 = new LabelPersonalizado(14);
		l3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		l3.setText("Detalles del registro");
		l3.setBounds(10, 384, 135, 15);
		getContentPane().add(l3);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 400, 864, 150);
		getContentPane().add(scrollPane_1);

		tableDetalle = new JTable();
		scrollPane_1.setViewportView(tableDetalle);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 11, 1, 68);
		getContentPane().add(separator_2);

		l1 = new LabelPersonalizado( 20);
		l1.setText("Registros");
		l1.setBounds(10, 122, 95, 25);
		getContentPane().add(l1);

		l2 = new JLabel("Doble click sobre registro para abrir");
		l2.setForeground(Color.LIGHT_GRAY);
		l2.setFont(new Font("Tahoma", Font.ITALIC, 9));
		l2.setBounds(115, 133, 150, 14);
		getContentPane().add(l2);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Criterios", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 864, 100);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnFiltrar = new MiBoton("Filtrar");
		btnFiltrar.setBounds(754, 20, 100, 30);
		panel.add(btnFiltrar);
		btnFiltrar.setActionCommand("Filtrar");

		btnImprimir = new MiBoton("Imprimir");
		btnImprimir.setText("Imprimir");
		btnImprimir.setBounds(754, 61, 100, 30);
		panel.add(btnImprimir);
		btnImprimir.setActionCommand("Imprimir");
		
		lblNewLabel = new JLabel("Colaborador");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 36, 75, 14);
		panel.add(lblNewLabel);
		
		cbColaborador = new JComboBox();
		cbColaborador.setFont(new Font("Tahoma", Font.BOLD, 12));
		cbColaborador.setBounds(9, 61, 250, 25);
		panel.add(cbColaborador);
		
		dcMeses = new JMonthChooser();
		dcMeses.setBounds(268, 61, 99, 25);
		panel.add(dcMeses);
		
		dcAnos = new JYearChooser();
		dcAnos.setBounds(376, 61, 47, 25);
		panel.add(dcAnos);
		
		lblPorPeriodos = new JLabel("Por periodos");
		lblPorPeriodos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorPeriodos.setBounds(268, 36, 77, 14);
		panel.add(lblPorPeriodos);
		
		rb1 = new JRadioButton("Incluir");
		rb1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb1.setBounds(432, 25, 150, 25);
		panel.add(rb1);
		
		rb2 = new JRadioButton("Incluir");
		rb2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb2.setBounds(432, 61, 150, 25);
		panel.add(rb2);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		
		rb3 = new JRadioButton("Reporte Diario");
		rb3.setSelected(true);
		rb3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb3.setBounds(591, 25, 150, 25);
		panel.add(rb3);
		
		rb4 = new JRadioButton("Reporte Mensual");
		rb4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb4.setBounds(591, 61, 150, 25);
		panel.add(rb4);
		
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rb3);
		bg2.add(rb4);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTable() {
		return table;
	}

	public JTable getTableDetalle() {
		return tableDetalle;
	}

	public MiBoton getBtnFiltrar() {
		return btnFiltrar;
	}

	public MiBoton getBtnImprimir() {
		return btnImprimir;
	}

	@SuppressWarnings("rawtypes")
	public JComboBox getCbColaborador() {
		return cbColaborador;
	}

	public JMonthChooser getDcMeses() {
		return dcMeses;
	}

	public JYearChooser getDcAnos() {
		return dcAnos;
	}

	public JRadioButton getRb1() {
		return rb1;
	}

	public JRadioButton getRb2() {
		return rb2;
	}

	public LabelPersonalizado getL1() {
		return l1;
	}

	public JLabel getL2() {
		return l2;
	}

	public LabelPersonalizado getL3() {
		return l3;
	}

	public JLabel getLblPorPeriodos() {
		return lblPorPeriodos;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public JScrollPane getScrollPane_1() {
		return scrollPane_1;
	}

	public JLabel getLblNewLabel() {
		return lblNewLabel;
	}

	public JPanel getPanel() {
		return panel;
	}

	public JRadioButton getRb3() {
		return rb3;
	}

	public JRadioButton getRb4() {
		return rb4;
	}
	
	
	
}
