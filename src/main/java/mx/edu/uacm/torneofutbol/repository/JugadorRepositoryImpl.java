package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Jugador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Implementación para operaciones con Jugadores
 */
@Transactional
public class JugadorRepositoryImpl implements JugadorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Jugador guardar(Jugador jugador) {
        if (jugador.getId() == null) {
            entityManager.persist(jugador);
        } else {
            jugador = entityManager.merge(jugador);
        }
        return jugador;
    }

    @Override
    public Optional<Jugador> buscarPorId(Long id) {
        Jugador jugador = entityManager.find(Jugador.class, id);
        return Optional.ofNullable(jugador);
    }

    @Override
    public List<Jugador> obtenerTodos() {
        String jpql = "SELECT j FROM Jugador j ORDER BY j.nombre";
        TypedQuery<Jugador> query = entityManager.createQuery(jpql, Jugador.class);
        return query.getResultList();
    }

    @Override
    public void eliminar(Jugador jugador) {
        entityManager.remove(entityManager.contains(jugador) ? jugador : entityManager.merge(jugador));
    }

    @Override
    public boolean existePorId(Long id) {
        String jpql = "SELECT COUNT(j) FROM Jugador j WHERE j.id = :id";
        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("id", id)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public List<Jugador> buscarPorEquipo(Long equipoId) {
        String jpql = "SELECT j FROM Jugador j WHERE j.equipo.id = :equipoId ORDER BY j.nombre";
        TypedQuery<Jugador> query = entityManager.createQuery(jpql, Jugador.class)
                .setParameter("equipoId", equipoId);
        return query.getResultList();
    }

    @Override
    public List<Jugador> buscarPorNombre(String nombre) {
        String jpql = "SELECT j FROM Jugador j WHERE j.nombre LIKE :nombre ORDER BY j.nombre";
        TypedQuery<Jugador> query = entityManager.createQuery(jpql, Jugador.class)
                .setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    @Override
    public Long contarJugadoresPorEquipo(Long equipoId) {
        String jpql = "SELECT COUNT(j) FROM Jugador j WHERE j.equipo.id = :equipoId";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("equipoId", equipoId)
                .getSingleResult();
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}