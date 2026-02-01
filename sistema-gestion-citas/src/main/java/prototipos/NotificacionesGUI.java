package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

/**
 * HU-07: Notificaciones de Cambios - Interfaz Funcional INTEGRADA
 */
public class NotificacionesGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextArea txtNotificaciones;
    private JButton btnActualizar, btnLimpiar;
    private JLabel lblCantidad;
    
    public NotificacionesGUI() {
        initComponents();
        cargarNotificaciones();
    }
    
    private void initComponents() {
        setTitle("HU-07: Notificaciones del Sistema");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(250, 245, 255));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(148, 0, 211));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel(" Centro de Notificaciones");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel info
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblInfo = new JLabel("Historial de eventos del sistema:");
        lblInfo.setFont(new Font("Arial", Font.BOLD, 13));
        panelInfo.add(lblInfo);
        
        lblCantidad = new JLabel();
        lblCantidad.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCantidad.setForeground(new Color(100, 100, 100));
        panelInfo.add(lblCantidad);
        
        // Área de notificaciones
        JPanel panelNotif = new JPanel(new BorderLayout());
        panelNotif.setBackground(Color.WHITE);
        panelNotif.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        txtNotificaciones = new JTextArea();
        txtNotificaciones.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtNotificaciones.setEditable(false);
        txtNotificaciones.setLineWrap(true);
        txtNotificaciones.setWrapStyleWord(true);
        txtNotificaciones.setBackground(new Color(248, 248, 255));
        
        JScrollPane scroll = new JScrollPane(txtNotificaciones);
        scroll.setPreferredSize(new Dimension(640, 400));
        panelNotif.add(scroll, BorderLayout.CENTER);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(250, 245, 255));
        
        btnActualizar = new JButton(" Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 13));
        btnActualizar.setBackground(new Color(30, 144, 255));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new Dimension(140, 35));
        btnActualizar.addActionListener(e -> cargarNotificaciones());
        
        btnLimpiar = new JButton(" Limpiar Historial");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 13));
        btnLimpiar.setBackground(new Color(220, 20, 60));
        btnLimpiar.setForeground(Color.BLACK);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.setPreferredSize(new Dimension(160, 35));
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelBotones.add(btnActualizar);
        panelBotones.add(btnLimpiar);
        
        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(250, 245, 255));
        
        panelInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelNotif.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelInfo);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelNotif);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarNotificaciones() {
        List<String> notificaciones = sistema.obtenerNotificaciones();
        
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        sb.append("  NOTIFICACIONES Y EVENTOS DEL SISTEMA\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n\n");
        
        if (notificaciones.isEmpty()) {
            sb.append("No hay notificaciones registradas.\n");
        } else {
            for (String notif : notificaciones) {
                sb.append("• " + notif + "\n\n");
            }
        }
        
        txtNotificaciones.setText(sb.toString());
        txtNotificaciones.setCaretPosition(0);
        
        lblCantidad.setText("(" + notificaciones.size() + " notificaciones)");
    }
    
    private void limpiar() {
        int resp = JOptionPane.showConfirmDialog(this,
            "¿Limpiar todo el historial de notificaciones?",
            "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (resp == JOptionPane.YES_OPTION) {
            sistema.obtenerNotificaciones().clear();
            cargarNotificaciones();
            JOptionPane.showMessageDialog(this,
                "Historial limpiado correctamente",
                "Limpieza Exitosa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NotificacionesGUI().setVisible(true));
    }
}
