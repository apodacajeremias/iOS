package iOS.modelo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Pedido;

public class PedidoDao extends GenericDao<Pedido> {

	public PedidoDao() {
		super(Pedido.class);
	}

	public List<Pedido> recuperarPorCliente(int cliente) {
		getSession().beginTransaction();

		String sql = "from Pedido where cliente.id = :cliente " + "and estado = true " + "and esPresupuesto = false "
				+ "order by id desc";

		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("cliente", cliente);
		try {
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Pedido> recuperarHistoricoCliente(String id, Date hoy, Date hace) {
		getSession().beginTransaction();

		// Se filtran los ultimos 30 d�as para buscar sus movimiento y generar valores
		// de mts2 en ese periodo
		String sql = "FROM Pedido " + "WHERE cliente.id = :id " + "AND fechaRegistro " + "BETWEEN :hace and :hoy "
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

	public List<Pedido> reporteDiarioPedido(int colaborador, boolean pedidoCarteleria, boolean pedidoCostura,
			boolean estado, boolean esPresupuesto, Date fecha) {
		getSession().beginTransaction();
		String sql = "FROM Pedido " + "WHERE colaborador.id = :colaborador "
				+ "AND (pedidoCarteleria = :pedidoCarteleria " + "OR pedidoCostura = :pedidoCostura) "
				+ "AND estado = :estado " + "AND esPresupuesto = :esPresupuesto " + "AND DATE(fechaRegistro) = :fecha "
				+ "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Pedido> query = getSession().createQuery(sql);
			query.setParameter("colaborador", colaborador);
			query.setParameter("pedidoCarteleria", pedidoCarteleria);
			query.setParameter("pedidoCostura", pedidoCostura);
			query.setParameter("estado", estado);
			query.setParameter("esPresupuesto", esPresupuesto);
			query.setParameter("fecha", fecha);
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Pedido> reporteMensualPedido(int colaborador, boolean pedidoCarteleria, boolean pedidoCostura,
			boolean estado, boolean esPresupuesto, Integer mes, Integer anho) {
		getSession().beginTransaction();
		String sql = "FROM Pedido " + "WHERE colaborador.id = :colaborador "
				+ "AND (pedidoCarteleria = :pedidoCarteleria " + "OR pedidoCostura = :pedidoCostura) "
				+ "AND estado = :estado " + "AND esPresupuesto = :esPresupuesto " + "AND MONTH(fechaRegistro) = :mes "
				+ "AND YEAR(fechaRegistro) = :anho " + "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Pedido> query = getSession().createQuery(sql);
			query.setParameter("colaborador", colaborador);
			query.setParameter("pedidoCarteleria", pedidoCarteleria);
			query.setParameter("pedidoCostura", pedidoCostura);
			query.setParameter("estado", estado);
			query.setParameter("esPresupuesto", esPresupuesto);
			query.setParameter("mes", mes);
			query.setParameter("anho", anho);
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
	

	public List<Pedido> aislarPedidosCarteleria() {
		getSession().beginTransaction();
		String sql = "FROM Pedido " 
				+ "WHERE (pedidoCarteleria = TRUE) "
				+ "AND estado = TRUE " 
				+ "AND esPresupuesto = FALSE "
				+ "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Pedido> query = getSession().createQuery(sql);
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
	
	public List<Pedido> aislarPedidosConfeccion() {
		getSession().beginTransaction();
		String sql = "FROM Pedido " 
				+ "WHERE (pedidoCostura = TRUE) "
				+ "AND estado = TRUE " 
				+ "AND esPresupuesto = FALSE "
				+ "ORDER BY id DESC";
		try {
			@SuppressWarnings("unchecked")
			Query<Pedido> query = getSession().createQuery(sql);
			List<Pedido> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public Pedido encontrarPedido(Integer pedido) {
		getSession().beginTransaction();

		String sql = "from Pedido where id = :pedido and estado = true and esPresupuesto = false order by id desc";

		try {
			@SuppressWarnings("unchecked")
			Query<Pedido> query = getSession().createQuery(sql);
			query.setParameter("pedido", pedido);
			Pedido resultado = query.getSingleResult();
			commit();
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
}
