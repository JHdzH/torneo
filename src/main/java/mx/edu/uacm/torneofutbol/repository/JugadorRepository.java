package mx.edu.uacm.torneofutbol.repository;

import mx.edu.uacm.torneofutbol.entities.Jugador;
import java.util.List;

/**
 * Repository para operaciones con Jugadores
 * Gestiona jugadores y sus relaciones con equipos y posiciones
 */
public interface JugadorRepository extends GenericRepository<Jugador, Long> {

    // Buscar jugadores por equipo
    List<Jugador> buscarPorEquipo(Long equipoId);

    // Buscar jugadores por nombre (like)
    List<Jugador> buscarPorNombre(String nombre);

    // Contar jugadores por equipo
    Long contarJugadoresPorEquipo(Long equipoId);
}