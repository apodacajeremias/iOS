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
public class Operacion {
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

	@Column(nullable = false)
	private String nombreOperacion;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Modulo modulo;

	@OneToMany(mappedBy = "operacion", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<RolOperacion> rolesOperaciones;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public String getNombreOperacion() {
		return nombreOperacion;
	}

	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public List<RolOperacion> getRolesOperaciones() {
		return rolesOperaciones;
	}

	public void setRolesOperaciones(List<RolOperacion> rolesOperaciones) {
		this.rolesOperaciones = rolesOperaciones;
	}

	public String registrar() {
		return "Operacion [id=" + id + ", fechaRegistro=" + fechaRegistro + ", estado=" + estado + ", colaborador="
				+ colaborador + ", nombreOperacion=" + nombreOperacion + ", modulo=" + modulo + ", rolesOperaciones="
				+ rolesOperaciones + "]";
	}

	@Override
	public String toString() {
		return nombreOperacion + " " + modulo;
	}

}
