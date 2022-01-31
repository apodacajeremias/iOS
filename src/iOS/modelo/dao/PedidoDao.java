package iOS.modelo.dao;

import java.util.ArrayList;
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

	public Pedido encontrarPedido(Integer pedido) {
		getSession().beginTransaction();

		String sql = "FROM Pedido "
				+ "WHERE id = :pedido "
				+ "AND estado = true "
				+ "AND esPresupuesto = false";

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
	
	public List<Pedido> recuperarHoy(Date hoy) {
		getSession().beginTransaction();
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "FROM Pedido "
				+ "WHERE DATE(fechaRegistro) = :hoy "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("hoy", hoy);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<Pedido> recuperarMes(int mes) {
		getSession().beginTransaction();
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "FROM Pedido "
				+ "WHERE MONTH(fechaRegistro) = :mes "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("mes", mes);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}

	public List<Pedido> recuperarAnho(int anho) {
		getSession().beginTransaction();
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "FROM Pedido "
				+ "WHERE YEAR(fechaRegistro) = :anho "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("anho", anho);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<Pedido> recuperarPeriodo(Date inicio, Date fin) {
		getSession().beginTransaction();
		List<Pedido> lista = new ArrayList<Pedido>();
		String sql = "FROM Pedido "
				+ "WHERE DATE(fechaRegistro) BETWEEN DATE(:inicio) AND DATE(:fin) "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<Pedido> query = getSession().createQuery(sql);
		query.setParameter("inicio", inicio);
		query.setParameter("fin", fin);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<PedidoDetalles> recuperarDetalleCarteleriaPorSectorDeProductoHoy(int idSector, Date hoy) {
		getSession().beginTransaction();
		List<PedidoDetalles> lista = new ArrayList<PedidoDetalles>();
		String sql = "FROM PedidoDetalles "
				+ "WHERE estado = true "
				+ "AND producto.sector.id = :idSector "
				+ "AND DATE(fechaRegistro) = :hoy "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalles> query = getSession().createQuery(sql);
		query.setParameter("idSector", idSector);
		query.setParameter("hoy", hoy);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<PedidoDetalleConfeccion> recuperarDetalleConfeccionPorSectorDeProductoHoy(int idSector, Date hoy) {
		getSession().beginTransaction();
		List<PedidoDetalleConfeccion> lista = new ArrayList<PedidoDetalleConfeccion>();
		String sql = "FROM PedidoDetalleConfeccion "
				+ "WHERE estado = true "
				+ "AND producto.sector.id = :idSector "
				+ "AND DATE(fechaRegistro) = :hoy "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalleConfeccion> query = getSession().createQuery(sql);
		query.setParameter("idSector", idSector);
		query.setParameter("hoy", hoy);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<PedidoDetalles> recuperarDetalleCarteleriaPorSectorDeProductoPeriodo(int idSector, Date inicio, Date fin) {
		getSession().beginTransaction();
		List<PedidoDetalles> lista = new ArrayList<PedidoDetalles>();
		String sql = "FROM PedidoDetalles "
				+ "WHERE estado = true "
				+ "AND producto.sector.id = :idSector "
				+ "AND DATE(fechaRegistro) BETWEEN DATE(:inicio) AND DATE(:fin) "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalles> query = getSession().createQuery(sql);
		query.setParameter("idSector", idSector);
		query.setParameter("inicio", inicio);
		query.setParameter("fin", fin);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	public List<PedidoDetalleConfeccion> recuperarDetalleConfeccionPorSectorDeProductoPeriodo(int idSector, Date inicio, Date fin) {
		getSession().beginTransaction();
		List<PedidoDetalleConfeccion> lista = new ArrayList<PedidoDetalleConfeccion>();
		String sql = "FROM PedidoDetalleConfeccion "
				+ "WHERE estado = true "
				+ "AND producto.sector.id = :idSector "
				+ "AND DATE(fechaRegistro) BETWEEN DATE(:inicio) AND DATE(:fin) "
				+ "ORDER BY id DESC";
		@SuppressWarnings("unchecked")
		Query<PedidoDetalleConfeccion> query = getSession().createQuery(sql);
		query.setParameter("idSector", idSector);
		query.setParameter("inicio", inicio);
		query.setParameter("fin", fin);
		try {
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			rollBack();
			return lista;
		}
	}
	
	
}
