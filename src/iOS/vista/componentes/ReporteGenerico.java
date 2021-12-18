package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import iOS.vista.ventanas.reportes.PanelEspecifico;
import iOS.vista.ventanas.reportes.PanelGeneral;

public class ReporteGenerico extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8635421035353331637L;
	private JTable table;
	private MiBoton btnBuscar;
	private JScrollPane scrollPane;
	private PanelGeneral panelGeneral;
	private PanelEspecifico panelEspecifico;
	private JTabbedPane tabbedPane;
	private MiBoton btnImprimir;
	private MiBoton btnActualizar;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the dialog.
	 */
	public ReporteGenerico() {
		getContentPane().setBackground(new Color(255, 255, 255));
		setFont(new Font("Tahoma", Font.BOLD, 12));
		setBackground(new Color(51, 51, 51));
		setBounds(100, 100, 1280, 640);
		setLocationRelativeTo(this);
		setResizable(false);
		setModal(true);
		setTitle("REPORTE");
		getContentPane().setLayout(null);
		setType(Type.NORMAL);
		setAlwaysOnTop(true);

		btnActualizar = new MiBoton("Actualizar");
		btnActualizar.setText("Actualizar");
		btnActualizar.setBounds(1144, 11, 100, 30);
		getContentPane().add(btnActualizar);

		btnImprimir = new MiBoton("Imprimir");
		btnImprimir.setText("Imprimir");
		btnImprimir.setBounds(1034, 11, 100, 30);
		getContentPane().add(btnImprimir);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(454, 52, 800, 538);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		btnBuscar = new MiBoton("Buscar");
		btnBuscar.setText("Filtrar");
		btnBuscar.setBounds(924, 11, 100, 30);
		getContentPane().add(btnBuscar);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 434, 579);
		getContentPane().add(tabbedPane);

		panelGeneral = new PanelGeneral();
		buttonGroup.add(panelGeneral.getRdTodo());
		buttonGroup.add(panelGeneral.getRdAlgunos());
		tabbedPane.addTab("Criterios General", null, panelGeneral, null);

		panelEspecifico = new PanelEspecifico();
		buttonGroup.add(panelEspecifico.getRdColaboradorEspecifico());
		buttonGroup.add(panelEspecifico.getRdTodoColaborador());
		tabbedPane.addTab("Criterios Especifico", null, panelEspecifico, null);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTable() {
		return table;
	}

	public MiBoton getBtnBuscar() {
		return btnBuscar;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public PanelGeneral getPanelGeneral() {
		return panelGeneral;
	}

	public PanelEspecifico getPanelEspecifico() {
		return panelEspecifico;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public MiBoton getBtnImprimir() {
		return btnImprimir;
	}

	public MiBoton getBtnActualizar() {
		return btnActualizar;
	}

	public ButtonGroup getButtonGroup() {
		return buttonGroup;
	}

}
