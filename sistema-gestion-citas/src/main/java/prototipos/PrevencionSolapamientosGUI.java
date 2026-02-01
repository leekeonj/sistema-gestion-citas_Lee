package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * HU-10: Prevención de Solapamientos - Interfaz Funcional INTEGRADA
 */
public class PrevencionSolapamientosGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JComboBox<String> cmbFecha, cmbHora;
    private JTable tablaConflictos;
    private DefaultTableModel modeloTabla;
    private JLabel lblEstado, lblSugerencias;
    private JButton btnVerificar, btnSugerencias;
    
    public PrevencionSolapamientosGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HU-10: Prevención de Solapamientos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(255, 250, 240));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(255, 140, 0));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("⚠ Validador de Disponibilidad");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel verificación
        JPanel panelVerif = new JPanel(new GridBagLayout());
        panelVerif.setBackground(Color.WHITE);
        panelVerif.setBorder(BorderFactory.createTitledBorder("Verificar Disponibilidad"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 13));
        panelVerif.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        cmbFecha = new JComboBox<>(generarFechas());
        cmbFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbFecha.setPreferredSize(new Dimension(150, 30));
        panelVerif.add(cmbFecha, gbc);
        
        gbc.gridx = 2;
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Arial", Font.BOLD, 13));
        panelVerif.add(lblHora, gbc);
        
        gbc.gridx = 3;
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        cmbHora = new JComboBox<>(horas);
        cmbHora.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbHora.setPreferredSize(new Dimension(100, 30));
        panelVerif.add(cmbHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 4;
        btnVerificar = new JButton(" Verificar Disponibilidad");
        btnVerificar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVerificar.setBackground(new Color(30, 144, 255));
        btnVerificar.setForeground(Color.BLACK);
        btnVerificar.setFocusPainted(false);
        btnVerificar.setPreferredSize(new Dimension(250, 40));
        btnVerificar.addActionListener(e -> verificar());
        panelVerif.add(btnVerificar, gbc);
        
        // Panel estado
        JPanel panelEstado = new JPanel();
        panelEstado.setLayout(new BoxLayout(panelEstado, BoxLayout.Y_AXIS));
        panelEstado.setBackground(Color.WHITE);
        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado de Disponibilidad"));
        
        lblEstado = new JLabel("Seleccione fecha y hora para verificar");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        panelEstado.add(Box.createRigidArea(new Dimension(0, 20)));
        panelEstado.add(lblEstado);
        panelEstado.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Tabla conflictos
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createTitledBorder("Citas en la Fecha Seleccionada"));
        
        String[] columnas = {"Código", "Hora", "Paciente", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaConflictos = new JTable(modeloTabla);
        tablaConflictos.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaConflictos.setRowHeight(28);
        tablaConflictos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaConflictos.getTableHeader().setBackground(new Color(255, 140, 0));
        tablaConflictos.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scroll = new JScrollPane(tablaConflictos);
        scroll.setPreferredSize(new Dimension(640, 180));
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        // Panel sugerencias
        JPanel panelSug = new JPanel(new BorderLayout(10, 10));
        panelSug.setBackground(new Color(255, 255, 224));
        panelSug.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 200, 0), 2),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitSug = new JLabel("💡 Horarios Alternativos");
        lblTitSug.setFont(new Font("Arial", Font.BOLD, 13));
        panelSug.add(lblTitSug, BorderLayout.NORTH);
        
        lblSugerencias = new JLabel("<html>Seleccione una fecha y hora para ver sugerencias</html>");
        lblSugerencias.setFont(new Font("Arial", Font.PLAIN, 12));
        panelSug.add(lblSugerencias, BorderLayout.CENTER);
        
        btnSugerencias = new JButton("Ver Todas las Disponibles");
        btnSugerencias.setFont(new Font("Arial", Font.BOLD, 12));
        btnSugerencias.setBackground(new Color(255, 165, 0));
        btnSugerencias.setForeground(Color.BLACK);
        btnSugerencias.setFocusPainted(false);
        btnSugerencias.addActionListener(e -> mostrarSugerencias());
        panelSug.add(btnSugerencias, BorderLayout.SOUTH);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(255, 250, 240));
        
        panelVerif.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSug.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelVerif);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelEstado);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelTabla);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelSug);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        add(panelPrincipal);
    }
    
    private String[] generarFechas() {
        String[] fechas = new String[7];
        java.time.LocalDate hoy = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            fechas[i] = hoy.plusDays(i + 1).format(fmt);
        }
        return fechas;
    }
    
    private void verificar() {
        String fecha = (String) cmbFecha.getSelectedItem();
        String hora = (String) cmbHora.getSelectedItem();
        
        boolean disponible = sistema.esHorarioDisponible(fecha, hora);
        
        if (disponible) {
            lblEstado.setText("✓ DISPONIBLE - Puede agendar cita en " + fecha + " a las " + hora);
            lblEstado.setForeground(new Color(34, 139, 34));
        } else {
            lblEstado.setText("✗ OCUPADO - El horario " + fecha + " a las " + hora + " NO está disponible");
            lblEstado.setForeground(new Color(220, 20, 60));
        }
        
        cargarCitasFecha(fecha);
        sugerirAlternativas(fecha, hora);
    }
    
    private void cargarCitasFecha(String fecha) {
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerCitasPorFecha(fecha);
        
        for (SistemaGestionCitas.Cita c : citas) {
            if (!c.estado.equals("Cancelada")) {
                modeloTabla.addRow(new Object[]{
                    c.codigo, c.hora, c.nombre, c.estado
                });
            }
        }
    }
    
    private void sugerirAlternativas(String fecha, String horaOcupada) {
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        List<String> alternativas = new ArrayList<>();
        
        for (String hora : horas) {
            if (!hora.equals(horaOcupada) && sistema.esHorarioDisponible(fecha, hora)) {
                alternativas.add(hora);
            }
        }
        
        if (alternativas.isEmpty()) {
            lblSugerencias.setText("<html><b>No hay horarios alternativos disponibles para esta fecha</b><br>" +
                                  "Pruebe con otra fecha</html>");
        } else {
            lblSugerencias.setText("<html><b>Horarios disponibles en " + fecha + ":</b><br>" +
                                  String.join(", ", alternativas) + "</html>");
        }
    }
    
    private void mostrarSugerencias() {
        String fecha = (String) cmbFecha.getSelectedItem();
        StringBuilder sb = new StringBuilder();
        sb.append("Horarios Disponibles para ").append(fecha).append(":\n\n");
        
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        int count = 0;
        
        for (String hora : horas) {
            if (sistema.esHorarioDisponible(fecha, hora)) {
                sb.append("✓ ").append(hora).append(" - DISPONIBLE\n");
                count++;
            } else {
                sb.append("✗ ").append(hora).append(" - Ocupado\n");
            }
        }
        
        sb.append("\nTotal disponibles: ").append(count);
        
        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
            "Disponibilidad Completa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrevencionSolapamientosGUI().setVisible(true));
    }
}
