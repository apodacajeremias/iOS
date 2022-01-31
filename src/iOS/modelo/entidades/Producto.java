package iOS.modelo.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Producto {
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
	private Sector sector;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double porcentajeSobreCosto;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double costo;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double precioMinimo;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double precioMaximo;

	@ColumnDefault("0")
	@Column(nullable = false)
	private String tipoCobro;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = true, unique = true)
	private String codigoReferencia;

	@Column(nullable = false)
	private boolean tieneMedidaFija;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double altoProducto;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double anchoProducto;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double areaProducto;

	@ColumnDefault("false")
	@Column(nullable = true)
	private boolean productoCarteleria;

	@ColumnDefault("false")
	@Column(nullable = true)
	private boolean productoCostura;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<PedidoDetalles> pedidoDetalleCarteleriaes = new ArrayList<PedidoDetalles>();

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<PedidoDetalleConfeccion> pedidoDetalleConfecciones = new ArrayList<PedidoDetalleConfeccion>();

	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductoMaterial> materiales = new ArrayList<ProductoMaterial>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Proceso> procesos = new ArrayList<Proceso>();

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

	public double getPorcentajeSobreCosto() {
		return porcentajeSobreCosto;
	}

	public void setPorcentajeSobreCosto(double porcentajeSobreCosto) {
		this.porcentajeSobreCosto = porcentajeSobreCosto;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
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

	public boolean isTieneMedidaFija() {
		return tieneMedidaFija;
	}

	public void setTieneMedidaFija(boolean tieneMedidaFija) {
		this.tieneMedidaFija = tieneMedidaFija;
	}

	public double getAltoProducto() {
		return altoProducto;
	}

	public void setAltoProducto(double altoProducto) {
		this.altoProducto = altoProducto;
	}

	public double getAnchoProducto() {
		return anchoProducto;
	}

	public void setAnchoProducto(double anchoProducto) {
		this.anchoProducto = anchoProducto;
	}

	public double getAreaProducto() {
		return areaProducto;
	}

	public void setAreaProducto(double areaProducto) {
		this.areaProducto = areaProducto;
	}

	public boolean isProductoCarteleria() {
		return productoCarteleria;
	}

	public void setProductoCarteleria(boolean productoCarteleria) {
		this.productoCarteleria = productoCarteleria;
	}

	public boolean isProductoCostura() {
		return productoCostura;
	}

	public void setProductoCostura(boolean productoCostura) {
		this.productoCostura = productoCostura;
	}

	public List<PedidoDetalles> getPedidoDetalleCarteleriaes() {
		return pedidoDetalleCarteleriaes;
	}

	public void setPedidoDetalleCarteleriaes(List<PedidoDetalles> pedidoDetalleCarteleriaes) {
		this.pedidoDetalleCarteleriaes = pedidoDetalleCarteleriaes;
	}

	public List<PedidoDetalleConfeccion> getPedidoDetalleConfecciones() {
		return pedidoDetalleConfecciones;
	}

	public void setPedidoDetalleConfecciones(List<PedidoDetalleConfeccion> pedidoDetalleConfecciones) {
		this.pedidoDetalleConfecciones = pedidoDetalleConfecciones;
	}

	public List<ProductoMaterial> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<ProductoMaterial> materiales) {
		this.materiales = materiales;
	}

	public List<Proceso> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Proceso> procesos) {
		this.procesos = procesos;
	}

	@Override
	public String toString() {
		return descripcion;
	}
}
