package iOS.vista.ventanas.buscadores;

import iOS.controlador.ventanas.buscadores.BuscadorRolControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorRol extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 512004321561647675L;
	public String modulo = "ROL";
	private BuscadorRolControlador controlador;
	
	public void setUpControlador() {
		controlador = new BuscadorRolControlador(this);
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorRol() {

	}

	public BuscadorRolControlador getControlador() {
		return controlador;
	}

	public void setControlador(BuscadorRolControlador controlador) {
		this.controlador = controlador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
