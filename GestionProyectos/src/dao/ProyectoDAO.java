/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Proyecto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import util.ConexionDB;
import util.LogErrorDAO;


/**
 *
 * @author USER
 */
public class ProyectoDAO {
       public List<Proyecto> listar() throws SQLException {
        List<Proyecto> proyectos = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, fechaInicio, fechaFin,estado";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

             while (rs.next()) {
                Proyecto p = new Proyecto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setFechaInicio(rs.getDate("fecha_inicio"));
                p.setFechaFin(rs.getDate("fecha_fin"));
                p.setEstado(rs.getString("estado"));
                proyectos.add(p);
            }
        } catch (SQLException e) {
            LogErrorDAO.registrarError("ProyectoDAO", "listar", e.getMessage());
            throw e;
        }
        return proyectos;
    }

    // Insertar un nuevo proyecto
    public void insertar(Proyecto proyecto) throws SQLException {
        String sql = "INSERT INTO proyecto(nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            stmt.setDate(4, proyecto.getFechaFin() != null ? new java.sql.Date(proyecto.getFechaFin().getTime()) : null);
            stmt.setString(5, proyecto.getEstado());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("ProyectoDAO", "insertar", e.getMessage());
            throw e;
        }
    }

    // Actualizar un proyecto existente
    public void actualizar(Proyecto proyecto) throws SQLException {
        String sql = "UPDATE proyecto SET nombre=?, descripcion=?, fecha_inicio=?, fecha_fin=?, estado=? WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, proyecto.getNombre());
            stmt.setString(2, proyecto.getDescripcion());
            stmt.setDate(3, new java.sql.Date(proyecto.getFechaInicio().getTime()));
            stmt.setDate(4, proyecto.getFechaFin() != null ? new java.sql.Date(proyecto.getFechaFin().getTime()) : null);
            stmt.setString(5, proyecto.getEstado());
            stmt.setInt(6, proyecto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("ProyectoDAO", "actualizar", e.getMessage());
            throw e;
        }
    }

    // Eliminar un proyecto por ID
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proyecto WHERE id=?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LogErrorDAO.registrarError("ProyectoDAO", "eliminar", e.getMessage());
            throw e;
        }
    }

    // Buscar proyecto por ID
    public Proyecto buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, descripcion, fecha_inicio, fecha_fin, estado FROM proyecto WHERE id=?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Proyecto p = new Proyecto();
                    p.setId(rs.getInt("id"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setFechaInicio(rs.getDate("fecha_inicio"));
                    p.setFechaFin(rs.getDate("fecha_fin"));
                    p.setEstado(rs.getString("estado"));
                    return p;
                }
            }
        } catch (SQLException e) {
            LogErrorDAO.registrarError("ProyectoDAO", "buscarPorId", e.getMessage());
            throw e;
        }
        return null;
    }
}
