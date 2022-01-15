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
public class Caja {
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModificado = new Date();

	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Colaborador colaborador;

	@Column(nullable = false)
	private boolean cajaCerrada;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoInicialGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoInicialRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoInicialUS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoFinalGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoFinalRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoFinalUS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoIngresadoGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoIngresadoRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoIngresadoUS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoRetiradoGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoRetiradoRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoRetiradoUS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoDeclaradoGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoDeclaradoRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoDeclaradoUS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoEntregadoGS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoEntregadoRS;

	@ColumnDefault("0")
	@Column(nullable = false)
	private double saldoEntregadoUS;

	@OneToMany(mappedBy = "caja", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
	private List<CajaMovimiento> cajaMovimientos;

	public String registrar() {
		return "Caja [id=" + id + ", fechaRegistro=" + fechaRegistro + ", fechaModificado=" + fechaModificado
				+ ", estado=" + estado + ", colaborador=" + colaborador + ", cajaCerrada=" + cajaCerrada
				+ ", saldoInicialGS=" + saldoInicialGS + ", saldoInicialRS=" + saldoInicialRS + ", saldoInicialUS="
				+ saldoInicialUS + ", saldoFinalGS=" + saldoFinalGS + ", saldoFinalRS=" + saldoFinalRS
				+ ", saldoFinalUS=" + saldoFinalUS + ", saldoIngresadoGS=" + saldoIngresadoGS + ", saldoIngresadoRS="
				+ saldoIngresadoRS + ", saldoIngresadoUS=" + saldoIngresadoUS + ", saldoRetiradoGS=" + saldoRetiradoGS
				+ ", saldoRetiradoRS=" + saldoRetiradoRS + ", saldoRetiradoUS=" + saldoRetiradoUS
				+ ", saldoDeclaradoGS=" + saldoDeclaradoGS + ", saldoDeclaradoRS=" + saldoDeclaradoRS
				+ ", saldoDeclaradoUS=" + saldoDeclaradoUS + ", saldoEntregadoGS=" + saldoEntregadoGS
				+ ", saldoEntregadoRS=" + saldoEntregadoRS + ", saldoEntregadoUS=" + saldoEntregadoUS
				+ ", cajaMovimientos=" + cajaMovimientos + "]";
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

	public Colaborador getColaborador() {
		return colaborador;
	}

	public boolean isCajaCerrada() {
		return cajaCerrada;
	}

	public double getSaldoInicialGS() {
		return saldoInicialGS;
	}

	public double getSaldoInicialRS() {
		return saldoInicialRS;
	}

	public double getSaldoInicialUS() {
		return saldoInicialUS;
	}

	public double getSaldoFinalGS() {
		return saldoFinalGS;
	}

	public double getSaldoFinalRS() {
		return saldoFinalRS;
	}

	public double getSaldoFinalUS() {
		return saldoFinalUS;
	}

	public double getSaldoIngresadoGS() {
		return saldoIngresadoGS;
	}

	public double getSaldoIngresadoRS() {
		return saldoIngresadoRS;
	}

	public double getSaldoIngresadoUS() {
		return saldoIngresadoUS;
	}

	public double getSaldoRetiradoGS() {
		return saldoRetiradoGS;
	}

	public double getSaldoRetiradoRS() {
		return saldoRetiradoRS;
	}

	public double getSaldoRetiradoUS() {
		return saldoRetiradoUS;
	}

	public double getSaldoDeclaradoGS() {
		return saldoDeclaradoGS;
	}

	public double getSaldoDeclaradoRS() {
		return saldoDeclaradoRS;
	}

	public double getSaldoDeclaradoUS() {
		return saldoDeclaradoUS;
	}

	public double getSaldoEntregadoGS() {
		return saldoEntregadoGS;
	}

	public double getSaldoEntregadoRS() {
		return saldoEntregadoRS;
	}

	public double getSaldoEntregadoUS() {
		return saldoEntregadoUS;
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

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public void setCajaCerrada(boolean cajaCerrada) {
		this.cajaCerrada = cajaCerrada;
	}

	public void setSaldoInicialGS(double saldoInicialGS) {
		this.saldoInicialGS = saldoInicialGS;
	}

	public void setSaldoInicialRS(double saldoInicialRS) {
		this.saldoInicialRS = saldoInicialRS;
	}

	public void setSaldoInicialUS(double saldoInicialUS) {
		this.saldoInicialUS = saldoInicialUS;
	}

	public void setSaldoFinalGS(double saldoFinalGS) {
		this.saldoFinalGS = saldoFinalGS;
	}

	public void setSaldoFinalRS(double saldoFinalRS) {
		this.saldoFinalRS = saldoFinalRS;
	}

	public void setSaldoFinalUS(double saldoFinalUS) {
		this.saldoFinalUS = saldoFinalUS;
	}

	public void setSaldoIngresadoGS(double saldoIngresadoGS) {
		this.saldoIngresadoGS = saldoIngresadoGS;
	}

	public void setSaldoIngresadoRS(double saldoIngresadoRS) {
		this.saldoIngresadoRS = saldoIngresadoRS;
	}

	public void setSaldoIngresadoUS(double saldoIngresadoUS) {
		this.saldoIngresadoUS = saldoIngresadoUS;
	}

	public void setSaldoRetiradoGS(double saldoRetiradoGS) {
		this.saldoRetiradoGS = saldoRetiradoGS;
	}

	public void setSaldoRetiradoRS(double saldoRetiradoRS) {
		this.saldoRetiradoRS = saldoRetiradoRS;
	}

	public void setSaldoRetiradoUS(double saldoRetiradoUS) {
		this.saldoRetiradoUS = saldoRetiradoUS;
	}

	public void setSaldoDeclaradoGS(double saldoDeclaradoGS) {
		this.saldoDeclaradoGS = saldoDeclaradoGS;
	}

	public void setSaldoDeclaradoRS(double saldoDeclaradoRS) {
		this.saldoDeclaradoRS = saldoDeclaradoRS;
	}

	public void setSaldoDeclaradoUS(double saldoDeclaradoUS) {
		this.saldoDeclaradoUS = saldoDeclaradoUS;
	}

	public void setSaldoEntregadoGS(double saldoEntregadoGS) {
		this.saldoEntregadoGS = saldoEntregadoGS;
	}

	public void setSaldoEntregadoRS(double saldoEntregadoRS) {
		this.saldoEntregadoRS = saldoEntregadoRS;
	}

	public void setSaldoEntregadoUS(double saldoEntregadoUS) {
		this.saldoEntregadoUS = saldoEntregadoUS;
	}

	public void setCajaMovimientos(List<CajaMovimiento> cajaMovimientos) {
		this.cajaMovimientos = cajaMovimientos;
	}

	public Date getFechaModificado() {
		return fechaModificado;
	}

	public void setFechaModificado(Date fechaModificado) {
		this.fechaModificado = fechaModificado;
	}

}
