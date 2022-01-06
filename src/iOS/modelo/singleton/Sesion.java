package iOS.modelo.singleton;

import java.util.List;

import javax.swing.JOptionPane;

import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Cotizacion;
import iOS.modelo.entidades.Maquina;
import iOS.modelo.entidades.Rol;
import iOS.modelo.entidades.Sector;

public class Sesion {
	private static Sesion sesion;

	private Colaborador colaborador;
	private List<Colaborador> colaboradores;

	private Sector sector;
	private List<Sector> sectores;

	private Rol rol;
	private List<Rol> roles;

	private List<Maquina> maquinas;

	private Cotizacion cotizacion;

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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<Sector> getSectores() {
		return sectores;
	}

	public void setSectores(List<Sector> sectores) {
		this.sectores = sectores;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public Cotizacion getCotizacion() {
		if (cotizacion == null) {
			JOptionPane.showMessageDialog(null, "Debe cargar la cotizacion del dia");
			return null;
		}
		return cotizacion;
	}

	public void setCotizacion(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

}
