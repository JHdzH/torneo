package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Equipo;
import java.util.Optional;

/**
 * Repository para operaciones de base de datos con Equipos
 * Gestiona la persistencia de equipos del torneo
 */
public interface EquipoRepository extends GenericRepository<Equipo, Long> {

    // Buscar equipo por nombre exacto
    Optional<Equipo> buscarPorNombre(String nombre);

    // Verificar si existe un equipo con ese nombre
    boolean existePorNombre(String nombre);

    // Obtener equipos ordenados por nombre
    java.util.List<Equipo> obtenerEquiposOrdenadosPorNombre();
}