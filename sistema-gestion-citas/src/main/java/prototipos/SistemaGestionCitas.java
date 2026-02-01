package prototipos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Sistema Central de Gestión de Citas
 * Singleton para compartir datos entre todas las interfaces
 */
public class SistemaGestionCitas {
    
    private static SistemaGestionCitas instancia = null;
    
    // Almacenamiento de datos
    private Map<String, Cita> citas;
    private Map<String, Usuario> usuarios;
    private Map<String, List<Horario>> horariosPersonal;
    private List<String> notificaciones;
    
    // Contadores
    private int contadorCitas = 1000;
    private int contadorUsuarios = 100;
    
    private SistemaGestionCitas() {
        System.out.println("⚙️ Inicializando Sistema de Gestión de Citas...");
        citas = new HashMap<>();
        usuarios = new HashMap<>();
        horariosPersonal = new HashMap<>();
        notificaciones = new ArrayList<>();
        inicializarDatos();
        System.out.println("✅ Sistema inicializado correctamente");
    }
    
    public static synchronized SistemaGestionCitas getInstancia() {
        if (instancia == null) {
            instancia = new SistemaGestionCitas();
        }
        return instancia;
    }
    
    private void inicializarDatos() {
        // Usuarios de ejemplo
        agregarUsuario(new Usuario("admin", "admin@sistema.com", "0999999999", "Administrador", "Activo"));
        agregarUsuario(new Usuario("Dr. Juan Pérez", "juan@hospital.com", "0991234567", "Personal de Atención", "Activo"));
        agregarUsuario(new Usuario("Dra. María López", "maria@hospital.com", "0997654321", "Personal de Atención", "Activo"));
        
        // Horarios de ejemplo
        List<Horario> horariosJuan = new ArrayList<>();
        horariosJuan.add(new Horario("Lunes", "09:00", "12:00", "Activo"));
        horariosJuan.add(new Horario("Martes", "14:00", "17:00", "Activo"));
        horariosPersonal.put("Dr. Juan Pérez", horariosJuan);
        
        System.out.println("📊 Datos iniciales cargados: " + usuarios.size() + " usuarios");
    }
    
    // Métodos para Citas
    public String agendarCita(String nombre, String email, String telefono, String fecha, String hora) {
        String codigo = String.format("%04d", contadorCitas++);
        Cita cita = new Cita(codigo, nombre, email, telefono, fecha, hora, "Pendiente");
        citas.put(codigo, cita);
        agregarNotificacion("Cita agendada: " + codigo + " - " + nombre + " - " + fecha + " " + hora);
        System.out.println("✅ Cita creada: " + codigo + " | Total citas: " + citas.size());
        return codigo;
    }
    
    public Cita buscarCita(String codigo) {
        System.out.println("🔍 Buscando cita: " + codigo + " | Citas disponibles: " + citas.keySet());
        Cita cita = citas.get(codigo);
        if (cita != null) {
            System.out.println("✅ Cita encontrada: " + codigo);
        } else {
            System.out.println("❌ Cita NO encontrada: " + codigo);
        }
        return cita;
    }
    
    public boolean cancelarCita(String codigo) {
        Cita cita = citas.get(codigo);
        if (cita != null && !cita.estado.equals("Cancelada")) {
            cita.estado = "Cancelada";
            agregarNotificacion("Cita cancelada: " + codigo + " - " + cita.nombre);
            System.out.println("✅ Cita cancelada: " + codigo);
            return true;
        }
        System.out.println("❌ No se pudo cancelar: " + codigo);
        return false;
    }
    
    public String reprogramarCita(String codigoViejo, String nuevaFecha, String nuevaHora) {
        Cita citaVieja = citas.get(codigoViejo);
        if (citaVieja != null) {
            cancelarCita(codigoViejo);
            String nuevoCodigo = agendarCita(citaVieja.nombre, citaVieja.email, 
                                            citaVieja.telefono, nuevaFecha, nuevaHora);
            agregarNotificacion("Cita reprogramada: " + codigoViejo + " → " + nuevoCodigo);
            return nuevoCodigo;
        }
        return null;
    }
    
