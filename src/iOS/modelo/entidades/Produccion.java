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
	@JoinColumn(nullable = false)
	private SectorProceso proceso;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificado;

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

	public Maquina getMaquina() {
		return maquina;
	}

	public Sector getSector() {
		return sector;
	}

	public SectorProceso getProceso() {
		return proceso;
	}

	public PedidoDetalles getPedidoDetalle() {
		return pedidoDetalle;
	}

	public PedidoDetalleConfeccion getPedidoDetalleConfeccion() {
		return pedidoDetalleConfeccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public boolean isDesperdicio() {
		return desperdicio;
	}

	public double getCantidadDesperdicio() {
		return cantidadDesperdicio;
	}

	public String getTipoTrabajo() {
		return tipoTrabajo;
	}

	public Date getFechaModificado() {
		return fechaModificado;
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

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public void setProceso(SectorProceso proceso) {
		this.proceso = proceso;
	}

	public void setPedidoDetalle(PedidoDetalles pedidoDetalle) {
		this.pedidoDetalle = pedidoDetalle;
	}

	public void setPedidoDetalleConfeccion(PedidoDetalleConfeccion pedidoDetalleConfeccion) {
		this.pedidoDetalleConfeccion = pedidoDetalleConfeccion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public void setDesperdicio(boolean desperdicio) {
		this.desperdicio = desperdicio;
	}

	public void setCantidadDesperdicio(double cantidadDesperdicio) {
		this.cantidadDesperdicio = cantidadDesperdicio;
	}

	public void setTipoTrabajo(String tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}
	
	

}
