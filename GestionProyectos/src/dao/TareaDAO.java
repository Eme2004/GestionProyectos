/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Tarea;
import util.ConexionDB;
import util.LogErrorDAO;
/**
 *
 * @author USER
 */

/**
 * Clase DAO para gestionar operaciones CRUD de Tareas.
 */
public class TareaDAO {
     public List<Tarea> listar() throws SQLException {
        List<Tarea> tareas = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, id_proyecto, id_usuario_asignado, " +
                     "prioridad, estado, fecha_asignacion, fecha_vencimiento, progreso FROM tarea ORDER BY estado, prioridad";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Tarea t = new Tarea();
                t.setId(rs.getInt("id"));
                t.setNombre(rs.getString("nombre"));
                t.setDescripcion(rs.getString("descripcion"));
                t.setIdProyecto(rs.getInt("id_proyecto"));
                t.setIdUsuarioAsignado(rs.getObject("id_usuario_asignado") != null ? 
                                       rs.getInt("id_usuario_asignado") : null);
                t.setPrioridad(rs.getString("prioridad"));
                t.setEstado(rs.getString("estado"));
                t.setFechaAsignacion(rs.getDate("fecha_asignacion"));
                t.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                t.setProgreso(rs.getInt("progreso"));
                tareas.add(t);
            }
        } catch (SQLException e) {
            LogErrorDAO.registrarError("TareaDAO", "listar", e.getMessage());
            throw e;
        }
        return tareas;
    }

    public void insertar(Tarea tarea) throws SQLException {
        String sql = "INSERT INTO tarea(nombre, descripcion, id_proyecto, id_usuario_asignado, " +
                     "prioridad, estado, fecha_asignacion, fecha_vencimiento, progreso) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarea.getNombre());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setInt(3, tarea.getIdProyecto());
            stmt.setObject(4, tarea.getIdUsuarioAsignado(), Types.INTEGER);
            stmt.setString(5, tarea.getPrioridad());
            stmt.setString(6, tarea.getEstado());
            stmt.setDate(7, new java.sql.Date(tarea.getFechaAsignacion().getTime()));
            stmt.setDate(8, tarea.getFechaVencimiento() != null ? 
                         new java.sql.Date(tarea.getFechaVencimiento().getTime()) : null);
            stmt.setInt(9, tarea.getProgreso());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("TareaDAO", "insertar", e.getMessage());
            throw e;
        }
    }

    public void actualizar(Tarea tarea) throws SQLException {
        String sql = "UPDATE tarea SET nombre=?, descripcion=?, id_proyecto=?, id_usuario_asignado=?, " +
                     "prioridad=?, estado=?, fecha_asignacion=?, fecha_vencimiento=?, progreso=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tarea.getNombre());
            stmt.setString(2, tarea.getDescripcion());
            stmt.setInt(3, tarea.getIdProyecto());
            stmt.setObject(4, tarea.getIdUsuarioAsignado(), Types.INTEGER);
            stmt.setString(5, tarea.getPrioridad());
            stmt.setString(6, tarea.getEstado());
            stmt.setDate(7, new java.sql.Date(tarea.getFechaAsignacion().getTime()));
            stmt.setDate(8, tarea.getFechaVencimiento() != null ? 
                         new java.sql.Date(tarea.getFechaVencimiento().getTime()) : null);
            stmt.setInt(9, tarea.getProgreso());
            stmt.setInt(10, tarea.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("TareaDAO", "actualizar", e.getMessage());
            throw e;
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM tarea WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("TareaDAO", "eliminar", e.getMessage());
            throw e;
        }
    }
}
