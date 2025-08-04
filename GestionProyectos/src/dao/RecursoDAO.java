/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Recursos;
import util.ConexionDB;
import util.LogErrorDAO;
/**
 *
 * @author USER
 */

/**
 * Clase DAO para gestionar recursos asociados a tareas.
 */
public class RecursoDAO {
    public List<Recursos> listarPorTarea(int idTarea) throws SQLException {
        List<Recursos> recursos = new ArrayList<>();
        String sql = "SELECT id, nombre_archivo, ruta, id_tarea, tipo, fecha_subida FROM recurso WHERE id_tarea = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idTarea);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Recursos r = new Recursos();
                    r.setId(rs.getInt("id"));
                    r.setNombreArchivo(rs.getString("nombre_archivo"));
                    r.setRuta(rs.getString("ruta"));
                    r.setIdTarea(rs.getInt("id_tarea"));
                    r.setTipo(rs.getString("tipo"));
                    r.setFechaSubida(rs.getDate("fecha_subida"));
                    recursos.add(r);
                }
            }
        } catch (SQLException e) {
            LogErrorDAO.registrarError("RecursoDAO", "listarPorTarea", e.getMessage());
            throw e;
        }
        return recursos;
    }

    public void insertar(Recursos recurso) throws SQLException {
        String sql = "INSERT INTO recurso(nombre_archivo, ruta, id_tarea, tipo, fecha_subida) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, recurso.getNombreArchivo());
            stmt.setString(2, recurso.getRuta());
            stmt.setInt(3, recurso.getIdTarea());
            stmt.setString(4, recurso.getTipo());
            stmt.setDate(5, new java.sql.Date(recurso.getFechaSubida().getTime()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("RecursoDAO", "insertar", e.getMessage());
            throw e;
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM recurso WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("RecursoDAO", "eliminar", e.getMessage());
            throw e;
        }
    }
}
