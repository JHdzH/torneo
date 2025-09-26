package mx.edu.uacm.torneofutbol.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jugadores")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_id")
    private Equipo equipo;

    @ManyToMany
    @JoinTable(
            name = "jugador_posicion",
            joinColumns = @JoinColumn(name = "jugador_id"),
            inverseJoinColumns = @JoinColumn(name = "posicion_id")
    )
    private List<Posicion> posiciones = new ArrayList<>();

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gol> goles = new ArrayList<>();

    // Constructores
    public Jugador() {}

    public Jugador(String nombre, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public Equipo getEquipo() { return equipo; }
    public void setEquipo(Equipo equipo) { this.equipo = equipo; }

    public List<Posicion> getPosiciones() { return posiciones; }
    public void setPosiciones(List<Posicion> posiciones) { this.posiciones = posiciones; }

    public List<Gol> getGoles() { return goles; }
    public void setGoles(List<Gol> goles) { this.goles = goles; }

    // Métodos helper
    public void agregarPosicion(Posicion posicion) {
        posiciones.add(posicion);
        posicion.getJugadores().add(this);
    }

    public void removerPosicion(Posicion posicion) {
        posiciones.remove(posicion);
        posicion.getJugadores().remove(this);
    }

    @Override
    public String toString() {
        return "Jugador{id=" + id + ", nombre='" + nombre + "', equipo=" +
                (equipo != null ? equipo.getNombre() : "Sin equipo") + "}";
    }
}
