package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.InformacionPago;
import iOS.modelo.entidades.Proveedor;

public class ProveedorDao extends GenericDao<Proveedor> {

	public ProveedorDao() {
		super(Proveedor.class);
	}
	
	public List<InformacionPago> recuperarInformacionPagoPorProveedor(int proveedor) {
		getSession().beginTransaction();

		String sql = "from InformacionPago where proveedor.id = :proveedor group by entidadBancaria.id ORDER BY entidadBancaria.id DESC";
		
		
		@SuppressWarnings("unchecked")
		Query<InformacionPago> query = getSession().createQuery(sql);
		query.setParameter("proveedor", proveedor);
		List<InformacionPago> lista = query.getResultList();
		commit();
		
		return lista;
	}
	
	public List<Proveedor> recuperarPorFiltro(String filtro) {
		getSession().beginTransaction();

		String sql = "from Proveedor "
				+ "where upper(nombreCompleto) like :nombreCompleto "
				+ "order by nombreCompleto";

		@SuppressWarnings("unchecked")
		Query<Proveedor> query = getSession().createQuery(sql);
		query.setParameter("nombreCompleto", "%" + filtro.toUpperCase() + "%");
		List<Proveedor> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<Proveedor> recuperarTodoOrdenadoPorNombre() {
		getSession().beginTransaction();
		
		String sql = "from Proveedor order by nombreCompleto";
		
		@SuppressWarnings("unchecked")
		Query<Proveedor> query = getSession().createQuery(sql);
		List<Proveedor> lista = query.getResultList();
		commit();
		return lista;
	}

}
