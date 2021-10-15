package iOS.vista.ventanas.reportes;

import java.awt.EventQueue;

import javax.swing.JDialog;

import iOS.controlador.ventanas.reportes.ReporteCajaControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteCaja extends ReporteGenerico{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7148852986205615741L;
	public String modulo = "CAJA";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporteCaja dialog = new ReporteCaja();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ReporteCajaControlador controlador;
	
	public void setUpControlador() {
		controlador = new ReporteCajaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public ReporteCaja() {
		setTitle("Reporte de Caja");

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteCajaControlador getControlador() {
		return controlador;
	}
	
	

}
