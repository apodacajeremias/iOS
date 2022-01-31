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
public class Pedido {
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

	@Column(nullable = true)
	private String tipoPagoPedido;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double descuentoTotal;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double costoTotal;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double gananciaTotal;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double sumatoriaPrecio;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double precioPagar;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double metrosTotal;

	@ColumnDefault("0")
	@Column(nullable = true)
	private double metrosFechaEmision;

	@ColumnDefault("false")
	@Column(nullable = true)
	private boolean esPresupuesto;

	@ColumnDefault("false")
	@Column(nullable = true)
	private boolean considerarMetraje;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Representante representante;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoDetalles> pedidoDetalles;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoDetalleConfeccion> pedidosConfecciones;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<CajaMovimiento> pagosPedido;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Imagen> imagenes;

	@Column(nullable = true)
	private boolean pedidoCostura;

	@Column(nullable = true)
	private boolean pedidoCarteleria;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean generaDeuda;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean deudaPaga;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double sumaPagos;

	@Column(nullable = true)
	private String informacionResponsable;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean produccionFinalizada = false;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaUltimoRegistroProduccion;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean conDetalle = false;

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

	public double getMetrosTotal() {
		return metrosTotal;
	}

	public double getMetrosFechaEmision() {
		return metrosFechaEmision;
	}

	public boolean isEsPresupuesto() {
		return esPresupuesto;
	}

	public boolean isConsiderarMetraje() {
		return considerarMetraje;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public List<PedidoDetalles> getPedidoDetalles() {
		try {
			pedidoDetalles = pedidoDetalles.stream().sorted(Comparator.comparing(PedidoDetalles::getId))
					.collect(Collectors.toList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pedidoDetalles;
	}

	public List<PedidoDetalleConfeccion> getPedidosConfecciones() {
		try {
			pedidosConfecciones = pedidosConfecciones.stream()
					.sorted(Comparator.comparing(PedidoDetalleConfeccion::getId)).collect(Collectors.toList());
		} catch (Exception e) {
			System.err.println("Sin confeccion");
		}
		return pedidosConfecciones;
	}

	public List<CajaMovimiento> getPagosPedido() {
		return pagosPedido;
	}

	public Representante getRepresentante() {
		return representante;
	}

	public void setRepresentante(Representante representante) {
		this.representante = representante;
	}

	public boolean isPedidoCostura() {
		return pedidoCostura;
	}

	public void setPedidoCostura(boolean pedidoCostura) {
		this.pedidoCostura = pedidoCostura;
	}

	public boolean isPedidoCarteleria() {
		return pedidoCarteleria;
	}

	public void setPedidoCarteleria(boolean pedidoCarteleria) {
		this.pedidoCarteleria = pedidoCarteleria;
	}

	public boolean isGeneraDeuda() {
		return generaDeuda;
	}

	public boolean isDeudaPaga() {
		return deudaPaga;
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

	public void setTipoPagoPedido(String tipoPagoPedido) {
		this.tipoPagoPedido = tipoPagoPedido;
	}

	public void setDescuentoTotal(int descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}

	public void setGananciaTotal(int gananciaTotal) {
		this.gananciaTotal = gananciaTotal;
	}

	public void setSumatoriaPrecio(int sumatoriaPrecio) {
		this.sumatoriaPrecio = sumatoriaPrecio;
	}

	public void setPrecioPagar(int precioPagar) {
		this.precioPagar = precioPagar;
	}

	public void setMetrosTotal(double metrosTotal) {
		this.metrosTotal = metrosTotal;
	}

	public void setMetrosFechaEmision(double metrosFechaEmision) {
		this.metrosFechaEmision = metrosFechaEmision;
	}

	public void setEsPresupuesto(boolean esPresupuesto) {
		this.esPresupuesto = esPresupuesto;
	}

	public void setConsiderarMetraje(boolean considerarMetraje) {
		this.considerarMetraje = considerarMetraje;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setPedidoDetalles(List<PedidoDetalles> pedidoDetalles) {
		this.pedidoDetalles = pedidoDetalles;
	}

	public void setPedidosConfecciones(List<PedidoDetalleConfeccion> pedidosConfecciones) {
		this.pedidosConfecciones = pedidosConfecciones;
	}

	public void setPagosPedido(List<CajaMovimiento> pagosPedido) {
		this.pagosPedido = pagosPedido;
	}

	public void setPedidoCostura(Boolean pedidoCostura) {
		this.pedidoCostura = pedidoCostura;
	}

	public void setPedidoCarteleria(Boolean pedidoCarteleria) {
		this.pedidoCarteleria = pedidoCarteleria;
	}

	public void setGeneraDeuda(boolean generaDeuda) {
		this.generaDeuda = generaDeuda;
	}

	public void setDeudaPaga(boolean deudaPaga) {
		this.deudaPaga = deudaPaga;
	}

	public String getInformacionResponsable() {
		return informacionResponsable;
	}

	public void setInformacionResponsable(String informacionResponsable) {
		this.informacionResponsable = informacionResponsable;
	}

	public double getSumaPagos() {
		sumaPagos = 0;
		try {
			sumaPagos = (pagosPedido.stream().filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false)
					.mapToDouble(b -> b.getValorGS() * b.getCotizacionGS()).sum())
					+ (pagosPedido.stream().filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false)
							.mapToDouble(b -> b.getValorRS() * b.getCotizacionRS()).sum())
					+ (pagosPedido.stream().filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false)
							.mapToDouble(b -> b.getValorUS() * b.getCotizacionUS()).sum());

		} catch (Exception e) {
			new Exception("Sin pagos");
		}
		return sumaPagos;
	}

	public void setSumaPagos(double sumaPagos) {
		this.sumaPagos = sumaPagos;
	}

	public boolean isProduccionFinalizada() {
		return produccionFinalizada;
	}

	public void setProduccionFinalizada(boolean produccionFinalizada) {
		this.produccionFinalizada = produccionFinalizada;
	}

	public List<Produccion> getProducciones() {
		try {
			producciones = producciones.stream().sorted(Comparator.comparing(Produccion::getId))
					.collect(Collectors.toList());
		} catch (Exception e) {
			new Exception("Sin produccion");
		}
		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	

	public double getDescuentoTotal() {
		return descuentoTotal;
	}

	public void setDescuentoTotal(double descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	public double getGananciaTotal() {
		return gananciaTotal;
	}

	public void setGananciaTotal(double gananciaTotal) {
		this.gananciaTotal = gananciaTotal;
	}

	public double getSumatoriaPrecio() {
		return sumatoriaPrecio;
	}

	public void setSumatoriaPrecio(double sumatoriaPrecio) {
		this.sumatoriaPrecio = sumatoriaPrecio;
	}

	public double getPrecioPagar() {
		return precioPagar;
	}

	public void setPrecioPagar(double precioPagar) {
		this.precioPagar = precioPagar;
	}

	public Date getFechaUltimoRegistroProduccion() {
		return fechaUltimoRegistroProduccion;
	}

	public void setFechaUltimoRegistroProduccion(Date fechaUltimoRegistroProduccion) {
		this.fechaUltimoRegistroProduccion = fechaUltimoRegistroProduccion;
	}

	public boolean isConDetalle() {
		return conDetalle;
	}

	public void setConDetalle(boolean conDetalle) {
		this.conDetalle = conDetalle;
	}

	public String getTipoPagoPedido() {
		return tipoPagoPedido;
	}

	@Override
	public String toString() {
		return id + " - " + fechaRegistro;
	}

	public List<Imagen> getImagenes() {
		return imagenes;
	}

	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}
	
	
}
