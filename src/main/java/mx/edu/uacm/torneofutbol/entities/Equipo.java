package mx.edu.uacm.torneofutbol.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Jugador> jugadores = new ArrayList<>();

    @OneToMany(mappedBy = "equipoLocal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partido> partidosComoLocal = new ArrayList<>();

    @OneToMany(mappedBy = "equipoVisitante", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Partido> partidosComoVisitante = new ArrayList<>();

    // Constructores
    public Equipo() {}

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    public List<Partido> getPartidosComoLocal() { return partidosComoLocal; }
    public void setPartidosComoLocal(List<Partido> partidosComoLocal) { this.partidosComoLocal = partidosComoLocal; }

    public List<Partido> getPartidosComoVisitante() { return partidosComoVisitante; }
    public void setPartidosComoVisitante(List<Partido> partidosComoVisitante) { this.partidosComoVisitante = partidosComoVisitante; }

    // Método helper para agregar jugador
    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
        jugador.setEquipo(this);
    }

    @Override
    public String toString() {
        return "Equipo{id=" + id + ", nombre='" + nombre + "'}";
    }
}