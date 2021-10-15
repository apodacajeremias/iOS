package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Material;

public class MaterialDao extends GenericDao<Material> {

	public MaterialDao() {
		super(Material.class);
	}
	
	public List<Material> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Material order by descripcion";
		
		@SuppressWarnings("unchecked")
		Query<Material> query = getSession().createQuery(sql);
		List<Material> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Material> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Material "
				+ "where upper(descripcion) like :descripcion "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Material> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		List<Material> lista = query.getResultList();
		commit();
		return lista;
	}
}

