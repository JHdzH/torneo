package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Posicion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementación para operaciones con Posiciones
 */
@Transactional
public class PosicionRepositoryImpl implements PosicionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Posicion guardar(Posicion posicion) {
        if (posicion.getId() == null) {
            entityManager.persist(posicion);
        } else {
            posicion = entityManager.merge(posicion);
        }
        return posicion;
    }

    @Override
    public Optional<Posicion> buscarPorId(Long id) {
        Posicion posicion = entityManager.find(Posicion.class, id);
        return Optional.ofNullable(posicion);
    }

    @Override
    public List<Posicion> obtenerTodos() {
        String jpql = "SELECT p FROM Posicion p ORDER BY p.nombre";
        TypedQuery<Posicion> query = entityManager.createQuery(jpql, Posicion.class);
        return query.getResultList();
    }

    @Override
    public void eliminar(Posicion posicion) {
        entityManager.remove(entityManager.contains(posicion) ? posicion : entityManager.merge(posicion));
    }

    @Override
    public boolean existePorId(Long id) {
        String jpql = "SELECT COUNT(p) FROM Posicion p WHERE p.id = :id";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<Posicion> buscarPorNombre(String nombre) {
        String jpql = "SELECT p FROM Posicion p WHERE p.nombre = :nombre";
        TypedQuery<Posicion> query = entityManager.createQuery(jpql, Posicion.class)
                .setParameter("nombre", nombre);
        return query.getResultStream().findFirst();
    }

    @Override
    public boolean existePorNombre(String nombre) {
        String jpql = "SELECT COUNT(p) FROM Posicion p WHERE p.nombre = :nombre";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
        return count > 0;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}