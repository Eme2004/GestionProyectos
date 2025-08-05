/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Tarea;
import util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Emesis
 */

// Clase DAO para manejar operaciones CRUD de la entidad Tarea en la base de datos
public class TareaDAO {

    /**
     * Guarda una nueva tarea en la base de datos.
     */
    public void guardar(Tarea tarea) throws SQLException {
        String sql = "INSERT INTO tarea (nombre, descripcion, id_proyecto, id_usuario_asignado, prioridad, estado, fecha_asignacion, fecha_vencimiento, progreso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Se ejecuta la inserción usando try-with-resources
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, tarea.getNombre());
            pstmt.setString(2, tarea.getDescripcion());
            pstmt.setInt(3, tarea.getIdProyecto());

            // Si hay usuario asignado, se establece; si no, se asigna NULL
            if (tarea.getIdUsuarioAsignado() != null) {
                pstmt.setInt(4, tarea.getIdUsuarioAsignado());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }

            pstmt.setString(5, tarea.getPrioridad());
            pstmt.setString(6, tarea.getEstado());
            pstmt.setObject(7, tarea.getFechaAsignacion());

            // Fecha de vencimiento puede ser nula
            if (tarea.getFechaVencimiento() != null) {
                pstmt.setObject(8, tarea.getFechaVencimiento());
            } else {
                pstmt.setNull(8, Types.DATE);
            }

            pstmt.setInt(9, tarea.getProgreso());

            pstmt.executeUpdate();

            // Recupera el ID generado para la tarea
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tarea.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene una tarea por su ID.
     */
    public Tarea obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM tarea WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTarea(rs); // Devuelve la tarea mapeada desde el ResultSet
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todas las tareas asociadas a un proyecto específico.
     */
    public List<Tarea> obtenerPorProyecto(int idProyecto) throws SQLException {
        String sql = "SELECT * FROM tarea WHERE id_proyecto = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProyecto);

            try (ResultSet rs = pstmt.executeQuery()) {
                List<Tarea> tareas = new ArrayList<>();
                while (rs.next()) {
                    tareas.add(mapearTarea(rs)); // Mapea cada fila del resultado a un objeto Tarea
                }
                return tareas;
            }
        }
    }

    /**
     * Obtiene todas las tareas de la base de datos.
     */
    public List<Tarea> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM tarea";
        List<Tarea> tareas = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Tarea tarea = new Tarea();
                tarea.setId(rs.getInt("id"));
                tarea.setNombre(rs.getString("nombre"));
                tarea.setDescripcion(rs.getString("descripcion"));
                tarea.setIdProyecto(rs.getInt("id_proyecto"));

                // Puede ser null si no hay usuario asignado
                Integer idUsuario = rs.getObject("id_usuario_asignado", Integer.class);
                tarea.setIdUsuarioAsignado(idUsuario);

                tarea.setPrioridad(rs.getString("prioridad"));
                tarea.setEstado(rs.getString("estado"));
                tarea.setFechaAsignacion(rs.getObject("fecha_asignacion", LocalDate.class));
                tarea.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
                tarea.setProgreso(rs.getInt("progreso"));

                tareas.add(tarea);
            }
        }

        return tareas;
    }

    /**
     * Actualiza una tarea existente.
     */
    public void actualizar(Tarea tarea) throws SQLException {
        String sql = "UPDATE tarea SET nombre = ?, descripcion = ?, id_usuario_asignado = ?, prioridad = ?, estado = ?, fecha_vencimiento = ?, progreso = ? WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tarea.getNombre());
            pstmt.setString(2, tarea.getDescripcion());

            if (tarea.getIdUsuarioAsignado() != null) {
                pstmt.setInt(3, tarea.getIdUsuarioAsignado());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, tarea.getPrioridad());
            pstmt.setString(5, tarea.getEstado());

            if (tarea.getFechaVencimiento() != null) {
                pstmt.setObject(6, tarea.getFechaVencimiento());
            } else {
                pstmt.setNull(6, Types.DATE);
            }

            pstmt.setInt(7, tarea.getProgreso());
            pstmt.setInt(8, tarea.getId());

            pstmt.executeUpdate();
        }
    }

    /**
     * Elimina una tarea por su ID.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tarea WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    /**
     * Mapea un ResultSet a un objeto Tarea.
     */
    private Tarea mapearTarea(ResultSet rs) throws SQLException {
        Tarea tarea = new Tarea();
        tarea.setId(rs.getInt("id"));
        tarea.setNombre(rs.getString("nombre"));
        tarea.setDescripcion(rs.getString("descripcion"));
        tarea.setIdProyecto(rs.getInt("id_proyecto"));
        tarea.setIdUsuarioAsignado(rs.getObject("id_usuario_asignado", Integer.class));
        tarea.setPrioridad(rs.getString("prioridad"));
        tarea.setEstado(rs.getString("estado"));
        tarea.setFechaAsignacion(rs.getObject("fecha_asignacion", LocalDate.class));
        tarea.setFechaVencimiento(rs.getObject("fecha_vencimiento", LocalDate.class));
        tarea.setProgreso(rs.getInt("progreso"));
        return tarea;
    }
}