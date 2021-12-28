package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Cliente;
import iOS.modelo.entidades.Pedido;

public class ClienteDao extends GenericDao<Cliente> {

	public ClienteDao() {
		super(Cliente.class);
	}

	public List<Cliente> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		try {			
			String sql = "from Cliente order by nombreCompleto";
			@SuppressWarnings("unchecked")
			Query<Cliente> query = getSession().createQuery(sql);
			List<Cliente> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Cliente> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		try {
			String sql = "from Cliente " + "where upper(nombreCompleto) like :nombreCompleto "
					+ "or identificacion like :nroci " + "order by nombreCompleto";
			@SuppressWarnings("unchecked")
			Query<Cliente> query = getSession().createQuery(sql);
			query.setParameter("nombreCompleto", "%" + filtro.toUpperCase() + "%");
			query.setParameter("nroci", filtro);
			List<Cliente> lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Pedido> recuperarPedidos(int cliente) {
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

}
