package prototipos;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Suite de Pruebas para Gestión de Usuarios
 * 
 * Valida:
 * - Agregar usuarios
 * - Actualizar usuarios
 * - Desactivar usuarios
 * - Consultar usuarios
 */
@DisplayName("Pruebas: Gestión de Usuarios")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GestionUsuariosTest {
    
    private SistemaGestionCitas sistema;
    
    @BeforeEach
    void setUp() {
        sistema = SistemaGestionCitas.getInstancia();
    }
    
    @Test
    @Order(1)
    @DisplayName("Debe agregar usuario correctamente")
    void debeAgregarUsuario() {
        // Arrange
        SistemaGestionCitas.Usuario usuario = new SistemaGestionCitas.Usuario(
            "Dr. Carlos Mendoza",
            "carlos.mendoza@hospital.com",
            "0998765432",
            "Personal de Atención",
            "Activo"
        );
        
        // Act
        String id = sistema.agregarUsuario(usuario);
        
        // Assert
        assertNotNull(id, "El ID no debe ser nulo");
        assertTrue(id.startsWith("USR-"), "El ID debe tener el formato USR-");
        assertEquals(id, usuario.id, "El ID debe asignarse al usuario");
    }
    
    @Test
    @Order(2)
    @DisplayName("Debe generar IDs únicos para usuarios")
    void debeGenerarIDsUnicos() {
        // Arrange & Act
        SistemaGestionCitas.Usuario u1 = new SistemaGestionCitas.Usuario(
            "Usuario 1", "u1@mail.com", "0991111111", "Usuario", "Activo"
        );
        SistemaGestionCitas.Usuario u2 = new SistemaGestionCitas.Usuario(
            "Usuario 2", "u2@mail.com", "0992222222", "Usuario", "Activo"
        );
        
        String id1 = sistema.agregarUsuario(u1);
        String id2 = sistema.agregarUsuario(u2);
        
        // Assert
        assertNotEquals(id1, id2, "Los IDs deben ser únicos");
    }
    
    @Test
    @Order(3)
    @DisplayName("Debe obtener todos los usuarios")
    void debeObtenerTodosLosUsuarios() {
        // Arrange
        int usuariosIniciales = sistema.obtenerTodosLosUsuarios().size();
        
        // Act
        sistema.agregarUsuario(new SistemaGestionCitas.Usuario(
            "Test User", "test@mail.com", "0999999999", "Usuario", "Activo"
        ));
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        
        // Assert
        assertEquals(usuariosIniciales + 1, usuarios.size());
        assertNotNull(usuarios);
    }
    
    @Test
    @Order(4)
    @DisplayName("Debe actualizar usuario existente")
    void debeActualizarUsuarioExistente() {
        // Arrange
        SistemaGestionCitas.Usuario usuario = new SistemaGestionCitas.Usuario(
            "Original", "original@mail.com", "0991111111", "Usuario", "Activo"
        );
        String id = sistema.agregarUsuario(usuario);
        
        // Act
        SistemaGestionCitas.Usuario actualizado = new SistemaGestionCitas.Usuario(
            "Actualizado", "actualizado@mail.com", "0992222222", "Administrador", "Activo"
        );
        boolean resultado = sistema.actualizarUsuario(id, actualizado);
        
        // Assert
        assertTrue(resultado, "La actualización debe ser exitosa");
        
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        SistemaGestionCitas.Usuario encontrado = usuarios.stream()
            .filter(u -> u.id.equals(id))
            .findFirst()
            .orElse(null);
        
        assertNotNull(encontrado);
        assertEquals("Actualizado", encontrado.nombre);
        assertEquals("actualizado@mail.com", encontrado.email);
    }
    
    @Test
    @Order(5)
    @DisplayName("Debe retornar false al actualizar usuario inexistente")
    void debeRetornarFalsoAlActualizarInexistente() {
        // Arrange
        SistemaGestionCitas.Usuario usuario = new SistemaGestionCitas.Usuario(
            "Test", "test@mail.com", "0991111111", "Usuario", "Activo"
        );
        
        // Act
        boolean resultado = sistema.actualizarUsuario("USR-99999", usuario);
        
        // Assert
        assertFalse(resultado, "Debe retornar false para ID inexistente");
    }
    
    @Test
    @Order(6)
    @DisplayName("Debe desactivar usuario correctamente")
    void debeDesactivarUsuario() {
        // Arrange
        SistemaGestionCitas.Usuario usuario = new SistemaGestionCitas.Usuario(
            "Por Desactivar", "desactivar@mail.com", "0993333333", "Usuario", "Activo"
        );
        String id = sistema.agregarUsuario(usuario);
        
        // Act
        boolean resultado = sistema.desactivarUsuario(id);
        
        // Assert
        assertTrue(resultado, "La desactivación debe ser exitosa");
        
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        SistemaGestionCitas.Usuario encontrado = usuarios.stream()
            .filter(u -> u.id.equals(id))
            .findFirst()
            .orElse(null);
        
        assertNotNull(encontrado);
        assertEquals("Inactivo", encontrado.estado);
    }
    
    @Test
    @Order(7)
    @DisplayName("Debe retornar false al desactivar usuario inexistente")
    void debeRetornarFalsoAlDesactivarInexistente() {
        // Act
        boolean resultado = sistema.desactivarUsuario("USR-99999");
        
        // Assert
        assertFalse(resultado, "Debe retornar false para ID inexistente");
    }
}
