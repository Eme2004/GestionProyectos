/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Recurso;
import util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {

    public void guardar(Recurso recurso) throws SQLException {
        String sql = "INSERT INTO recurso (nombre_archivo, ruta, id_tarea, tipo, fecha_subida) VALUES (?, ?, ?, ?, ?)";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, recurso.getNombreArchivo());
            pstmt.setString(2, recurso.getRuta());
            if (recurso.getIdTarea() != null) {
                pstmt.setInt(3, recurso.getIdTarea());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }
            pstmt.setString(4, recurso.getTipo());
            pstmt.setObject(5, recurso.getFechaSubida());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    recurso.setId(rs.getInt(1));
                }
            }
        }
    }

    public Recurso obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM recurso WHERE id = ?";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecurso(rs);
                }
            }
        }
        return null;
    }

    public List<Recurso> obtenerPorTarea(int idTarea) throws SQLException {
        String sql = "SELECT * FROM recurso WHERE id_tarea = ?";
        List<Recurso> recursos = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTarea);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recursos.add(mapearRecurso(rs));
                }
            }
        }
        return recursos;
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM recurso WHERE id = ?";
        
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

    private Recurso mapearRecurso(ResultSet rs) throws SQLException {
        Recurso recurso = new Recurso();
        recurso.setId(rs.getInt("id"));
        recurso.setNombreArchivo(rs.getString("nombre_archivo"));
        recurso.setRuta(rs.getString("ruta"));
        recurso.setIdTarea(rs.getObject("id_tarea", Integer.class));
        recurso.setTipo(rs.getString("tipo"));
        recurso.setFechaSubida(rs.getObject("fecha_subida", LocalDate.class));
        return recurso;
    }
     public List<Recurso> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM recurso";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            List<Recurso> recursos = new ArrayList<>();
            while (rs.next()) {
                recursos.add(mapearRecurso(rs));
            }
            return recursos;
        }
    }
     
}