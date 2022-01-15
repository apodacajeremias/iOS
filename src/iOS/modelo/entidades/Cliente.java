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
public class Cliente {
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

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean esB2B;

	@ColumnDefault("false")
	@Column(nullable = false)
	private boolean esB2C;

	@Column(nullable = false)
	private String nombreCompleto;

	@Column(nullable = true, unique = true)
	private String identificacion;

	@Column(nullable = true)
	private String contacto;

	@Column(nullable = true)
	private String direccion;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Pedido> pedidos;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<CajaMovimiento> cajaMovimientos;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<Representante> representantes;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double pagos;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double deudas;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double diferencia;

	public int getId() {
		return id;
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

	public String getDireccion() {
		return direccion;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public List<CajaMovimiento> getCajaMovimientos() {
		return cajaMovimientos;
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

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public void setCajaMovimientos(List<CajaMovimiento> cajaMovimientos) {
		this.cajaMovimientos = cajaMovimientos;
	}

	public double getPagos() {
		pagos = 0;
		try {
			pagos = (cajaMovimientos.stream()
					.filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false && a.getPedido() != null)
					.mapToDouble(b -> b.getValorGS() * b.getCotizacionGS()).sum())
					+ (cajaMovimientos.stream()
							.filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false && a.getPedido() != null)
							.mapToDouble(b -> b.getValorRS() * b.getCotizacionRS()).sum())
					+ (cajaMovimientos.stream()
							.filter(a -> a.isEsAnulado() == false && a.isEsRetiro() == false && a.getPedido() != null)
							.mapToDouble(b -> b.getValorUS() * b.getCotizacionUS()).sum());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pagos;
	}

	public double getDeudas() {
		deudas = 0;
		try {
			deudas = pedidos.stream().filter(a -> a.isEsPresupuesto() == false && a.isEstado() == true)
					.mapToDouble(b -> b.getPrecioPagar()).sum();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return deudas;
	}

	public double getDiferencia() {
		diferencia = 0;
		try {
			diferencia = getDeudas() - getPagos();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diferencia;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setPagos(double pagos) {
		this.pagos = pagos;
	}

	public void setDeudas(double deudas) {
		this.deudas = deudas;
	}

	public void setDiferencia(double diferencia) {
		this.diferencia = diferencia;
	}

//	public String registrar() {
//		return "Cliente [id=" + id + ", fechaRegistro=" + fechaRegistro + ", estado=" + estado + ", colaborador="
//				+ colaborador + ", nombreCompleto=" + nombreCompleto + ", identificacion=" + identificacion
//				+ ", contacto=" + contacto + ", direccion=" + direccion + ", pedidos=" + pedidos + ", cajaMovimientos="
//				+ cajaMovimientos + ", registros=" + registros + ", pagos=" + pagos + ", deudas=" + deudas
//				+ ", diferencia=" + diferencia + "]";
//	}

	public boolean isEsB2B() {
		return esB2B;
	}

	public void setEsB2B(boolean esB2B) {
		this.esB2B = esB2B;
	}

	public boolean isEsB2C() {
		return esB2C;
	}

	public void setEsB2C(boolean esB2C) {
		this.esB2C = esB2C;
	}

	public List<Representante> getRepresentantes() {
		return representantes;
	}

	public void setRepresentantes(List<Representante> representantes) {
		this.representantes = representantes;
	}

	@Override
	public String toString() {
		return nombreCompleto;
	}

}
