package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * HU-05: Gestionar Horarios (Personal) - Interfaz de Diseño
 * Sistema de Gestión de Citas en Línea
 */
public class GestionarHorariosGUI extends JFrame {
    
    private JComboBox<String> cmbDia, cmbHoraInicio, cmbHoraFin;
    private JButton btnAgregar, btnEliminar, btnGuardar;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private JLabel lblPersonal, lblMensaje;
    
    public GestionarHorariosGUI() {
        initComponents();
        cargarDatosEjemplo();
    }
    
    private void initComponents() {
        setTitle("HU-05: Gestionar Horarios - Personal de Atención");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 250, 255));
        
        // Panel de título
        JPanel panelTitulo = new JPanel(new BorderLayout());
        panelTitulo.setBackground(new Color(106, 90, 205));
        panelTitulo.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Gestión de Horarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        
        lblPersonal = new JLabel("Personal: Dr. Juan Pérez");
        lblPersonal.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPersonal.setForeground(Color.WHITE);
        
        JPanel panelTituloTexto = new JPanel(new BorderLayout());
        panelTituloTexto.setBackground(new Color(106, 90, 205));
        panelTituloTexto.add(lblTitulo, BorderLayout.CENTER);
        panelTituloTexto.add(lblPersonal, BorderLayout.SOUTH);
        
        panelTitulo.add(panelTituloTexto, BorderLayout.WEST);
        
        // Panel de agregar horario
        JPanel panelAgregar = new JPanel(new GridBagLayout());
        panelAgregar.setBackground(Color.WHITE);
        panelAgregar.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            "Agregar Nuevo Horario Disponible",
            0, 0, new Font("Arial", Font.BOLD, 13)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Día de la semana
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblDia = new JLabel("Día:");
        lblDia.setFont(new Font("Arial", Font.BOLD, 12));
        panelAgregar.add(lblDia, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
        cmbDia = new JComboBox<>(dias);
        cmbDia.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbDia.setPreferredSize(new Dimension(150, 28));
        panelAgregar.add(cmbDia, gbc);
        
        // Hora inicio
        gbc.gridx = 2; gbc.gridy = 0;
        JLabel lblHoraInicio = new JLabel("Hora Inicio:");
        lblHoraInicio.setFont(new Font("Arial", Font.BOLD, 12));
        panelAgregar.add(lblHoraInicio, gbc);
        
        gbc.gridx = 3; gbc.gridy = 0;
        String[] horas = {"08:00", "09:00", "10:00", "11:00", "12:00", 
                         "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"};
        cmbHoraInicio = new JComboBox<>(horas);
        cmbHoraInicio.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbHoraInicio.setPreferredSize(new Dimension(100, 28));
        panelAgregar.add(cmbHoraInicio, gbc);
        
        // Hora fin
        gbc.gridx = 4; gbc.gridy = 0;
        JLabel lblHoraFin = new JLabel("Hora Fin:");
        lblHoraFin.setFont(new Font("Arial", Font.BOLD, 12));
        panelAgregar.add(lblHoraFin, gbc);
        
        gbc.gridx = 5; gbc.gridy = 0;
        cmbHoraFin = new JComboBox<>(horas);
        cmbHoraFin.setFont(new Font("Arial", Font.PLAIN, 12));
        cmbHoraFin.setPreferredSize(new Dimension(100, 28));
        cmbHoraFin.setSelectedIndex(4);
        panelAgregar.add(cmbHoraFin, gbc);
        
        // Botón agregar
        gbc.gridx = 6; gbc.gridy = 0;
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setBackground(new Color(34, 139, 34));
        btnAgregar.setForeground(Color.BLACK);
        btnAgregar.setFocusPainted(false);
        btnAgregar.setPreferredSize(new Dimension(100, 30));
        panelAgregar.add(btnAgregar, gbc);
        
        // Panel de tabla de horarios
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            "Horarios Configurados",
            0, 0, new Font("Arial", Font.BOLD, 13)
        ));
        
        String[] columnas = {"ID", "Día", "Hora Inicio", "Hora Fin", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaHorarios = new JTable(modeloTabla);
        tablaHorarios.setFont(new Font("Arial", Font.PLAIN, 12));
        tablaHorarios.setRowHeight(30);
        tablaHorarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tablaHorarios.getTableHeader().setBackground(new Color(106, 90, 205));
        tablaHorarios.getTableHeader().setForeground(Color.WHITE);
        tablaHorarios.setSelectionBackground(new Color(200, 191, 231));
        
        // Configurar ancho de columnas
        tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaHorarios.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaHorarios.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablaHorarios.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaHorarios.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(tablaHorarios);
        scrollPane.setPreferredSize(new Dimension(680, 250));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de información
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setBackground(Color.WHITE);
        
        JLabel lblInfo = new JLabel("💡 Seleccione un horario de la tabla para eliminarlo");
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        lblInfo.setForeground(new Color(100, 100, 100));
        panelInfo.add(lblInfo);
        
        panelTabla.add(panelInfo, BorderLayout.SOUTH);
        
        // Panel de botones de acción
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(new Color(245, 250, 255));
        
        btnEliminar = new JButton("Eliminar Seleccionado");
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 13));
        btnEliminar.setBackground(new Color(220, 20, 60));
        btnEliminar.setForeground(Color.BLACK);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new Dimension(180, 35));
        
        btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.setFont(new Font("Arial", Font.BOLD, 13));
        btnGuardar.setBackground(new Color(30, 144, 255));
        btnGuardar.setForeground(Color.BLACK);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new Dimension(150, 35));
        
        panelBotones.add(btnEliminar);
        panelBotones.add(btnGuardar);
        
        // Panel de mensaje
        JPanel panelMensaje = new JPanel();
        panelMensaje.setBackground(new Color(245, 250, 255));
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        panelMensaje.add(lblMensaje);
        
        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(245, 250, 255));
        
        panelAgregar.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelAgregar);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentral.add(panelTabla);
        panelCentral.add(panelBotones);
        
        // Agregar todo al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(panelMensaje, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarDatosEjemplo() {
        // Agregar algunos horarios de ejemplo
        modeloTabla.addRow(new Object[]{"001", "Lunes", "09:00", "12:00", "Activo"});
        modeloTabla.addRow(new Object[]{"002", "Lunes", "14:00", "17:00", "Activo"});
        modeloTabla.addRow(new Object[]{"003", "Martes", "09:00", "13:00", "Activo"});
        modeloTabla.addRow(new Object[]{"004", "Miércoles", "10:00", "16:00", "Activo"});
        modeloTabla.addRow(new Object[]{"005", "Jueves", "08:00", "12:00", "Bloqueado"});
        modeloTabla.addRow(new Object[]{"006", "Viernes", "14:00", "18:00", "Activo"});
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GestionarHorariosGUI().setVisible(true);
        });
    }
}