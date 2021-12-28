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
	@JoinColumn(nullable = false)
	private Sector sector;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Maquina maquina;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Pedido pedido;

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalles pedidoDetalle;

	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalleConfeccion pedidoDetalleConfeccion;

	// Iniciar, reiniciar, pausar, cancelar
	@Column(nullable = false)
	private String proceso;

	// Observaciones hechas por el operador
	@Column(nullable = true)
	private String comentario;

	@Column(nullable = true)
	private boolean desperdicio;

	@Column(nullable = false)
	private double cantidadDesperdicio;

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

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
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

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
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

}
