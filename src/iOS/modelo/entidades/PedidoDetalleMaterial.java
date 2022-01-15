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
public class PedidoDetalleMaterial {
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
	private PedidoDetalles detalleCarteleria;

	@ManyToOne
	@JoinColumn(nullable = true)
	private PedidoDetalleConfeccion detalleConfeccion;

	@ManyToOne
	@JoinColumn(nullable = false)
	private ProductoMaterial material;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double costo;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double subtotal;

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

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean materialFijo;

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

	public PedidoDetalles getDetalleCarteleria() {
		return detalleCarteleria;
	}

	public void setDetalleCarteleria(PedidoDetalles detalleCarteleria) {
		this.detalleCarteleria = detalleCarteleria;
	}

	public PedidoDetalleConfeccion getDetalleConfeccion() {
		return detalleConfeccion;
	}

	public void setDetalleConfeccion(PedidoDetalleConfeccion detalleConfeccion) {
		this.detalleConfeccion = detalleConfeccion;
	}

	public ProductoMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ProductoMaterial material) {
		this.material = material;
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

	public double getCantidadDetalle() {
		return cantidadDetalle;
	}

	public void setCantidadDetalle(double cantidadDetalle) {
		this.cantidadDetalle = cantidadDetalle;
	}

	public boolean isMaterialFijo() {
		return materialFijo;
	}

	public void setMaterialFijo(boolean materialFijo) {
		this.materialFijo = materialFijo;
	}
	

	@Override
	public String toString() {
		return id + " - " + fechaRegistro;
	}

}