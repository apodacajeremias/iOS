package iOS.modelo.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.controlador.util.EventosUtil;
import iOS.modelo.entidades.Caja;
import iOS.modelo.entidades.CajaMovimiento;

public class CajaDao extends GenericDao<Caja> {

	public CajaDao() {
		super(Caja.class);
	}

	public Caja encontrarCajaHoy(Date hoy, int id) {		
		getSession().beginTransaction();
		String sql = "from Caja "
				+ "where colaborador.id = :id "
				+ "and DATE(fechaRegistro) = :hoy "
				+ "and cajaCerrada = false "
				+ "order by id ASC";

		@SuppressWarnings("unchecked")
		Query<Caja> query = getSession().createQuery(sql);
		query.setParameter("hoy", hoy);
		query.setParameter("id", id);
		try {
			Caja caja = null;
			List<Caja> resultados = query.getResultList();
			for (int i = 0; i < resultados.size(); i++) {
				caja = resultados.get(i);
			}
			commit();
			return caja;
		} catch (Exception e) {
			EventosUtil.formatException(e);
			rollBack();
			return null;
		}
	}

	public List<Caja> recuperarCajasPorFecha(Date fecha) {
		getSession().beginTransaction();
		String sql = "from Caja "
				+ "where DATE(fechaRegistro) = :fecha "
				+ "order by id";
		@SuppressWarnings("unchecked")
		Query<Caja> query = getSession().createQuery(sql);
		query.setParameter("fecha", fecha);
		try {
			List<Caja> lista = query.getResultList();
			commit();
			return lista;
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
	
	public List<CajaMovimiento> ordenarMovimientosPorID(int cajaID) {
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

}
