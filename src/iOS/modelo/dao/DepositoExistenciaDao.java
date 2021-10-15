package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.DepositoExistencia;

public class DepositoExistenciaDao extends GenericDao<DepositoExistencia> {

	public DepositoExistenciaDao() {
		super(DepositoExistencia.class);
		// TODO Auto-generated constructor stub
	}
	
	public List<DepositoExistencia> verificarExistenciaEnDeposito(int deposito){
		getSession().beginTransaction();
		String sql = "FROM DepositoExistencia "
				+ "WHERE deposito.id = :deposito";
		
		
		//SELECT * FROM DEPOSITOEXISTENCIA JOIN EXISTENCIA ON DEPOSITOEXISTENCIA.EXISTENCIA_ID = EXISTENCIA.ID WHERE MATERIAL_ID = 3
		
		
		@SuppressWarnings("unchecked")
		Query<DepositoExistencia> query = getSession().createQuery(sql);
		query.setParameter("deposito", deposito);
		List<DepositoExistencia> lista = query.getResultList();
		commit();
		return lista;
	}
	

}
