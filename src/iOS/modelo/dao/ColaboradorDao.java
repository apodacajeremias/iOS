
package iOS.modelo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.CajaMovimiento;
import iOS.modelo.entidades.Colaborador;
import iOS.modelo.entidades.Maquina;
import iOS.modelo.entidades.Rol;
import iOS.modelo.entidades.Sector;

public class ColaboradorDao extends GenericDao<Colaborador> {

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

		String sql = "from Colaborador " + "where upper(nombreCompleto) like :nombreCompleto "
				+ "or identificacion like :nroci " + "order by nombreCompleto";

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
		String sql = "from Colaborador " + "where upper(usuario) like :usuario " + "and password like :password";

		try {
			@SuppressWarnings("unchecked")
			Query<Colaborador> query = getSession().createQuery(sql);
			query.setParameter("usuario", usuario);
			query.setParameter("password", password);
			colaborador = query.getSingleResult();
			commit();
			return colaborador;
		} catch (Exception e) {
			e.printStackTrace();
			rollBack();
		}
		return null;
	}

	public List<Sector> recuperarSectoresOrdenadoPorNombre() {
		getSession().beginTransaction();

		List<Sector> lista = new ArrayList<Sector>();

		String sql = "from Sector order by descripcion";

		try {
			@SuppressWarnings("unchecked")
			Query<Sector> query = getSession().createQuery(sql);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}

	public List<Rol> recuperarRolesOrdenadoPorNombre() {
		getSession().beginTransaction();
		List<Rol> lista = new ArrayList<Rol>();
		String sql = "from Rol order by nombreRol";
		try {
			@SuppressWarnings("unchecked")
			Query<Rol> query = getSession().createQuery(sql);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}

	public List<Maquina> recuperarMaquinasOrdenadoPorNombre() {
		getSession().beginTransaction();
		List<Maquina> lista = new ArrayList<Maquina>();
		String sql = "from Maquina order by nombreMaquina";
		try {
			@SuppressWarnings("unchecked")
			Query<Maquina> query = getSession().createQuery(sql);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}

	public List<CajaMovimiento> recuperarValesHoy(Date hoy) {
		getSession().beginTransaction();
		List<CajaMovimiento> lista = new ArrayList<CajaMovimiento>();
		String sql = "FROM CajaMovimiento " 
				+ "WHERE esAnulado = false " 
				+ "AND esRetiro = true " 
				+ "AND estado = true "
				+ "AND esVale = true "
				+ "AND DATE(fechaRegistro) = :hoy " 
				+ "ORDER BY ID";
		try {
			@SuppressWarnings("unchecked")
			Query<CajaMovimiento> query = getSession().createQuery(sql);
			query.setParameter("hoy", hoy);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}

	public List<CajaMovimiento> recuperarValesMes(int mes) {
		getSession().beginTransaction();
		List<CajaMovimiento> lista = new ArrayList<CajaMovimiento>();
		String sql = "FROM CajaMovimiento " 
				+ "WHERE esAnulado = false " 
				+ "AND esRetiro = true " 
				+ "AND estado = true "
				+ "AND esVale = true "
				+ "AND MONTH(fechaRegistro) = :mes " 
				+ "ORDER BY ID";
		try {
			@SuppressWarnings("unchecked")
			Query<CajaMovimiento> query = getSession().createQuery(sql);
			query.setParameter("mes", mes);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}

	public List<CajaMovimiento> recuperarValesAnho(int anho) {
		getSession().beginTransaction();
		List<CajaMovimiento> lista = new ArrayList<CajaMovimiento>();
		String sql = "FROM CajaMovimiento " 
				+ "WHERE esAnulado = false " 
				+ "AND esRetiro = true " 
				+ "AND estado = true "
				+ "AND esVale = true "
				+ "AND YEAR(fechaRegistro) = :anho "  
				+ "ORDER BY ID";
		try {
			@SuppressWarnings("unchecked")
			Query<CajaMovimiento> query = getSession().createQuery(sql);
			query.setParameter("anho", anho);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return lista;
		}
	}
}
