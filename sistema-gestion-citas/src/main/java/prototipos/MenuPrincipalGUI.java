package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * HU-12: Menú Principal - Acceso a Todas las Funcionalidades
 * Sistema Integrado de Gestión de Citas
 */
public class MenuPrincipalGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JLabel lblEstadoSistema;
    
    public MenuPrincipalGUI() {
        initComponents();
        actualizarEstado();
    }
    
    private void initComponents() {
        setTitle("Sistema de Gestión de Citas - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Panel título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setLayout(new BoxLayout(panelTitulo, BoxLayout.Y_AXIS));
        panelTitulo.setBackground(new Color(25, 25, 112));
        panelTitulo.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel(" SISTEMA DE GESTIÓN DE CITAS");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblSubtitulo = new JLabel("Menú Principal de Funcionalidades");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(200, 200, 255));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelTitulo.add(lblTitulo);
        panelTitulo.add(Box.createRigidArea(new Dimension(0, 10)));
        panelTitulo.add(lblSubtitulo);
        
        // Panel central con botones
        JPanel panelCentral = new JPanel(new GridLayout(4, 3, 15, 15));
        panelCentral.setBackground(new Color(240, 248, 255));
        panelCentral.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Crear botones para cada funcionalidad
        panelCentral.add(crearBoton(" Agendar Cita", 
            "HU-01: Agendar nueva cita", 
            new Color(70, 130, 180), 
            () -> new AgendarCitaGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Consultar Disponibilidad", 
            "HU-02: Ver horarios disponibles", 
            new Color(30, 144, 255), 
            () -> new ConsultarDisponibilidadGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Cancelar Cita", 
            "HU-03: Cancelar cita existente", 
            new Color(220, 20, 60), 
            () -> new CancelarCitaGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Reprogramar Cita", 
            "HU-04: Cambiar fecha/hora", 
            new Color(255, 165, 0), 
            () -> new ReprogramarCitaGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Gestionar Horarios", 
            "HU-05: Configurar disponibilidad", 
            new Color(106, 90, 205), 
            () -> new GestionarHorariosGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Gestionar Usuarios", 
            "HU-06: Administrar usuarios", 
            new Color(139, 0, 0), 
            () -> new GestionarUsuariosGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Notificaciones", 
            "HU-07: Ver notificaciones", 
            new Color(148, 0, 211), 
            () -> new NotificacionesGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Historial de Citas", 
            "HU-08: Consultar historial", 
            new Color(60, 179, 113), 
            () -> new HistorialCitasGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Marcar Atendida", 
            "HU-09: Registrar atención", 
            new Color(0, 128, 128), 
            () -> new MarcarAtendidaGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Prevenir Solapamientos", 
            "HU-10: Validar disponibilidad", 
            new Color(255, 140, 0), 
            () -> new PrevencionSolapamientosGUI().setVisible(true)));
        
        panelCentral.add(crearBoton("Reportes", 
            "HU-11: Ver estadísticas", 
            new Color(72, 61, 139), 
            () -> new ReportesGUI().setVisible(true)));
        
        panelCentral.add(crearBoton(" Actualizar Estado", 
            "Refrescar información", 
            new Color(34, 139, 34), 
            this::actualizarEstado));
        
        // Panel inferior con estado
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));
        panelInferior.setBackground(Color.WHITE);
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblInfoTitulo = new JLabel("Estado del Sistema");
        lblInfoTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfoTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        lblEstadoSistema = new JLabel();
        lblEstadoSistema.setFont(new Font("Arial", Font.PLAIN, 12));
        lblEstadoSistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelInferior.add(lblInfoTitulo);
        panelInferior.add(Box.createRigidArea(new Dimension(0, 10)));
        panelInferior.add(lblEstadoSistema);
        
        // Ensamblar
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private JButton crearBoton(String texto, String tooltip, Color color, Runnable accion) {
        JButton btn = new JButton();
        btn.setLayout(new BoxLayout(btn, BoxLayout.Y_AXIS));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setToolTipText(tooltip);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel lblTexto = new JLabel(texto);
        lblTexto.setFont(new Font("Arial", Font.BOLD, 14));
        lblTexto.setForeground(Color.WHITE);
        lblTexto.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTooltip = new JLabel(tooltip);
        lblTooltip.setFont(new Font("Arial", Font.PLAIN, 10));
        lblTooltip.setForeground(new Color(230, 230, 255));
        lblTooltip.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        btn.add(Box.createVerticalGlue());
        btn.add(lblTexto);
        btn.add(Box.createRigidArea(new Dimension(0, 5)));
        btn.add(lblTooltip);
        btn.add(Box.createVerticalGlue());
        
        // Efecto hover
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(color);
            }
        });
        
        btn.addActionListener(e -> accion.run());
        
        return btn;
    }
    
    private void actualizarEstado() {
        java.util.Map<String, Integer> stats = sistema.obtenerEstadisticasPorEstado();
        int totalCitas = sistema.obtenerTodasLasCitas().size();
        int totalUsuarios = sistema.obtenerTodosLosUsuarios().size();
        int notificaciones = sistema.obtenerNotificaciones().size();
        
        String estado = String.format(
            "<html><center>" +
            "📌 Total de Citas: %d | " +
            "⏳ Pendientes: %d | " +
            "✅ Atendidas: %d | " +
            "❌ Canceladas: %d<br>" +
            "👥 Usuarios: %d | " +
            "📬 Notificaciones: %d | " +
            "🕐 Actualizado: %s" +
            "</center></html>",
            totalCitas,
            stats.get("Pendiente"),
            stats.get("Atendida"),
            stats.get("Cancelada"),
            totalUsuarios,
            notificaciones,
            java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"))
        );
        
        lblEstadoSistema.setText(estado);
    }
    
    public static void main(String[] args) {
        // Configurar look and feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            MenuPrincipalGUI menu = new MenuPrincipalGUI();
            menu.setVisible(true);
            
            // Mensaje de bienvenida
            ;
        });
    }
}
