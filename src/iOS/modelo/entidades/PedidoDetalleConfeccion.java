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
public class PedidoDetalleConfeccion {
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

	@Column(nullable = true)
	private String archivo;

	@Column(nullable = true)
	private String tamano;

	@Column(nullable = true)
	private String molde;

	@Column(nullable = true)
	private double cantidadDetalle;

	@Column(nullable = true)
	private double precioProducto;

	@Column(nullable = true)
	private double precioDetalle;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Produccion produccion;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificado;

	public int getId() {
		return id;
	}

	public String getArchivo() {
		return archivo;
	}

	public String getTamano() {
		return tamano;
	}

	public String getMolde() {
		return molde;
	}

	public double getCantidadDetalle() {
		return cantidadDetalle;
	}

	public double getPrecioProducto() {
		return precioProducto;
	}

	public double getPrecioDetalle() {
		return precioDetalle;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Producto getProducto() {
		return producto;
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

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public void setMolde(String molde) {
		this.molde = molde;
	}

	public void setCantidadDetalle(double cantidadDetalle) {
		this.cantidadDetalle = cantidadDetalle;
	}

	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}

	public void setPrecioDetalle(double precioDetalle) {
		this.precioDetalle = precioDetalle;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

}
