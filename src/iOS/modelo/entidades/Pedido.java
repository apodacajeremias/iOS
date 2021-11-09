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

import iOS.controlador.util.EventosUtil;

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
	

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}

	
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
	
	//orphanRemoval = true es para que elimine un item de la lista en BD
	//fetch = lazy es para que no cargue si no hay la solicitud con el get
	@OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<PedidoDetalles> pedidoDetalles;
	
	@OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.LAZY)
	private List<PedidoDetalleConfeccion> pedidosConfecciones;
	
	@OneToMany(mappedBy = "pedido", cascade=CascadeType.ALL, orphanRemoval=false, fetch=FetchType.LAZY)
	private List<CajaMovimiento> pagosPedido;
	
	@Column(nullable = true)
	private Boolean pedidoCostura;
	
	@Column(nullable = true)
	private Boolean pedidoCarteleria;
	
	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean generaDeuda;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getTipoPagoPedido() {
		return tipoPagoPedido;
	}

	public void setTipoPagoPedido(String tipoPagoPedido) {
		this.tipoPagoPedido = tipoPagoPedido;
	}

	public int getDescuentoTotal() {
		return descuentoTotal;
	}

	public void setDescuentoTotal(int descuentoTotal) {
		this.descuentoTotal = descuentoTotal;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}

	public int getGananciaTotal() {
		return gananciaTotal;
	}

	public void setGananciaTotal(int gananciaTotal) {
		this.gananciaTotal = gananciaTotal;
	}

	public int getSumatoriaPrecio() {
		return sumatoriaPrecio;
	}

	public void setSumatoriaPrecio(int sumatoriaPrecio) {
		this.sumatoriaPrecio = sumatoriaPrecio;
	}

	public int getPrecioPagar() {
		return precioPagar;
	}

	public void setPrecioPagar(int precioPagar) {
		this.precioPagar = precioPagar;
	}

	public double getMetrosTotal() {
		return metrosTotal;
	}

	public void setMetrosTotal(double metrosTotal) {
		this.metrosTotal = metrosTotal;
	}

	public double getMetrosFechaEmision() {
		return metrosFechaEmision;
	}

	public void setMetrosFechaEmision(double metrosFechaEmision) {
		this.metrosFechaEmision = metrosFechaEmision;
	}

	public boolean isEsPresupuesto() {
		return esPresupuesto;
	}

	public void setEsPresupuesto(boolean esPresupuesto) {
		this.esPresupuesto = esPresupuesto;
	}

	public boolean isConsiderarMetraje() {
		return considerarMetraje;
	}

	public void setConsiderarMetraje(boolean considerarMetraje) {
		this.considerarMetraje = considerarMetraje;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<PedidoDetalles> getPedidoDetalles() {
		return pedidoDetalles;
	}

	public void setPedidoDetalles(List<PedidoDetalles> pedidoDetalles) {
		this.pedidoDetalles = pedidoDetalles;
	}
	
	


	public List<PedidoDetalleConfeccion> getPedidosConfecciones() {
		return pedidosConfecciones;
	}


	public void setPedidosConfecciones(List<PedidoDetalleConfeccion> pedidosConfecciones) {
		this.pedidosConfecciones = pedidosConfecciones;
	}


	@Override
	public String toString() {
		return id + " - " + EventosUtil.formatoFecha(fechaRegistro);
	}


	public Boolean getPedidoCostura() {
		return pedidoCostura;
	}


	public Boolean getPedidoCarteleria() {
		return pedidoCarteleria;
	}


	public void setPedidoCostura(Boolean pedidoCostura) {
		this.pedidoCostura = pedidoCostura;
	}


	public void setPedidoCarteleria(Boolean pedidoCarteleria) {
		this.pedidoCarteleria = pedidoCarteleria;
	}


	public List<CajaMovimiento> getPagosPedido() {
		return pagosPedido;
	}


	public void setPagosPedido(List<CajaMovimiento> pagosPedido) {
		this.pagosPedido = pagosPedido;
	}


	public boolean isGeneraDeuda() {
		return generaDeuda;
	}


	public void setGeneraDeuda(boolean generaDeuda) {
		this.generaDeuda = generaDeuda;
	}
	



	
}
