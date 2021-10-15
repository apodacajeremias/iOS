package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Maquina;
import iOS.modelo.entidades.Tinta;

public class MaquinaDao extends GenericDao<Maquina> {

	public MaquinaDao() {
		super(Maquina.class);
	}
	
	public List<Maquina> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Maquina order by descripcion";
		
		@SuppressWarnings("unchecked")
		Query<Maquina> query = getSession().createQuery(sql);
		List<Maquina> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<Maquina> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Maquina "
				+ "where descripcion LIKE :filtro  "
				+ "order by descripcion";

		@SuppressWarnings("unchecked")
		Query<Maquina> query = getSession().createQuery(sql);
		query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");

		List<Maquina> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Tinta> recuperarTintas(int filtro) {
		getSession().beginTransaction();

		String sql = "from Tinta "
				+ "where maquina.id = :filtro "
				+ "and estado LIKE true"
				+ "order by id desc";

		@SuppressWarnings("unchecked")
		Query<Tinta> query = getSession().createQuery(sql);
		query.setParameter("filtro", filtro);

		List<Tinta> lista = query.getResultList();
		commit();
		return lista;
	}
}

