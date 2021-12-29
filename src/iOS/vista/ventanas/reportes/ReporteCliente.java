package iOS.vista.ventanas.reportes;

import iOS.controlador.ventanas.reportes.ReporteClienteControlador;
import iOS.vista.componentes.ReporteGenerico;

public class ReporteCliente extends ReporteGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1203406937927609263L;
	private ReporteClienteControlador controlador;
	public String modulo = "CLIENTE";

	/**
	 * Create the dialog.
	 */

	public void setUpControlador() {
		controlador = new ReporteClienteControlador(this);
	}

	public ReporteCliente() {
		getPanelEspecifico().getPanel_2().setVisible(false);
		getPanelGeneral().getPanel_3().setVisible(false);
		getPanelGeneral().getPanel_2().setVisible(false);
		getPanelGeneral().getLblrdAnho().setText("Buscar los registros del a\u00F1o");
		getPanelEspecifico().getlInfo1().setText("Reporte de Cliente");
		getPanelEspecifico().getLblprsnlzdBuscaTodosLos_1().setText("Busca los registros del cliente indicado en las opciones abajo\r\n");
		getPanelEspecifico().getRdColaboradorEspecifico().setText("Encontrar y Mostrar Cliente Registrador Por");
		getPanelEspecifico().getRdTodoColaborador().setText("Encontrar y Mostrar Todos los Clientes");
		getPanelGeneral().getLblrdTodo().setText("Busca todos los registros relacionados en el sistema");
		getPanelGeneral().getLblrdMes().setText("Buscar los registros del mes");
		getPanelGeneral().getLblrdHoy().setText("Buscar los registros de hoy");
		getPanelGeneral().getRdTodo().setText("Encontrar y Mostrar Solo Clientes con Deuda");
		getPanelGeneral().getlInfo1().setText("Reporte de Clientes");
		getPanelEspecifico().getRdTipo3().setText("Confeccion");
		getPanelEspecifico().getRdTipo2().setText("Carteleria");
		getPanelEspecifico().getRdTipo1().setText("Carteleria + Confeccion");
		getPanelGeneral().getLblrdAlgunos().setText("Busca todos los registros relacionados en el sistema");
		getPanelGeneral().getRdAlgunos().setText("Encontrar y Mostrar Solo Clientes Sin Deuda");
		setTitle("REPORTE DE CLIENTE");
		
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ReporteClienteControlador getControlador() {
		return controlador;
	}

	public String getModulo() {
		return modulo;
	}
	
	
}
