package iOS.modelo.dao;



import org.hibernate.query.Query;

import iOS.modelo.entidades.Configuracion;

public class ConfiguracionDao extends GenericDao<Configuracion>{
	public ConfiguracionDao() {
		super(Configuracion.class);
		
	}
	
	public Configuracion recuperarUltimaConfiguracion() {
		getSession().beginTransaction();

		Configuracion resultado = new Configuracion();

		String sql = "FROM Configuracion WHERE id = (SELECT MAX(id) FROM Configuracion) ORDER BY ID DESC";

		try {
			@SuppressWarnings("unchecked")
			Query<Configuracion> query = getSession().createQuery(sql);
			resultado = query.getSingleResult();
			commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rollBack();
		}
		return resultado;
	}
	
	

}
