package mx.edu.uacm.torneofutbol.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import mx.edu.uacm.torneofutbol.config.HibernateUtil;
import mx.edu.uacm.torneofutbol.entities.Posicion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PosicionService {

    public void crearPosicionesBasicas() {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Verificar si ya existen posiciones
            Long count = em.createQuery("SELECT COUNT(p) FROM Posicion p", Long.class)
                    .getSingleResult();

            if (count == 0) {
                // Crear posiciones básicas
                List<String> posiciones = Arrays.asList("Portero", "Defensa", "Medio", "Delantero");

                for (String nombre : posiciones) {
                    Posicion posicion = new Posicion(nombre);
                    em.persist(posicion);
                }
                System.out.println("Posiciones básicas creadas");
            } else {
                System.out.println("Ya existen " + count + " posiciones");
            }

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error creando posiciones: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public List<Posicion> obtenerTodasPosiciones() {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            return em.createQuery("SELECT p FROM Posicion p ORDER BY p.nombre", Posicion.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo posiciones: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }

    public Optional<Posicion> buscarPorNombre(String nombre) {
        EntityManager em = HibernateUtil.getEntityManager();

        try {
            List<Posicion> resultados = em.createQuery("SELECT p FROM Posicion p WHERE p.nombre = :nombre", Posicion.class)
                    .setParameter("nombre", nombre)
                    .getResultList();

            return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Error buscando posición: " + e.getMessage(), e);
        } finally {
            em.close();
        }
    }
}
