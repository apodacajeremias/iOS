package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Rol;

public class RolDao extends GenericDao<Rol>{

	public RolDao() {
		super(Rol.class);
		// TODO Auto-generated constructor stub
	}

	public List<Rol> recuperarTodoOrdenadoPorNombre(){
		getSession().beginTransaction();
		String sql = "from Rol order by nombreRol";
		@SuppressWarnings("unchecked")
		Query<Rol> query = getSession().createQuery(sql);
		List<Rol> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Rol> recuperarPorFiltro(String nombreRol){
		getSession().beginTransaction();
		String sql = "from Rol "
				+ "where upper(nombreRol) like :nombreRol "
				+ "order by nombreRol";
		@SuppressWarnings("unchecked")
		Query<Rol> query = getSession().createQuery(sql);
		query.setParameter("nombreRol", "%" + nombreRol.toUpperCase() + "%");
		List<Rol> lista = query.getResultList();
		commit();
		return lista;
	}

}
