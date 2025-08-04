/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui;
import util.ErrorLoggerDAO;
import dao.*;
import modelo.*;
import util.ConexionDB; // Asegúrate de tener esta clase
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InterfazGUI extends JFrame {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ProyectoDAO proyectoDAO = new ProyectoDAO();
    private final TareaDAO tareaDAO = new TareaDAO();
    private final RecursoDAO recursoDAO = new RecursoDAO();
    private final ErrorLoggerDAO errorDAO = new ErrorLoggerDAO();

    private final JTabbedPane tabbedPane = new JTabbedPane();

    // ================== COMPONENTES USUARIOS ==================
    private final JTextField txtUsuarioNombre = new JTextField(20);
    private final JTextField txtUsuarioEmail = new JTextField(20);
    private final JTextField txtUsuarioRol = new JTextField(20);
    private final JTable tableUsuarios = new JTable();
    private final DefaultTableModel modelUsuarios = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Email", "Rol"}, 0);

    // ================== COMPONENTES PROYECTOS ==================
    private final JTextField txtProyectoNombre = new JTextField(20);
    private final JTextArea txtProyectoDescripcion = new JTextArea(3, 20);
    private final JTextField txtProyectoFechaInicio = new JTextField(10);
    private final JTextField txtProyectoFechaFin = new JTextField(10);
    private final JTextField txtProyectoEstado = new JTextField(10);
    private final JTable tableProyectos = new JTable();
    private final DefaultTableModel modelProyectos = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Descripción", "Inicio", "Fin", "Estado"}, 0);

    // ================== COMPONENTES TAREAS ==================
    private final JTextField txtTareaNombre = new JTextField(20);
    private final JTextArea txtTareaDescripcion = new JTextArea(3, 20);
    private final JComboBox<Integer> comboTareaProyecto = new JComboBox<>();
    private final JComboBox<Integer> comboTareaUsuario = new JComboBox<>();
    private final JTextField txtTareaPrioridad = new JTextField(10);
    private final JTextField txtTareaEstado = new JTextField(10);
    private final JTextField txtTareaFechaVencimiento = new JTextField(10);
    private final JTextField txtTareaProgreso = new JTextField(10);
    private final JTable tableTareas = new JTable();
    private final DefaultTableModel modelTareas = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Proyecto", "Usuario", "Prioridad", "Estado", "Progreso"}, 0);

    // ================== COMPONENTES RECURSOS ==================
    private final JTextField txtRecursoNombre = new JTextField(20);
    private final JTextField txtRecursoRuta = new JTextField(20);
    private final JComboBox<Integer> comboRecursoTarea = new JComboBox<>();
    private final JTextField txtRecursoTipo = new JTextField(10);
    private final JTable tableRecursos = new JTable();
    private final DefaultTableModel modelRecursos = new DefaultTableModel(
            new Object[]{"ID", "Nombre", "Ruta", "Tarea", "Tipo"}, 0);

    // ================== COMPONENTES ERRORES ==================
    private final JTable tableErrores = new JTable();
    private final DefaultTableModel modelErrores = new DefaultTableModel(
            new Object[]{"ID", "Clase", "Método", "Mensaje", "Fecha"}, 0);

    public InterfazGUI() {
        setTitle("📊 Sistema de Gestión de Proyectos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800); // Tamaño grande
        setMinimumSize(new Dimension(1000, 600)); // Evita que se achique
        setLocationRelativeTo(null);

        // Crear pestañas
        crearPestanaUsuarios();
        crearPestanaProyectos();
        crearPestanaTareas();
        crearPestanaRecursos();
        crearPestanaErrores();

        add(tabbedPane);
        setVisible(true);

        // Cargar datos iniciales
        refrescarUsuarios();
        refrescarProyectos();
        refrescarTareas();
        refrescarRecursos();
        refrescarErrores();
    }

    // ================== PESTAÑA USUARIOS ==================
    private void crearPestanaUsuarios() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int y = 0;
        panel.add(new JLabel("Nombre:"), gbc(0, y));
        panel.add(txtUsuarioNombre, gbc(1, y++));

        panel.add(new JLabel("Email:"), gbc(0, y));
        panel.add(txtUsuarioEmail, gbc(1, y++));

        panel.add(new JLabel("Rol:"), gbc(0, y));
        panel.add(txtUsuarioRol, gbc(1, y++));

        JButton btnGuardar = new JButton("Guardar Usuario");
        panel.add(btnGuardar, gbc(0, y, 2, 1)); y++;

        // Tabla - Ocupa todo el espacio vertical restante
        JScrollPane scroll = new JScrollPane(tableUsuarios);
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // ← CLAVE: permite expansión vertical
        panel.add(scroll, gbc);
        y++;

        JButton btnRefrescar = new JButton("Refrescar");
        gbc.gridy = y; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnRefrescar, gbc);

        tableUsuarios.setModel(modelUsuarios);
        tabbedPane.addTab("Usuarios", panel);

        btnGuardar.addActionListener(e -> guardarUsuario());
        btnRefrescar.addActionListener(e -> refrescarUsuarios());
    }

    private void guardarUsuario() {
        try {
            Usuario u = new Usuario(
                txtUsuarioNombre.getText(),
                txtUsuarioEmail.getText(),
                txtUsuarioRol.getText()
            );
            usuarioDAO.guardar(u);
            JOptionPane.showMessageDialog(this, "✅ Usuario guardado con ID: " + u.getId());
            refrescarUsuarios();
            limpiarCamposUsuario();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarUsuarios() {
        modelUsuarios.setRowCount(0);
        try {
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            for (Usuario u : usuarios) {
                modelUsuarios.addRow(new Object[]{u.getId(), u.getNombre(), u.getEmail(), u.getRol()});
            }
            actualizarCombosUsuarios(usuarios);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron cargar usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposUsuario() {
        txtUsuarioNombre.setText("");
        txtUsuarioEmail.setText("");
        txtUsuarioRol.setText("");
    }

    // ================== PESTAÑA PROYECTOS ==================
    private void crearPestanaProyectos() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int y = 0;
        panel.add(new JLabel("Nombre:"), gbc(0, y));
        panel.add(txtProyectoNombre, gbc(1, y++));

        panel.add(new JLabel("Descripción:"), gbc(0, y));
        panel.add(new JScrollPane(txtProyectoDescripcion), gbc(1, y++));

        panel.add(new JLabel("Inicio (AAAA-MM-DD):"), gbc(0, y));
        panel.add(txtProyectoFechaInicio, gbc(1, y++));

        panel.add(new JLabel("Fin (AAAA-MM-DD):"), gbc(0, y));
        panel.add(txtProyectoFechaFin, gbc(1, y++));

        panel.add(new JLabel("Estado:"), gbc(0, y));
        panel.add(txtProyectoEstado, gbc(1, y++));

        JButton btnGuardar = new JButton("Guardar Proyecto");
        panel.add(btnGuardar, gbc(0, y, 2, 1)); y++;

        // Tabla
        JScrollPane scroll = new JScrollPane(tableProyectos);
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scroll, gbc);
        y++;

        JButton btnRefrescar = new JButton("Refrescar");
        gbc.gridy = y; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnRefrescar, gbc);

        tableProyectos.setModel(modelProyectos);
        tabbedPane.addTab("Proyectos", panel);

        btnGuardar.addActionListener(e -> guardarProyecto());
        btnRefrescar.addActionListener(e -> refrescarProyectos());
    }

    private void guardarProyecto() {
        try {
            Proyecto p = new Proyecto();
            p.setNombre(txtProyectoNombre.getText());
            p.setDescripcion(txtProyectoDescripcion.getText());
            p.setFechaInicio(LocalDate.parse(txtProyectoFechaInicio.getText()));
            p.setFechaFin(LocalDate.parse(txtProyectoFechaFin.getText()));
            p.setEstado(txtProyectoEstado.getText());

            proyectoDAO.guardar(p);
            JOptionPane.showMessageDialog(this, "✅ Proyecto guardado con ID: " + p.getId());
            refrescarProyectos();
            limpiarCamposProyecto();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarProyectos() {
        modelProyectos.setRowCount(0);
        try {
            List<Proyecto> proyectos = proyectoDAO.obtenerTodos();
            for (Proyecto p : proyectos) {
                modelProyectos.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getDescripcion(),
                    p.getFechaInicio(), p.getFechaFin(), p.getEstado()
                });
            }
            actualizarCombosProyectos(proyectos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron cargar proyectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposProyecto() {
        txtProyectoNombre.setText("");
        txtProyectoDescripcion.setText("");
        txtProyectoFechaInicio.setText("");
        txtProyectoFechaFin.setText("");
        txtProyectoEstado.setText("");
    }

    // ================== PESTAÑA TAREAS ==================
    private void crearPestanaTareas() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int y = 0;
        panel.add(new JLabel("Nombre:"), gbc(0, y));
        panel.add(txtTareaNombre, gbc(1, y++));

        panel.add(new JLabel("Descripción:"), gbc(0, y));
        panel.add(new JScrollPane(txtTareaDescripcion), gbc(1, y++));

        panel.add(new JLabel("Proyecto:"), gbc(0, y));
        panel.add(comboTareaProyecto, gbc(1, y++));

        panel.add(new JLabel("Usuario Asignado:"), gbc(0, y));
        panel.add(comboTareaUsuario, gbc(1, y++));

        panel.add(new JLabel("Prioridad:"), gbc(0, y));
        panel.add(txtTareaPrioridad, gbc(1, y++));

        panel.add(new JLabel("Estado:"), gbc(0, y));
        panel.add(txtTareaEstado, gbc(1, y++));

        panel.add(new JLabel("Fecha Vencimiento:"), gbc(0, y));
        panel.add(txtTareaFechaVencimiento, gbc(1, y++));

        panel.add(new JLabel("Progreso (%):"), gbc(0, y));
        panel.add(txtTareaProgreso, gbc(1, y++));

        JButton btnGuardar = new JButton("Guardar Tarea");
        panel.add(btnGuardar, gbc(0, y, 2, 1)); y++;

        // Tabla de Tareas - Ocupa todo el espacio disponible
        JScrollPane scroll = new JScrollPane(tableTareas);
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0; // ← Esto es lo que hace que se expanda
        panel.add(scroll, gbc);
        y++;

        JButton btnRefrescar = new JButton("Refrescar");
        gbc.gridy = y; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnRefrescar, gbc);

        tableTareas.setModel(modelTareas);
        tabbedPane.addTab("Tareas", panel);

        btnGuardar.addActionListener(e -> guardarTarea());
        btnRefrescar.addActionListener(e -> refrescarTareas());
    }

    private void guardarTarea() {
    try {
        Tarea tarea = new Tarea();
        tarea.setNombre(txtTareaNombre.getText());
        tarea.setDescripcion(txtTareaDescripcion.getText());
        tarea.setIdProyecto((Integer) comboTareaProyecto.getSelectedItem());
        tarea.setIdUsuarioAsignado((Integer) comboTareaUsuario.getSelectedItem());
        tarea.setPrioridad(txtTareaPrioridad.getText());
        tarea.setEstado(txtTareaEstado.getText());

        // Validar y parsear la fecha
        String fechaStr = txtTareaFechaVencimiento.getText().trim();
        if (!fechaStr.isEmpty()) {
            try {
                // Parsear la fecha en formato YYYY-MM-DD
                tarea.setFechaVencimiento(LocalDate.parse(fechaStr));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Fecha inválida. Formato requerido: AAAA-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detener la operación si la fecha es inválida
            }
        }

        tarea.setProgreso(Integer.parseInt(txtTareaProgreso.getText()));

        tareaDAO.guardar(tarea);
        JOptionPane.showMessageDialog(this, "✅ Tarea guardada con ID: " + tarea.getId());
        refrescarTareas();
        limpiarCamposTarea();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    private void refrescarTareas() {
        modelTareas.setRowCount(0);
        try {
            List<Tarea> tareas = tareaDAO.obtenerTodos();
            for (Tarea t : tareas) {
                modelTareas.addRow(new Object[]{
                    t.getId(), t.getNombre(), t.getIdProyecto(),
                    t.getIdUsuarioAsignado(), t.getPrioridad(), t.getEstado(), t.getProgreso()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron cargar tareas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposTarea() {
        txtTareaNombre.setText("");
        txtTareaDescripcion.setText("");
        txtTareaPrioridad.setText("");
        txtTareaEstado.setText("");
        txtTareaFechaVencimiento.setText("");
        txtTareaProgreso.setText("");
    }

    // ================== PESTAÑA RECURSOS ==================
    private void crearPestanaRecursos() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        int y = 0;
        panel.add(new JLabel("Nombre Archivo:"), gbc(0, y));
        panel.add(txtRecursoNombre, gbc(1, y++));

        panel.add(new JLabel("Ruta:"), gbc(0, y));
        panel.add(txtRecursoRuta, gbc(1, y++));

        panel.add(new JLabel("Tarea:"), gbc(0, y));
        panel.add(comboRecursoTarea, gbc(1, y++));

        panel.add(new JLabel("Tipo:"), gbc(0, y));
        panel.add(txtRecursoTipo, gbc(1, y++));

        JButton btnGuardar = new JButton("Subir Recurso");
        panel.add(btnGuardar, gbc(0, y, 2, 1)); y++;

        // Tabla
        JScrollPane scroll = new JScrollPane(tableRecursos);
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scroll, gbc);
        y++;

        JButton btnRefrescar = new JButton("Refrescar");
        gbc.gridy = y; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnRefrescar, gbc);

        tableRecursos.setModel(modelRecursos);
        tabbedPane.addTab("Recursos", panel);

        btnGuardar.addActionListener(e -> subirRecurso());
        btnRefrescar.addActionListener(e -> refrescarRecursos());
    }

    private void subirRecurso() {
        try {
            Recurso recurso = new Recurso();
            recurso.setNombreArchivo(txtRecursoNombre.getText());
            recurso.setRuta(txtRecursoRuta.getText());
            recurso.setIdTarea((Integer) comboRecursoTarea.getSelectedItem());
            recurso.setTipo(txtRecursoTipo.getText());

            recursoDAO.guardar(recurso);
            JOptionPane.showMessageDialog(this, "✅ Recurso subido con ID: " + recurso.getId());
            refrescarRecursos();
            limpiarCamposRecurso();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refrescarRecursos() {
        modelRecursos.setRowCount(0);
        try {
            List<Recurso> recursos = recursoDAO.obtenerTodos();
            for (Recurso r : recursos) {
                modelRecursos.addRow(new Object[]{
                    r.getId(), r.getNombreArchivo(), r.getRuta(), r.getIdTarea(), r.getTipo()
                });
            }
            actualizarCombosTareas(recursos);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron cargar recursos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposRecurso() {
        txtRecursoNombre.setText("");
        txtRecursoRuta.setText("");
        txtRecursoTipo.setText("");
    }

    // ================== PESTAÑA ERRORES ==================
    private void crearPestanaErrores() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scroll = new JScrollPane(tableErrores);
        panel.add(scroll, BorderLayout.CENTER);

        JButton btnRefrescar = new JButton("Refrescar");
        panel.add(btnRefrescar, BorderLayout.SOUTH);

        tableErrores.setModel(modelErrores);
        tabbedPane.addTab("Errores", panel);

        btnRefrescar.addActionListener(e -> refrescarErrores());
    }

    private void refrescarErrores() {
        modelErrores.setRowCount(0);
        try {
            List<ErrorLogger> errores = errorDAO.obtenerTodos();
            for (ErrorLogger e : errores) {
                modelErrores.addRow(new Object[]{
                    e.getId(), e.getClase(), e.getMetodo(), e.getMensaje(), e.getFecha()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ No se pudieron cargar errores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ================== MÉTODOS AUXILIARES ==================
    private GridBagConstraints gbc(int x, int y) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        return c;
    }

    private GridBagConstraints gbc(int x, int y, int w, int h) {
        GridBagConstraints c = gbc(x, y);
        c.gridwidth = w;
        c.gridheight = h;
        return c;
    }

    // Métodos para actualizar combos
    private void actualizarCombosUsuarios(List<Usuario> usuarios) {
        comboTareaUsuario.removeAllItems();
        for (Usuario u : usuarios) {
            comboTareaUsuario.addItem(u.getId());
        }
    }

    private void actualizarCombosProyectos(List<Proyecto> proyectos) {
        comboTareaProyecto.removeAllItems();
        for (Proyecto p : proyectos) {
            comboTareaProyecto.addItem(p.getId());
        }
    }

    private void actualizarCombosTareas(List<Recurso> recursos) {
        comboRecursoTarea.removeAllItems();
        for (Recurso r : recursos) {
            if (r.getIdTarea() != null) {
                comboRecursoTarea.addItem(r.getIdTarea());
            }
        }
    }

    // ================== EJECUTAR ==================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new InterfazGUI();
        });
    }
}