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
public class PedidoDetalleMaterial {
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
	private Pedido pedido;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalles detalleCarteleria;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalleConfeccion detalleConfeccion;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;
	
	@ColumnDefault("0")
	@Column(nullable = false)
	private double precio;
	
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PedidoDetalles getDetalleCarteleria() {
		return detalleCarteleria;
	}

	public void setDetalleCarteleria(PedidoDetalles detalleCarteleria) {
		this.detalleCarteleria = detalleCarteleria;
	}

	public PedidoDetalleConfeccion getDetalleConfeccion() {
		return detalleConfeccion;
	}

	public void setDetalleConfeccion(PedidoDetalleConfeccion detalleConfeccion) {
		this.detalleConfeccion = detalleConfeccion;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	

}
