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
public class Compra {
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
	@JoinColumn(nullable = true)
	private Colaborador colaborador;
	

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}
	
	
	
	public Colaborador getColaborador() {
		return colaborador;
	}


	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}



	@ManyToOne
	@JoinColumn(nullable = false)
	private Proveedor proveedor;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private InformacionPago informacionPago;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistroCompra;
	
	@Temporal(TemporalType.TIME)
	private Date fechaCompra;
	
	@Column(nullable = false)
	private int valorCompra;
	
	@Column(nullable = false)
	private int valorNTCR;
	
	@Column(nullable = false)
	private int valorPago;
	
	@Column(nullable = false)
	private String nroFactura;
	
	@Column(nullable = false)
	private String nroNTCR;
	
	@OneToMany(mappedBy = "compra", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<CompraDetalle> compraDetalles;

	public int getId() {
		return id;
	}

	public Date getFechaRegistroCompra() {
		return fechaRegistroCompra;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public int getValorCompra() {
		return valorCompra;
	}

	public int getValorNTCR() {
		return valorNTCR;
	}

	public int getValorPago() {
		return valorPago;
	}

	public String getNroFactura() {
		return nroFactura;
	}

	public String getNroNTCR() {
		return nroNTCR;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFechaRegistroCompra(Date fechaRegistroCompra) {
		this.fechaRegistroCompra = fechaRegistroCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public void setValorCompra(int valorCompra) {
		this.valorCompra = valorCompra;
	}

	public void setValorNTCR(int valorNTCR) {
		this.valorNTCR = valorNTCR;
	}

	public void setValorPago(int valorPago) {
		this.valorPago = valorPago;
	}

	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}

	public void setNroNTCR(String nroNTCR) {
		this.nroNTCR = nroNTCR;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<CompraDetalle> getCompraDetalles() {
		return compraDetalles;
	}

	public void setCompraDetalles(List<CompraDetalle> compraDetalles) {
		this.compraDetalles = compraDetalles;
	}

	public InformacionPago getInformacionPago() {
		return informacionPago;
	}

	public void setInformacionPago(InformacionPago informacionPago) {
		this.informacionPago = informacionPago;
	}
	
	
	
	
	
}
