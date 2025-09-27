package mx.edu.uacm.torneofutbol.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz base para operaciones CRUD comunes
 * T: Tipo de entidad, ID: Tipo del ID
 */
public interface GenericRepository<T, ID> {

    // Guardar entidad (insert o update)
    T guardar(T entidad);

    // Buscar por ID
    Optional<T> buscarPorId(ID id);

    // Obtener todos los registros
    List<T> obtenerTodos();

    // Eliminar entidad
    void eliminar(T entidad);

    // Verificar si existe por ID
    boolean existePorId(ID id);

    // Método para inyectar EntityManager
    void setEntityManager(EntityManager entityManager);
}