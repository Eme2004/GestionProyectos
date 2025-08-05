/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Usuario;
import util.ConexionDB; 

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Emesis
 */


// Clase DAO que gestiona las operaciones CRUD de la entidad Usuario
public class UsuarioDAO {

    /**
     * Guarda un nuevo usuario en la base de datos.
     */
    public void guardar(Usuario usuario) throws SQLException {
        // Sentencia SQL para insertar un nuevo usuario
        String sql = "INSERT INTO usuarios (nombre, email, rol) VALUES (?, ?, ?)";

        // Se intenta establecer la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Se utiliza try-with-resources para cerrar la conexión y el statement automáticamente
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asigna los valores al statement
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getRol());

            // Ejecuta la inserción
            pstmt.executeUpdate();

            // Recupera el ID generado automáticamente y lo asigna al objeto Usuario
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene un usuario de la base de datos por su ID.
     */
    public Usuario obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Establece el parámetro del ID
            pstmt.setInt(1, id);

            // Ejecuta la consulta y mapea el resultado si se encuentra
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                }
            }
        }
        return null; // Si no se encuentra el usuario
    }

    /**
     * Obtiene todos los usuarios registrados en la base de datos.
     */
    public List<Usuario> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> usuarios = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Itera sobre cada fila del resultado y agrega el usuario mapeado a la lista
            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }
        }
        return usuarios;
    }

    /**
     * Actualiza un usuario existente.
     */
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, rol = ? WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asigna los nuevos valores del usuario
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getEmail());
            pstmt.setString(3, usuario.getRol());
            pstmt.setInt(4, usuario.getId());

            // Ejecuta la actualización
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el usuario con ID: " + usuario.getId());
            }
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            // Ejecuta la eliminación
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el usuario con ID: " + id);
            }
        }
    }

    /**
     * Convierte una fila del ResultSet en un objeto Usuario.
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