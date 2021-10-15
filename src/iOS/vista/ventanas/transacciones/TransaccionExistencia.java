package iOS.vista.ventanas.transacciones;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.transacciones.TransaccionExistenciaControlador;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.MiBoton;

public class TransaccionExistencia extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tableCompraDetalle;
	private JTable tableReferencia;
	private MiBoton btnSalir;
	private MiBoton btnDisponibilizar;
	private LabelPersonalizado lMensaje;
	private TransaccionExistenciaControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransaccionExistencia dialog = new TransaccionExistencia();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUpControlador();
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setUpControlador() {
		controlador = new TransaccionExistenciaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public TransaccionExistencia() {
		setResizable(false);
		setTitle("Disponibilizar materiales");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setBounds(100, 100, 650, 500);
		
		LabelPersonalizado lblprsnlzdMaterialesPendientesDe = new LabelPersonalizado(0);
		lblprsnlzdMaterialesPendientesDe.setText("Materiales pendientes de ingreso");
		lblprsnlzdMaterialesPendientesDe.setBounds(11, 21, 202, 15);
		getContentPane().add(lblprsnlzdMaterialesPendientesDe);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 37, 350, 423);
		getContentPane().add(scrollPane);
		
		tableCompraDetalle = new JTable();
		scrollPane.setViewportView(tableCompraDetalle);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(372, 37, 250, 290);
		getContentPane().add(scrollPane_1);
		
		tableReferencia = new JTable();
		scrollPane_1.setViewportView(tableReferencia);
		
		btnSalir = new MiBoton("Salir");
		btnSalir.setText("Salir");
		btnSalir.setBounds(522, 430, 100, 30);
		getContentPane().add(btnSalir);
		
		btnDisponibilizar = new MiBoton("Finalizar");
		btnDisponibilizar.setActionCommand("Disponibilizar");
		btnDisponibilizar.setText("Disponibilizar");
		btnDisponibilizar.setBounds(372, 430, 119, 30);
		getContentPane().add(btnDisponibilizar);
		
		lMensaje = new LabelPersonalizado(0);
		lMensaje.setForeground(Color.YELLOW);
		lMensaje.setFont(new Font("Tahoma", Font.BOLD, 10));
		lMensaje.setBounds(371, 404, 250, 15);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public JTable getTableCompraDetalle() {
		return tableCompraDetalle;
	}

	public JTable getTableReferencia() {
		return tableReferencia;
	}

	public MiBoton getBtnSalir() {
		return btnSalir;
	}

	public MiBoton getBtnDisponibilizar() {
		return btnDisponibilizar;
	}

	public LabelPersonalizado getlMensaje() {
		return lMensaje;
	}

	public TransaccionExistenciaControlador getControlador() {
		return controlador;
	}
	
	
}
