package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * HU-03: Cancelar Cita - Interfaz Funcional INTEGRADA
 */
public class CancelarCitaGUI extends JFrame {
    
    private SistemaGestionCitas sistema = SistemaGestionCitas.getInstancia();
    private JTextField txtCodigo;
    private JButton btnBuscar, btnCancelar, btnNueva;
    private JPanel panelResultado;
    private JLabel lblMensaje;
    private SistemaGestionCitas.Cita citaActual = null;
    
    public CancelarCitaGUI() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("HU-03: Cancelar Cita");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, 600);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(248, 248, 255));
        
        // Título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(220, 20, 60));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("Cancelar Cita");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BoxLayout(panelBusqueda, BoxLayout.Y_AXIS));
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblInstrucciones = new JLabel("Ingrese el código de su cita:");
        lblInstrucciones.setFont(new Font("Arial", Font.BOLD, 14));
        lblInstrucciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelBusqueda.add(lblInstrucciones);
        
        panelBusqueda.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JPanel panelCodigo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCodigo.setBackground(Color.WHITE);
        
        txtCodigo = new JTextField(15);
        txtCodigo.setFont(new Font("Arial", Font.PLAIN, 13));
        panelCodigo.add(txtCodigo);
        
        btnBuscar = new JButton("Buscar");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 13));
        btnBuscar.setBackground(new Color(30, 144, 255));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.setFocusPainted(false);
        btnBuscar.addActionListener(e -> buscar());
        panelCodigo.add(btnBuscar);
        
        panelBusqueda.add(panelCodigo);
        
        // Panel resultado
        panelResultado = new JPanel();
        panelResultado.setLayout(new BoxLayout(panelResultado, BoxLayout.Y_AXIS));
        panelResultado.setBackground(Color.WHITE);
        panelResultado.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        panelResultado.setVisible(false);
        
        // Mensaje
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 12));
        lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setBackground(new Color(248, 248, 255));
        panelCentral.add(panelBusqueda);
        panelCentral.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCentral.add(panelResultado);
        
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelPrincipal.add(lblMensaje, BorderLayout.SOUTH);
        
        add(panelPrincipal);
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
            mostrarError("No se encontró la cita: " + codigo);
            panelResultado.setVisible(false);
        } else if (citaActual.estado.equals("Cancelada")) {
            mostrarError("Esta cita ya fue cancelada");
            panelResultado.setVisible(false);
        } else {
            mostrarCita();
            lblMensaje.setText(" ");
        }
        revalidate();
        repaint();
    }
    
    private void mostrarCita() {
        panelResultado.removeAll();
        
        JLabel lblTit = new JLabel("Cita Encontrada");
        lblTit.setFont(new Font("Arial", Font.BOLD, 16));
        lblTit.setForeground(new Color(30, 144, 255));
        lblTit.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelResultado.add(lblTit);
        panelResultado.add(Box.createRigidArea(new Dimension(0, 15)));
        
        agregarInfo("Código:", citaActual.codigo);
        agregarInfo("Paciente:", citaActual.nombre);
        agregarInfo("Email:", citaActual.email);
        agregarInfo("Teléfono:", citaActual.telefono);
        agregarInfo("Fecha:", citaActual.fecha);
        agregarInfo("Hora:", citaActual.hora);
        agregarInfo("Estado:", citaActual.estado);
        
        panelResultado.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel panelAdv = new JPanel();
        panelAdv.setBackground(new Color(255, 245, 230));
        panelAdv.setBorder(BorderFactory.createLineBorder(new Color(255, 165, 0), 2));
        
        JLabel lblAdv = new JLabel("⚠ ¿Confirma la cancelación?");
        lblAdv.setFont(new Font("Arial", Font.BOLD, 12));
        lblAdv.setForeground(new Color(255, 140, 0));
        panelAdv.add(lblAdv);
        
        panelResultado.add(panelAdv);
        panelResultado.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(Color.WHITE);
        
        btnCancelar = new JButton("Confirmar Cancelación");
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 13));
        btnCancelar.setBackground(new Color(220, 20, 60));
        btnCancelar.setForeground(Color.BLACK);
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(e -> confirmarCancelacion());
        
        btnNueva = new JButton("Nueva Búsqueda");
        btnNueva.setFont(new Font("Arial", Font.BOLD, 13));
        btnNueva.setBackground(new Color(128, 128, 128));
        btnNueva.setForeground(Color.BLACK);
        btnNueva.setFocusPainted(false);
        btnNueva.addActionListener(e -> nueva());
        
        panelBotones.add(btnCancelar);
        panelBotones.add(btnNueva);
        
        panelResultado.add(panelBotones);
        panelResultado.setVisible(true);
    }
    
    private void agregarInfo(String etiqueta, String valor) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBackground(Color.WHITE);
        
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setPreferredSize(new Dimension(80, 20));
        p.add(lbl);
        
        JLabel val = new JLabel(valor);
        val.setFont(new Font("Arial", Font.PLAIN, 13));
        p.add(val);
        
        panelResultado.add(p);
    }
    
    private void confirmarCancelacion() {
        int resp = JOptionPane.showConfirmDialog(this,
            "¿Confirma cancelar la cita " + citaActual.codigo + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (resp == JOptionPane.YES_OPTION) {
            sistema.cancelarCita(citaActual.codigo);
            
            JOptionPane.showMessageDialog(this,
                "Cita cancelada exitosamente\n\n" +
                "Código: " + citaActual.codigo + "\n" +
                "Notificación enviada a: " + citaActual.email,
                "Cancelada", JOptionPane.INFORMATION_MESSAGE);
            
            mostrarExito("Cita cancelada - " + citaActual.codigo);
            nueva();
        }
    }
    
    private void nueva() {
        txtCodigo.setText("");
        panelResultado.setVisible(false);
        citaActual = null;
        lblMensaje.setText(" ");
        revalidate();
        repaint();
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
        SwingUtilities.invokeLater(() -> new CancelarCitaGUI().setVisible(true));
    }
}