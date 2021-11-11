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
public class Proveedor {
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
	private String nombreCompleto;
	
	@Column(nullable = false, unique = true)
	private String identificacion;
	
	@Column(nullable = true)
	private String direccion;
	
	@OneToMany(mappedBy = "proveedor", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<ProveedorContactos> listaContactos;
	
	@OneToMany(mappedBy = "proveedor", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<ProveedorCorreos> listaCorreos;	
	
	@OneToMany(mappedBy = "proveedor", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<InformacionPago> informacionesPago;
	
	@OneToMany(mappedBy = "proveedor", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<Compra> compras;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Funcionario getColaborador() {
		return colaborador;
	}


	public void setColaborador(Funcionario colaborador) {
		this.colaborador = colaborador;
	}


	public String getNombreCompleto() {
		return nombreCompleto;
	}


	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}


	public String getIdentificacion() {
		return identificacion;
	}


	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public List<ProveedorContactos> getListaContactos() {
		return listaContactos;
	}


	public void setListaContactos(List<ProveedorContactos> listaContactos) {
		this.listaContactos = listaContactos;
	}


	public List<ProveedorCorreos> getListaCorreos() {
		return listaCorreos;
	}


	public void setListaCorreos(List<ProveedorCorreos> listaCorreos) {
		this.listaCorreos = listaCorreos;
	}


	public List<InformacionPago> getInformacionesPago() {
		return informacionesPago;
	}


	public void setInformacionesPago(List<InformacionPago> informacionesPago) {
		this.informacionesPago = informacionesPago;
	}


	public List<Compra> getCompras() {
		return compras;
	}


	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}


	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
	
	
}
