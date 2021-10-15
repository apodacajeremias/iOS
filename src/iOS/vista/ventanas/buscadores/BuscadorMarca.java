package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorMarcaControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorMarca extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorMarcaControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorMarcaControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorMarca() {
		setTitle("Búsqueda de Materiales");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorMarcaControlador getControlador() {
		return controlador;
	}

}
