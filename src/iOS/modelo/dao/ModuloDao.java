package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Modulo;

public class ModuloDao extends GenericDao<Modulo>{

	public ModuloDao() {
		super(Modulo.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<Modulo> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Modulo order by nombreModulo";
		
		@SuppressWarnings("unchecked")
		Query<Modulo> query = getSession().createQuery(sql);
		List<Modulo> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Modulo> recuperarPorFiltro(String nombreModulo){
		getSession().beginTransaction();
		String sql = "from Modulo "
				+ "where upper(nombreModulo) like :nombreModulo "
				+ "order by nombreModulo";
		@SuppressWarnings("unchecked")
		Query<Modulo> query = getSession().createQuery(sql);
		query.setParameter("nombreModulo", "%" + nombreModulo.toUpperCase() + "%");
		List<Modulo> lista = query.getResultList();
		commit();
		return lista;
	}

}
