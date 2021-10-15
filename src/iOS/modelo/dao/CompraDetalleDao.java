package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.CompraDetalle;

public class CompraDetalleDao extends GenericDao<CompraDetalle> {

	public CompraDetalleDao() {
		super(CompraDetalle.class);
	}

	public List<CompraDetalle> recuperarDetallesSinExistencia(){
		getSession().beginTransaction();

		String sql = "from CompraDetalle where "
				+ "existenciaDisponible = false "
				+ "order by id ASC";

		@SuppressWarnings("unchecked")
		Query<CompraDetalle> query = getSession().createQuery(sql);
		List<CompraDetalle> lista = query.getResultList();
		commit();
		return lista;
	}
}
