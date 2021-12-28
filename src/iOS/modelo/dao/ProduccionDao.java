package iOS.modelo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Produccion;

public class ProduccionDao extends GenericDao<Produccion> {

	public ProduccionDao() {
		super(Produccion.class);
	}
	
	public List<Produccion> recuperarHoy(Date hoy) {
		getSession().beginTransaction();
		List<Produccion> lista = new ArrayList<Produccion>();
		String sql = "FROM Produccion "
				+ "WHERE DATE(fechaRegistro) = :hoy "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Produccion> query = getSession().createQuery(sql);
		query.setParameter("hoy", hoy);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<Produccion> recuperarMes(int mes) {
		getSession().beginTransaction();
		List<Produccion> lista = new ArrayList<Produccion>();
		String sql = "FROM Produccion "
				+ "WHERE MONTH(fechaRegistro) = :mes "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Produccion> query = getSession().createQuery(sql);
		query.setParameter("mes", mes);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}

	public List<Produccion> recuperarAnho(int anho) {
		getSession().beginTransaction();
		List<Produccion> lista = new ArrayList<Produccion>();
		String sql = "FROM Produccion "
				+ "WHERE YEAR(fechaRegistro) = :anho "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Produccion> query = getSession().createQuery(sql);
		query.setParameter("anho", anho);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	

}
