package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * HU-11: Reportes Administrativos - Interfaz Funcional INTEGRADA
 */
public class ReportesGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextArea txtReporte;
    private JComboBox<String> cmbTipoReporte;
    private JButton btnGenerar, btnExportar;
    
    public ReportesGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HU-11: Reportes Administrativos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 255));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(72, 61, 139));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("Reportes y Estadísticas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel selección
        JPanel panelSel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelSel.setBackground(Color.WHITE);
        panelSel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblTipo = new JLabel("Tipo de Reporte:");
        lblTipo.setFont(new Font("Arial", Font.BOLD, 13));
        panelSel.add(lblTipo);
        
        String[] tipos = {
            "Estadísticas Generales",
            "Citas por Estado",
            "Citas por Fecha",
            "Análisis de Cancelaciones",
            "Resumen Completo"
        };
        cmbTipoReporte = new JComboBox<>(tipos);
        cmbTipoReporte.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbTipoReporte.setPreferredSize(new Dimension(220, 30));
        panelSel.add(cmbTipoReporte);
        
        btnGenerar = new JButton("📈 Generar Reporte");
        btnGenerar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGenerar.setBackground(new Color(30, 144, 255));
        btnGenerar.setForeground(Color.BLACK);
        btnGenerar.setFocusPainted(false);
        btnGenerar.setPreferredSize(new Dimension(170, 35));
        btnGenerar.addActionListener(e -> generarReporte());
        panelSel.add(btnGenerar);
        
        btnExportar = new JButton("💾 Exportar");
        btnExportar.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportar.setBackground(new Color(34, 139, 34));
        btnExportar.setForeground(Color.BLACK);
        btnExportar.setFocusPainted(false);
        btnExportar.setPreferredSize(new Dimension(120, 35));
        btnExportar.addActionListener(e -> exportar());
        panelSel.add(btnExportar);
        
        // Panel reporte
        JPanel panelReporte = new JPanel(new BorderLayout());
        panelReporte.setBackground(Color.WHITE);
        panelReporte.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        txtReporte = new JTextArea();
        txtReporte.setFont(new Font("Courier New", Font.PLAIN, 11));
        txtReporte.setEditable(false);
        txtReporte.setBackground(new Color(250, 250, 255));
        txtReporte.setText("Seleccione un tipo de reporte y presione 'Generar Reporte'");
        
        JScrollPane scroll = new JScrollPane(txtReporte);
        scroll.setPreferredSize(new Dimension(740, 500));
        panelReporte.add(scroll, BorderLayout.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(245, 245, 255));
        
        panelSel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelReporte.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelSel);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelReporte);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }
    
    private void generarReporte() {
        String tipo = (String) cmbTipoReporte.getSelectedItem();
        StringBuilder sb = new StringBuilder();
        
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        sb.append("  SISTEMA DE GESTIÓN DE CITAS - REPORTE ADMINISTRATIVO\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n\n");
        sb.append("Tipo de Reporte: ").append(tipo).append("\n");
        sb.append("Fecha de Generación: ").append(java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))).append("\n");
        sb.append("───────────────────────────────────────────────────────────────────\n\n");
        
        switch (tipo) {
            case "Estadísticas Generales":
                generarEstadisticasGenerales(sb);
                break;
            case "Citas por Estado":
                generarCitasPorEstado(sb);
                break;
            case "Citas por Fecha":
                generarCitasPorFecha(sb);
                break;
            case "Análisis de Cancelaciones":
                generarAnalisisCancelaciones(sb);
                break;
            case "Resumen Completo":
                generarResumenCompleto(sb);
                break;
        }
        
        sb.append("\n═══════════════════════════════════════════════════════════════════\n");
        sb.append("  FIN DEL REPORTE\n");
        sb.append("═══════════════════════════════════════════════════════════════════\n");
        
        txtReporte.setText(sb.toString());
        txtReporte.setCaretPosition(0);
    }
    
    private void generarEstadisticasGenerales(StringBuilder sb) {
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerTodasLasCitas();
        Map<String, Integer> stats = sistema.obtenerEstadisticasPorEstado();
        
        sb.append("ESTADÍSTICAS GENERALES DEL SISTEMA\n\n");
        sb.append(String.format("Total de Citas Registradas:   %d\n", citas.size()));
        sb.append(String.format("Citas Pendientes:             %d\n", stats.get("Pendiente")));
        sb.append(String.format("Citas Atendidas:              %d\n", stats.get("Atendida")));
        sb.append(String.format("Citas Canceladas:             %d\n\n", stats.get("Cancelada")));
        
        double tasaAsistencia = citas.size() > 0 ? 
            (stats.get("Atendida") * 100.0 / citas.size()) : 0;
        double tasaCancelacion = citas.size() > 0 ? 
            (stats.get("Cancelada") * 100.0 / citas.size()) : 0;
        
        sb.append(String.format("Tasa de Asistencia:           %.2f%%\n", tasaAsistencia));
        sb.append(String.format("Tasa de Cancelación:          %.2f%%\n", tasaCancelacion));
    }
    
    private void generarCitasPorEstado(StringBuilder sb) {
        Map<String, Integer> stats = sistema.obtenerEstadisticasPorEstado();
        
        sb.append("DISTRIBUCIÓN DE CITAS POR ESTADO\n\n");
        
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            String barra = generarBarra(entry.getValue(), 50);
            sb.append(String.format("%-15s %3d  %s\n", 
                entry.getKey() + ":", entry.getValue(), barra));
        }
    }
    
    private void generarCitasPorFecha(StringBuilder sb) {
        Map<String, Integer> citasPorFecha = sistema.obtenerCitasPorFechaReporte();
        
        sb.append("CITAS AGENDADAS POR FECHA\n\n");
        
        if (citasPorFecha.isEmpty()) {
            sb.append("No hay citas registradas\n");
        } else {
            for (Map.Entry<String, Integer> entry : citasPorFecha.entrySet()) {
                sb.append(String.format("%-12s  %2d citas\n", 
                    entry.getKey(), entry.getValue()));
            }
        }
    }
    
    private void generarAnalisisCancelaciones(StringBuilder sb) {
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerTodasLasCitas();
        int canceladas = 0;
        
        sb.append("ANÁLISIS DE CITAS CANCELADAS\n\n");
        sb.append("Listado de Citas Canceladas:\n\n");
        
        for (SistemaGestionCitas.Cita c : citas) {
            if (c.estado.equals("Cancelada")) {
                sb.append(String.format("• %s - %s (%s %s) - %s\n",
                    c.codigo, c.nombre, c.fecha, c.hora, c.email));
                canceladas++;
            }
        }
        
        if (canceladas == 0) {
            sb.append("No hay citas canceladas\n");
        } else {
            sb.append(String.format("\nTotal: %d citas canceladas\n", canceladas));
        }
    }
    
    private void generarResumenCompleto(StringBuilder sb) {
        generarEstadisticasGenerales(sb);
        sb.append("\n───────────────────────────────────────────────────────────────────\n\n");
        generarCitasPorEstado(sb);
        sb.append("\n───────────────────────────────────────────────────────────────────\n\n");
        generarCitasPorFecha(sb);
        sb.append("\n───────────────────────────────────────────────────────────────────\n\n");
        
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        sb.append("USUARIOS DEL SISTEMA\n\n");
        sb.append(String.format("Total de Usuarios: %d\n\n", usuarios.size()));
        
        int activos = 0;
        for (SistemaGestionCitas.Usuario u : usuarios) {
            if (u.estado.equals("Activo")) activos++;
        }
        sb.append(String.format("Usuarios Activos:   %d\n", activos));
        sb.append(String.format("Usuarios Inactivos: %d\n", usuarios.size() - activos));
    }
    
    private String generarBarra(int valor, int maxLength) {
        if (valor == 0) return "";
        int length = Math.min(valor, maxLength);
        return "█".repeat(length);
    }
    
    private void exportar() {
        String contenido = txtReporte.getText();
        
        if (contenido.contains("Seleccione")) {
            JOptionPane.showMessageDialog(this,
                "Primero genere un reporte",
                "Exportar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this,
            "Reporte exportado exitosamente\n\n" +
            "El reporte ha sido guardado en formato de texto.\n" +
            "En un sistema real, aquí se guardaría como archivo.",
            "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReportesGUI().setVisible(true));
    }
}
