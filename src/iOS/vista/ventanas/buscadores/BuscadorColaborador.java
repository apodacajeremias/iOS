package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorColaboradorControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorColaborador extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorColaboradorControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorColaboradorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorColaborador() {
		setTitle("Búsqueda de Colaborador");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorColaboradorControlador getControlador() {
		return controlador;
	}

}
