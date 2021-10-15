package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorProductoControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorProducto extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorProductoControlador controlador;

	public void setUpControlador(boolean esServicio) {
		controlador = new BuscadorProductoControlador(this, esServicio);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorProducto() {
		setTitle("Búsqueda de Productoes");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorProductoControlador getControlador() {
		return controlador;
	}

}
