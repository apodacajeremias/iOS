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
public class Colaborador {
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
	private Colaborador colaboradorQueRegistra;

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean isEstado() {
		return estado;
	}

	public Colaborador getColaboradorQueRegistra() {
		return colaboradorQueRegistra;
	}

	public void setColaboradorQueRegistra(Colaborador colaboradorQueRegistra) {
		this.colaboradorQueRegistra = colaboradorQueRegistra;
	}

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

	@Column(nullable = false, unique = true)
	private String usuario;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean esActivo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public String getTipoSalario() {
		return tipoSalario;
	}

	public void setTipoSalario(String tipoSalario) {
		this.tipoSalario = tipoSalario;
	}

	public boolean isEsOperador() {
		return esOperador;
	}

	public void setEsOperador(boolean esOperador) {
		this.esOperador = esOperador;
	}

	public Date getFechaIngresoColaborador() {
		return fechaIngresoColaborador;
	}

	public void setFechaIngresoColaborador(Date fechaIngresoColaborador) {
		this.fechaIngresoColaborador = fechaIngresoColaborador;
	}

	public Date getFechaDesvinculacionColaborador() {
		return fechaDesvinculacionColaborador;
	}

	public void setFechaDesvinculacionColaborador(Date fechaDesvinculacionColaborador) {
		this.fechaDesvinculacionColaborador = fechaDesvinculacionColaborador;
	}

	public boolean isFueDesvinculado() {
		return fueDesvinculado;
	}

	public void setFueDesvinculado(boolean fueDesvinculado) {
		this.fueDesvinculado = fueDesvinculado;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public List<Caja> getCaja() {
		return caja;
	}

	public void setCaja(List<Caja> caja) {
		this.caja = caja;
	}

	public List<CajaMovimiento> getCajaMovimientos() {
		return cajaMovimientos;
	}

	public void setCajaMovimientos(List<CajaMovimiento> cajaMovimientos) {
		this.cajaMovimientos = cajaMovimientos;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEsActivo() {
		return esActivo;
	}

	public void setEsActivo(boolean esActivo) {
		this.esActivo = esActivo;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return nombreCompleto;
	}

}
