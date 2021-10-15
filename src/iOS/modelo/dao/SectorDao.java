package iOS.modelo.dao;

import java.util.List;

import org.hibernate.query.Query;

import iOS.modelo.entidades.Sector;

public class SectorDao extends GenericDao<Sector> {

	public SectorDao() {
		super(Sector.class);
	}
	
	public List<Sector> recuperarTodoOrdenadoPorNombre(){
		getSession().beginTransaction();

		String sql = "from Sector order by descripcion";
		
		
		@SuppressWarnings("unchecked")
		Query<Sector> query = getSession().createQuery(sql);
		List<Sector> lista = query.getResultList();
		commit();
		
		return lista;
	}
	
	public List<Sector> recuperarDeposito(int id){
		getSession().beginTransaction();

		String sql = "from Deposito where sector.id = :id AND estadoUno = false";
		
		
		@SuppressWarnings("unchecked")
		Query<Sector> query = getSession().createQuery(sql);
		query.setParameter("id", id);
		List<Sector> lista = query.getResultList();
		commit();
		
		return lista;
	}
	
	public List<Sector> recuperarDepositos(){
		getSession().beginTransaction();

		String sql = "from Sector order by descripcion";
		
		
		@SuppressWarnings("unchecked")
		Query<Sector> query = getSession().createQuery(sql);
		List<Sector> lista = query.getResultList();
		commit();
		return lista;
	}
}