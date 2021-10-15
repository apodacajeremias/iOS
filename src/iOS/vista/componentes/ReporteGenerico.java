package iOS.vista.componentes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class ReporteGenerico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3167363763266536167L;
	private JTable table;
	private JTable tableDetalle;
	private MiBoton btnFiltrar;
	private MiBoton btnImprimir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteGenerico dialog = new ReporteGenerico();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public ReporteGenerico() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 800, 600);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 113, 764, 200);
		getContentPane().add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		LabelPersonalizado lblDetalleDePedido = new LabelPersonalizado(14);
		lblDetalleDePedido.setText("Detalles del registro");
		lblDetalleDePedido.setBounds(10, 324, 135, 15);
		getContentPane().add(lblDetalleDePedido);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 350, 764, 200);
		getContentPane().add(scrollPane_1);

		tableDetalle = new JTable();
		scrollPane_1.setViewportView(tableDetalle);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 11, 1, 68);
		getContentPane().add(separator_2);

		LabelPersonalizado lblPedido = new LabelPersonalizado( 20);
		lblPedido.setText("Registros");
		lblPedido.setBounds(10, 87, 95, 25);
		getContentPane().add(lblPedido);

		JLabel lblDobleClickSobre = new JLabel("Doble click sobre pedido para abrir");
		lblDobleClickSobre.setForeground(Color.LIGHT_GRAY);
		lblDobleClickSobre.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblDobleClickSobre.setBounds(115, 96, 185, 14);
		getContentPane().add(lblDobleClickSobre);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(null, "Informaci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 764, 65);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnFiltrar = new MiBoton("Filtrar");
		btnFiltrar.setBounds(544, 24, 100, 30);
		panel.add(btnFiltrar);
		btnFiltrar.setActionCommand("Filtrar");

		btnImprimir = new MiBoton("Imprimir");
		btnImprimir.setText("Imprimir");
		btnImprimir.setBounds(654, 24, 100, 30);
		panel.add(btnImprimir);
		btnImprimir.setActionCommand("Imprimir");

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
	
}
