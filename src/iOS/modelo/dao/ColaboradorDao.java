
package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Colaborador;

public class ColaboradorDao extends GenericDao<Colaborador>{

	public ColaboradorDao() {
		super(Colaborador.class);
	}
	
	public List<Colaborador> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Colaborador order by nombreCompleto";
		
		@SuppressWarnings("unchecked")
		Query<Colaborador> query = getSession().createQuery(sql);
		List<Colaborador> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Colaborador> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Colaborador "
				+ "where upper(nombreCompleto) like :nombreCompleto "
				+ "or identificacion like :nroci "
				+ "order by nombreCompleto";

		@SuppressWarnings("unchecked")
		Query<Colaborador> query = getSession().createQuery(sql);
		query.setParameter("nombreCompleto", "%" + filtro.toUpperCase() + "%");

		@SuppressWarnings("unused")
		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {
		}
		query.setParameter("nroci", filtro);

		List<Colaborador> lista = query.getResultList();
		commit();
		return lista;
	}
	
	
	public Colaborador verificarAcceso(String usuario, String password) {
		getSession().beginTransaction();
		Colaborador colaborador = new Colaborador();
		String sql = "from Colaborador "
				+ "where upper(usuario) like :usuario "
				+ "and password like :password";
		
		try {
			@SuppressWarnings("unchecked")
			Query<Colaborador> query = getSession().createQuery(sql);
			query.setParameter("usuario", usuario);
			query.setParameter("password", password);
			colaborador =  query.getSingleResult();
			commit();
			return colaborador;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
		}
		return null;
	}

}
