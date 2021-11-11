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
public class InformacionPago {
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
	private Funcionario colaborador;
	

	public Date getFechaRegistro() {
		return fechaRegistro;
	}


	public boolean isEstado() {
		return estado;
	}

	@Column(nullable = false)
	private String numeroCuenta;
	
	@Column(nullable = false)
	private String nombreCuenta;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Proveedor proveedor;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private EntidadBancaria entidadBancaria;
	
	@OneToMany(mappedBy = "informacionPago", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<Compra> compras;

	public int getId() {
		return id;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public String getNombreCuenta() {
		return nombreCuenta;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public EntidadBancaria getEntidadBancaria() {
		return entidadBancaria;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public void setNombreCuenta(String nombreCuenta) {
		this.nombreCuenta = nombreCuenta;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void setEntidadBancaria(EntidadBancaria entidadBancaria) {
		this.entidadBancaria = entidadBancaria;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	@Override
	public String toString() {
		return numeroCuenta+ " - " + entidadBancaria.getNombreBanco();
	}	

}
