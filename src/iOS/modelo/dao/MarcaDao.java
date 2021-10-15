package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Marca;

public class MarcaDao extends GenericDao<Marca> {

	public MarcaDao() {
		super(Marca.class);
	}
	
	public List<Marca> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Marca order by descripcion";
		
		@SuppressWarnings("unchecked")
		Query<Marca> query = getSession().createQuery(sql);
		List<Marca> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Marca> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Marca "
				+ "where upper(descripcion) like :descripcion "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Marca> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		List<Marca> lista = query.getResultList();
		commit();
		return lista;
	}
}

