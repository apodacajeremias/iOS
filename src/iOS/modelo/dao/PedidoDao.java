package iOS.modelo.dao;

import java.sql.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Pedido;
import iOS.modelo.entidades.PedidoDetalleConfeccion;
import iOS.modelo.entidades.PedidoDetalles;

public class PedidoDao extends GenericDao<Pedido> {

	public PedidoDao() {
		super(Pedido.class);
	}

	public List<Pedido> recuperarPorCliente(int cliente) {
		getSession().beginTransaction();

		String sql = "from Pedido where cliente.id = :cliente order by id desc";

		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("cliente", cliente);
		List<Pedido> lista = query.getResultList();
		commit();

		return lista;
	}
	
	public List<Pedido> recuperarHistoricoCliente(String id, Date hoy ,Date hace) {
		getSession().beginTransaction();

		//Se filtran los ultimos 30 días para buscar sus movimiento y generar valores de mts2 en ese periodo
		String sql = "FROM Pedido "
				+ "WHERE cliente.id = :id "
				+ "AND fechaRegistro "
				+ "BETWEEN :hace and :hoy "
				+ "ORDER BY id DESC";

		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);

		int codg = 0;
		try {
			codg = Integer.parseInt(id);
		} catch (Exception e) {

		}
		query.setParameter("id", codg);
		query.setParameter("hace", hace);
		query.setParameter("hoy", hoy);
		List<Pedido> lista = query.getResultList();

		commit();

		return lista;
	}

	public List<PedidoDetalles> recuperarDetallesPorPedido (int id) {
		getSession().beginTransaction();

		String sql = "from PedidoDetalles where pedido.id = :id order by id ASC";

		@SuppressWarnings("unchecked")
		Query<PedidoDetalles> query = getSession().createQuery(sql);
		query.setParameter("id", id);
		List<PedidoDetalles> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Pedido> recuperarTodoPorColaborador(int colaborador){
		getSession().beginTransaction();
		String sql = "from Pedido "
				+ "where colaborador.id = :colaborador "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}
	
	public List<PedidoDetalles> reporteVentasCarteleria (int colaborador, java.util.Date fecha) {
		getSession().beginTransaction();
		String sql = "from PedidoDetalles "
				+ "where colaborador.id = :colaborador "
				+ "and DATE(pedido.fechaRegistro) = :fecha "
				+ "and pedido.esPresupuesto = false "
				+ "and pedido.pedidoCarteleria = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalles> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("fecha", fecha);
		try {
			List<PedidoDetalles> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
	
	public List<PedidoDetalleConfeccion> reporteVentasConfeccion(int colaborador, java.util.Date fecha) {
		getSession().beginTransaction();
		String sql = "from PedidoDetalleConfeccion "
				+ "where colaborador.id = :colaborador "
				+ "and DATE(pedido.fechaRegistro) = :fecha "
				+ "and pedido.esPresupuesto = false "
				+ "and pedido.pedidoCostura = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalleConfeccion> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("fecha", fecha);
		try {
			List<PedidoDetalleConfeccion> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
	
	public List<Pedido> recuperarPedidosCarteleria() {
		getSession().beginTransaction();
		String sql = "from Pedido "
				+ "where pedidoCarteleria = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	
	public List<Pedido> recuperarPedidosCostura() {
		getSession().beginTransaction();
		String sql = "from Pedido "
				+ "where pedidoCostura = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}
	
	public List<Pedido> recuperarPedidosCarteleria(int colaborador) {
		getSession().beginTransaction();
		String sql = "from Pedido "
				+ "where pedidoCarteleria = true "
				+ "and colaborador.id = :colaborador "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}

	
	public List<Pedido> recuperarPedidosCostura(int colaborador) {
		getSession().beginTransaction();
		String sql = "from Pedido "
				+ "where pedidoCostura = true "
				+ "and colaborador.id = :colaborador "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return null;
		}
	}
	
}
