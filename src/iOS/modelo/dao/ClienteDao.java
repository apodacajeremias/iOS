package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Cliente;

public class ClienteDao extends GenericDao<Cliente> {

	public ClienteDao() {
		super(Cliente.class);
	}
	
	public List<Cliente> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Cliente order by nombreCompleto";
		
		@SuppressWarnings("unchecked")
		Query<Cliente> query = getSession().createQuery(sql);
		List<Cliente> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Cliente> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Cliente "
				+ "where upper(nombreCompleto) like :nombreCompleto "
				+ "or identificacion like :nroci "
				+ "order by nombreCompleto";

		@SuppressWarnings("unchecked")
		Query<Cliente> query = getSession().createQuery(sql);
		query.setParameter("nombreCompleto", "%" + filtro.toUpperCase() + "%");

		@SuppressWarnings("unused")
		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {
		}
		query.setParameter("nroci", filtro);

		List<Cliente> lista = query.getResultList();
		commit();
		return lista;
	}

	public long verificarClienteEnUso(Cliente paciente) {
		getSession().beginTransaction();
		String sql = "select count(paciente) from Presupuesto where paciente.id = :pacienteId";

		@SuppressWarnings("unchecked")
		Query<Long> query = getSession().createQuery(sql);
		query.setParameter("pacienteId", paciente.getId());
		long cant = query.getSingleResult();
		commit();
		System.out.println(cant);
		return cant;
	}

	

}

