package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Operacion;

public class OperacionDao extends GenericDao<Operacion>{

	public OperacionDao() {
		super(Operacion.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<Operacion> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Operacion order by nombreOperacion";
		
		@SuppressWarnings("unchecked")
		Query<Operacion> query = getSession().createQuery(sql);
		List<Operacion> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Operacion> recuperarPorFiltro(String nombreOperacion){
		getSession().beginTransaction();
		String sql = "from Operacion "
				+ "where upper(nombreOperacion) like :nombreOperacion "
				+ "order by nombreOperacion";
		@SuppressWarnings("unchecked")
		Query<Operacion> query = getSession().createQuery(sql);
		query.setParameter("nombreOperacion", "%" + nombreOperacion.toUpperCase() + "%");
		List<Operacion> lista = query.getResultList();
		commit();
		return lista;
	}

}
