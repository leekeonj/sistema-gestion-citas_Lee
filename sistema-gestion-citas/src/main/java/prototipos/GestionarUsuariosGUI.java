package prototipos;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * HU-06: Gestionar Usuarios (Admin) - Interfaz Funcional INTEGRADA
 */
public class GestionarUsuariosGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextField txtNombre, txtEmail, txtTelefono, txtBuscar;
    private JComboBox<String> cmbRol, cmbEstado;
    private JButton btnAgregar, btnEditar, btnDesactivar, btnBuscar, btnLimpiar;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JLabel lblMensaje;
    private String idSeleccionado = null;
    
    public GestionarUsuariosGUI() {
        initComponents();
        cargarUsuarios();
    }
    
    private void initComponents() {
        setTitle("HU-06: Gestionar Usuarios - Admin");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 245, 250));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(139, 0, 0));
        panelTitulo.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("Administración de Usuarios");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Formulario
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Arial", Font.BOLD, 12));
        panelForm.add(lblNombre, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtNombre = new JTextField(25);
        panelForm.add(txtNombre, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 12));
        panelForm.add(lblEmail, gbc);
        
        gbc.gridx = 1; gbc.gridwidth = 2;
        txtEmail = new JTextField(25);
        panelForm.add(txtEmail, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setFont(new Font("Arial", Font.BOLD, 12));
        panelForm.add(lblTelefono, gbc);
        
        gbc.gridx = 1;
        txtTelefono = new JTextField(15);
        panelForm.add(txtTelefono, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblRol = new JLabel("Rol:");
        lblRol.setFont(new Font("Arial", Font.BOLD, 12));
        panelForm.add(lblRol, gbc);
        
        gbc.gridx = 1;
        String[] roles = {"Usuario", "Personal de Atención", "Administrador"};
        cmbRol = new JComboBox<>(roles);
        panelForm.add(cmbRol, gbc);
        
        gbc.gridx = 2;
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setFont(new Font("Arial", Font.BOLD, 12));
        panelForm.add(lblEstado, gbc);
        
        gbc.gridx = 3;
        String[] estados = {"Activo", "Inactivo"};
        cmbEstado = new JComboBox<>(estados);
        panelForm.add(cmbEstado, gbc);
        
        JPanel panelBotonesForm = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotonesForm.setBackground(Color.WHITE);
        
        btnAgregar = new JButton("Agregar");
        btnAgregar.setFont(new Font("Arial", Font.BOLD, 12));
        btnAgregar.setBackground(new Color(34, 139, 34));
        btnAgregar.setForeground(Color.WHITE);
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(e -> agregar());
        
        btnEditar = new JButton("Actualizar");
        btnEditar.setFont(new Font("Arial", Font.BOLD, 12));
        btnEditar.setBackground(new Color(255, 140, 0));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(e -> actualizar());
        
        btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpiar.setBackground(new Color(128, 128, 128));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(e -> limpiar());
        
        panelBotonesForm.add(btnAgregar);
        panelBotonesForm.add(btnEditar);
        panelBotonesForm.add(btnLimpiar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 4;
        panelForm.add(panelBotonesForm, gbc);
        
        // Tabla
        JPanel panelTablaCompleto = new JPanel(new BorderLayout(0, 10));
        panelTablaCompleto.setBackground(Color.WHITE);
        panelTablaCompleto.setBorder(BorderFactory.createTitledBorder("Usuarios Registrados"));
        
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.setBackground(Color.WHITE);
        
        JLabel lblBuscar = new JLabel("Buscar:");
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        panelBusqueda.add(lblBuscar);
        
        txtBuscar = new JTextField(20);
        panelBusqueda.add(txtBuscar);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 12));
        btnBuscar.setBackground(new Color(30, 144, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscar());
        panelBusqueda.add(btnBuscar);
        
        btnDesactivar = new JButton("Desactivar Seleccionado");
        btnDesactivar.setFont(new Font("Arial", Font.BOLD, 12));
        btnDesactivar.setBackground(new Color(220, 20, 60));
        btnDesactivar.setForeground(Color.WHITE);
        btnDesactivar.setFocusPainted(false);
        btnDesactivar.addActionListener(e -> desactivar());
        panelBusqueda.add(btnDesactivar);
        
        panelTablaCompleto.add(panelBusqueda, BorderLayout.NORTH);
        
        String[] columnas = {"ID", "Nombre", "Email", "Teléfono", "Rol", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.setFont(new Font("Arial", Font.PLAIN, 11));
        tablaUsuarios.setRowHeight(28);
        tablaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 11));
        tablaUsuarios.getTableHeader().setBackground(new Color(139, 0, 0));
        tablaUsuarios.getTableHeader().setForeground(Color.WHITE);
        
        tablaUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    cargarUsuarioSeleccionado();
                }
            }
        });
        
        JScrollPane scroll = new JScrollPane(tablaUsuarios);
        scroll.setPreferredSize(new Dimension(820, 200));
        panelTablaCompleto.add(scroll, BorderLayout.CENTER);
        
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(240, 245, 250));
        
        panelForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelTablaCompleto.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelCentral.add(panelForm);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentral.add(panelTablaCompleto);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(lblMensaje, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void cargarUsuarios() {
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        
        for (SistemaGestionCitas.Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{
                u.id, u.nombre, u.email, u.telefono, u.rol, u.estado
            });
        }
    }
    
    private void agregar() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rol = (String) cmbRol.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();
        
        if (nombre.isEmpty() || email.isEmpty() || telefono.isEmpty()) {
            mostrarError("Complete todos los campos");
            return;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarError("Email inválido");
            return;
        }
        
        SistemaGestionCitas.Usuario usuario = 
            new SistemaGestionCitas.Usuario(nombre, email, telefono, rol, estado);
        String id = sistema.agregarUsuario(usuario);
        
        cargarUsuarios();
        limpiar();
        mostrarExito("Usuario agregado - ID: " + id);
    }
    
    private void actualizar() {
        if (idSeleccionado == null) {
            mostrarError("Seleccione un usuario de la tabla");
            return;
        }
        
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String rol = (String) cmbRol.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();
        
        SistemaGestionCitas.Usuario usuario = 
            new SistemaGestionCitas.Usuario(nombre, email, telefono, rol, estado);
        
        if (sistema.actualizarUsuario(idSeleccionado, usuario)) {
            cargarUsuarios();
            limpiar();
            mostrarExito("Usuario actualizado");
        } else {
            mostrarError("Error al actualizar");
        }
    }
    
    private void desactivar() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila == -1) {
            mostrarError("Seleccione un usuario");
            return;
        }
        
        String id = (String) tablaUsuarios.getValueAt(fila, 0);
        int resp = JOptionPane.showConfirmDialog(this,
            "¿Desactivar el usuario seleccionado?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        
        if (resp == JOptionPane.YES_OPTION) {
            sistema.desactivarUsuario(id);
            cargarUsuarios();
            mostrarExito("Usuario desactivado");
        }
    }
    
    private void buscar() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            cargarUsuarios();
            return;
        }
        
        modeloTabla.setRowCount(0);
        List<SistemaGestionCitas.Usuario> usuarios = sistema.obtenerTodosLosUsuarios();
        
        for (SistemaGestionCitas.Usuario u : usuarios) {
            if (u.nombre.toLowerCase().contains(busqueda) ||
                u.email.toLowerCase().contains(busqueda)) {
                modeloTabla.addRow(new Object[]{
                    u.id, u.nombre, u.email, u.telefono, u.rol, u.estado
                });
            }
        }
    }
    
    private void cargarUsuarioSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila != -1) {
            idSeleccionado = (String) tablaUsuarios.getValueAt(fila, 0);
            txtNombre.setText((String) tablaUsuarios.getValueAt(fila, 1));
            txtEmail.setText((String) tablaUsuarios.getValueAt(fila, 2));
            txtTelefono.setText((String) tablaUsuarios.getValueAt(fila, 3));
            cmbRol.setSelectedItem(tablaUsuarios.getValueAt(fila, 4));
            cmbEstado.setSelectedItem(tablaUsuarios.getValueAt(fila, 5));
        }
    }
    
    private void limpiar() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        cmbRol.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        idSeleccionado = null;
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
        SwingUtilities.invokeLater(() -> new GestionarUsuariosGUI().setVisible(true));
    }
}