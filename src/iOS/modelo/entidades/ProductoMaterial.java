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
public class ProductoMaterial {
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

	@ColumnDefault("0")
	@Column(nullable = false)
	private double costo;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double subtotal;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double cantidad;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean editable;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean tieneMedidaFija;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double alto;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double ancho;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double area;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;

	@OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<PedidoDetalleMaterial> pedidosDetalles;

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

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isTieneMedidaFija() {
		return tieneMedidaFija;
	}

	public void setTieneMedidaFija(boolean tieneMedidaFija) {
		this.tieneMedidaFija = tieneMedidaFija;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<PedidoDetalleMaterial> getPedidosDetalles() {
		return pedidosDetalles;
	}

	public void setPedidosDetalles(List<PedidoDetalleMaterial> pedidosDetalles) {
		this.pedidosDetalles = pedidosDetalles;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public String toString() {
		return material.toString();
	}

}
