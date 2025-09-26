package mx.edu.uacm.torneofutbol.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utilidad para la configuración de JPA/Hibernate
 */
public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("torneo_futbol");
            System.out.println("EntityManagerFactory creado exitosamente");
        } catch (Exception ex) {
            System.err.println("Error al inicializar EntityManagerFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
            System.out.println("EntityManagerFactory cerrado");
        }
    }

    public static boolean isConnected() {
        try {
            EntityManager em = getEntityManager();
            boolean connected = em.isOpen();
            em.close();
            return connected;
        } catch (Exception e) {
            return false;
        }
    }
}