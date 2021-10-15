package iOS.modelo.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Compra;
import iOS.modelo.entidades.CompraDetalle;

public class CompraDao extends GenericDao<Compra> {

	public CompraDao() {
		super(Compra.class);
	}

	public List<Compra> recuperarTodos() {

		getSession().beginTransaction();

		String sql = "from Compra order by id desc";

		@SuppressWarnings("unchecked")
		Query<Compra> query = getSession().createQuery(sql);

		List<Compra> lista = query.getResultList();

		commit();

		return lista;
	}

	public List<Compra> recuperarPorProveedor(String proveedor) {
		getSession().beginTransaction();

		String sql = "from Compra where proveedor.id = :proveedor order by id desc";

		@SuppressWarnings("unchecked")
		Query<Compra> query = getSession().createQuery(sql);

		int id = 0;
		try {
			id = Integer.parseInt(proveedor);
		} catch (Exception e) {

		}
		query.setParameter("proveedor", id);

		System.out.println(id);
		List<Compra> lista = query.getResultList();

		commit();

		return lista;
	}
	
	public List<Compra> recuperarHistoricoProveedor(String id, Date hoy ,Date hace) {
		getSession().beginTransaction();

		//Se filtran los ultimos 30 días para buscar sus movimiento y generar valores de mts2 en ese periodo
		String sql = "FROM Compra "
				+ "WHERE proveedor.id = :id "
				+ "AND fechaCompra "
				+ "BETWEEN :hace and :hoy "
				+ "ORDER BY id DESC";

		@SuppressWarnings("unchecked")
		Query<Compra> query = getSession().createQuery(sql);

		int codg = 0;
		try {
			codg = Integer.parseInt(id);
		} catch (Exception e) {

		}
		query.setParameter("id", codg);
		query.setParameter("hace", hace);
		query.setParameter("hoy", hoy);
		List<Compra> lista = query.getResultList();

		commit();

		return lista;
	}

	public List<CompraDetalle> recuperarDetallesPorCompra (int id){
		getSession().beginTransaction();

		String sql = "from CompraDetalle where pedido.id = :id order by id ASC";

		@SuppressWarnings("unchecked")
		Query<CompraDetalle> query = getSession().createQuery(sql);
		query.setParameter("id", id);
		List<CompraDetalle> lista = query.getResultList();
		commit();
		return lista;
	}
}
