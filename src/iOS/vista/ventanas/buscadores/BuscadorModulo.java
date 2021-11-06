package iOS.vista.ventanas.buscadores;

import java.awt.EventQueue;

import javax.swing.WindowConstants;

import iOS.controlador.ventanas.buscadores.BuscadorModuloControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorModulo extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 512004321561647675L;
	private BuscadorModuloControlador controlador;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BuscadorModulo dialog = new BuscadorModulo();
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
		controlador = new BuscadorModuloControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public BuscadorModulo() {

	}

	public BuscadorModuloControlador getControlador() {
		return controlador;
	}

	public void setControlador(BuscadorModuloControlador controlador) {
		this.controlador = controlador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
