package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReportePedidoControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReportePedido extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReportePedidoControlador controlador;
	public String modulo = "PEDIDO";

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReportePedidoControlador(this);
	}

	public ReportePedido() {
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");
		getPanelGeneral().getLblrdAlgunos().setText("Busca todos los registros de presupuestos relacionados a tu perfil");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Solo Presupuestos");
		setTitle("REPORTE DE PEDIDO");
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReportePedidoControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}
	
	
}
