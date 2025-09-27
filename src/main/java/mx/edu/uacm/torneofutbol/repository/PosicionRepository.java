package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Posicion;
import java.util.Optional;

/**
 * Repository para gestionar posiciones de jugadores
 * Catálogo de posiciones: Portero, Defensa, Medio, Delantero
 */
public interface PosicionRepository extends GenericRepository<Posicion, Long> {

    // Buscar posición por nombre
    Optional<Posicion> buscarPorNombre(String nombre);

    // Verificar si existe posición por nombre
    boolean existePorNombre(String nombre);
}