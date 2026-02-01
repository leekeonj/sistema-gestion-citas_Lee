package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * HU-08: Historial de Citas - Interfaz Funcional INTEGRADA
 */
public class HistorialCitasGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextField txtBuscar;
    private JComboBox<String> cmbFiltro;
    private JButton btnBuscar, btnTodas;
    private JTable tablaCitas;
    private DefaultTableModel modeloTabla;
    private JLabel lblMensaje;
    
    public HistorialCitasGUI() {
        initComponents();
        cargarTodasLasCitas();
    }
    
    private void initComponents() {
        setTitle("HU-08: Historial de Citas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 250, 245));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(60, 179, 113));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel(" Historial de Citas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JLabel lblBuscar = new JLabel("Buscar por Email:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        panelBusqueda.add(lblBuscar);
        
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 12));
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(30, 144, 255));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscarPorEmail());
        panelBusqueda.add(btnBuscar);
        
        btnTodas = new JButton("Ver Todas");
        btnTodas.setFont(new Font("Arial", Font.BOLD, 12));
        btnTodas.setBackground(new Color(128, 128, 128));
        btnTodas.setForeground(Color.BLACK);
        btnTodas.setFocusPainted(false);
        btnTodas.addActionListener(e -> cargarTodasLasCitas());
        panelBusqueda.add(btnTodas);
        
        JLabel lblFiltro = new JLabel("  |  Filtrar por:");
        lblFiltro.setFont(new Font("Arial", Font.BOLD, 12));
        panelBusqueda.add(lblFiltro);
        
        String[] filtros = {"Todas", "Pendiente", "Atendida", "Cancelada"};
        cmbFiltro = new JComboBox<>(filtros);
        cmbFiltro.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbFiltro.addActionListener(e -> filtrar());
        panelBusqueda.add(cmbFiltro);
        
        // Tabla
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        String[] columnas = {"Código", "Paciente", "Email", "Fecha", "Hora", "Estado", "Observaciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaCitas = new JTable(modeloTabla);
        tablaCitas.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaCitas.setRowHeight(28);
        tablaCitas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaCitas.getTableHeader().setBackground(new Color(60, 179, 113));
        tablaCitas.getTableHeader().setForeground(Color.WHITE);
        
        // Ancho de columnas
        tablaCitas.getColumnModel().getColumn(0).setPreferredWidth(90);
        tablaCitas.getColumnModel().getColumn(1).setPreferredWidth(130);
        tablaCitas.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablaCitas.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablaCitas.getColumnModel().getColumn(4).setPreferredWidth(60);
        tablaCitas.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaCitas.getColumnModel().getColumn(6).setPreferredWidth(150);
        
        JScrollPane scroll = new JScrollPane(tablaCitas);
        scroll.setPreferredSize(new Dimension(840, 380));
        panelTabla.add(scroll, BorderLayout.CENTER);
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(245, 250, 245));
        
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelBusqueda);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelTabla);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(lblMensaje, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarTodasLasCitas() {
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerTodasLasCitas();
        
        for (SistemaGestionCitas.Cita c : citas) {
            modeloTabla.addRow(new Object[]{
                c.codigo, c.nombre, c.email, c.fecha, c.hora, c.estado,
                c.observaciones.isEmpty() ? "-" : c.observaciones
            });
        }
        
        lblMensaje.setText("Total de citas: " + citas.size());
        lblMensaje.setForeground(new Color(60, 179, 113));
        cmbFiltro.setSelectedIndex(0);
        txtBuscar.setText("");
    }
    
    private void buscarPorEmail() {
        String email = txtBuscar.getText().trim();
        
        if (email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Ingrese un email para buscar",
                "Búsqueda", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerCitasPorEmail(email);
        
        if (citas.isEmpty()) {
            lblMensaje.setText("No se encontraron citas para: " + email);
            lblMensaje.setForeground(new Color(220, 20, 60));
        } else {
            for (SistemaGestionCitas.Cita c : citas) {
                modeloTabla.addRow(new Object[]{
                    c.codigo, c.nombre, c.email, c.fecha, c.hora, c.estado,
                    c.observaciones.isEmpty() ? "-" : c.observaciones
                });
            }
            lblMensaje.setText("Encontradas " + citas.size() + " citas");
            lblMensaje.setForeground(new Color(60, 179, 113));
        }
    }
    
    private void filtrar() {
        String filtro = (String) cmbFiltro.getSelectedItem();
        
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Cita> citas = sistema.obtenerTodasLasCitas();
        
        int count = 0;
        for (SistemaGestionCitas.Cita c : citas) {
            if (filtro.equals("Todas") || c.estado.equals(filtro)) {
                modeloTabla.addRow(new Object[]{
                    c.codigo, c.nombre, c.email, c.fecha, c.hora, c.estado,
                    c.observaciones.isEmpty() ? "-" : c.observaciones
                });
                count++;
            }
        }
        
        lblMensaje.setText("Citas con estado '" + filtro + "': " + count);
        lblMensaje.setForeground(new Color(60, 179, 113));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HistorialCitasGUI().setVisible(true));
    }
}
