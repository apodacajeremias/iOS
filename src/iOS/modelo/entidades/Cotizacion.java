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
public class Cotizacion {
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
	private double cotizacionGS;

	@Column(nullable = false)
	private double cotizacionRS;

	@Column(nullable = false)
	private double cotizacionUS;

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

	public double getCotizacionGS() {
		return cotizacionGS;
	}

	public void setCotizacionGS(double cotizacionGS) {
		this.cotizacionGS = cotizacionGS;
	}

	public double getCotizacionRS() {
		return cotizacionRS;
	}

	public void setCotizacionRS(double cotizacionRS) {
		this.cotizacionRS = cotizacionRS;
	}

	public double getCotizacionUS() {
		return cotizacionUS;
	}

	public void setCotizacionUS(double cotizacionUS) {
		this.cotizacionUS = cotizacionUS;
	}

	public String registrar() {
		return "Cotizacion [id=" + id + ", fechaRegistro=" + fechaRegistro + ", estado=" + estado + ", colaborador="
				+ colaborador + ", cotizacionGS=" + cotizacionGS + ", cotizacionRS=" + cotizacionRS + ", cotizacionUS="
				+ cotizacionUS + "]";
	}

}
