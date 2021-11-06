package iOS.modelo.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Rol {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro = new Date();
	
	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Colaborador colaborador;
	

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}
	
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	@Column(nullable = false, unique = true)
	private String nombreRol;
	
	@OneToMany(mappedBy = "rol", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<RolOperacion> rolesOperaciones;
	
	@OneToMany(mappedBy = "rol", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<Colaborador> colaboradores;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public List<RolOperacion> getRolesOperaciones() {
		return rolesOperaciones;
	}

	public void setRolesOperaciones(List<RolOperacion> rolesOperaciones) {
		this.rolesOperaciones = rolesOperaciones;
	}

	public List<Colaborador> getColaborador() {
		return colaboradores;
	}

	public void setColaborador(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}
	
	@Override
	public String toString() {
		return nombreRol;
	}


}
