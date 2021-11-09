package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorSectorControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorSector extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6236729697885088006L;
	
	private BuscadorSectorControlador controlador;
	
	public void setUpControlador(){
		controlador = new BuscadorSectorControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorSector() {
		setTitle("Buscador de Sector");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BuscadorSectorControlador getControlador() {
		return controlador;
	}
	
	

}
