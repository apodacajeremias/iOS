package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteDeudasPagosControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteDeudasPagos extends ReporteGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8918225936203470477L;
	private ReporteDeudasPagosControlador controlador;

	public void setUpControlador() {
		controlador = new ReporteDeudasPagosControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public ReporteDeudasPagos() {
		getRb6().setText("DEUDA HISTORICA");
		getRb5().setText("DEUDA GENERAL");
		getRb2().setText("CONFECCION");
		getRb1().setText("CARTELERIA");
		getRb4().setText("SIN PAGO TOTAL");
		getRb3().setText("CON PAGO TOTAL");

	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteDeudasPagosControlador getControlador() {
		return controlador;
	}

	public void setControlador(ReporteDeudasPagosControlador controlador) {
		this.controlador = controlador;
	}
}
