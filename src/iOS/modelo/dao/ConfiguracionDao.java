package iOS.modelo.dao;



import java.util.List;

import iOS.modelo.entidades.Configuracion;

public class ConfiguracionDao extends GenericDao<Configuracion>{
	private List<Configuracion> configuraciones;
	private Configuracion configuracion;

	public ConfiguracionDao() {
		super(Configuracion.class);
		
	}
	
	public Configuracion recuperarUltimo() {
		try {
			configuraciones = recuperarTodo();
			for (int i = 0; i < configuraciones.size(); i++) {
				configuracion = configuraciones.get(i);		} 
			
			return configuracion;
		} catch (Exception e) {
			return null;
		}
			
		
	}
	
	

}
