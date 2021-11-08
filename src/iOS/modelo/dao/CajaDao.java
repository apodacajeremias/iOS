package iOS.modelo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;

public class CajaDao extends GenericDao<Caja> {

	public CajaDao() {
		super(Caja.class);
	}

	public Caja encontrarCajaHoy(int id) {
		getSession().beginTransaction();

		Date hoy = new Date();

		String sql = "from Caja " + "where colaborador.id = :id " + "and DATE(fechaRegistro) = :hoy "
				+ "and cajaCerrada = false";

		@SuppressWarnings("unchecked")
		Query<Caja> query = getSession().createQuery(sql);
		query.setParameter("hoy", hoy);
		query.setParameter("id", id);
		try {
			Caja caja = query.getSingleResult();
			commit();
			return caja;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<CajaMovimiento> ordenarMovimientosPorID(int cajaID) {
		getSession().beginTransaction();
		String sql = "from CajaMovimiento where caja.id = :cajaID order by id";
		@SuppressWarnings("unchecked")
		Query<CajaMovimiento> query = getSession().createQuery(sql);
		query.setParameter("cajaID", cajaID);
		try {
			List<CajaMovimiento> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	public List<CajaMovimiento> recuperarMovimientoNoAnulados(int cajaID) {
		getSession().beginTransaction();
		String sql = "from CajaMovimiento " + "WHERE caja.id = :cajaID " + "AND esAnulado = false " + "order by id";
		@SuppressWarnings("unchecked")
		Query<CajaMovimiento> query = getSession().createQuery(sql);
		query.setParameter("cajaID", cajaID);
		try {
			List<CajaMovimiento> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	public List<CajaMovimiento> recuperarEntregaPedido(int pedido) {
		getSession().beginTransaction();
		String sql = "from CajaMovimiento " + "where pedido.id = :pedido " + "and esAnulado = false "
				+ "order by id ASC";
		@SuppressWarnings("unchecked")
		Query<CajaMovimiento> query = getSession().createQuery(sql);
		query.setParameter("pedido", pedido);
		try {
			List<CajaMovimiento> resultados = query.getResultList();
			commit();
			return resultados;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	public Double sumarIngresosGS(int caja) {
		getSession().beginTransaction();
		String sql = "SELECT SUM(valorGS) " + "FROM CajaMovimiento " + "where esRetiro = false "
				+ "and esanulado = false " + "and caja.id = :caja";
		@SuppressWarnings("unchecked")
		Query<Double> query = getSession().createQuery(sql);
		query.setParameter("caja", caja);
		try {
			Double resultado = query.getSingleResult();
			commit();
			System.out.println(resultado);
			return resultado;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	public List<Caja> recuperarTodoPorColaborador(int colaborador) {
		getSession().beginTransaction();
		String sql = "from Caja where colaborador.id = :colaborador order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Caja> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		try {
			List<Caja> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	public List<CajaMovimiento> recuperarPorCliente(int cliente) {
		getSession().beginTransaction();
		String sql = "from CajaMovimiento " + "where cliente.id = :cliente " + "and esAnulado = false "
				+ "and esRetiro = false " + "and estado = true " + "order by id ASC";
		@SuppressWarnings("unchecked")
		Query<CajaMovimiento> query = getSession().createQuery(sql);
		query.setParameter("cliente", cliente);
		try {
			List<CajaMovimiento> resultados = query.getResultList();
			commit();
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<CajaMovimiento> recuperarCandidatosVales(String observacion) {
		getSession().beginTransaction();
		String sql = "from CajaMovimiento " + "where observacion LIKE :observacion " + "order by id ASC";
		@SuppressWarnings("unchecked")
		Query<CajaMovimiento> query = getSession().createQuery(sql);
		query.setParameter("observacion", "%" + observacion + "%");
		try {
			List<CajaMovimiento> resultados = query.getResultList();
			commit();
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Caja> reporteDiarioCaja(int colaborador, boolean estado, boolean cajaCerrada, Date fecha) {
		getSession().beginTransaction();
		String sql = "FROM Caja " 
		+ "WHERE colaborador.id = :colaborador " 
				+ "AND estado = :estado "
				+ "AND cajaCerrada = :cajaCerrada " 
				+ "AND DATE(fechaRegistro) = :fecha " 
				+ "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Caja> query = getSession().createQuery(sql);
			query.setParameter("colaborador", colaborador);
			query.setParameter("estado", estado);
			query.setParameter("cajaCerrada", cajaCerrada);
			query.setParameter("fecha", fecha);
			List<Caja> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
	
	public List<Caja> reporteMensualCaja(int colaborador, boolean estado, boolean cajaCerrada, Integer mes, Integer anho) {
		getSession().beginTransaction();
		String sql = "FROM Caja " 
		+ "WHERE colaborador.id = :colaborador " 
				+ "AND estado = :estado "
				+ "AND cajaCerrada = :cajaCerrada " 
				+ "AND MONTH(fechaRegistro) = :mes "
				+ "AND YEAR(fechaRegistro) = :anho " 
				+ "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Caja> query = getSession().createQuery(sql);
			query.setParameter("colaborador", colaborador);
			query.setParameter("estado", estado);
			query.setParameter("cajaCerrada", cajaCerrada);
			query.setParameter("mes", mes);
			query.setParameter("anho", anho);
			List<Caja> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
}
