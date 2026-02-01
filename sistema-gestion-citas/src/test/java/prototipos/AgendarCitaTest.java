package prototipos;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite de Pruebas para HU-01: Agendar Cita
 * 
 * Criterios de Aceptación:
 * - El sistema debe permitir agendar una cita con datos válidos
 * - El sistema debe generar un código único para cada cita
 * - El sistema debe validar que los datos obligatorios estén presentes
 * - El sistema debe validar el formato del email
 * - El sistema debe registrar la cita con estado "Pendiente"
 */
@DisplayName("HU-01: Pruebas de Agendar Cita")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AgendarCitaTest {
    
    private SistemaGestionCitas sistema;
    
    @BeforeEach
    void setUp() {
        // Crear nueva instancia del sistema para cada prueba
        sistema = SistemaGestionCitas.getInstancia();
    }
    
    @Test
    @Order(1)
    @DisplayName("Debe agendar cita con datos válidos")
    void debeAgendarCitaConDatosValidos() {
        // Arrange
        String nombre = "Juan Pérez";
        String email = "juan.perez@mail.com";
        String telefono = "0991234567";
        String fecha = "31/01/2026";
        String hora = "09:00";
        
        // Act
        String codigo = sistema.agendarCita(nombre, email, telefono, fecha, hora);
        
        // Assert
        assertNotNull(codigo, "El código de cita no debe ser nulo");
        assertFalse(codigo.isEmpty(), "El código de cita no debe estar vacío");
        assertEquals(4, codigo.length(), "El código debe tener 4 dígitos");
        
        // Verificar que la cita fue guardada
        SistemaGestionCitas.Cita citaGuardada = sistema.buscarCita(codigo);
        assertNotNull(citaGuardada, "La cita debe estar guardada en el sistema");
        assertEquals(nombre, citaGuardada.nombre);
        assertEquals(email, citaGuardada.email);
        assertEquals(telefono, citaGuardada.telefono);
        assertEquals(fecha, citaGuardada.fecha);
        assertEquals(hora, citaGuardada.hora);
        assertEquals("Pendiente", citaGuardada.estado);
    }
    
    @Test
    @Order(2)
    @DisplayName("Debe generar códigos únicos para diferentes citas")
    void debeGenerarCodigosUnicos() {
        // Arrange & Act
        String codigo1 = sistema.agendarCita("Paciente 1", "p1@mail.com", "0991111111", "31/01/2026", "09:00");
        String codigo2 = sistema.agendarCita("Paciente 2", "p2@mail.com", "0992222222", "31/01/2026", "10:00");
        String codigo3 = sistema.agendarCita("Paciente 3", "p3@mail.com", "0993333333", "31/01/2026", "11:00");
        
        // Assert
        assertNotEquals(codigo1, codigo2, "Los códigos deben ser únicos");
        assertNotEquals(codigo2, codigo3, "Los códigos deben ser únicos");
        assertNotEquals(codigo1, codigo3, "Los códigos deben ser únicos");
    }
    
    @Test
    @Order(3)
    @DisplayName("Debe almacenar múltiples citas correctamente")
    void debeAlmacenarMultiplesCitas() {
        // Arrange
        int citasIniciales = sistema.obtenerTodasLasCitas().size();
        
        // Act
        sistema.agendarCita("Ana García", "ana@mail.com", "0994444444", "31/01/2026", "14:00");
        sistema.agendarCita("Carlos López", "carlos@mail.com", "0995555555", "01/02/2026", "15:00");
        sistema.agendarCita("María Torres", "maria@mail.com", "0996666666", "02/02/2026", "16:00");
        
        // Assert
        int citasFinales = sistema.obtenerTodasLasCitas().size();
        assertEquals(citasIniciales + 3, citasFinales, "Deben haberse agregado 3 citas");
    }
    
    @Test
    @Order(4)
    @DisplayName("Debe registrar cita con estado Pendiente por defecto")
    void debeRegistrarCitaConEstadoPendiente() {
        // Arrange & Act
        String codigo = sistema.agendarCita("Pedro Sánchez", "pedro@mail.com", "0997777777", "31/01/2026", "17:00");
        SistemaGestionCitas.Cita cita = sistema.buscarCita(codigo);
        
        // Assert
        assertNotNull(cita);
        assertEquals("Pendiente", cita.estado, "El estado inicial debe ser Pendiente");
    }
    
    @Test
    @Order(5)
    @DisplayName("Debe generar notificación al agendar cita")
    void debeGenerarNotificacionAlAgendar() {
        // Arrange
        int notificacionesIniciales = sistema.obtenerNotificaciones().size();
        
        // Act
        String codigo = sistema.agendarCita("Laura Martínez", "laura@mail.com", "0998888888", "31/01/2026", "12:00");
        
        // Assert
        int notificacionesFinales = sistema.obtenerNotificaciones().size();
        assertTrue(notificacionesFinales > notificacionesIniciales, "Debe generarse una notificación");
        
        // Verificar que la notificación contiene información relevante
        String ultimaNotificacion = sistema.obtenerNotificaciones().get(0);
        assertTrue(ultimaNotificacion.contains("agendada"), "La notificación debe indicar que se agendó");
        assertTrue(ultimaNotificacion.contains(codigo), "La notificación debe contener el código");
    }
    
    @Test
    @Order(6)
    @DisplayName("Debe aceptar diferentes formatos de hora válidos")
    void debeAceptarDiferentesFormatosHora() {
        // Arrange & Act
        String codigo1 = sistema.agendarCita("Test 1", "test1@mail.com", "0991111111", "31/01/2026", "09:00");
        String codigo2 = sistema.agendarCita("Test 2", "test2@mail.com", "0992222222", "31/01/2026", "14:00");
        String codigo3 = sistema.agendarCita("Test 3", "test3@mail.com", "0993333333", "31/01/2026", "17:00");
        
        // Assert
        assertNotNull(sistema.buscarCita(codigo1));
        assertNotNull(sistema.buscarCita(codigo2));
        assertNotNull(sistema.buscarCita(codigo3));
    }
    
    @Test
    @Order(7)
    @DisplayName("Debe permitir agendar citas en diferentes fechas")
    void debePermitirAgendarEnDiferentesFechas() {
        // Arrange & Act
        String codigo1 = sistema.agendarCita("Paciente A", "a@mail.com", "0991111111", "31/01/2026", "09:00");
        String codigo2 = sistema.agendarCita("Paciente B", "b@mail.com", "0992222222", "01/02/2026", "09:00");
        String codigo3 = sistema.agendarCita("Paciente C", "c@mail.com", "0993333333", "05/02/2026", "09:00");
        
        // Assert
        SistemaGestionCitas.Cita cita1 = sistema.buscarCita(codigo1);
        SistemaGestionCitas.Cita cita2 = sistema.buscarCita(codigo2);
        SistemaGestionCitas.Cita cita3 = sistema.buscarCita(codigo3);
        
        assertNotEquals(cita1.fecha, cita2.fecha);
        assertNotEquals(cita2.fecha, cita3.fecha);
        assertNotEquals(cita1.fecha, cita3.fecha);
    }
}
