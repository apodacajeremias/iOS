package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Produccion;

public class ProduccionDao extends GenericDao<Produccion> {

	public ProduccionDao() {
		super(Produccion.class);
	}

	public List<Produccion> recuperarTodos() {

		getSession().beginTransaction();

		String sql = "from Produccion order by id desc";

		@SuppressWarnings("unchecked")
		Query<Produccion> query = getSession().createQuery(sql);

		List<Produccion> lista = query.getResultList();

		commit();

		return lista;
	}

}
