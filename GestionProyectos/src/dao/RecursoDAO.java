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
/**
 *
 * @author Emesis
 */

// Clase DAO para manejar operaciones CRUD de la entidad Recurso en la base de datos
public class RecursoDAO {

    /**
     * Guarda un nuevo recurso en la base de datos.
     */
    public void guardar(Recurso recurso) throws SQLException {
        // Consulta SQL para insertar un nuevo recurso
        String sql = "INSERT INTO recurso (nombre_archivo, ruta, id_tarea, tipo, fecha_subida) VALUES (?, ?, ?, ?, ?)";

        // Se establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Se usa try-with-resources para manejar automáticamente el cierre de recursos
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Se establecen los parámetros del PreparedStatement con los datos del objeto Recurso
            pstmt.setString(1, recurso.getNombreArchivo());
            pstmt.setString(2, recurso.getRuta());

            // Si el recurso está asociado a una tarea, se asigna el ID; si no, se asigna null
            if (recurso.getIdTarea() != null) {
                pstmt.setInt(3, recurso.getIdTarea());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, recurso.getTipo());
            pstmt.setObject(5, recurso.getFechaSubida());

            // Ejecuta la inserción
            pstmt.executeUpdate();

            // Obtiene la clave primaria generada (ID del recurso) y la asigna al objeto Recurso
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    recurso.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene un recurso por su ID.
     */
    public Recurso obtenerPorId(int id) throws SQLException {
        // Consulta SQL para seleccionar un recurso por ID
        String sql = "SELECT * FROM recurso WHERE id = ?";

        // Conexión a la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta con el ID como parámetro
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearRecurso(rs); // Devuelve el recurso si existe
                }
            }
        }
        return null; // Si no se encuentra, retorna null
    }

    /**
     * Obtiene todos los recursos asociados a una tarea.
     */
    public List<Recurso> obtenerPorTarea(int idTarea) throws SQLException {
        // Consulta SQL para obtener todos los recursos vinculados a una tarea específica
        String sql = "SELECT * FROM recurso WHERE id_tarea = ?";
        List<Recurso> recursos = new ArrayList<>();

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta con el ID de la tarea como parámetro
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTarea);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    recursos.add(mapearRecurso(rs)); // Agrega cada recurso encontrado a la lista
                }
            }
        }
        return recursos; // Devuelve la lista de recursos encontrados
    }

    /**
     * Obtiene todos los recursos de la base de datos.
     */
    public List<Recurso> obtenerTodos() throws SQLException {
        // Consulta SQL para obtener todos los recursos
        String sql = "SELECT * FROM recurso";

        // Conexión a la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta y construye la lista de recursos
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

    /**
     * Actualiza un recurso existente en la base de datos.
     */
    public void actualizar(Recurso recurso) throws SQLException {
        // Consulta SQL para actualizar un recurso existente
        String sql = "UPDATE recurso SET nombre_archivo = ?, ruta = ?, id_tarea = ?, tipo = ?, fecha_subida = ? WHERE id = ?";

        // Establece la conexión
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la actualización de los datos
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recurso.getNombreArchivo());
            pstmt.setString(2, recurso.getRuta());

            // Si tiene tarea asignada, se establece; si no, se pasa como null
            if (recurso.getIdTarea() != null) {
                pstmt.setInt(3, recurso.getIdTarea());
            } else {
                pstmt.setNull(3, Types.INTEGER);
            }

            pstmt.setString(4, recurso.getTipo());
            pstmt.setObject(5, recurso.getFechaSubida());
            pstmt.setInt(6, recurso.getId());

            // Ejecuta la consulta y verifica si se afectó alguna fila
            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el recurso con ID: " + recurso.getId());
            }
        }
    }

    /**
     * Elimina un recurso de la base de datos por su ID.
     */
    public void eliminar(int id) throws SQLException {
        // Consulta SQL para eliminar un recurso por ID
        String sql = "DELETE FROM recurso WHERE id = ?";

        // Conexión a la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la eliminación
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // Establece el ID como parámetro
            pstmt.executeUpdate(); // Ejecuta la eliminación
        }
    }

    /**
     * Mapea un ResultSet a un objeto Recurso.
     * Este método convierte una fila del ResultSet en una instancia de Recurso.
     */
    private Recurso mapearRecurso(ResultSet rs) throws SQLException {
        Recurso recurso = new Recurso();
        recurso.setId(rs.getInt("id"));
        recurso.setNombreArchivo(rs.getString("nombre_archivo"));
        recurso.setRuta(rs.getString("ruta"));
        recurso.setIdTarea(rs.getObject("id_tarea", Integer.class)); // Puede ser null
        recurso.setTipo(rs.getString("tipo"));
        recurso.setFechaSubida(rs.getObject("fecha_subida", LocalDate.class));
        return recurso;
    }
}