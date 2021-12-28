package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteProduccionControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteProduccion extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReporteProduccionControlador controlador;
	public String modulo = "PEDIDO";

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReporteProduccionControlador(this);
	}

	public ReporteProduccion() {
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");
		getPanelGeneral().getLblrdAlgunos().setText("Busca todas las producciones finalizadas relacionados a tu perfil");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Solo Finalizados");
		setTitle("REPORTE DE PEDIDO");
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteProduccionControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}
	
	
}
