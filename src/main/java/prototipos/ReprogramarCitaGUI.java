package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * HU-04: Reprogramar Cita - Interfaz Funcional INTEGRADA
 */
public class ReprogramarCitaGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextField txtCodigo;
    private JComboBox<String> cmbNuevaFecha, cmbNuevaHora;
    private JButton btnBuscar, btnReprogramar, btnCancelar;
    private JTextArea txtInfoActual;
    private SistemaGestionCitas.Cita citaActual = null;
    private JLabel lblMensaje;
    
    public ReprogramarCitaGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HU-04: Reprogramar Cita");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(250, 250, 255));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(255, 165, 0));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("Reprogramar Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createTitledBorder("Buscar Cita"));
        
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setFont(new Font("Arial", Font.BOLD, 12));
        panelBusqueda.add(lblCodigo);
        
        txtCodigo = new JTextField(15);
        panelBusqueda.add(txtCodigo);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(30, 144, 255));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(btnBuscar);
        
        // Info actual
        JPanel panelActual = new JPanel(new BorderLayout());
        panelActual.setBackground(Color.WHITE);
        panelActual.setBorder(BorderFactory.createTitledBorder("Cita Actual"));
        
        txtInfoActual = new JTextArea(5, 40);
        txtInfoActual.setFont(new Font("Arial", Font.PLAIN, 12));
        txtInfoActual.setEditable(false);
        txtInfoActual.setBackground(new Color(245, 245, 245));
        txtInfoActual.setText("Código: (busque una cita)\n");
        
        JScrollPane scroll = new JScrollPane(txtInfoActual);
        panelActual.add(scroll, BorderLayout.CENTER);
        
        // Reprogramación
        JPanel panelReprog = new JPanel(new GridBagLayout());
        panelReprog.setBackground(Color.WHITE);
        panelReprog.setBorder(BorderFactory.createTitledBorder("Nueva Fecha y Hora"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblFecha = new JLabel("Nueva Fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
        panelReprog.add(lblFecha, gbc);
        
        gbc.gridx = 1;
        cmbNuevaFecha = new JComboBox<>(generarFechas());
        cmbNuevaFecha.addActionListener(e -> actualizarHorarios());
        panelReprog.add(cmbNuevaFecha, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblHora = new JLabel("Nueva Hora:");
        lblHora.setFont(new Font("Arial", Font.BOLD, 12));
        panelReprog.add(lblHora, gbc);
        
        gbc.gridx = 1;
        cmbNuevaHora = new JComboBox<>();
        panelReprog.add(cmbNuevaHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JPanel panelAdv = new JPanel();
        panelAdv.setBackground(new Color(255, 255, 224));
        panelAdv.setBorder(BorderFactory.createLineBorder(new Color(255, 200, 0), 2));
        
        JLabel lblAdv = new JLabel("⚠ Se generará un nuevo código");
        lblAdv.setFont(new Font("Arial", Font.BOLD, 11));
        lblAdv.setForeground(new Color(204, 102, 0));
        panelAdv.add(lblAdv);
        
        panelReprog.add(panelAdv, gbc);
        
        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(new Color(250, 250, 255));
        
        btnReprogramar = new JButton("Confirmar Reprogramación");
        btnReprogramar.setFont(new Font("Arial", Font.BOLD, 14));
        btnReprogramar.setBackground(new Color(255, 165, 0));
        btnReprogramar.setForeground(Color.BLACK);
        btnReprogramar.setFocusPainted(false);
        btnReprogramar.setEnabled(false);
        btnReprogramar.addActionListener(e -> reprogramar());
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCancelar.setBackground(new Color(128, 128, 128));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> limpiar());
        
        panelBotones.add(btnReprogramar);
        panelBotones.add(btnCancelar);
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(250, 250, 255));
        
        panelBusqueda.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelActual.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelReprog.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelBusqueda);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelActual);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCentral.add(panelReprog);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(new Color(250, 250, 255));
        panelSur.add(panelBotones, BorderLayout.CENTER);
        panelSur.add(lblMensaje, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelSur, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        actualizarHorarios();
    }
    
    private String[] generarFechas() {
        String[] fechas = new String[7];
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            fechas[i] = hoy.plusDays(i + 1).format(fmt);
        }
        return fechas;
    }
    
    private void actualizarHorarios() {
        cmbNuevaHora.removeAllItems();
        String fecha = (String) cmbNuevaFecha.getSelectedItem();
        String[] horas = {"09:00", "10:00", "11:00", "12:00", "14:00", "15:00", "16:00", "17:00"};
        
        for (String hora : horas) {
            if (sistema.esHorarioDisponible(fecha, hora)) {
                cmbNuevaHora.addItem(hora);
            }
        }
    }
    
    private void buscar() {
        String codigo = txtCodigo.getText().trim();
        
        if (codigo.isEmpty()) {
            mostrarError("Ingrese un código");
            return;
        }
        
        System.out.println("Buscando código: " + codigo);
        sistema.mostrarEstado();
        citaActual = sistema.buscarCita(codigo);
        
        if (citaActual == null) {
            mostrarError("No se encontró la cita");
            btnReprogramar.setEnabled(false);
        } else if (citaActual.estado.equals("Cancelada")) {
            mostrarError("La cita está cancelada");
            btnReprogramar.setEnabled(false);
        } else {
            txtInfoActual.setText(
                "Código: " + citaActual.codigo + "\n" +
                "Paciente: " + citaActual.nombre + "\n" +
                "Email: " + citaActual.email + "\n" +
                "Fecha Actual: " + citaActual.fecha + "\n" +
                "Hora Actual: " + citaActual.hora + "\n" +
                "Estado: " + citaActual.estado
            );
            btnReprogramar.setEnabled(true);
            lblMensaje.setText(" ");
        }
    }
    
    private void reprogramar() {
        if (citaActual == null) return;
        
        String nuevaFecha = (String) cmbNuevaFecha.getSelectedItem();
        String nuevaHora = (String) cmbNuevaHora.getSelectedItem();
        
        if (nuevaHora == null) {
            mostrarError("No hay horarios disponibles");
            return;
        }
        
        String nuevoCodigo = sistema.reprogramarCita(citaActual.codigo, nuevaFecha, nuevaHora);
        
        if (nuevoCodigo != null) {
            JOptionPane.showMessageDialog(this,
                "¡Cita Reprogramada!\n\n" +
                "Código Anterior: " + citaActual.codigo + "\n" +
                "Nuevo Código: " + nuevoCodigo + "\n\n" +
                "Nueva Fecha: " + nuevaFecha + "\n" +
                "Nueva Hora: " + nuevaHora + "\n\n" +
                "Notificación enviada a: " + citaActual.email,
                "Reprogramación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            
            mostrarExito("Reprogramada - Nuevo código: " + nuevoCodigo);
            limpiar();
        } else {
            mostrarError("Error al reprogramar");
        }
    }
    
    private void limpiar() {
        txtCodigo.setText("");
        txtInfoActual.setText("Código: (busque una cita)\n");
        citaActual = null;
        btnReprogramar.setEnabled(false);
        lblMensaje.setText(" ");
        cmbNuevaFecha.setSelectedIndex(0);
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
        SwingUtilities.invokeLater(() -> new ReprogramarCitaGUI().setVisible(true));
    }
}