package iOS.vista.ventanas.buscadores;

import java.awt.EventQueue;

import javax.swing.WindowConstants;

import iOS.controlador.ventanas.buscadores.BuscadorRolControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorRol extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 512004321561647675L;
	public String modulo = "ROL";
	private BuscadorRolControlador controlador;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BuscadorRol dialog = new BuscadorRol();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
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
