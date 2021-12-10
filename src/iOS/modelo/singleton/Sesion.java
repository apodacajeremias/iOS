package iOS.modelo.singleton;

import java.util.List;

import javax.swing.JOptionPane;

import iOS.modelo.entidades.Colaborador;

public class Sesion {
	private static Sesion sesion;

	private Colaborador colaborador;
	
	private List<Colaborador> colaboradores;

	private Sesion() {
	}

	// synchronized por si dos o mas hilos llaman al metodo al mismo tiempo
	// entonces el primero ejecuta y el segundo aguarda
	public synchronized static Sesion getInstance() {
		if (sesion == null) {
			sesion = new Sesion();
		}
		return sesion;
	}

	public Colaborador getColaborador() {
		if (colaborador == null) {
			JOptionPane.showMessageDialog(null, "Debe iniciar sesión");
			return colaborador;
		}
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}
	
	
	
}
