/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Usuario;
import util.ConexionDB; // ← Asegúrate de que la clase esté en el paquete 'util'

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Clase DAO para gestionar operaciones CRUD de Usuario en la base de datos.
 */
public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos.
     */
    public void guardar(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuario (nombre, email, rol) VALUES (?, ?, ?)";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getRol());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene un usuario por su ID.
     */
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null;
    }

    /**
     * Obtiene todos los usuarios de la base de datos.
     */
    public List<Usuario> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    /**
     * Mapea un ResultSet a un objeto Usuario.
     */
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRol(rs.getString("rol"));
        return usuario;
    }
}