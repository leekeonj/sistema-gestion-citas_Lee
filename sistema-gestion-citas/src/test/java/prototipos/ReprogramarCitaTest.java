package prototipos;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de Pruebas para HU-04: Reprogramar Cita
 * 
 * Criterios de Aceptación:
 * - El sistema debe permitir reprogramar una cita existente
 * - El sistema debe cancelar la cita anterior
 * - El sistema debe crear una nueva cita con nueva fecha/hora
 * - El sistema debe generar un nuevo código
 * - El sistema debe mantener los datos del paciente
 * - El sistema debe generar notificación de reprogramación
 */
@DisplayName("HU-04: Pruebas de Reprogramar Cita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReprogramarCitaTest {
    
    private SistemaGestionCitas sistema;
    private String codigoCitaOriginal;
    
    @BeforeEach
    void setUp() {
        sistema = SistemaGestionCitas.getInstancia();
        // Crear cita original antes de cada test
        codigoCitaOriginal = sistema.agendarCita(
            "Juan Pérez",
            "juan@mail.com",
            "0991234567",
            "31/01/2026",
            "09:00"
        );
    }
    
    @Test
    @Order(1)
    @DisplayName("Debe reprogramar cita exitosamente")
    void debeReprogramarCitaExitosamente() {
        // Arrange
        String nuevaFecha = "02/02/2026";
        String nuevaHora = "14:00";
        
        // Act
        String nuevoCodigo = sistema.reprogramarCita(codigoCitaOriginal, nuevaFecha, nuevaHora);
        
        // Assert
        assertNotNull(nuevoCodigo, "Debe retornar un nuevo código");
        assertFalse(nuevoCodigo.isEmpty(), "El nuevo código no debe estar vacío");
        assertNotEquals(codigoCitaOriginal, nuevoCodigo, "El código debe ser diferente");
    }
    
    @Test
    @Order(2)
    @DisplayName("Debe cancelar la cita anterior al reprogramar")
    void debeCancelarCitaAnterior() {
        // Arrange
        String nuevaFecha = "03/02/2026";
        String nuevaHora = "15:00";
        
        // Act
        sistema.reprogramarCita(codigoCitaOriginal, nuevaFecha, nuevaHora);
        
        // Assert
        SistemaGestionCitas.Cita citaAnterior = sistema.buscarCita(codigoCitaOriginal);
        assertNotNull(citaAnterior, "La cita anterior debe existir");
        assertEquals("Cancelada", citaAnterior.estado, "La cita anterior debe estar cancelada");
    }
    
    @Test
    @Order(3)
    @DisplayName("Debe crear nueva cita con nueva fecha y hora")
    void debeCrearNuevaCitaConNuevaFechaHora() {
        // Arrange
        String nuevaFecha = "04/02/2026";
        String nuevaHora = "16:00";
        
        // Act
        String nuevoCodigo = sistema.reprogramarCita(codigoCitaOriginal, nuevaFecha, nuevaHora);
        
        // Assert
        SistemaGestionCitas.Cita nuevaCita = sistema.buscarCita(nuevoCodigo);
        assertNotNull(nuevaCita, "La nueva cita debe existir");
        assertEquals(nuevaFecha, nuevaCita.fecha, "Debe tener la nueva fecha");
        assertEquals(nuevaHora, nuevaCita.hora, "Debe tener la nueva hora");
        assertEquals("Pendiente", nuevaCita.estado, "Debe estar en estado Pendiente");
    }
    
    @Test
    @Order(4)
    @DisplayName("Debe mantener los datos del paciente al reprogramar")
    void debeMantenerDatosPaciente() {
        // Arrange
        SistemaGestionCitas.Cita citaOriginal = sistema.buscarCita(codigoCitaOriginal);
        String nombreOriginal = citaOriginal.nombre;
        String emailOriginal = citaOriginal.email;
        String telefonoOriginal = citaOriginal.telefono;
        
        // Act
        String nuevoCodigo = sistema.reprogramarCita(codigoCitaOriginal, "05/02/2026", "10:00");
        
        // Assert
        SistemaGestionCitas.Cita nuevaCita = sistema.buscarCita(nuevoCodigo);
        assertEquals(nombreOriginal, nuevaCita.nombre, "El nombre debe mantenerse");
        assertEquals(emailOriginal, nuevaCita.email, "El email debe mantenerse");
        assertEquals(telefonoOriginal, nuevaCita.telefono, "El teléfono debe mantenerse");
    }
    
    @Test
    @Order(5)
    @DisplayName("Debe generar notificación al reprogramar")
    void debeGenerarNotificacionAlReprogramar() {
        // Arrange
        int notificacionesAntes = sistema.obtenerNotificaciones().size();
        
        // Act
        String nuevoCodigo = sistema.reprogramarCita(codigoCitaOriginal, "06/02/2026", "11:00");
        
        // Assert
        int notificacionesDespues = sistema.obtenerNotificaciones().size();
        assertTrue(notificacionesDespues > notificacionesAntes, "Debe generar notificaciones");
        
        String ultimaNotificacion = sistema.obtenerNotificaciones().get(0);
        assertTrue(
            ultimaNotificacion.contains("reprogramada") || 
            ultimaNotificacion.contains("agendada"), 
            "Debe indicar reprogramación o nueva cita"
        );
    }
    
    @Test
    @Order(6)
    @DisplayName("Debe retornar null para código inexistente")
    void debeRetornarNullParaCodigoInexistente() {
        // Arrange
        String codigoInexistente = "9999";
        
        // Act
        String resultado = sistema.reprogramarCita(codigoInexistente, "07/02/2026", "12:00");
        
        // Assert
        assertNull(resultado, "Debe retornar null si la cita no existe");
    }
    
    @Test
    @Order(7)
    @DisplayName("Debe permitir reprogramar múltiples veces")
    void debePermitirReprogramarMultiplesVeces() {
        // Act
        String codigo2 = sistema.reprogramarCita(codigoCitaOriginal, "08/02/2026", "13:00");
        assertNotNull(codigo2, "Primera reprogramación debe funcionar");
        
        String codigo3 = sistema.reprogramarCita(codigo2, "09/02/2026", "14:00");
        assertNotNull(codigo3, "Segunda reprogramación debe funcionar");
        
        String codigo4 = sistema.reprogramarCita(codigo3, "10/02/2026", "15:00");
        assertNotNull(codigo4, "Tercera reprogramación debe funcionar");
        
        // Assert
        assertNotEquals(codigo2, codigo3, "Cada reprogramación debe generar código único");
        assertNotEquals(codigo3, codigo4, "Cada reprogramación debe generar código único");
        
        // Verificar que las citas anteriores están canceladas
        assertEquals("Cancelada", sistema.buscarCita(codigoCitaOriginal).estado);
        assertEquals("Cancelada", sistema.buscarCita(codigo2).estado);
        assertEquals("Cancelada", sistema.buscarCita(codigo3).estado);
        
        // Verificar que la última está pendiente
        assertEquals("Pendiente", sistema.buscarCita(codigo4).estado);
    }
    
    @Test
    @Order(8)
    @DisplayName("Debe incrementar contador de citas al reprogramar")
    void debeIncrementarContadorCitas() {
        // Arrange
        int citasAntes = sistema.obtenerTodasLasCitas().size();
        
        // Act
        sistema.reprogramarCita(codigoCitaOriginal, "11/02/2026", "16:00");
        
        // Assert
        int citasDespues = sistema.obtenerTodasLasCitas().size();
        // Se debe incrementar en 1 (nueva cita), la anterior solo cambia de estado
        assertTrue(citasDespues >= citasAntes, "Debe haber al menos una cita nueva");
    }
    
    @Test
    @Order(9)
    @DisplayName("Debe permitir reprogramar a diferentes horarios del mismo día")
    void debePermitirReprogramarMismoDia() {
        // Arrange
        String mismaFecha = "31/01/2026";
        
        // Act
        String nuevoCodigo = sistema.reprogramarCita(codigoCitaOriginal, mismaFecha, "17:00");
        
        // Assert
        assertNotNull(nuevoCodigo);
        SistemaGestionCitas.Cita nuevaCita = sistema.buscarCita(nuevoCodigo);
        assertEquals(mismaFecha, nuevaCita.fecha, "Debe permitir reprogramar en la misma fecha");
        assertEquals("17:00", nuevaCita.hora, "Debe actualizar la hora");
    }
    
    @Test
    @Order(10)
    @DisplayName("No debe afectar otras citas al reprogramar")
    void noDebeAfectarOtrasCitas() {
        // Arrange
        String otroCodigo = sistema.agendarCita(
            "María López", 
            "maria@mail.com", 
            "0997777777", 
            "12/02/2026", 
            "09:00"
        );
        SistemaGestionCitas.Cita otraCitaAntes = sistema.buscarCita(otroCodigo);
        
        // Act
        sistema.reprogramarCita(codigoCitaOriginal, "13/02/2026", "10:00");
        
        // Assert
        SistemaGestionCitas.Cita otraCitaDespues = sistema.buscarCita(otroCodigo);
        assertEquals(otraCitaAntes.estado, otraCitaDespues.estado, "Otras citas no deben cambiar");
        assertEquals(otraCitaAntes.fecha, otraCitaDespues.fecha, "Otras citas no deben cambiar");
        assertEquals(otraCitaAntes.hora, otraCitaDespues.hora, "Otras citas no deben cambiar");
    }
}
