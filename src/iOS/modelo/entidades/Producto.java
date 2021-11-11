package iOS.modelo.entidades;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	private Funcionario colaborador;
	

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}
	
	@Column(nullable = false)
	private double precioMinimo;
	
	@Column(nullable = false)
	private double precioMaximo;
	
	@Column(nullable = false)
	private String tipoCobro;
	
	@Column(nullable = false)
	private String descripcion;
	
	@Column(nullable = true, unique = true)
	private String codigoReferencia;
	
	@Column(nullable = false)
	private boolean tieneMedidaFija;
	
	@Column(nullable = false)
	private double altoProducto;
	
	@Column(nullable = false)
	private double anchoProducto;

	@Column(nullable = false)
	private boolean esServicio;
	
	@Column(nullable = true)
	private Boolean productoCarteleria;
	
	@Column(nullable = true)
	private Boolean productoCostura;
	
	@OneToMany(mappedBy = "producto", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<PedidoDetalles> pedidoDetalles;

	@Override
	public String toString() {
		return descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Funcionario getColaborador() {
		return colaborador;
	}

	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
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

	public boolean isEsServicio() {
		return esServicio;
	}

	public void setEsServicio(boolean esServicio) {
		this.esServicio = esServicio;
	}

	public List<PedidoDetalles> getPedidoDetalles() {
		return pedidoDetalles;
	}

	public void setPedidoDetalles(List<PedidoDetalles> pedidoDetalles) {
		this.pedidoDetalles = pedidoDetalles;
	}


	public Boolean getProductoCarteleria() {
		return productoCarteleria;
	}


	public Boolean getProductoCostura() {
		return productoCostura;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}


	public void setProductoCarteleria(Boolean productoCarteleria) {
		this.productoCarteleria = productoCarteleria;
	}


	public void setProductoCostura(Boolean productoCostura) {
		this.productoCostura = productoCostura;
	}
	
	
	
}
