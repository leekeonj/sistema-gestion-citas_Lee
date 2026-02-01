package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * HU-01: Agendar Cita - Interfaz Funcional INTEGRADA
 */
public class AgendarCitaGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextField txtNombre, txtEmail, txtTelefono;
    private JComboBox<String> cmbFecha, cmbHora;
    private JButton btnAgendar, btnLimpiar;
    private JLabel lblMensaje;
    
    public AgendarCitaGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HU-01: Agendar Cita");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 248, 255));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(70, 130, 180));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("Agendar Nueva Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);
        
        // Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre Completo:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblNombre, gbc);
        
        gbc.gridx = 1;
        txtNombre = new JTextField(20);
        panelFormulario.add(txtNombre, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblEmail, gbc);
        
        gbc.gridx = 1;
        txtEmail = new JTextField(20);
        panelFormulario.add(txtEmail, gbc);
        
        // Teléfono
        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblTelefono, gbc);
        
        gbc.gridx = 1;
        txtTelefono = new JTextField(20);
        panelFormulario.add(txtTelefono, gbc);
        
        // Fecha
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblFecha = new JLabel("Fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        cmbFecha = new JComboBox<>(generarFechasDisponibles());
        cmbFecha.addActionListener(e -> actualizarHorarios());
        panelFormulario.add(cmbFecha, gbc);
        
        // Hora
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblHora = new JLabel("Hora:");
        lblHora.setFont(new Font("Arial", Font.BOLD, 12));
        panelFormulario.add(lblHora, gbc);
        
        gbc.gridx = 1;
        cmbHora = new JComboBox<>();
        panelFormulario.add(cmbHora, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(Color.WHITE);
        
        btnAgendar = new JButton("Agendar Cita");
        btnAgendar.setFont(new Font("Arial", Font.BOLD, 14));
        btnAgendar.setBackground(new Color(34, 139, 34));
        btnAgendar.setForeground(Color.BLACK);
        btnAgendar.setFocusPainted(false);
        btnAgendar.addActionListener(e -> agendarCita());
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 14));
        btnLimpiar.setBackground(new Color(220, 220, 220));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelBotones.add(btnAgendar);
        panelBotones.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);
        
        // Mensaje
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(lblMensaje, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        actualizarHorarios();
    }
    
    private String[] generarFechasDisponibles() {
        String[] fechas = new String[7];
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            fechas[i] = hoy.plusDays(i + 1).format(fmt);
        }
        return fechas;
    }
    
    private void actualizarHorarios() {
        cmbHora.removeAllItems();
        String fecha = (String) cmbFecha.getSelectedItem();
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        
        for (String hora : horas) {
            if (sistema.esHorarioDisponible(fecha, hora)) {
                cmbHora.addItem(hora);
            } else {
                cmbHora.addItem(hora + " (Ocupado)");
            }
        }
    }
    
    private void agendarCita() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String fecha = (String) cmbFecha.getSelectedItem();
        String hora = (String) cmbHora.getSelectedItem();
        
        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarError("Email inválido");
            return;
        }
        
        if (hora.contains("(Ocupado)")) {
            mostrarError("Horario no disponible");
            return;
        }
        
        String codigo = sistema.agendarCita(nombre, email, telefono, fecha, hora);
        
        // Verificar que se guardó
        sistema.mostrarEstado();
        
        JOptionPane.showMessageDialog(this,
            "¡Cita Agendada Exitosamente!\n\n" +
            "Código: " + codigo + "\n" +
            "Fecha: " + fecha + "\n" +
            "Hora: " + hora + "\n" +
            "Paciente: " + nombre + "\n\n" +
            "⚠️ IMPORTANTE: Anote este código para futuras consultas\n" +
            "Confirmación enviada a: " + email,
            "Cita Confirmada", JOptionPane.INFORMATION_MESSAGE);
        
        mostrarExito("Cita agendada correctamente - Código: " + codigo);
        limpiar();
        actualizarHorarios();
    }
    
    private void limpiar() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cmbFecha.setSelectedIndex(0);
        lblMensaje.setText(" ");
    }
    
    private void mostrarError(String msg) {
        lblMensaje.setText("❌ " + msg);
        lblMensaje.setForeground(new Color(220, 20, 60));
    }
    
    private void mostrarExito(String msg) {
        lblMensaje.setText("✓ " + msg);
        lblMensaje.setForeground(new Color(34, 139, 34));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AgendarCitaGUI().setVisible(true));
    }
}