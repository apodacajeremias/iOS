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
	
	@ColumnDefault("true")
	@Column(nullable = false)
	private boolean estado = true;
	
	@ManyToOne
	@JoinColumn(nullable = false)
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

	
	@OneToMany(mappedBy = "caja", cascade=CascadeType.ALL, orphanRemoval=false, fetch=FetchType.EAGER)
	private List<CajaMovimiento> cajaMovimientos;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public boolean isCajaCerrada() {
		return cajaCerrada;
	}


	public void setCajaCerrada(boolean cajaCerrada) {
		this.cajaCerrada = cajaCerrada;
	}


	public double getSaldoInicialGS() {
		return saldoInicialGS;
	}


	public void setSaldoInicialGS(double saldoInicialGS) {
		this.saldoInicialGS = saldoInicialGS;
	}


	public double getSaldoInicialRS() {
		return saldoInicialRS;
	}


	public void setSaldoInicialRS(double saldoInicialRS) {
		this.saldoInicialRS = saldoInicialRS;
	}


	public double getSaldoInicialUS() {
		return saldoInicialUS;
	}


	public void setSaldoInicialUS(double saldoInicialUS) {
		this.saldoInicialUS = saldoInicialUS;
	}


	public double getSaldoFinalGS() {
		return saldoFinalGS;
	}


	public void setSaldoFinalGS(double saldoFinalGS) {
		this.saldoFinalGS = saldoFinalGS;
	}


	public double getSaldoFinalRS() {
		return saldoFinalRS;
	}


	public void setSaldoFinalRS(double saldoFinalRS) {
		this.saldoFinalRS = saldoFinalRS;
	}


	public double getSaldoFinalUS() {
		return saldoFinalUS;
	}


	public void setSaldoFinalUS(double saldoFinalUS) {
		this.saldoFinalUS = saldoFinalUS;
	}


	public double getSaldoIngresadoGS() {
		return saldoIngresadoGS;
	}


	public void setSaldoIngresadoGS(double saldoIngresadoGS) {
		this.saldoIngresadoGS = saldoIngresadoGS;
	}


	public double getSaldoIngresadoRS() {
		return saldoIngresadoRS;
	}


	public void setSaldoIngresadoRS(double saldoIngresadoRS) {
		this.saldoIngresadoRS = saldoIngresadoRS;
	}


	public double getSaldoIngresadoUS() {
		return saldoIngresadoUS;
	}


	public void setSaldoIngresadoUS(double saldoIngresadoUS) {
		this.saldoIngresadoUS = saldoIngresadoUS;
	}


	public double getSaldoRetiradoGS() {
		return saldoRetiradoGS;
	}


	public void setSaldoRetiradoGS(double saldoRetiradoGS) {
		this.saldoRetiradoGS = saldoRetiradoGS;
	}


	public double getSaldoRetiradoRS() {
		return saldoRetiradoRS;
	}


	public void setSaldoRetiradoRS(double saldoRetiradoRS) {
		this.saldoRetiradoRS = saldoRetiradoRS;
	}


	public double getSaldoRetiradoUS() {
		return saldoRetiradoUS;
	}


	public void setSaldoRetiradoUS(double saldoRetiradoUS) {
		this.saldoRetiradoUS = saldoRetiradoUS;
	}


	public List<CajaMovimiento> getCajaMovimientos() {
		return cajaMovimientos;
	}


	public void setCajaMovimientos(List<CajaMovimiento> cajaMovimientos) {
		this.cajaMovimientos = cajaMovimientos;
	}


	public double getSaldoDeclaradoGS() {
		return saldoDeclaradoGS;
	}


	public void setSaldoDeclaradoGS(double saldoDeclaradoGS) {
		this.saldoDeclaradoGS = saldoDeclaradoGS;
	}


	public double getSaldoDeclaradoRS() {
		return saldoDeclaradoRS;
	}


	public void setSaldoDeclaradoRS(double saldoDeclaradoRS) {
		this.saldoDeclaradoRS = saldoDeclaradoRS;
	}


	public double getSaldoDeclaradoUS() {
		return saldoDeclaradoUS;
	}


	public void setSaldoDeclaradoUS(double saldoDeclaradoUS) {
		this.saldoDeclaradoUS = saldoDeclaradoUS;
	}


	public double getSaldoEntregadoGS() {
		return saldoEntregadoGS;
	}


	public void setSaldoEntregadoGS(double saldoEntregadoGS) {
		this.saldoEntregadoGS = saldoEntregadoGS;
	}


	public double getSaldoEntregadoRS() {
		return saldoEntregadoRS;
	}


	public void setSaldoEntregadoRS(double saldoEntregadoRS) {
		this.saldoEntregadoRS = saldoEntregadoRS;
	}


	public double getSaldoEntregadoUS() {
		return saldoEntregadoUS;
	}


	public void setSaldoEntregadoUS(double saldoEntregadoUS) {
		this.saldoEntregadoUS = saldoEntregadoUS;
	}


	
	
	
	
}
