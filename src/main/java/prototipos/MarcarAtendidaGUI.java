package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * HU-09: Marcar Cita como Atendida - Interfaz Funcional INTEGRADA
 */
public class MarcarAtendidaGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JComboBox<String> cmbFecha;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JButton btnMarcar, btnActualizar;
    private JTextArea txtObservaciones;
    private JLabel lblMensaje;
    
    public MarcarAtendidaGUI() {
        initComponents();
        cargarCitas();
    }
    
    private void initComponents() {
        setTitle("HU-09: Marcar Cita como Atendida");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 255, 240));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(0, 128, 128));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel(" Marcar Citas como Atendidas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltro.setBackground(Color.WHITE);
        panelFiltro.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblFecha = new JLabel("Filtrar por fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 13));
        panelFiltro.add(lblFecha);
        
        String[] fechas = generarFechas();
        cmbFecha = new JComboBox<>(fechas);
        cmbFecha.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbFecha.setPreferredSize(new Dimension(150, 28));
        cmbFecha.addActionListener(e -> cargarCitas());
        panelFiltro.add(cmbFecha);
        
        btnActualizar = new JButton(" Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(30, 144, 255));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> cargarCitas());
        panelFiltro.add(btnActualizar);
        
        // Tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createTitledBorder("Citas Pendientes"));
        
        String[] columnas = {"Código", "Paciente", "Hora", "Teléfono", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaCitas.setRowHeight(30);
        tablaCitas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaCitas.getTableHeader().setBackground(new Color(0, 128, 128));
        tablaCitas.getTableHeader().setForeground(Color.WHITE);
        
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(180);
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(80);
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setPreferredSize(new Dimension(740, 250));
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        // Panel observaciones
        JPanel panelObs = new JPanel(new BorderLayout(10, 10));
        panelObs.setBackground(Color.WHITE);
        panelObs.setBorder(BorderFactory.createTitledBorder("Observaciones de Atención"));
        
        txtObservaciones = new JTextArea(4, 50);
        txtObservaciones.setFont(new Font("Arial", Font.PLAIN, 12));
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        
        JScrollPane scrollObs = new JScrollPane(txtObservaciones);
        panelObs.add(scrollObs, BorderLayout.CENTER);
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel lblInfo = new JLabel("💡 Seleccione una cita y agregue observaciones opcionales");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(100, 100, 100));
        panelInfo.add(lblInfo);
        
        panelObs.add(panelInfo, BorderLayout.SOUTH);
        
        // Botón marcar
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBoton.setBackground(new Color(240, 255, 240));
        
        btnMarcar = new JButton("✓ Marcar como Atendida");
        btnMarcar.setFont(new Font("Arial", Font.BOLD, 16));
        btnMarcar.setBackground(new Color(34, 139, 34));
        btnMarcar.setForeground(Color.BLACK);
        btnMarcar.setFocusPainted(false);
        btnMarcar.setPreferredSize(new Dimension(250, 45));
        btnMarcar.addActionListener(e -> marcarAtendida());
        
        panelBoton.add(btnMarcar);
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(240, 255, 240));
        
        panelFiltro.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelFiltro);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelTabla);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelObs);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(240, 255, 240));
        panelSur.add(panelBoton, BorderLayout.CENTER);
        panelSur.add(lblMensaje, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private String[] generarFechas() {
        java.time.LocalDate hoy = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        String[] fechas = new String[8];
        fechas[0] = "Todas las fechas";
        for (int i = 0; i < 7; i++) {
            fechas[i + 1] = hoy.plusDays(i).format(fmt);
        }
        return fechas;
    }
    
    private void cargarCitas() {
        modeloTabla.setRowCount(0);
        String fechaFiltro = (String) cmbFecha.getSelectedItem();
        
        List<SistemaGestionCitas.Cita> citas;
        if (fechaFiltro.equals("Todas las fechas")) {
            citas = sistema.obtenerTodasLasCitas();
        } else {
            citas = sistema.obtenerCitasPorFecha(fechaFiltro);
        }
        
        int count = 0;
        for (SistemaGestionCitas.Cita c : citas) {
            if (c.estado.equals("Pendiente")) {
                modeloTabla.addRow(new Object[]{
                    c.codigo, c.nombre, c.hora, c.telefono, c.estado
                });
                count++;
            }
        }
        
        if (count == 0) {
            lblMensaje.setText("No hay citas pendientes para esta fecha");
            lblMensaje.setForeground(new Color(128, 128, 128));
        } else {
            lblMensaje.setText("Citas pendientes: " + count);
            lblMensaje.setForeground(new Color(0, 128, 128));
        }
    }
    
    private void marcarAtendida() {
        int fila = tablaCitas.getSelectedRow();
        
        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                "Seleccione una cita de la tabla",
                "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String codigo = (String) tablaCitas.getValueAt(fila, 0);
        String observaciones = txtObservaciones.getText().trim();
        
        int resp = JOptionPane.showConfirmDialog(this,
            "¿Marcar la cita " + codigo + " como atendida?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (resp == JOptionPane.YES_OPTION) {
            if (sistema.marcarCitaAtendida(codigo, observaciones)) {
                JOptionPane.showMessageDialog(this,
                    "Cita marcada como atendida exitosamente\n\n" +
                    "Código: " + codigo + "\n" +
                    "Observaciones: " + (observaciones.isEmpty() ? "Ninguna" : observaciones),
                    "Atención Completada", JOptionPane.INFORMATION_MESSAGE);
                
                txtObservaciones.setText("");
                cargarCitas();
                lblMensaje.setText("✓ Cita " + codigo + " marcada como atendida");
                lblMensaje.setForeground(new Color(34, 139, 34));
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error al marcar la cita",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MarcarAtendidaGUI().setVisible(true));
    }
}
