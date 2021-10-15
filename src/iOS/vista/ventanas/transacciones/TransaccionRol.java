package iOS.vista.ventanas.transacciones;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.transacciones.TransaccionRolControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class TransaccionRol extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4159897396493525397L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransaccionRol dialog = new TransaccionRol();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private TransaccionRolControlador controlador;
	private CampoTextoPersonalizado tNombreCompleto;
	private LabelPersonalizado lOperacion;
	private JTable tableOperaciones;
	
	public void setUpControlador() {
		
		controlador = new TransaccionRolControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionRol() {
		
		LabelPersonalizado lblprsnlzdNombreDelCargo = new LabelPersonalizado(0);
		lblprsnlzdNombreDelCargo.setText("Nombre del rol");
		lblprsnlzdNombreDelCargo.setBounds(12, 7, 107, 15);
		getPanelFormulario().add(lblprsnlzdNombreDelCargo);
		
		tNombreCompleto = new CampoTextoPersonalizado();
		tNombreCompleto.setBounds(12, 29, 450, 25);
		tNombreCompleto.mayusculas();
		tNombreCompleto.limite(50);
		getPanelFormulario().add(tNombreCompleto);
		
		lOperacion = new LabelPersonalizado(0);
		lOperacion.setToolTipText("Click para agregar operaciones habilitadas");
		lOperacion.setText("Operacion");
		lOperacion.setBounds(12, 61, 65, 15);
		getPanelFormulario().add(lOperacion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 83, 450, 280);
		getPanelFormulario().add(scrollPane);
		
		tableOperaciones = new JTable();
		scrollPane.setViewportView(tableOperaciones);
		lOperacion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lOperacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lOperacion.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public TransaccionRolControlador getControlador() {
		return controlador;
	}

	public CampoTextoPersonalizado gettNombreCompleto() {
		return tNombreCompleto;
	}

	public LabelPersonalizado getlOperacion() {
		return lOperacion;
	}

	public JTable getTableOperaciones() {
		return tableOperaciones;
	}
	
	

	
}
