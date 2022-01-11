package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorProcesoControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorProceso extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorProcesoControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorProcesoControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorProceso() {
		setTitle("Búsqueda de Procesos");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorProcesoControlador getControlador() {
		return controlador;
	}

}
