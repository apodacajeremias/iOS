package iOS.modelo.singleton;

import java.util.List;

import javax.swing.JOptionPane;

import iOS.modelo.dao.ColaboradorDao;
import iOS.modelo.entidades.Funcionario;

public class Sesion {
	private static Sesion sesion;

	private Funcionario colaborador;

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

	public Funcionario getColaborador() {
		if (colaborador == null) {
			JOptionPane.showMessageDialog(null, "Inicie sesión");
		}
		return colaborador;
	}

	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
	}
	
	public List<Funcionario> recuperarColaboradores() {
		List<Funcionario> cs = null;
		ColaboradorDao cDao = new ColaboradorDao();
		cs = cDao.recuperarTodoOrdenadoPorNombre();
		
		return cs;
	}

}
