package mx.edu.uacm.torneofutbol.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "partidos")
public class Partido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_local_id", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipo_visitante_id", nullable = false)
    private Equipo equipoVisitante;

    @Column(name = "goles_local")
    private Integer golesLocal = 0;

    @Column(name = "goles_visitante")
    private Integer golesVisitante = 0;

    @Column(name = "fecha_partido")
    private LocalDateTime fechaPartido;

    @OneToMany(mappedBy = "partido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Gol> goles = new ArrayList<>();

    // Constructores
    public Partido() {}

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, LocalDateTime fechaPartido) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fechaPartido = fechaPartido;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Equipo getEquipoLocal() { return equipoLocal; }
    public void setEquipoLocal(Equipo equipoLocal) { this.equipoLocal = equipoLocal; }

    public Equipo getEquipoVisitante() { return equipoVisitante; }
    public void setEquipoVisitante(Equipo equipoVisitante) { this.equipoVisitante = equipoVisitante; }

    public Integer getGolesLocal() { return golesLocal; }
    public void setGolesLocal(Integer golesLocal) { this.golesLocal = golesLocal; }

    public Integer getGolesVisitante() { return golesVisitante; }
    public void setGolesVisitante(Integer golesVisitante) { this.golesVisitante = golesVisitante; }

    public LocalDateTime getFechaPartido() { return fechaPartido; }
    public void setFechaPartido(LocalDateTime fechaPartido) { this.fechaPartido = fechaPartido; }

    public List<Gol> getGoles() { return goles; }
    public void setGoles(List<Gol> goles) { this.goles = goles; }

    // Métodos helper
    public void agregarGol(Gol gol) {
        goles.add(gol);
        gol.setPartido(this);

        // Actualizar contador de goles
        if (gol.getJugador().getEquipo().equals(equipoLocal)) {
            golesLocal++;
        } else {
            golesVisitante++;
        }
    }

    public String getResultado() {
        return golesLocal + " - " + golesVisitante;
    }

    @Override
    public String toString() {
        return "Partido{id=" + id + ", " + equipoLocal.getNombre() + " " +
                getResultado() + " " + equipoVisitante.getNombre() + "}";
    }
}