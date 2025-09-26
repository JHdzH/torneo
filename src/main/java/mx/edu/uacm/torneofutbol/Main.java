package mx.edu.uacm.torneofutbol;

import mx.edu.uacm.torneofutbol.config.HibernateUtil;
import mx.edu.uacm.torneofutbol.entities.*;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("🚀 Probando entidades JPA...");

        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            System.out.println("Conexión JPA establecida correctamente");

            // Iniciar transacción para pruebas
            em.getTransaction().begin();

            // Probar que las entidades están mapeadas correctamente
            System.out.println("Entidades mapeadas:");
            System.out.println(" " + em.getMetamodel().entity(Equipo.class));
            System.out.println(" " + em.getMetamodel().entity(Jugador.class));
            System.out.println(" " + em.getMetamodel().entity(Partido.class));
            System.out.println(" " + em.getMetamodel().entity(Posicion.class));
            System.out.println(" " + em.getMetamodel().entity(Gol.class));

            // Probar una consulta simple
            Long count = em.createQuery("SELECT COUNT(e) FROM Equipo e", Long.class).getSingleResult();
            System.out.println("Equipos en base de datos: " + count);

            em.getTransaction().commit();
            System.out.println("Prueba completada exitosamente");

        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            HibernateUtil.shutdown();
        }
    }
}