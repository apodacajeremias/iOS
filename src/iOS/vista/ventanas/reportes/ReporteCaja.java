package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteCajaControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteCaja extends ReporteGenerico{

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
		getRb2().setText("Incluir abiertos");
		getRb1().setText("Incluir anulados");
		setTitle("Reporte de Caja");

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteCajaControlador getControlador() {
		return controlador;
	}
	
	

}
