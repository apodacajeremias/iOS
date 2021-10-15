package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorMaterialControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorMaterial extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9047956247480719813L;

	private BuscadorMaterialControlador controlador;

	public void setUpControlador() {
		controlador = new BuscadorMaterialControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorMaterial() {
		setTitle("Búsqueda de Materiales");
		getTable().setToolTipText("Doble click para seleccionar");
	}

	public BuscadorMaterialControlador getControlador() {
		return controlador;
	}

}
