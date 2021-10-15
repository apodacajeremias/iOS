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
public class CajaMovimiento {
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
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Colaborador colaborador;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Sector sector;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Caja caja;

	//Cheque, efectivo u otros
	@Column(nullable = false)
	private String tipoValor;

	@Column(nullable = true)
	private String observacion;
	
	@Column(nullable = false)
	private boolean esAnulado;
	
	@Column(nullable = false)
	private boolean esRetiro;

	@Column(nullable = false)
	private double valorGS;

	@Column(nullable = false)
	private double valorRS;

	@Column(nullable = false)
	private double valorUS;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean isEsAnulado() {
		return esAnulado;
	}

	public void setEsAnulado(boolean esAnulado) {
		this.esAnulado = esAnulado;
	}

	public boolean isEsRetiro() {
		return esRetiro;
	}

	public void setEsRetiro(boolean esRetiro) {
		this.esRetiro = esRetiro;
	}

	public double getValorGS() {
		return valorGS;
	}

	public void setValorGS(double valorGS) {
		this.valorGS = valorGS;
	}

	public double getValorRS() {
		return valorRS;
	}

	public void setValorRS(double valorRS) {
		this.valorRS = valorRS;
	}

	public double getValorUS() {
		return valorUS;
	}

	public void setValorUS(double valorUS) {
		this.valorUS = valorUS;
	}
}
