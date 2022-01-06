
package iOS.modelo.dao;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Cotizacion;

public class CotizacionDao extends GenericDao<Cotizacion> {

	public CotizacionDao() {
		super(Cotizacion.class);
	}

	public Cotizacion recuperarUltimaCotizacion() {
		getSession().beginTransaction();

		Cotizacion resultado = new Cotizacion();

		String sql = "FROM Cotizacion WHERE id = (SELECT MAX(id) FROM Cotizacion) ORDER BY ID DESC";

		try {
			@SuppressWarnings("unchecked")
			Query<Cotizacion> query = getSession().createQuery(sql);
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
