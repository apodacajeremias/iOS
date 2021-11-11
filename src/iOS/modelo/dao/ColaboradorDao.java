
package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Funcionario;

public class ColaboradorDao extends GenericDao<Funcionario>{

	public ColaboradorDao() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Colaborador order by nombreCompleto";
		
		@SuppressWarnings("unchecked")
		Query<Funcionario> query = getSession().createQuery(sql);
		List<Funcionario> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Funcionario> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Colaborador "
				+ "where upper(nombreCompleto) like :nombreCompleto "
				+ "or identificacion like :nroci "
				+ "order by nombreCompleto";

		@SuppressWarnings("unchecked")
		Query<Funcionario> query = getSession().createQuery(sql);
		query.setParameter("nombreCompleto", "%" + filtro.toUpperCase() + "%");

		@SuppressWarnings("unused")
		int id = 0;
		try {
			id = Integer.parseInt(filtro);
		} catch (Exception e) {
		}
		query.setParameter("nroci", filtro);

		List<Funcionario> lista = query.getResultList();
		commit();
		return lista;
	}
	
	
	public Funcionario verificarAcceso(String usuario, String password) {
		getSession().beginTransaction();
		Funcionario colaborador = new Funcionario();
		String sql = "from Colaborador "
				+ "where upper(usuario) like :usuario "
				+ "and password like :password";
		
		try {
			@SuppressWarnings("unchecked")
			Query<Funcionario> query = getSession().createQuery(sql);
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
