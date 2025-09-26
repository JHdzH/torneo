package mx.edu.uacm.torneofutbol.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posiciones")
public class Posicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;

    @ManyToMany(mappedBy = "posiciones")
    private List<Jugador> jugadores = new ArrayList<>();

    // Constructores
    public Posicion() {}

    public Posicion(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Jugador> getJugadores() { return jugadores; }
    public void setJugadores(List<Jugador> jugadores) { this.jugadores = jugadores; }

    @Override
    public String toString() {
        return "Posicion{id=" + id + ", nombre='" + nombre + "'}";
    }
}