package iOS.modelo.dao;


import java.util.Date;
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

		String sql = "from Pedido where cliente.id = :cliente "
				+ "and estado = true "
				+ "and esPresupuesto = false "
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

	public List<PedidoDetalles> reporteVentasDiarioCarteleria (int colaborador) {
		getSession().beginTransaction();
		Date fecha = new Date();
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
	
	public List<PedidoDetalles> reporteVentasMensualCarteleria (int colaborador, Integer mes, Integer anho) {
		System.out.println(colaborador+" "+mes+" "+anho);
		getSession().beginTransaction();
		String sql = "from PedidoDetalles "
				+ "where colaborador.id = :colaborador "
				+ "and MONTH(fechaRegistro) = :mes "
				+ "and YEAR(fechaRegistro) = :anho "
				+ "and pedido.esPresupuesto = false "
				+ "and pedido.pedidoCarteleria = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalles> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("mes", mes);
		query.setParameter("anho", anho);
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

	public List<Pedido> recuperarPedidosDiarioCarteleriaPorFiltros(Integer colaborador) {
		getSession().beginTransaction();
		Date fecha = new Date();
		String sql = "FROM Pedido "
				+ "WHERE pedidoCarteleria = true "
				+ "AND colaborador.id = :colaborador "
				+ "and DATE(fechaRegistro) = :fecha "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("fecha", fecha);
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
	
	public List<Pedido> recuperarPedidosMensualCarteleriaPorFiltros(int colaborador, int mes, int ano) {
		getSession().beginTransaction();
		String sql = "FROM Pedido "
				+ "WHERE pedidoCarteleria = true "
				+ "AND colaborador.id = :colaborador "
				+ "AND MONTH(fechaRegistro) = :mes "
				+ "AND YEAR(fechaRegistro) = :ano "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
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

	public List<PedidoDetalleConfeccion> reporteVentasDiarioCostura (int colaborador) {
		getSession().beginTransaction();
		Date fecha = new Date();
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
	
	public List<PedidoDetalleConfeccion> reporteVentasMensualCostura (int colaborador, Integer mes, Integer anho) {
		getSession().beginTransaction();
		String sql = "from PedidoDetalleConfeccion "
				+ "where colaborador.id = :colaborador "
				+ "and MONTH(fechaRegistro) = :mes "
				+ "and YEAR(fechaRegistro) = :anho "
				+ "and pedido.esPresupuesto = false "
				+ "and pedido.pedidoCostura = true "
				+ "order by id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalleConfeccion> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("mes", mes);
		query.setParameter("anho", anho);
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

	public List<Pedido> recuperarPedidosDiarioCosturaPorFiltros(Integer colaborador) {
		getSession().beginTransaction();
		Date fecha = new Date();
		String sql = "FROM Pedido "
				+ "WHERE pedidoCostura = true "
				+ "AND colaborador.id = :colaborador "
				+ "and DATE(fechaRegistro) = :fecha "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("fecha", fecha);
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
	
	public List<Pedido> recuperarPedidosMensualCosturaPorFiltros(int colaborador, int mes, int ano) {
		getSession().beginTransaction();
		String sql = "FROM Pedido "
				+ "WHERE pedidoCostura = true "
				+ "AND colaborador.id = :colaborador "
				+ "AND MONTH(fechaRegistro) = :mes "
				+ "AND YEAR(fechaRegistro) = :ano "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("colaborador", colaborador);
		query.setParameter("mes", mes);
		query.setParameter("ano", ano);
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
}

