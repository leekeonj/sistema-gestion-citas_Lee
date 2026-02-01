package prototipos;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de Pruebas para HU-03: Cancelar Cita
 * 
 * Criterios de Aceptación:
 * - El sistema debe permitir cancelar una cita existente
 * - El sistema debe cambiar el estado de la cita a "Cancelada"
 * - El sistema no debe permitir cancelar una cita ya cancelada
 * - El sistema debe generar notificación de cancelación
 * - El sistema debe retornar false si la cita no existe
 */
@DisplayName("HU-03: Pruebas de Cancelar Cita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CancelarCitaTest {
    
    private SistemaGestionCitas sistema;
    private String codigoCitaTest;
    
    @BeforeEach
    void setUp() {
        sistema = SistemaGestionCitas.getInstancia();
        // Crear una cita de prueba antes de cada test
        codigoCitaTest = sistema.agendarCita(
            "Paciente Test", 
            "test@mail.com", 
            "0991234567", 
            "31/01/2026", 
            "09:00"
        );
    }
    
    @Test
    @Order(1)
    @DisplayName("Debe cancelar una cita existente correctamente")
    void debeCancelarCitaExistente() {
        // Arrange
        SistemaGestionCitas.Cita citaAntes = sistema.buscarCita(codigoCitaTest);
        assertEquals("Pendiente", citaAntes.estado);
        
        // Act
        boolean resultado = sistema.cancelarCita(codigoCitaTest);
        
        // Assert
        assertTrue(resultado, "La cancelación debe ser exitosa");
        
        SistemaGestionCitas.Cita citaDespues = sistema.buscarCita(codigoCitaTest);
        assertNotNull(citaDespues, "La cita debe seguir existiendo");
        assertEquals("Cancelada", citaDespues.estado, "El estado debe cambiar a Cancelada");
    }
    
    @Test
    @Order(2)
    @DisplayName("No debe permitir cancelar una cita ya cancelada")
    void noDebePermitirCancelarCitaYaCancelada() {
        // Arrange
        sistema.cancelarCita(codigoCitaTest); // Primera cancelación
        
        // Act
        boolean resultado = sistema.cancelarCita(codigoCitaTest); // Segunda cancelación
        
        // Assert
        assertFalse(resultado, "No debe permitir cancelar una cita ya cancelada");
    }
    
    @Test
    @Order(3)
    @DisplayName("Debe retornar false al intentar cancelar cita inexistente")
    void debeRetornarFalsoParaCitaInexistente() {
        // Arrange
        String codigoInexistente = "9999";
        
        // Act
        boolean resultado = sistema.cancelarCita(codigoInexistente);
        
        // Assert
        assertFalse(resultado, "Debe retornar false para código inexistente");
    }
    
    @Test
    @Order(4)
    @DisplayName("Debe generar notificación al cancelar cita")
    void debeGenerarNotificacionAlCancelar() {
        // Arrange
        int notificacionesAntes = sistema.obtenerNotificaciones().size();
        
        // Act
        sistema.cancelarCita(codigoCitaTest);
        
        // Assert
        int notificacionesDespues = sistema.obtenerNotificaciones().size();
        assertTrue(notificacionesDespues > notificacionesAntes, "Debe generar una notificación");
        
        String ultimaNotificacion = sistema.obtenerNotificaciones().get(0);
        assertTrue(ultimaNotificacion.contains("cancelada"), "Debe indicar cancelación");
        assertTrue(ultimaNotificacion.contains(codigoCitaTest), "Debe contener el código");
    }
    
    @Test
    @Order(5)
    @DisplayName("Debe mantener los datos de la cita después de cancelar")
    void debeMantenerDatosDespuesDeCancelar() {
        // Arrange
        SistemaGestionCitas.Cita citaOriginal = sistema.buscarCita(codigoCitaTest);
        String nombreOriginal = citaOriginal.nombre;
        String emailOriginal = citaOriginal.email;
        String fechaOriginal = citaOriginal.fecha;
        String horaOriginal = citaOriginal.hora;
        
        // Act
        sistema.cancelarCita(codigoCitaTest);
        
        // Assert
        SistemaGestionCitas.Cita citaCancelada = sistema.buscarCita(codigoCitaTest);
        assertEquals(nombreOriginal, citaCancelada.nombre, "El nombre debe mantenerse");
        assertEquals(emailOriginal, citaCancelada.email, "El email debe mantenerse");
        assertEquals(fechaOriginal, citaCancelada.fecha, "La fecha debe mantenerse");
        assertEquals(horaOriginal, citaCancelada.hora, "La hora debe mantenerse");
    }
    
    @Test
    @Order(6)
    @DisplayName("Debe permitir cancelar múltiples citas diferentes")
    void debePermitirCancelarMultiplesCitas() {
        // Arrange
        String codigo1 = sistema.agendarCita("P1", "p1@mail.com", "0991111111", "31/01/2026", "10:00");
        String codigo2 = sistema.agendarCita("P2", "p2@mail.com", "0992222222", "31/01/2026", "11:00");
        String codigo3 = sistema.agendarCita("P3", "p3@mail.com", "0993333333", "31/01/2026", "12:00");
        
        // Act
        boolean r1 = sistema.cancelarCita(codigo1);
        boolean r2 = sistema.cancelarCita(codigo2);
        boolean r3 = sistema.cancelarCita(codigo3);
        
        // Assert
        assertTrue(r1 && r2 && r3, "Todas las cancelaciones deben ser exitosas");
        assertEquals("Cancelada", sistema.buscarCita(codigo1).estado);
        assertEquals("Cancelada", sistema.buscarCita(codigo2).estado);
        assertEquals("Cancelada", sistema.buscarCita(codigo3).estado);
    }
    
    @Test
    @Order(7)
    @DisplayName("No debe afectar otras citas al cancelar una")
    void noDebeAfectarOtrasCitas() {
        // Arrange
        String codigo2 = sistema.agendarCita("Otro Paciente", "otro@mail.com", "0997777777", "31/01/2026", "10:00");
        
        // Act
        sistema.cancelarCita(codigoCitaTest);
        
        // Assert
        SistemaGestionCitas.Cita otraCita = sistema.buscarCita(codigo2);
        assertEquals("Pendiente", otraCita.estado, "Otras citas no deben verse afectadas");
    }
    
    @Test
    @Order(8)
    @DisplayName("Debe manejar códigos null o vacíos correctamente")
    void debeManejarCodigosInvalidos() {
        // Act & Assert
        assertFalse(sistema.cancelarCita(null), "Debe manejar código null");
        assertFalse(sistema.cancelarCita(""), "Debe manejar código vacío");
    }
}
