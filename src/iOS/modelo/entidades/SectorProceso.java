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
public class SectorProceso {
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
	@JoinColumn(nullable = true)
	private Colaborador colaborador;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Sector sector;

	@Column(nullable = false)
	private String nombreProceso;
	
	@Column(nullable = false)
	private boolean esRepetible;

	@OneToMany(mappedBy = "proceso", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@Override
	public String toString() {
		return nombreProceso;
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

	public Colaborador getColaborador() {
		return colaborador;
	}

	public Sector getSector() {
		return sector;
	}

	public String getNombreProceso() {
		return nombreProceso;
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

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	public boolean isEsRepetible() {
		return esRepetible;
	}

	public void setEsRepetible(boolean esRepetible) {
		this.esRepetible = esRepetible;
	}
	
	

}
