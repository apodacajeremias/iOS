package iOS.modelo.entidades;

import java.util.Date;
import java.util.List;

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
public class EntidadBancaria {
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

	public Colaborador getColaborador() {
		return colaborador;
	}


	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}


	@Column(nullable = false)
	private String nombreBanco;

	@OneToMany(mappedBy = "entidadBancaria")
	private List<InformacionPago> informacionesPago;

	public int getId() {
		return id;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public List<InformacionPago> getInformacionesPago() {
		return informacionesPago;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public void setInformacionesPago(List<InformacionPago> informacionesPago) {
		this.informacionesPago = informacionesPago;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "EntidadBancaria [id=" + id + ", nombreBanco=" + nombreBanco
				+ ", fechaRegistro=" + fechaRegistro + ", informacionesPago="
				+ informacionesPago + "]";
	}
	
	
	
	
}
