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
public class PedidoDetalles {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
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
	private String archivo;

	@ColumnDefault("100")
	@Column(nullable = true)
	private double medidaAncho;

	@ColumnDefault("100")
	@Column(nullable = true)
	private double medidaAlto;

	@ColumnDefault("100")
	@Column(nullable = true)
	private double medidaDetalle;

	@ColumnDefault("1")
	@Column(nullable = true)
	private double cantidadDetalle;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double precioProducto;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double precioDetalle;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double gananciaDetalle;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double costo;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double porcentajeSobreCosto;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Pedido pedido;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;

	@OneToMany(mappedBy = "pedidoDetalle", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@OneToMany(mappedBy = "detalleCarteleria", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<PedidoDetalleMaterial> materiales;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean produccionFinalizada = false;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaUltimoRegistroProduccion;

	@Column(nullable = true)
	private String ultimoEstadoProduccion;

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

	public Date getFechaModificado() {
		return fechaModificado;
	}

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
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

	public double getMedidaAncho() {
		return medidaAncho;
	}

	public void setMedidaAncho(double medidaAncho) {
		this.medidaAncho = medidaAncho;
	}

	public double getMedidaAlto() {
		return medidaAlto;
	}

	public void setMedidaAlto(double medidaAlto) {
		this.medidaAlto = medidaAlto;
	}

	public double getMedidaDetalle() {
		return medidaDetalle;
	}

	public void setMedidaDetalle(double medidaDetalle) {
		this.medidaDetalle = medidaDetalle;
	}

	public String getArchivo() {
		return archivo;
	}

	public void setArchivo(String archivo) {
		this.archivo = archivo;
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

	public List<Produccion> getProducciones() {
		try {
			producciones = producciones.stream().sorted(Comparator.comparing(Produccion::getId))
					.collect(Collectors.toList());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
		System.out.println(ultimoEstadoProduccion);
		return ultimoEstadoProduccion;
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

	public double getGananciaDetalle() {
		return gananciaDetalle;
	}

	public void setGananciaDetalle(double gananciaDetalle) {
		this.gananciaDetalle = gananciaDetalle;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getPorcentajeSobreCosto() {
		return porcentajeSobreCosto;
	}

	public void setPorcentajeSobreCosto(double porcentajeSobreCosto) {
		this.porcentajeSobreCosto = porcentajeSobreCosto;
	}

	public List<PedidoDetalleMaterial> getMateriales() {
		return materiales;
	}

	public void setMateriales(List<PedidoDetalleMaterial> materiales) {
		this.materiales = materiales;
	}

	public Date getFechaUltimoRegistroProduccion() {
		return fechaUltimoRegistroProduccion;
	}

	public void setFechaUltimoRegistroProduccion(Date fechaUltimoRegistroProduccion) {
		this.fechaUltimoRegistroProduccion = fechaUltimoRegistroProduccion;
	}

	public void setUltimoEstadoProduccion(String ultimoEstadoProduccion) {
		this.ultimoEstadoProduccion = ultimoEstadoProduccion;
	}

	public String registrar() {
		return "PedidoDetalles [id=" + id + ", fechaRegistro=" + fechaRegistro + ", fechaModificado=" + fechaModificado
				+ ", estado=" + estado + ", colaborador=" + colaborador + ", archivo=" + archivo + ", medidaAncho="
				+ medidaAncho + ", medidaAlto=" + medidaAlto + ", medidaDetalle=" + medidaDetalle + ", cantidadDetalle="
				+ cantidadDetalle + ", precioProducto=" + precioProducto + ", precioDetalle=" + precioDetalle
				+ ", gananciaDetalle=" + gananciaDetalle + ", costo=" + costo + ", porcentajeSobreCosto="
				+ porcentajeSobreCosto + ", pedido=" + pedido + ", producto=" + producto + ", producciones="
				+ producciones + ", materiales=" + materiales + ", produccionFinalizada=" + produccionFinalizada
				+ ", fechaUltimoRegistroProduccion=" + fechaUltimoRegistroProduccion + ", ultimoEstadoProduccion="
				+ ultimoEstadoProduccion + "]";
	}

	@Override
	public String toString() {
		return id + " - " + fechaRegistro;
	}

}
