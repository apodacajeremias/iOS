package iOS.modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import iOS.controlador.util.ConnectionHelper;

public class GenericDao<T> {
	//Una clase abstracta
	private Class<T> clase;

	/// Contructor
	public GenericDao(Class<T> clase) {
		this.clase = clase;
	}
	
	protected Session getSession() {
		return ConnectionHelper.getSessionFactory().getCurrentSession();
	}
	
	//METODO PARA INSERTAR
	public void insertar(T entity) throws Exception {
		// recupera la sesion de la bd inicia una transaccion
		getSession().beginTransaction();
		// se inserta en la tabla
		getSession().save(entity);
	}
	
	//METODO PARA MODIFICAR
	public void modificar(T entity) throws Exception {
		getSession().beginTransaction();
		// se actualizan los datos
		getSession().update(entity);
	}

	//METODO PARA ELIMINAR
	public void eliminar(T entity) throws Exception {
		getSession().beginTransaction();
		// se elimina los datos
		getSession().delete(entity);

	}

	//METODO PARA RECUPERAR POR ID
	public T recuperarPorId(int id) {
		getSession().beginTransaction();
		// recuperamos un registro por su id
		T result = getSession().get(clase, id);
		commit();
		return result;

	}
	
	//METODO PARA RECUPERAR TODO
	public List<T> recuperarTodo() {
		getSession().beginTransaction();
		//sql select para cualquier entidad
		String sql="from "+clase.getName()+" order by id DESC";
		@SuppressWarnings("unchecked")
		Query<T>query= getSession().createQuery(sql);
		//se obtiene los resultados
		List<T>lista= query.getResultList();
		commit();
		return lista;
	}
	
	public void vaciarTabla(String nombreTabla) {
		getSession().beginTransaction();
		//sql select para cualquier entidad
		String sql = "TRUNCATE TABLE "+nombreTabla+" CASCADE";
		//se instancia una sentencia sql
		getSession().createNativeQuery(sql).executeUpdate();
		commit();
	}

	public void commit() {
		getSession().flush();
		getSession().getTransaction().commit();
		getSession().close();
	}

	public void rollBack() {
		getSession().getTransaction().rollback();
	}

}
