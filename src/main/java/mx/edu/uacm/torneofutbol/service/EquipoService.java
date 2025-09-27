package mx.edu.uacm.torneofutbol.service;

import mx.edu.uacm.torneofutbol.config.HibernateUtil;
import mx.edu.uacm.torneofutbol.entities.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

/**
 * Servicio simplificado para Equipos
 */
public class EquipoService {

    public Equipo crearEquipo(String nombre) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Validación simple
            if (nombre == null || nombre.trim().isEmpty()) {
                throw new RuntimeException("El nombre del equipo es obligatorio");
            }

            Equipo equipo = new Equipo(nombre);
            em.persist(equipo);

            tx.commit();
            return equipo;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error creando equipo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Equipo> obtenerTodosEquipos() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery("SELECT e FROM Equipo e ORDER BY e.nombre", Equipo.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo equipos: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Equipo> buscarPorId(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            Equipo equipo = em.find(Equipo.class, id);
            return Optional.ofNullable(equipo);
        } catch (Exception e) {
            throw new RuntimeException("Error buscando equipo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public boolean eliminarEquipo(Long id) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Equipo equipo = em.find(Equipo.class, id);
            if (equipo == null) {
                return false;
            }

            em.remove(equipo);
            tx.commit();
            return true;

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error eliminando equipo: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}