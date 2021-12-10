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
public class Produccion {
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

	@ManyToOne
	@JoinColumn(nullable = true)
	private Maquina maquina;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Sector sector;

	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalles pedidoDetalle;

	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalleConfeccion pedidoDetalleConfeccion;

	@Column(nullable = true)
	private String observacion;

	@Column(nullable = true)
	private boolean desperdicio;

	@Column(nullable = false)
	private double cantidadDesperdicio;

	// Reimpresion o algo semejante
	@Column(nullable = false)
	private String tipoTrabajo;

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

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public PedidoDetalles getPedidoDetalle() {
		return pedidoDetalle;
	}

	public void setPedidoDetalle(PedidoDetalles pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}

	public PedidoDetalleConfeccion getPedidoDetalleConfeccion() {
		return pedidoDetalleConfeccion;
	}

	public void setPedidoDetalleConfeccion(PedidoDetalleConfeccion pedidoDetalleConfeccion) {
		this.pedidoDetalleConfeccion = pedidoDetalleConfeccion;
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

	public String getTipoTrabajo() {
		return tipoTrabajo;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}
	
	
}
