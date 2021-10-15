package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Producto;

public class ProductoDao extends GenericDao<Producto> {

	public ProductoDao() {
		super(Producto.class);
	}
	
	public List<Producto> recuperarTodoOrdenadoPorNombre(boolean esServicio) {
		getSession().beginTransaction();
		
		String sql = "from Producto where esServicio = :esServicio order by descripcion";
		@SuppressWarnings("unchecked")
		Query<Producto> query = getSession().createQuery(sql);
		query.setParameter("esServicio", esServicio);
		List<Producto> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Producto> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Producto order by descripcion";
		@SuppressWarnings("unchecked")
		Query<Producto> query = getSession().createQuery(sql);
		List<Producto> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Producto> recuperarPorFiltro(String filtro, boolean esServicio) {
		getSession().beginTransaction();

		String sql = "from Producto "
				+ "where upper(descripcion) like :descripcion "
				+ "and esServicio = :esServicio "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Producto> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		query.setParameter("esServicio", esServicio);
		List<Producto> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Producto> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Producto "
				+ "where upper(descripcion) like :descripcion "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Producto> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		List<Producto> lista = query.getResultList();
		commit();
		return lista;
	}
}

