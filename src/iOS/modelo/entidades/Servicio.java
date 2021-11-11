package iOS.modelo.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Servicio {
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
	private Funcionario colaborador;

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}
	
	@Column(nullable = false)
	private double precioMinimo;
	
	@Column(nullable = false)
	private double precioMaximo;
	
	@Column(nullable = true)
	private String tipoCobro;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true, unique = true)
	private String codigoReferencia;
	
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Sector sector;
	
	@Override
	public String toString() {
		return descripcion+" - "+tipoCobro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Funcionario getColaborador() {
		return colaborador;
	}

	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
	}

	public double getPrecioMinimo() {
		return precioMinimo;
	}

	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public double getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public String getTipoCobro() {
		return tipoCobro;
	}

	public void setTipoCobro(String tipoCobro) {
		this.tipoCobro = tipoCobro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}
	
 
}
