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
public class ProduccionDetalles {
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

	@Temporal(TemporalType.TIME)
	private Date horaProduccion;

	@Column(nullable = true)
	private String observacion;

	@Column(nullable = true)
	private boolean desperdicio;

	@Column(nullable = true)
	private double cantidadDesperdicio;


	//Reimpresion o algo semejante
	@Column(nullable = true)
	private String tipoTrabajo;

	//Pendiente, En produccion, disponible para retirar, retirado, etc
	@Column(nullable = true)
	private String estadoProduccion;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Produccion produccion;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Maquina maquina;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getHoraProduccion() {
		return horaProduccion;
	}

	public void setHoraProduccion(Date horaProduccion) {
		this.horaProduccion = horaProduccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean isDesperdicio() {
		return desperdicio;
	}

	public void setDesperdicio(boolean desperdicio) {
		this.desperdicio = desperdicio;
	}

	public double getCantidadDesperdicio() {
		return cantidadDesperdicio;
	}

	public void setCantidadDesperdicio(double cantidadDesperdicio) {
		this.cantidadDesperdicio = cantidadDesperdicio;
	}

	public Produccion getProduccion() {
		return produccion;
	}

	public void setProduccion(Produccion produccion) {
		this.produccion = produccion;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public String getTipoTrabajo() {
		return tipoTrabajo;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	public String getEstadoProduccion() {
		return estadoProduccion;
	}

	public void setEstadoProduccion(String estadoProduccion) {
		this.estadoProduccion = estadoProduccion;
	}
	
	



}
