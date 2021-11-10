package iOS.controlador.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ConnectionHelper {
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			setUp();
		}
		return sessionFactory;
	}

	public static void setUp() {
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
		try {
			sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
			StandardServiceRegistryBuilder.destroy(serviceRegistry);
		}
	}

	public static void main(String[] args) {
		try {
			System.out.println(getSessionFactory());
		} catch (Exception ex) {
			throw new RuntimeException(ex);

		}
	}

}
