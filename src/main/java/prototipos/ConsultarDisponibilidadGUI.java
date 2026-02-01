package prototipos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * HU-02: Consultar Disponibilidad - Interfaz Funcional
 * Sistema de Gestión de Citas en Línea
 */
public class ConsultarDisponibilidadGUI extends JFrame {
    
    private JComboBox<String> cmbFecha;
    private JTable tablaHorarios;
    private DefaultTableModel modeloTabla;
    private JLabel lblInfoFecha;
    
    // Horarios disponibles y ocupados (simulación)
    private String[] horariosBase = {"09:00", "10:00", "11:00", "12:00", 
                                     "14:00", "15:00", "16:00", "17:00"};
    private Map<String, Set<String>> horariosOcupadosPorFecha = new HashMap<>();
    
    public ConsultarDisponibilidadGUI() {
        inicializarDatos();
        initComponents();
    }
    
    private void inicializarDatos() {
        // Simular horarios ocupados para diferentes fechas
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        horariosOcupadosPorFecha.put(hoy.plusDays(1).format(formatter), 
                                      new HashSet<>(Arrays.asList("10:00", "15:00")));
        horariosOcupadosPorFecha.put(hoy.plusDays(2).format(formatter), 
                                      new HashSet<>(Arrays.asList("09:00", "14:00", "16:00")));
        horariosOcupadosPorFecha.put(hoy.plusDays(3).format(formatter), 
                                      new HashSet<>(Arrays.asList("11:00", "12:00")));
    }
    
    private void initComponents() {
        setTitle("HU-02: Consultar Disponibilidad");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(245, 245, 250));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(30, 144, 255));
        panelTitulo.setBorder(new EmptyBorder(15, 10, 15, 10));
        
        JLabel lblTitulo = new JLabel("Consultar Disponibilidad de Citas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        // Panel de selección de fecha
        JPanel panelFecha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFecha.setBackground(Color.WHITE);
        panelFecha.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblFecha = new JLabel("Seleccione una fecha:");
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        panelFecha.add(lblFecha);
        
        cmbFecha = new JComboBox<>(generarFechasDisponibles());
        cmbFecha.setFont(new Font("Arial", Font.PLAIN, 13));
        cmbFecha.setPreferredSize(new Dimension(150, 30));
        cmbFecha.addActionListener(e -> actualizarDisponibilidad());
        panelFecha.add(cmbFecha);
        
        lblInfoFecha = new JLabel();
        lblInfoFecha.setFont(new Font("Arial", Font.ITALIC, 12));
        lblInfoFecha.setForeground(new Color(100, 100, 100));
        panelFecha.add(lblInfoFecha);
        
        // Panel de tabla de horarios
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Color.WHITE);
        panelTabla.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        String[] columnas = {"Horario", "Estado", "Disponibilidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaHorarios = new JTable(modeloTabla);
        tablaHorarios.setFont(new Font("Arial", Font.PLAIN, 13));
        tablaHorarios.setRowHeight(35);
        tablaHorarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tablaHorarios.getTableHeader().setBackground(new Color(30, 144, 255));
        tablaHorarios.getTableHeader().setForeground(Color.WHITE);
        tablaHorarios.setSelectionBackground(new Color(173, 216, 230));
        
        // Configurar ancho de columnas
        tablaHorarios.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaHorarios.getColumnModel().getColumn(1).setPreferredWidth(150);
        tablaHorarios.getColumnModel().getColumn(2).setPreferredWidth(250);
        
        JScrollPane scrollPane = new JScrollPane(tablaHorarios);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        // Panel de leyenda
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelLeyenda.setBackground(new Color(245, 245, 250));
        
        JLabel lblLeyendaDisponible = new JLabel("● Disponible");
        lblLeyendaDisponible.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLeyendaDisponible.setForeground(new Color(34, 139, 34));
        panelLeyenda.add(lblLeyendaDisponible);
        
        JLabel lblLeyendaOcupado = new JLabel("● Ocupado");
        lblLeyendaOcupado.setFont(new Font("Arial", Font.PLAIN, 12));
        lblLeyendaOcupado.setForeground(new Color(220, 20, 60));
        panelLeyenda.add(lblLeyendaOcupado);
        
        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar Disponibilidad");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 13));
        btnActualizar.setBackground(new Color(30, 144, 255));
        btnActualizar.setForeground(Color.BLACK);
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> actualizarDisponibilidad());
        panelLeyenda.add(btnActualizar);
        
        // Agregar componentes al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFecha, BorderLayout.CENTER);
        
        JPanel panelCentral = new JPanel(new BorderLayout(0, 10));
        panelCentral.setBackground(new Color(245, 245, 250));
        panelCentral.add(panelTabla, BorderLayout.CENTER);
        panelCentral.add(panelLeyenda, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelCentral, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        // Cargar datos iniciales
        actualizarDisponibilidad();
    }
    
    private String[] generarFechasDisponibles() {
        String[] fechas = new String[7];
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter diaSemana = DateTimeFormatter.ofPattern("EEEE", new Locale("es"));
        
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = hoy.plusDays(i + 1);
            String dia = fecha.format(diaSemana);
            dia = dia.substring(0, 1).toUpperCase() + dia.substring(1);
            fechas[i] = fecha.format(formatter) + " (" + dia + ")";
        }
        return fechas;
    }
    
    private void actualizarDisponibilidad() {
        modeloTabla.setRowCount(0);
        
        String fechaSeleccionada = (String) cmbFecha.getSelectedItem();
        String fecha = fechaSeleccionada.substring(0, 10); // Extraer solo la fecha
        
        Set<String> ocupados = horariosOcupadosPorFecha.getOrDefault(fecha, new HashSet<>());
        
        int disponibles = 0;
        int ocupadosCount = 0;
        
        for (String horario : horariosBase) {
            Object[] fila = new Object[3];
            fila[0] = horario;
            
            if (ocupados.contains(horario)) {
                fila[1] = "OCUPADO";
                fila[2] = "No disponible para agendar";
                ocupadosCount++;
            } else {
                fila[1] = "DISPONIBLE";
                fila[2] = "Puede agendar cita en este horario";
                disponibles++;
            }
            
            modeloTabla.addRow(fila);
        }
        
        // Colorear filas según disponibilidad
        tablaHorarios.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                String estado = (String) table.getValueAt(row, 1);
                if (estado.equals("DISPONIBLE")) {
                    c.setBackground(new Color(220, 255, 220));
                    if (column == 1) {
                        setForeground(new Color(0, 128, 0));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(Color.BLACK);
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                } else {
                    c.setBackground(new Color(255, 220, 220));
                    if (column == 1) {
                        setForeground(new Color(178, 34, 34));
                        setFont(getFont().deriveFont(Font.BOLD));
                    } else {
                        setForeground(Color.BLACK);
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                }
                
                if (isSelected) {
                    c.setBackground(new Color(173, 216, 230));
                }
                
                return c;
            }
        });
        
        lblInfoFecha.setText(String.format("  →  %d disponibles | %d ocupados", 
                                          disponibles, ocupadosCount));
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultarDisponibilidadGUI().setVisible(true);
        });
    }
}