/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Proyecto;
import util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProyectoDAO {

    /**
     * Guarda un nuevo proyecto en la base de datos.
     */
    public void guardar(Proyecto proyecto) throws SQLException {
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setObject(3, proyecto.getFechaInicio());
            pstmt.setObject(4, proyecto.getFechaFin());
            pstmt.setString(5, proyecto.getEstado());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    proyecto.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene un proyecto por su ID.
     */
    public Proyecto obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM proyectos WHERE id = ?";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProyecto(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todos los proyectos de la base de datos.
     */
    public List<Proyecto> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM proyectos";
        List<Proyecto> proyectos = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                proyectos.add(mapearProyecto(rs));
            }
        }
        return proyectos;
    }

    /**
     * Actualiza un proyecto existente en la base de datos.
     */
    public void actualizar(Proyecto proyecto) throws SQLException {
        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setObject(3, proyecto.getFechaInicio());
            pstmt.setObject(4, proyecto.getFechaFin());
            pstmt.setString(5, proyecto.getEstado());
            pstmt.setInt(6, proyecto.getId());

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el proyecto con ID: " + proyecto.getId());
            }
        }
    }

    /**
     * Elimina un proyecto de la base de datos por su ID.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM proyectos WHERE id = ?";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el proyecto con ID: " + id);
            }
        }
    }

    /**
     * Mapea un ResultSet a un objeto Proyecto.
     */
    private Proyecto mapearProyecto(ResultSet rs) throws SQLException {
        Proyecto proyecto = new Proyecto();
        proyecto.setId(rs.getInt("id"));
        proyecto.setNombre(rs.getString("nombre"));
        proyecto.setDescripcion(rs.getString("descripcion"));
        proyecto.setFechaInicio(rs.getObject("fecha_inicio", LocalDate.class));
        proyecto.setFechaFin(rs.getObject("fecha_fin", LocalDate.class));
        proyecto.setEstado(rs.getString("estado"));
        return proyecto;
    }
}