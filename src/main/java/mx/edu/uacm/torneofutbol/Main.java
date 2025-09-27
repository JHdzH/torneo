package mx.edu.uacm.torneofutbol;

import mx.edu.uacm.torneofutbol.config.HibernateUtil;
import mx.edu.uacm.torneofutbol.entities.*;
import mx.edu.uacm.torneofutbol.repository.*;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Probando Repositories JPA...");

        EntityManager em = null;
        try {
            em = HibernateUtil.getEntityManager();
            em.getTransaction().begin();

            // Crear instancias de repositories
            EquipoRepository equipoRepo = new EquipoRepositoryImpl();
            PosicionRepository posicionRepo = new PosicionRepositoryImpl();
            JugadorRepository jugadorRepo = new JugadorRepositoryImpl();

            // Inyectar EntityManager (simulación simple)
            ((EquipoRepositoryImpl) equipoRepo).setEntityManager(em);
            ((PosicionRepositoryImpl) posicionRepo).setEntityManager(em);
            ((JugadorRepositoryImpl) jugadorRepo).setEntityManager(em);

            // Probar creación de posiciones
            Posicion portero = new Posicion("Portero");
            Posicion defensa = new Posicion("Defensa");
            Posicion medio = new Posicion("Medio");
            Posicion delantero = new Posicion("Delantero");

            posicionRepo.guardar(portero);
            posicionRepo.guardar(defensa);
            posicionRepo.guardar(medio);
            posicionRepo.guardar(delantero);

            System.out.println("Posiciones creadas: " + posicionRepo.obtenerTodos().size());

            // Probar creación de equipo
            Equipo equipo1 = new Equipo("Equipo A");
            equipoRepo.guardar(equipo1);

            System.out.println("Equipo creado: " + equipo1.getNombre());

            // Probar creación de jugador
            Jugador jugador1 = new Jugador("Jugador 1", LocalDate.of(1990, 1, 1));
            jugador1.setEquipo(equipo1);
            jugador1.agregarPosicion(delantero);
            jugadorRepo.guardar(jugador1);

            System.out.println("Jugador creado: " + jugador1.getNombre());
            System.out.println("Total equipos: " + equipoRepo.obtenerTodos().size());
            System.out.println("Total jugadores: " + jugadorRepo.obtenerTodos().size());

            em.getTransaction().commit();
            System.out.println("Prueba de repositories completada exitosamente");

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