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

/**
 * @author 59598
 *
 */
@Entity
public class Funcionario {
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
	private Funcionario colaboradorQueRegistra;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Sector sector;

	@ManyToOne
	@JoinColumn(nullable = true)
	private Rol rol;

	@Column(nullable = false)
	private String nombreCompleto;

	@Column(nullable = false, unique = true)
	private String identificacion;

	@Column(nullable = false)
	private String contacto;

	@Column(nullable = false)
	private double salario;

	// Salario o comision
	@Column(nullable = false)
	private String tipoSalario;

	@Column(nullable = false)
	private boolean esOperador;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngresoColaborador;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaDesvinculacionColaborador;

	@Column(nullable = false)
	private boolean fueDesvinculado;

	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Caja> caja;

	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<CajaMovimiento> cajaMovimientos;

	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Pedido> pedidos;
	
	@OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Produccion> producciones;

	@Column(nullable = false, unique = true)
	private String usuario;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean esActivo;

	@Override
	public String toString() {
		return nombreCompleto;
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

	public Funcionario getColaboradorQueRegistra() {
		return colaboradorQueRegistra;
	}

	public Sector getSector() {
		return sector;
	}

	public Rol getRol() {
		return rol;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public String getContacto() {
		return contacto;
	}

	public double getSalario() {
		return salario;
	}

	public String getTipoSalario() {
		return tipoSalario;
	}

	public boolean isEsOperador() {
		return esOperador;
	}

	public Date getFechaIngresoColaborador() {
		return fechaIngresoColaborador;
	}

	public Date getFechaDesvinculacionColaborador() {
		return fechaDesvinculacionColaborador;
	}

	public boolean isFueDesvinculado() {
		return fueDesvinculado;
	}

	public List<Caja> getCaja() {
		return caja;
	}

	public List<CajaMovimiento> getCajaMovimientos() {
		return cajaMovimientos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public List<Produccion> getProducciones() {
		return producciones;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEsActivo() {
		return esActivo;
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

	public void setColaboradorQueRegistra(Funcionario colaboradorQueRegistra) {
		this.colaboradorQueRegistra = colaboradorQueRegistra;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public void setTipoSalario(String tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	public void setEsOperador(boolean esOperador) {
		this.esOperador = esOperador;
	}

	public void setFechaIngresoColaborador(Date fechaIngresoColaborador) {
		this.fechaIngresoColaborador = fechaIngresoColaborador;
	}

	public void setFechaDesvinculacionColaborador(Date fechaDesvinculacionColaborador) {
		this.fechaDesvinculacionColaborador = fechaDesvinculacionColaborador;
	}

	public void setFueDesvinculado(boolean fueDesvinculado) {
		this.fueDesvinculado = fueDesvinculado;
	}

	public void setCaja(List<Caja> caja) {
		this.caja = caja;
	}

	public void setCajaMovimientos(List<CajaMovimiento> cajaMovimientos) {
		this.cajaMovimientos = cajaMovimientos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public void setProducciones(List<Produccion> producciones) {
		this.producciones = producciones;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	
}
