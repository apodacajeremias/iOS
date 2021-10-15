package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorProveedorControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorProveedor extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorProveedorControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorProveedorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorProveedor() {
		setTitle("Búsqueda de Proveedores");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorProveedorControlador getControlador() {
		return controlador;
	}

}
