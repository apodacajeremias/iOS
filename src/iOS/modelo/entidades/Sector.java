package iOS.modelo.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Sector {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro = new Date();

	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;

	@Column(nullable = false)
	private String descripcion;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Funcionario colaborador;

	@OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SectorProceso> procesos;

	@OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Funcionario> colaboradoresDelSector;
	
	@OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@Override
	public String toString() {
		return descripcion;
	}

	public int getId() {
		return id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean isEstado() {
		return estado;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Funcionario getColaborador() {
		return colaborador;
	}

	public List<SectorProceso> getProcesos() {
		return procesos;
	}

	public List<Funcionario> getColaboradoresDelSector() {
		return colaboradoresDelSector;
	}

	public List<Produccion> getProducciones() {
		return producciones;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
	}

	public void setProcesos(List<SectorProceso> procesos) {
		this.procesos = procesos;
	}

	public void setColaboradoresDelSector(List<Funcionario> colaboradoresDelSector) {
		this.colaboradoresDelSector = colaboradoresDelSector;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}
	
	

}
