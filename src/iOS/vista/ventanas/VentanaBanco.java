package iOS.vista.ventanas;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import iOS.controlador.ventanas.VentanaBancoControlador;
import iOS.vista.componentes.CampoTextoPersonalizado;
import iOS.vista.componentes.LabelPersonalizado;
import iOS.vista.componentes.VentanaGenerica;

public class VentanaBanco extends VentanaGenerica {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1196814908659540277L;
	public String modulo = "BANCO";
	private CampoTextoPersonalizado tNombreBanco;
	private JTable tProveedorCuenta;
	private VentanaBancoControlador controlador;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaBanco dialog = new VentanaBanco();
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
		controlador = new VentanaBancoControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public VentanaBanco() {
		
		LabelPersonalizado lblprsnlzdNombreDelBanco = new LabelPersonalizado(0);
		lblprsnlzdNombreDelBanco.setText("Nombre del Banco");
		lblprsnlzdNombreDelBanco.setBounds(12, 11, 110, 15);
		getPanelFormulario().add(lblprsnlzdNombreDelBanco);
		
		tNombreBanco = new CampoTextoPersonalizado();
		tNombreBanco.mayusculas();
		tNombreBanco.setBounds(12, 27, 450, 30);
		getPanelFormulario().add(tNombreBanco);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 83, 450, 275);
		getPanelFormulario().add(scrollPane);
		
		tProveedorCuenta = new JTable();
		tProveedorCuenta.setEnabled(false);
		scrollPane.setViewportView(tProveedorCuenta);
		
		LabelPersonalizado lblprsnlzdProveedoresYCuentas = new LabelPersonalizado(0);
		lblprsnlzdProveedoresYCuentas.setText("Proveedores y Cuentas");
		lblprsnlzdProveedoresYCuentas.setBounds(12, 68, 141, 15);
		getPanelFormulario().add(lblprsnlzdProveedoresYCuentas);

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CampoTextoPersonalizado gettNombreBanco() {
		return tNombreBanco;
	}

	public JTable gettProveedorCuenta() {
		return tProveedorCuenta;
	}

	public VentanaBancoControlador getControlador() {
		return controlador;
	}
	
	
	
	
}
