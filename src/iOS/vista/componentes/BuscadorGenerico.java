package iOS.vista.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class BuscadorGenerico extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1140798880877093242L;
	private CampoTextoPersonalizado tBuscador;
	private ToolbarBuscador toolbar;
	private JTable table;


	
	/**
	 * Create the dialog.
	 */
	public BuscadorGenerico() {
		getContentPane().setBackground(Color.WHITE);
		setBounds(100, 100, 500, 650);
		setResizable(false);
		setType(Type.NORMAL);
		setLocationRelativeTo(this);
		setModal(true);
		getContentPane().setLayout(null);
		
		tBuscador = new CampoTextoPersonalizado();
		tBuscador.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tBuscador.setBounds(10, 11, 474, 30);
		tBuscador.mayusculas();
		getContentPane().add(tBuscador);
		tBuscador.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 52, 474, 507);
		scrollPane.setToolTipText("Doble clic para modificar");
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		table.setRowHeight(25);
		scrollPane.setViewportView(table);
		
		toolbar = new ToolbarBuscador();
		toolbar.getbtSalir().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		toolbar.setBounds(10, 570, 474, 40);
		getContentPane().add(toolbar);

	}

	public JTable getTable() {
		return table;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public CampoTextoPersonalizado gettBuscador() {
		return tBuscador;
	}



	public ToolbarBuscador getToolbar() {
		return toolbar;
	}

}
