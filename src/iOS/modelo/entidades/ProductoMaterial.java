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
public class ProductoMaterial {
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
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Producto producto;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Material material;
	
	@Column(nullable = false)
	private double precioMaterial;

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

	public Producto getProducto() {
		return producto;
	}

	public Material getMaterial() {
		return material;
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

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public double getPrecioMaterial() {
		return precioMaterial;
	}

	public void setPrecioMaterial(double precioMaterial) {
		this.precioMaterial = precioMaterial;
	}
	
	
}
