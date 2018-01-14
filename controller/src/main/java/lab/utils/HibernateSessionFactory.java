package lab.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.stereotype.Service;


@Service
public class HibernateSessionFactory {
    
    private SessionFactory sessionFactory = buildSessionFactory();
    
    protected SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        
        return sessionFactory;
    }
    
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public void shutdown() {
        getSessionFactory().close();
    }
    
}