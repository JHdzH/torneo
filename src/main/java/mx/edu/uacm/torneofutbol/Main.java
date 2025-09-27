package mx.edu.uacm.torneofutbol;

import mx.edu.uacm.torneofutbol.service.EquipoService;
import mx.edu.uacm.torneofutbol.service.PosicionService;

/**
 * Clase principal del sistema de gestión de torneos
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando sistema de gestion de torneos de futbol...");

        try {
            // Inicializar servicios
            PosicionService posicionService = new PosicionService();
            EquipoService equipoService = new EquipoService();

            // Cargar datos básicos
            posicionService.crearPosicionesBasicas();

            // Demo del sistema
            demoGestionEquipos(equipoService);
            demoGestionPosiciones(posicionService);

            System.out.println("Sistema iniciado correctamente");

        } catch (Exception e) {
            System.err.println("Error iniciando sistema: " + e.getMessage());
            e.printStackTrace();
        } finally {
            mx.edu.uacm.torneofutbol.config.HibernateUtil.shutdown();
        }
    }

    private static void demoGestionEquipos(EquipoService equipoService) {
        var equipos = equipoService.obtenerTodosEquipos();
        System.out.println("Equipos en sistema: " + equipos.size());

        if (equipos.isEmpty()) {
            var equipo = equipoService.crearEquipo("Equipo Demo");
            System.out.println("Equipo demo creado: " + equipo.getNombre());
        }
    }

    private static void demoGestionPosiciones(PosicionService posicionService) {
        var posiciones = posicionService.obtenerTodasPosiciones();
        System.out.println("Posiciones disponibles: " + posiciones.size());

        for (var posicion : posiciones) {
            System.out.println(" - " + posicion.getNombre());
        }
    }
}