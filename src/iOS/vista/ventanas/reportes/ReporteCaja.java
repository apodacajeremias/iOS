package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteCajaControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteCaja extends ReporteGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7148852986205615741L;
	public String modulo = "CAJA";

	private ReporteCajaControlador controlador;

	public void setUpControlador() {
		controlador = new ReporteCajaControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public ReporteCaja() {
		getRb1().setText("Cajas cerradas");
		getRb2().setText("Cajas abiertas");
		setTitle("Reporte de Caja");

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteCajaControlador getControlador() {
		return controlador;
	}

}
