package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorClienteControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorCliente extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;
	public String modulo = "CLIENTE";
	private BuscadorClienteControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorClienteControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorCliente() {
		setTitle("Búsqueda de Cliente");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorClienteControlador getControlador() {
		return controlador;
	}

}
