package app;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtilities {

	private static SessionFactory sessionFactory = buildSessionFactory();
	//private static ServiceRegistry serviceRegistry;
		
	private static SessionFactory buildSessionFactory(){
		try{
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate-mysql.cfg.xml").build();
			Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			return metadata.getSessionFactoryBuilder().build();
		}catch(HibernateException e){
			System.out.println("Problema creando SessionFactory "+e);
		}
		return sessionFactory;
	}
	
	public static SessionFactory getSessionFactory(){
		return buildSessionFactory();
	}
	
	//Versiones anteriores a Hibernate 5
	/*static{
	try{
		//loads configuration and mappings
		Configuration configuration = new Configuration().configure();
		configuration.addAnnotatedClass(User.class);
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		//builds a session factory from the service registry
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	
	}catch(HibernateException e){
		System.out.println("Problema creando SessionFactory"+e);
	}
	}*/
	
}