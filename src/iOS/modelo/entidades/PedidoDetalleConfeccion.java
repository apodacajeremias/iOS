package iOS.modelo.entidades;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
public class PedidoDetalleConfeccion {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
//	@GenericGenerator(name = "native", strategy = "native")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro = new Date();

	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Colaborador colaborador;

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

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificado;

	@OneToMany(mappedBy = "detalleConfeccion", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<DetalleMaterial> materiales;

	@OneToMany(mappedBy = "pedidoDetalle", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean produccionFinalizada = false;

	@Column(nullable = true)
	private String ultimoEstadoProduccion;

	@Override
	public String toString() {
		return id + " - " + fechaRegistro;
	}

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

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}

	public String getTamano() {
		return tamano;
	}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getMolde() {
		return molde;
	}

	public void setMolde(String molde) {
		this.molde = molde;
	}

	public double getCantidadDetalle() {
		return cantidadDetalle;
	}

	public void setCantidadDetalle(double cantidadDetalle) {
		this.cantidadDetalle = cantidadDetalle;
	}

	public double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(double precioProducto) {
		this.precioProducto = precioProducto;
	}

	public double getPrecioDetalle() {
		return precioDetalle;
	}

	public void setPrecioDetalle(double precioDetalle) {
		this.precioDetalle = precioDetalle;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Date getFechaModificado() {
		return fechaModificado;
	}

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

	public List<DetalleMaterial> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<DetalleMaterial> materiales) {
		this.materiales = materiales;
	}

	public List<Produccion> getProducciones() {
		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	public boolean isProduccionFinalizada() {
		return produccionFinalizada;
	}

	public void setProduccionFinalizada(boolean produccionFinalizada) {
		this.produccionFinalizada = produccionFinalizada;
	}

	public String getUltimoEstadoProduccion() {
		ultimoEstadoProduccion = "PENDIENTE";
		try {
			producciones = producciones.stream().sorted(Comparator.comparing(Produccion::getId).reversed())
					.collect(Collectors.toList());
			ultimoEstadoProduccion = producciones.stream().findFirst().get().getProceso();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ultimoEstadoProduccion;
	}

	public void setUltimoEstadoProduccion(String ultimoEstadoProduccion) {
		this.ultimoEstadoProduccion = ultimoEstadoProduccion;
	}

	
}