    public List<Cita> obtenerCitasPorEmail(String email) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita cita : citas.values()) {
            if (cita.email.equalsIgnoreCase(email)) {
                resultado.add(cita);
            }
        }
        return resultado;
    }
    
    public List<Cita> obtenerTodasLasCitas() {
        System.out.println("📋 Obteniendo todas las citas. Total: " + citas.size());
        return new ArrayList<>(citas.values());
    }
    
    public List<Cita> obtenerCitasPorFecha(String fecha) {
        List<Cita> resultado = new ArrayList<>();
        for (Cita cita : citas.values()) {
            if (cita.fecha.equals(fecha)) {
                resultado.add(cita);
            }
        }
        return resultado;
    }
    
    public boolean marcarCitaAtendida(String codigo, String observaciones) {
        Cita cita = citas.get(codigo);
        if (cita != null && cita.estado.equals("Pendiente")) {
            cita.estado = "Atendida";
            cita.observaciones = observaciones;
            cita.horaAtencion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            agregarNotificacion("Cita atendida: " + codigo);
            return true;
        }
        return false;
    }
    
    public boolean esHorarioDisponible(String fecha, String hora) {
        for (Cita cita : citas.values()) {
            if (cita.fecha.equals(fecha) && cita.hora.equals(hora) && 
                !cita.estado.equals("Cancelada")) {
                return false;
            }
        }
        return true;
    }
    
    // Métodos para Usuarios
    public String agregarUsuario(Usuario usuario) {
        String id = "USR-" + (contadorUsuarios++);
        usuario.id = id;
        usuarios.put(id, usuario);
        return id;
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        return new ArrayList<>(usuarios.values());
    }
    
    public boolean actualizarUsuario(String id, Usuario usuario) {
        if (usuarios.containsKey(id)) {
            usuario.id = id;
            usuarios.put(id, usuario);
            return true;
        }
        return false;
    }
    
    public boolean desactivarUsuario(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null) {
            usuario.estado = "Inactivo";
            return true;
        }
        return false;
    }
    
    // Métodos para Horarios
    public void agregarHorario(String personal, Horario horario) {
        horariosPersonal.computeIfAbsent(personal, k -> new ArrayList<>()).add(horario);
    }
    
    public List<Horario> obtenerHorarios(String personal) {
        return horariosPersonal.getOrDefault(personal, new ArrayList<>());
    }
    
    public boolean eliminarHorario(String personal, int index) {
        List<Horario> horarios = horariosPersonal.get(personal);
        if (horarios != null && index >= 0 && index < horarios.size()) {
            horarios.remove(index);
            return true;
        }
        return false;
    }
    
    // Métodos para Notificaciones
    public void agregarNotificacion(String mensaje) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        notificaciones.add(0, "[" + timestamp + "] " + mensaje);
        System.out.println("📬 Notificación: " + mensaje);
    }
    
    public List<String> obtenerNotificaciones() {
        return new ArrayList<>(notificaciones);
    }
    
    // Métodos para Reportes
    public Map<String, Integer> obtenerEstadisticasPorEstado() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Pendiente", 0);
        stats.put("Atendida", 0);
        stats.put("Cancelada", 0);
        
        for (Cita cita : citas.values()) {
            stats.put(cita.estado, stats.get(cita.estado) + 1);
        }
        return stats;
    }
    
    public Map<String, Integer> obtenerCitasPorFechaReporte() {
        Map<String, Integer> stats = new HashMap<>();
        for (Cita cita : citas.values()) {
            stats.put(cita.fecha, stats.getOrDefault(cita.fecha, 0) + 1);
        }
        return stats;
    }
    
    // Método de depuración
    public void mostrarEstado() {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("📊 ESTADO DEL SISTEMA");
        System.out.println("════════════════════════════════════════");
        System.out.println("Total Citas: " + citas.size());
        System.out.println("Códigos de citas: " + citas.keySet());
        System.out.println("Total Usuarios: " + usuarios.size());
        System.out.println("Total Notificaciones: " + notificaciones.size());
        System.out.println("════════════════════════════════════════\n");
    }
    
    // Clases internas
    public static class Cita {
        public String codigo, nombre, email, telefono, fecha, hora, estado;
        public String observaciones = "";
        public String horaAtencion = "";
        
        public Cita(String codigo, String nombre, String email, String telefono, 
                   String fecha, String hora, String estado) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
            this.fecha = fecha;
            this.hora = hora;
            this.estado = estado;
        }
    }
    
    public static class Usuario {
        public String id, nombre, email, telefono, rol, estado;
        
        public Usuario(String nombre, String email, String telefono, String rol, String estado) {
            this.nombre = nombre;
            this.email = email;
            this.telefono = telefono;
            this.rol = rol;
            this.estado = estado;
        }
    }
    
    public static class Horario {
        public String dia, horaInicio, horaFin, estado;
        
        public Horario(String dia, String horaInicio, String horaFin, String estado) {
            this.dia = dia;
            this.horaInicio = horaInicio;
            this.horaFin = horaFin;
            this.estado = estado;
        }
    }
}