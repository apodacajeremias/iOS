package iOS.vista.ventanas.buscadores;

import java.awt.EventQueue;

import javax.swing.WindowConstants;

import iOS.controlador.ventanas.buscadores.BuscadorOperacionControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorOperacion extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7290675919425241557L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BuscadorOperacion dialog = new BuscadorOperacion();
					dialog.setUpControlador();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private BuscadorOperacionControlador controlador;
	
	public void setUpControlador() {
		controlador = new BuscadorOperacionControlador(this);

	}

	/**
	 * Create the dialog.
	 */
	public BuscadorOperacion() {

	}

	public BuscadorOperacionControlador getControlador() {
		return controlador;
	}
	
	

}
