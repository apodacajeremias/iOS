package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Proceso;

public class ProcesoDao extends GenericDao<Proceso> {

	public ProcesoDao() {
		super(Proceso.class);
	}
	
	public List<Proceso> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Proceso order by descripcion";
		
		@SuppressWarnings("unchecked")
		Query<Proceso> query = getSession().createQuery(sql);
		List<Proceso> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Proceso> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Proceso "
				+ "where upper(descripcion) like :descripcion "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Proceso> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		List<Proceso> lista = query.getResultList();
		commit();
		return lista;
	}
}
