package prototipos;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Suite de Pruebas de Integración para Disponibilidad de Horarios
 * 
 * Valida:
 * - Verificación de disponibilidad de horarios
 * - Prevención de solapamientos
 * - Consulta de horarios ocupados
 */
@DisplayName("Pruebas de Integración: Disponibilidad de Horarios")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DisponibilidadHorariosTest {
    
    private SistemaGestionCitas sistema;
    
    @BeforeEach
    void setUp() {
        sistema = SistemaGestionCitas.getInstancia();
    }
    
    @Test
    @Order(1)
    @DisplayName("Debe indicar horario disponible cuando no hay citas")
    void debeIndicarHorarioDisponibleSinCitas() {
        // Arrange
        String fecha = "15/02/2026";
        String hora = "09:00";
        
        // Act
        boolean disponible = sistema.esHorarioDisponible(fecha, hora);
        
        // Assert
        assertTrue(disponible, "El horario debe estar disponible sin citas agendadas");
    }
    
    @Test
    @Order(2)
    @DisplayName("Debe indicar horario ocupado después de agendar")
    void debeIndicarHorarioOcupadoDespuesDeAgendar() {
        // Arrange
        String fecha = "16/02/2026";
        String hora = "10:00";
        
        // Act
        sistema.agendarCita("Paciente 1", "p1@mail.com", "0991111111", fecha, hora);
        boolean disponible = sistema.esHorarioDisponible(fecha, hora);
        
        // Assert
        assertFalse(disponible, "El horario debe estar ocupado después de agendar");
    }
    
    @Test
    @Order(3)
    @DisplayName("Debe liberar horario al cancelar cita")
    void debeLiberarHorarioAlCancelar() {
        // Arrange
        String fecha = "17/02/2026";
        String hora = "11:00";
        String codigo = sistema.agendarCita("Paciente 2", "p2@mail.com", "0992222222", fecha, hora);
        
        // Act
        sistema.cancelarCita(codigo);
        boolean disponible = sistema.esHorarioDisponible(fecha, hora);
        
        // Assert
        assertTrue(disponible, "El horario debe liberarse al cancelar");
    }
    
    @Test
    @Order(4)
    @DisplayName("Debe permitir diferentes horarios en la misma fecha")
    void debePermitirDiferentesHorariosMismaFecha() {
        // Arrange
        String fecha = "18/02/2026";
        
        // Act
        sistema.agendarCita("P1", "p1@mail.com", "0991111111", fecha, "09:00");
        sistema.agendarCita("P2", "p2@mail.com", "0992222222", fecha, "10:00");
        sistema.agendarCita("P3", "p3@mail.com", "0993333333", fecha, "11:00");
        
        // Assert
        assertFalse(sistema.esHorarioDisponible(fecha, "09:00"));
        assertFalse(sistema.esHorarioDisponible(fecha, "10:00"));
        assertFalse(sistema.esHorarioDisponible(fecha, "11:00"));
        assertTrue(sistema.esHorarioDisponible(fecha, "12:00"));
    }
    
    @Test
    @Order(5)
    @DisplayName("Debe permitir mismo horario en diferentes fechas")
    void debePermitirMismoHorarioDiferentesFechas() {
        // Arrange
        String hora = "14:00";
        
        // Act
        sistema.agendarCita("P1", "p1@mail.com", "0991111111", "19/02/2026", hora);
        sistema.agendarCita("P2", "p2@mail.com", "0992222222", "20/02/2026", hora);
        sistema.agendarCita("P3", "p3@mail.com", "0993333333", "21/02/2026", hora);
        
        // Assert
        assertFalse(sistema.esHorarioDisponible("19/02/2026", hora));
        assertFalse(sistema.esHorarioDisponible("20/02/2026", hora));
        assertFalse(sistema.esHorarioDisponible("21/02/2026", hora));
        assertTrue(sistema.esHorarioDisponible("22/02/2026", hora));
    }
    
    @Test
    @Order(6)
    @DisplayName("Debe obtener citas por fecha correctamente")
    void debeObtenerCitasPorFecha() {
        // Arrange
        String fecha = "23/02/2026";
        sistema.agendarCita("P1", "p1@mail.com", "0991111111", fecha, "09:00");
        sistema.agendarCita("P2", "p2@mail.com", "0992222222", fecha, "10:00");
        sistema.agendarCita("P3", "p3@mail.com", "0993333333", "24/02/2026", "09:00");
        
        // Act
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerCitasPorFecha(fecha);
        
        // Assert
        assertEquals(2, citas.size(), "Debe retornar solo las citas de la fecha específica");
        assertTrue(citas.stream().allMatch(c -> c.fecha.equals(fecha)));
    }
    
    @Test
    @Order(7)
    @DisplayName("Debe validar prevención de solapamientos")
    void debePrevenirSolapamientos() {
        // Arrange
        String fecha = "25/02/2026";
        String hora = "15:00";
        
        // Act
        String codigo1 = sistema.agendarCita("Primero", "p1@mail.com", "0991111111", fecha, hora);
        boolean disponibleDespues = sistema.esHorarioDisponible(fecha, hora);
        
        // Assert
        assertNotNull(codigo1, "Primera cita debe agendarse");
        assertFalse(disponibleDespues, "No debe permitir solapamiento");
    }
}
