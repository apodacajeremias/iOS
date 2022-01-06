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

	@Column(nullable = true)
	private int descuentoTotal;

	@Column(nullable = true)
	private int costoTotal;

	@Column(nullable = true)
	private int gananciaTotal;

	@Column(nullable = true)
	private int sumatoriaPrecio;

	@Column(nullable = true)
	private int precioPagar;

	@Column(nullable = true)
	private double metrosTotal;

	@Column(nullable = true)
	private double metrosFechaEmision;

	@Column(nullable = true)
	private boolean esPresupuesto;

	@Column(nullable = true)
	private boolean considerarMetraje;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Cliente cliente;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoDetalles> pedidoDetalles;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoDetalleConfeccion> pedidosConfecciones;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<CajaMovimiento> pagosPedido;

	@Column(nullable = true)
	private Boolean pedidoCostura;

	@Column(nullable = true)
	private Boolean pedidoCarteleria;

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

	public String getTipoPagoPedido() {
		return tipoPagoPedido;
	}

	public int getDescuentoTotal() {
		return descuentoTotal;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public int getGananciaTotal() {
		return gananciaTotal;
	}

	public int getSumatoriaPrecio() {
		return sumatoriaPrecio;
	}

	public int getPrecioPagar() {
		return precioPagar;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pedidosConfecciones;
	}

	public List<CajaMovimiento> getPagosPedido() {
		return pagosPedido;
	}

	public Boolean getPedidoCostura() {
		return pedidoCostura;
	}

	public Boolean getPedidoCarteleria() {
		return pedidoCarteleria;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			// TODO: handle exception
			e.printStackTrace();
		}
		return producciones;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	public Date getFechaUltimoRegistroProduccion() {
		return fechaUltimoRegistroProduccion;
	}

	public void setFechaUltimoRegistroProduccion(Date fechaUltimoRegistroProduccion) {
		this.fechaUltimoRegistroProduccion = fechaUltimoRegistroProduccion;
	}

}
