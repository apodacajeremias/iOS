package iOS.modelo.singleton;

import javax.swing.JOptionPane;

import iOS.modelo.entidades.Colaborador;

public class Sesion {	
	private static Sesion sesion;
	
	private Colaborador colaborador;
	
	private Sesion() {	
	}
	
	//synchronized por si dos o mas hilos llaman al metodo al mismo tiempo
	// entonces el primero ejecuta y el segundo aguarda
	public synchronized static Sesion getInstance() {
		if (sesion == null) {
			sesion = new Sesion();
		}
		return sesion;
	}

	public Colaborador getColaborador() {
		if (colaborador == null) {
			JOptionPane.showMessageDialog(null, "Inicie sesión");
		}
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}
	
	

}
