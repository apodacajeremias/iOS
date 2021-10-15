package iOS.vista.ventanas;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaSectorControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaSector extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196814908659540277L;
	private CampoTextoPersonalizado tNombreSector;
	private JTable tableDeposito;
	private VentanaSectorControlador controlador;
	private LabelPersonalizado lblDeposito;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaSector dialog = new VentanaSector();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setUpControlador() {
		controlador = new VentanaSectorControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public VentanaSector() {
		
		LabelPersonalizado lblprsnlzdNombreDelSector = new LabelPersonalizado(0);
		lblprsnlzdNombreDelSector.setText("Nombre del Sector");
		lblprsnlzdNombreDelSector.setBounds(12, 11, 113, 15);
		getPanelFormulario().add(lblprsnlzdNombreDelSector);
		
		tNombreSector = new CampoTextoPersonalizado();
		tNombreSector.mayusculas();
		tNombreSector.avisar();
		tNombreSector.setBounds(12, 27, 450, 30);
		getPanelFormulario().add(tNombreSector);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(12, 83, 450, 275);
		getPanelFormulario().add(scrollPane);
		
		tableDeposito = new JTable();
		tableDeposito.setEnabled(false);
		scrollPane.setViewportView(tableDeposito);
		
		lblDeposito = new LabelPersonalizado(0);
		lblDeposito.setVisible(false);
		lblDeposito.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				Font font = lblDeposito.getFont();
				HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
				attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
				lblDeposito.setFont(font.deriveFont(attributes));
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Font font = lblDeposito.getFont();
				HashMap<TextAttribute,Object> attributes = new HashMap<>(font.getAttributes());
				attributes.put(TextAttribute.UNDERLINE, -1);
				lblDeposito.setFont(font.deriveFont(attributes));
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		lblDeposito.setText("Depositos");
		lblDeposito.setBounds(13, 64, 66, 15);
		getPanelFormulario().add(lblDeposito);

	}

	public CampoTextoPersonalizado gettNombreSector() {
		return tNombreSector;
	}

	public void settNombreSector(CampoTextoPersonalizado tNombreSector) {
		this.tNombreSector = tNombreSector;
	}

	public JTable getTableDeposito() {
		return tableDeposito;
	}

	public void setTableDeposito(JTable tableDeposito) {
		this.tableDeposito = tableDeposito;
	}

	public VentanaSectorControlador getControlador() {
		return controlador;
	}

	public void setControlador(VentanaSectorControlador controlador) {
		this.controlador = controlador;
	}

	public LabelPersonalizado getLblDeposito() {
		return lblDeposito;
	}

	public void setLblDeposito(LabelPersonalizado lblDeposito) {
		this.lblDeposito = lblDeposito;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
