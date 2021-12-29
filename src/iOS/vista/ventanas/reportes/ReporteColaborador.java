package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteColaboradorControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteColaborador extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReporteColaboradorControlador controlador;
	public String modulo = "PEDIDO";

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReporteColaboradorControlador(this);
	}

	public ReporteColaborador() {
		getPanelEspecifico().getPanel_2().setVisible(false);
		getPanelGeneral().getLblrdAlgunos().setVisible(false);
		getPanelGeneral().getRdAlgunos().setVisible(false);
		getPanelEspecifico().getlInfo1().setText("Reporte de Colaborador");
		getPanelGeneral().getlInfo1().setText("Reporte de Colaborador");
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");
		getPanelGeneral().getLblrdAlgunos().setText("Busca todos los registros de presupuestos relacionados a tu perfil");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Solo Presupuestos");
		setTitle("REPORTE DE COLABORADOR");
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteColaboradorControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}


	
}
