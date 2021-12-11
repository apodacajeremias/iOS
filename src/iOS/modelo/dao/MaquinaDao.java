package iOS.modelo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Maquina;

public class MaquinaDao extends GenericDao<Maquina> {

	public MaquinaDao() {
		super(Maquina.class);
	}

	public List<Maquina> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		List<Maquina> lista = new ArrayList<Maquina>();
		try {
			String sql = "from Maquina order by nombreMaquina";
			@SuppressWarnings("unchecked")
			Query<Maquina> query = getSession().createQuery(sql);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return null;
		}
	}

	public List<Maquina> recuperarPorTipoOrdenadoPorNombre(String tipoMaquina) {
		getSession().beginTransaction();
		List<Maquina> lista = new ArrayList<Maquina>();
		try {
			String sql = "from Maquina " + "where tipoMaquina = :tipoMaquina " + "order by nombreMaquina";
			@SuppressWarnings("unchecked")
			Query<Maquina> query = getSession().createQuery(sql);
			query.setParameter("tipoMaquina", tipoMaquina);
			lista = query.getResultList();
			commit();
			return lista;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
			return null;
		}
	}
}
