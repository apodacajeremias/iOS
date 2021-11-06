package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReportePedidoCarteleriaControlador;
import iOS.controlador.ventanas.reportes.ReportePedidoConfeccionControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReportePedido extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReportePedidoCarteleriaControlador controladorCarteleria;
	public String modulo = "PEDIDO";
	private ReportePedidoConfeccionControlador controladoConfeccion;


	/**
	 * Create the dialog.
	 */

	public void setUpControladorCarteleria() {
		controladorCarteleria = new ReportePedidoCarteleriaControlador(this);
	}
	
	public void setUpControladorConfeccion() {
		controladoConfeccion = new ReportePedidoConfeccionControlador(this);
	}

	public ReportePedido() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReportePedidoCarteleriaControlador getControladorCarteleria() {
		return controladorCarteleria;
	}

	public String getModulo() {
		return modulo;
	}

	public ReportePedidoConfeccionControlador getControladoConfeccion() {
		return controladoConfeccion;
	}
}
