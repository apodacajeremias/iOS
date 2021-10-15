package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.EntidadBancaria;
import iOS.modelo.entidades.InformacionPago;

public class BancoDao extends GenericDao<EntidadBancaria> {

	public BancoDao() {
		super(EntidadBancaria.class);
	}

	public List<InformacionPago> recuperarInformacionPagoPorBanco(int banco) {
		getSession().beginTransaction();

		String sql = "from InformacionPago where entidadBancaria.id = :banco group by proveedor.id ORDER BY proveedor.id DESC";


		@SuppressWarnings("unchecked")
		Query<InformacionPago> query = getSession().createQuery(sql);
		query.setParameter("banco", banco);
		List<InformacionPago> lista = query.getResultList();
		commit();

		return lista;
	}
	
	public List<InformacionPago> recuperarInformacionPagoPorProveedor(int proveedor) {
		getSession().beginTransaction();

		String sql = "from InformacionPago where entidadBancaria.id = :proveedor";


		@SuppressWarnings("unchecked")
		Query<InformacionPago> query = getSession().createQuery(sql);
		query.setParameter("proveedor", proveedor);
		List<InformacionPago> lista = query.getResultList();
		commit();
		return lista;
	}

	public List<EntidadBancaria> recuperarTodoOrdenadoPorNombre(){
		getSession().beginTransaction();
		String sql = "from EntidadBancaria order by nombreBanco";
		@SuppressWarnings("unchecked")
		Query<EntidadBancaria> query = getSession().createQuery(sql);
		List<EntidadBancaria> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<InformacionPago> recuperarInformacionPagoPorProveedorNumeroCuenta(int proveedor, String numeroCuenta) {
		getSession().beginTransaction();

		String sql = "from InformacionPago where proveedor.id = :proveedor "
				+ "AND numeroCuenta LIKE :numeroCuenta "
				+ "group by proveedor.id ORDER BY proveedor.id DESC";


		@SuppressWarnings("unchecked")
		Query<InformacionPago> query = getSession().createQuery(sql);
		query.setParameter("proveedor", proveedor);
		List<InformacionPago> lista = query.getResultList();
		commit();
		return lista;
	}
	
	public List<EntidadBancaria> recuperarPorFiltro(String nombreBanco){
		getSession().beginTransaction();
		String sql = "from EntidadBancaria "
				+ "where upper(nombreBanco) like :nombreBanco "
				+ "order by nombreBanco";
		@SuppressWarnings("unchecked")
		Query<EntidadBancaria> query = getSession().createQuery(sql);
		query.setParameter("nombreBanco", "%" + nombreBanco.toUpperCase() + "%");
		List<EntidadBancaria> lista = query.getResultList();
		commit();
		return lista;
	}
	
	
	
}
