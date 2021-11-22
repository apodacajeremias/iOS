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

@Entity
public class Material {
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
	private Colaborador colaboradorQueRegistra;

	@Column(nullable = false)
	private double precioMinimo;

	@Column(nullable = false)
	private double precioMaximo;

	@Column(nullable = false)
	private String descripcion;

	@Column(nullable = true, unique = true)
	private String codigoReferencia;
	
	@Column(nullable = false)
	private String tipoCobro;
	
	@OneToMany(mappedBy = "material", cascade=CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ProductoMaterial> productos;

	@Override
	public String toString() {
		return descripcion;
	}

	public int getId() {
		return id;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean isEstado() {
		return estado;
	}

	public Colaborador getColaboradorQueRegistra() {
		return colaboradorQueRegistra;
	}

	public double getPrecioMinimo() {
		return precioMinimo;
	}

	public double getPrecioMaximo() {
		return precioMaximo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public String getTipoCobro() {
		return tipoCobro;
	}

	public List<ProductoMaterial> getProductos() {
		return productos;
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

	public void setColaboradorQueRegistra(Colaborador colaboradorQueRegistra) {
		this.colaboradorQueRegistra = colaboradorQueRegistra;
	}

	public void setPrecioMinimo(double precioMinimo) {
		this.precioMinimo = precioMinimo;
	}

	public void setPrecioMaximo(double precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public void setTipoCobro(String tipoCobro) {
		this.tipoCobro = tipoCobro;
	}

	public void setProductos(List<ProductoMaterial> productos) {
		this.productos = productos;
	}
	
	

}
