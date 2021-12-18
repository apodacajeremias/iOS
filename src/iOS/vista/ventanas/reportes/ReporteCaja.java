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
		setTitle("REPORTE DE CAJA");
		getPanelEspecifico().getRdTipo3().setText("Ambos Tipos");
		getPanelEspecifico().getRdTipo2().setText("Caja Abierta");
		getPanelEspecifico().getRdTipo1().setText("Caja Cerrada");
		getPanelGeneral().getRdTodo().setText("Encontrar y Mostrar Todos los Movimientos");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteCajaControlador getControlador() {
		return controlador;
	}

}
