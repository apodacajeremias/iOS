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
public class CompraDetalle {
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
	@JoinColumn(nullable = false)
	private Compra compra;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Marca marca;

	@Column(nullable = true)
	private double medidaAncho;

	@Column(nullable = true)
	private double medidaAlto;

	@Column(nullable = true)
	private double medidaDetalle;

	@Column(nullable = true)
	private int cantidadDetalle;

	@Column(nullable = true)
	private int costoMaterial;

	@Column(nullable = true)
	private int precioMinorista;

	@Column(nullable = true)
	private int precioMayorista5;

	@Column(nullable = true)
	private int precioMayorista10;

	@Column(nullable = true)
	private int precioMayorista50;

	@Column(nullable = true)
	private int precioMayorista100;

	@Column(nullable = true)
	private int precioMayorista200;

	@Column(nullable = true)
	private int precioMayorista300;

	@Column(nullable = true)
	private double porcentajeMinorista;

	@Column(nullable = true)
	private double porcentajeMayorista;

	@Column(nullable = false)
	private boolean existenciaDisponible;

	@OneToMany(mappedBy = "compraDetalle", cascade = CascadeType.ALL, orphanRemoval = false)
	private List<Existencia> existencia;

	public int getId() {
		return id;
	}

	public Material getMaterial() {
		return material;
	}

	public double getMedidaAncho() {
		return medidaAncho;
	}

	public double getMedidaAlto() {
		return medidaAlto;
	}

	public double getMedidaDetalle() {
		return medidaDetalle;
	}

	public int getCantidadDetalle() {
		return cantidadDetalle;
	}

	public int getCostoMaterial() {
		return costoMaterial;
	}

	public int getPrecioMinorista() {
		return precioMinorista;
	}

	public int getPrecioMayorista5() {
		return precioMayorista5;
	}

	public int getPrecioMayorista10() {
		return precioMayorista10;
	}

	public int getPrecioMayorista50() {
		return precioMayorista50;
	}

	public int getPrecioMayorista100() {
		return precioMayorista100;
	}

	public int getPrecioMayorista200() {
		return precioMayorista200;
	}

	public int getPrecioMayorista300() {
		return precioMayorista300;
	}

	public double getPorcentajeMinorista() {
		return porcentajeMinorista;
	}

	public double getPorcentajeMayorista() {
		return porcentajeMayorista;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public void setMedidaAncho(double medidaAncho) {
		this.medidaAncho = medidaAncho;
	}

	public void setMedidaAlto(double medidaAlto) {
		this.medidaAlto = medidaAlto;
	}

	public void setMedidaDetalle(double medidaDetalle) {
		this.medidaDetalle = medidaDetalle;
	}

	public void setCantidadDetalle(int cantidadDetalle) {
		this.cantidadDetalle = cantidadDetalle;
	}

	public void setCostoMaterial(int costoMaterial) {
		this.costoMaterial = costoMaterial;
	}

	public void setPrecioMinorista(int precioMinorista) {
		this.precioMinorista = precioMinorista;
	}

	public void setPrecioMayorista5(int precioMayorista5) {
		this.precioMayorista5 = precioMayorista5;
	}

	public void setPrecioMayorista10(int precioMayorista10) {
		this.precioMayorista10 = precioMayorista10;
	}

	public void setPrecioMayorista50(int precioMayorista50) {
		this.precioMayorista50 = precioMayorista50;
	}

	public void setPrecioMayorista100(int precioMayorista100) {
		this.precioMayorista100 = precioMayorista100;
	}

	public void setPrecioMayorista200(int precioMayorista200) {
		this.precioMayorista200 = precioMayorista200;
	}

	public void setPrecioMayorista300(int precioMayorista300) {
		this.precioMayorista300 = precioMayorista300;
	}

	public void setPorcentajeMinorista(double porcentajeMinorista) {
		this.porcentajeMinorista = porcentajeMinorista;
	}

	public void setPorcentajeMayorista(double porcentajeMayorista) {
		this.porcentajeMayorista = porcentajeMayorista;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	public List<Existencia> getExistencia() {
		return existencia;
	}

	public void setExistencia(List<Existencia> existencia) {
		this.existencia = existencia;
	}

	public boolean isExistenciaDisponible() {
		return existenciaDisponible;
	}

	public void setExistenciaDisponible(boolean existenciaDisponible) {
		this.existenciaDisponible = existenciaDisponible;
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

	public String registrar() {
		return "CompraDetalle [id=" + id + ", fechaRegistro=" + fechaRegistro + ", estado=" + estado + ", colaborador="
				+ colaborador + ", compra=" + compra + ", material=" + material + ", marca=" + marca + ", medidaAncho="
				+ medidaAncho + ", medidaAlto=" + medidaAlto + ", medidaDetalle=" + medidaDetalle + ", cantidadDetalle="
				+ cantidadDetalle + ", costoMaterial=" + costoMaterial + ", precioMinorista=" + precioMinorista
				+ ", precioMayorista5=" + precioMayorista5 + ", precioMayorista10=" + precioMayorista10
				+ ", precioMayorista50=" + precioMayorista50 + ", precioMayorista100=" + precioMayorista100
				+ ", precioMayorista200=" + precioMayorista200 + ", precioMayorista300=" + precioMayorista300
				+ ", porcentajeMinorista=" + porcentajeMinorista + ", porcentajeMayorista=" + porcentajeMayorista
				+ ", existenciaDisponible=" + existenciaDisponible + ", existencia=" + existencia + "]";
	}
	
	

}
