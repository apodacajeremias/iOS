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
public class Existencia {
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
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private CompraDetalle compraDetalle;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Material material;
	
	@OneToMany(mappedBy = "existencia", cascade=CascadeType.ALL, orphanRemoval=false)
	private List<DepositoExistencia> depositoExistencia;

	@Column(nullable = false)
	private String referencia;
	
	@Column(nullable = false)
	private boolean estadoUso;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CompraDetalle getCompraDetalle() {
		return compraDetalle;
	}

	public void setCompraDetalle(CompraDetalle compraDetalle) {
		this.compraDetalle = compraDetalle;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public List<DepositoExistencia> getDepositoExistencia() {
		return depositoExistencia;
	}

	public void setDepositoExistencia(List<DepositoExistencia> depositoExistencia) {
		this.depositoExistencia = depositoExistencia;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public boolean isEstadoUso() {
		return estadoUso;
	}

	public void setEstadoUso(boolean estadoUso) {
		this.estadoUso = estadoUso;
	}

	@Override
	public String toString() {
		return "Existencia [id=" + id + ", compraDetalle=" + compraDetalle
				+ ", material=" + material + ", referencia=" + referencia
				+ ", estadoUso=" + estadoUso + "]";
	}

	
	
	
	
	
}
