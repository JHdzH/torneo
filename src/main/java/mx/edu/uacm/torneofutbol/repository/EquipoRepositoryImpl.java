package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Equipo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repository de Equipos
 * Maneja las operaciones de base de datos para la entidad Equipo
 */
@Transactional
public class EquipoRepositoryImpl implements EquipoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Equipo guardar(Equipo equipo) {
        if (equipo.getId() == null) {
            entityManager.persist(equipo); // Insert
        } else {
            equipo = entityManager.merge(equipo); // Update
        }
        return equipo;
    }

    @Override
    public Optional<Equipo> buscarPorId(Long id) {
        Equipo equipo = entityManager.find(Equipo.class, id);
        return Optional.ofNullable(equipo);
    }

    @Override
    public List<Equipo> obtenerTodos() {
        String jpql = "SELECT e FROM Equipo e";
        TypedQuery<Equipo> query = entityManager.createQuery(jpql, Equipo.class);
        return query.getResultList();
    }

    @Override
    public void eliminar(Equipo equipo) {
        entityManager.remove(entityManager.contains(equipo) ? equipo : entityManager.merge(equipo));
    }

    @Override
    public boolean existePorId(Long id) {
        String jpql = "SELECT COUNT(e) FROM Equipo e WHERE e.id = :id";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<Equipo> buscarPorNombre(String nombre) {
        String jpql = "SELECT e FROM Equipo e WHERE e.nombre = :nombre";
        TypedQuery<Equipo> query = entityManager.createQuery(jpql, Equipo.class)
                .setParameter("nombre", nombre);
        return query.getResultStream().findFirst();
    }

    @Override
    public boolean existePorNombre(String nombre) {
        String jpql = "SELECT COUNT(e) FROM Equipo e WHERE e.nombre = :nombre";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("nombre", nombre)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Equipo> obtenerEquiposOrdenadosPorNombre() {
        String jpql = "SELECT e FROM Equipo e ORDER BY e.nombre";
        TypedQuery<Equipo> query = entityManager.createQuery(jpql, Equipo.class);
        return query.getResultList();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


}