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
public class PedidoDetalles {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificado;

	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Colaborador colaborador;

	@Column(nullable = true)
	private double medidaAncho;

	@Column(nullable = true)
	private double medidaAlto;

	@Column(nullable = true)
	private double medidaDetalle;

	@Column(nullable = true)
	private int cantidadDetalle;

	@Column(nullable = true)
	private int precioProducto;

	@Column(nullable = true)
	private int precioDetalle;

	@Column(nullable = true)
	private int gananciaDetalle;

	@Column(nullable = true)
	private String archivo;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

	@OneToMany(mappedBy = "detalleCarteleria", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DetalleMaterial> materiales;

	@OneToMany(mappedBy = "pedidoDetalle", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@Override
	public String toString() {
		return id + " - " + fechaRegistro;
	}

	public boolean produccionTerminada() {
		return false;
	}

	public int getId() {
		return id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public Date getFechaModificado() {
		return fechaModificado;
	}

	public boolean isEstado() {
		return estado;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public double getMedidaAncho() {
		return medidaAncho;
	}

	public double getMedidaAlto() {
		return medidaAlto;
	}

	public double getMedidaDetalle() {
		return medidaDetalle;
	}

	public int getCantidadDetalle() {
		return cantidadDetalle;
	}

	public int getPrecioProducto() {
		return precioProducto;
	}

	public int getPrecioDetalle() {
		return precioDetalle;
	}

	public int getGananciaDetalle() {
		return gananciaDetalle;
	}

	public String getArchivo() {
		return archivo;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public List<DetalleMaterial> getMateriales() {
		return materiales;
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

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setMedidaAncho(double medidaAncho) {
		this.medidaAncho = medidaAncho;
	}

	public void setMedidaAlto(double medidaAlto) {
		this.medidaAlto = medidaAlto;
	}

	public void setMedidaDetalle(double medidaDetalle) {
		this.medidaDetalle = medidaDetalle;
	}

	public void setCantidadDetalle(int cantidadDetalle) {
		this.cantidadDetalle = cantidadDetalle;
	}

	public void setPrecioProducto(int precioProducto) {
		this.precioProducto = precioProducto;
	}

	public void setPrecioDetalle(int precioDetalle) {
		this.precioDetalle = precioDetalle;
	}

	public void setGananciaDetalle(int gananciaDetalle) {
		this.gananciaDetalle = gananciaDetalle;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setMateriales(List<DetalleMaterial> materiales) {
		this.materiales = materiales;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

}
