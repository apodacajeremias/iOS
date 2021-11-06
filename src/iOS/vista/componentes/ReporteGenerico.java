package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;

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

import com.toedter.calendar.JDateChooser;

public class ReporteGenerico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167363763266536167L;
	private JTable table;
	private MiBoton btnFiltrar;
	private MiBoton btnImprimir;
	@SuppressWarnings("rawtypes")
	private JComboBox cbColaborador;
	private JRadioButton rb1;
	private JRadioButton rb2;
	private LabelPersonalizado l1;
	private JLabel l2;
	private JLabel lblPorPeriodos;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JPanel panel;
	private JRadioButton rb3;
	private JRadioButton rb4;
	private JRadioButton rb5;
	private JRadioButton rb6;
	private MiBoton btnLimpiar;
	private JDateChooser dateChooser;

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public ReporteGenerico() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("Reporte");
		getContentPane().setLayout(null);
		setType(Type.NORMAL);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 198, 1254, 402);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 11, 1, 68);
		getContentPane().add(separator_2);

		l1 = new LabelPersonalizado( 20);
		l1.setText("Registros");
		l1.setBounds(10, 162, 95, 25);
		getContentPane().add(l1);

		l2 = new JLabel("Doble click sobre registro para abrir");
		l2.setForeground(Color.LIGHT_GRAY);
		l2.setFont(new Font("Tahoma", Font.ITALIC, 9));
		l2.setBounds(115, 173, 150, 14);
		getContentPane().add(l2);

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Criterios", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 1254, 140);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnFiltrar = new MiBoton("Filtrar");
		btnFiltrar.setBounds(1107, 12, 100, 30);
		panel.add(btnFiltrar);
		btnFiltrar.setActionCommand("Filtrar");

		btnImprimir = new MiBoton("Imprimir");
		btnImprimir.setBounds(1107, 54, 100, 30);
		panel.add(btnImprimir);
		btnImprimir.setActionCommand("Imprimir");
		
		lblNewLabel = new LabelPersonalizado(0);
		lblNewLabel.setText("Seleccione el colaborador");
		lblNewLabel.setBounds(43, 12, 249, 30);
		panel.add(lblNewLabel);
		
		cbColaborador = new JComboBox();
		cbColaborador.setFont(new Font("Tahoma", Font.BOLD, 12));
		cbColaborador.setBounds(43, 54, 250, 30);
		panel.add(cbColaborador);
		
		lblPorPeriodos = new LabelPersonalizado(0);
		lblPorPeriodos.setText("Indique una fecha");
		lblPorPeriodos.setBounds(335, 12, 150, 30);
		panel.add(lblPorPeriodos);
		
		rb1 = new JRadioButton("Pedidos");
		rb1.setSelected(true);
		rb1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb1.setBounds(528, 54, 150, 30);
		panel.add(rb1);
		
		rb2 = new JRadioButton("Presupuestos");
		rb2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb2.setBounds(528, 96, 150, 30);
		panel.add(rb2);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1);
		bg.add(rb2);
		
		rb3 = new JRadioButton("Vigentes");
		rb3.setSelected(true);
		rb3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb3.setBounds(721, 54, 150, 30);
		panel.add(rb3);
		
		rb4 = new JRadioButton("Anulados");
		rb4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb4.setBounds(721, 96, 150, 30);
		panel.add(rb4);
		
		ButtonGroup bg2 = new ButtonGroup();
		bg2.add(rb3);
		bg2.add(rb4);
		
		rb5 = new JRadioButton("Reporte Diario");
		rb5.setSelected(true);
		rb5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb5.setBounds(914, 54, 150, 30);
		panel.add(rb5);
		
		rb6 = new JRadioButton("Reporte Mensual");
		rb6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rb6.setBounds(914, 96, 150, 30);
		panel.add(rb6);
		
		ButtonGroup bg3 = new ButtonGroup();
		bg3.add(rb5);
		bg3.add(rb6);
		
		LabelPersonalizado lblprsnlzdTipo = new LabelPersonalizado(0);
		lblprsnlzdTipo.setText("Tipo de registro");
		lblprsnlzdTipo.setBounds(528, 12, 150, 30);
		panel.add(lblprsnlzdTipo);
		
		LabelPersonalizado lblprsnlzdEstado = new LabelPersonalizado(0);
		lblprsnlzdEstado.setText("Estado del registro");
		lblprsnlzdEstado.setBounds(721, 12, 150, 30);
		panel.add(lblprsnlzdEstado);
		
		LabelPersonalizado lblprsnlzdClaseReporte = new LabelPersonalizado(0);
		lblprsnlzdClaseReporte.setText("Tipo del reporte");
		lblprsnlzdClaseReporte.setBounds(914, 12, 150, 30);
		panel.add(lblprsnlzdClaseReporte);
		
		btnLimpiar = new MiBoton("Imprimir");
		btnLimpiar.setText("Limpiar");
		btnLimpiar.setActionCommand("Limpiar");
		btnLimpiar.setBounds(1107, 96, 100, 30);
		panel.add(btnLimpiar);
		
		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date());
		dateChooser.setBounds(335, 54, 150, 30);
		panel.add(dateChooser);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTable() {
		return table;
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

	public JLabel getLblPorPeriodos() {
		return lblPorPeriodos;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
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

	public JRadioButton getRb5() {
		return rb5;
	}

	public JRadioButton getRb6() {
		return rb6;
	}

	public MiBoton getBtnLimpiar() {
		return btnLimpiar;
	}

	public JDateChooser getDateChooser() {
		return dateChooser;
	}


}
