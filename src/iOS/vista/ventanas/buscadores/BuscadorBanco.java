package iOS.vista.ventanas.buscadores;

import java.awt.EventQueue;

import javax.swing.WindowConstants;

import iOS.controlador.ventanas.buscadores.BuscadorBancoControlador;
import iOS.vista.componentes.BuscadorGenerico;

public class BuscadorBanco extends BuscadorGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6236729697885088006L;
	
	private BuscadorBancoControlador controlador;
	
	public void setUpControlador(){
		controlador = new BuscadorBancoControlador(this);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					BuscadorBanco dialog = new BuscadorBanco();
					dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public BuscadorBanco() {
		setTitle("Buscador de Banco");
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BuscadorBancoControlador getControlador() {
		return controlador;
	}
	
	

}
