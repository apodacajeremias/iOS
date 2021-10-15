package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Existencia;

public class ExistenciaDao extends GenericDao<Existencia> {

	public ExistenciaDao() {
		super(Existencia.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<Existencia> verificarReferencia(String referencia){
		getSession().beginTransaction();
		String sql = "from Existencia where referencia = :referencia and estadoUso = false";		
		@SuppressWarnings("unchecked")
		Query<Existencia> query = getSession().createQuery(sql);
		query.setParameter("referencia", referencia);
		List<Existencia> lista = query.getResultList();
		commit();
		return lista;
	}

}
