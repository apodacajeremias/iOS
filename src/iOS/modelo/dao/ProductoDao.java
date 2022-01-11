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

		try {
			String sql = "from Producto order by descripcion";
			@SuppressWarnings("unchecked")
			Query<Producto> query = getSession().createQuery(sql);
			List<Producto> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Producto> recuperarPorFiltro(String filtro, boolean esServicio) {
		getSession().beginTransaction();

		String sql = "from Producto " + "where upper(descripcion) like :descripcion " + "and esServicio = :esServicio "
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

		String sql = "from Producto " + "where upper(descripcion) like :descripcion "
				+ "or upper(codigoReferencia) like :descripcion " + "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Producto> query = getSession().createQuery(sql);
		query.setParameter("descripcion", "%" + filtro.toUpperCase() + "%");
		List<Producto> lista = query.getResultList();
		commit();
		return lista;
	}

	public boolean codigoReferenciaDisponible(String codigoReferencia) {
		getSession().beginTransaction();
		Producto producto = null;
		codigoReferencia = codigoReferencia.replaceAll("\\s+", "");

		// Se busca un producto con este codigo
		String sql = "from Producto where upper(codigoReferencia) like :codigoReferencia";

		try {
			@SuppressWarnings("unchecked")
			Query<Producto> query = getSession().createQuery(sql);
			query.setParameter("codigoReferencia", codigoReferencia);
			producto = query.getSingleResult();
			commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
		}
		// Si no existe, entonces disponible == true
		if (producto == null) {
			return true;
		} else {
			return false;
		}
	}
}
