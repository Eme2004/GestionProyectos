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
/**
 *
 * @author Emesis
 */

// Clase DAO (Data Access Object) para realizar operaciones CRUD sobre la tabla "proyectos"
public class ProyectoDAO {

    /**
     * Guarda un nuevo proyecto en la base de datos.
     */
    public void guardar(Proyecto proyecto) throws SQLException {
        // Consulta SQL para insertar un nuevo proyecto
        String sql = "INSERT INTO proyectos (nombre, descripcion, fecha_inicio, fecha_fin, estado) VALUES (?, ?, ?, ?, ?)";

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Utiliza try-with-resources para asegurar el cierre automático de la conexión y el PreparedStatement
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asigna los valores al PreparedStatement usando los datos del objeto Proyecto
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setObject(3, proyecto.getFechaInicio());
            pstmt.setObject(4, proyecto.getFechaFin());
            pstmt.setString(5, proyecto.getEstado());

            // Ejecuta la consulta
            pstmt.executeUpdate();

            // Recupera el ID generado automáticamente por la base de datos
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    proyecto.setId(rs.getInt(1)); // Asigna el ID al objeto Proyecto
                }
            }
        }
    }

    /**
     * Obtiene un proyecto por su ID.
     */
    public Proyecto obtenerPorId(int id) throws SQLException {
        // Consulta SQL para seleccionar un proyecto por su ID
        String sql = "SELECT * FROM proyectos WHERE id = ?";

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta con el ID proporcionado
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // Asigna el ID al parámetro de la consulta

            // Ejecuta la consulta y procesa los resultados
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapearProyecto(rs); // Devuelve el objeto Proyecto mapeado desde el ResultSet
                }
            }
        }

        // Si no se encuentra el proyecto, se retorna null
        return null;
    }

    /**
     * Obtiene todos los proyectos de la base de datos.
     */
    public List<Proyecto> obtenerTodos() throws SQLException {
        // Consulta SQL para obtener todos los proyectos
        String sql = "SELECT * FROM proyectos";
        List<Proyecto> proyectos = new ArrayList<>();

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta y recorre todos los resultados
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                proyectos.add(mapearProyecto(rs)); // Agrega cada proyecto a la lista
            }
        }

        // Devuelve la lista de todos los proyectos
        return proyectos;
    }

    /**
     * Actualiza un proyecto existente en la base de datos.
     */
    public void actualizar(Proyecto proyecto) throws SQLException {
        // Consulta SQL para actualizar un proyecto por su ID
        String sql = "UPDATE proyectos SET nombre = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, estado = ? WHERE id = ?";

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta de actualización
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Asigna los nuevos valores a los parámetros
            pstmt.setString(1, proyecto.getNombre());
            pstmt.setString(2, proyecto.getDescripcion());
            pstmt.setObject(3, proyecto.getFechaInicio());
            pstmt.setObject(4, proyecto.getFechaFin());
            pstmt.setString(5, proyecto.getEstado());
            pstmt.setInt(6, proyecto.getId());

            // Ejecuta la actualización y valida si se afectó alguna fila
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
        // Consulta SQL para eliminar un proyecto por ID
        String sql = "DELETE FROM proyectos WHERE id = ?";

        // Establece la conexión con la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Ejecuta la consulta de eliminación
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id); // Asigna el ID al parámetro
            int filasAfectadas = pstmt.executeUpdate();

            // Si no se eliminó ninguna fila, el proyecto no existe
            if (filasAfectadas == 0) {
                throw new SQLException("No se encontró el proyecto con ID: " + id);
            }
        }
    }

    /**
     * Mapea un ResultSet a un objeto Proyecto.
     * Este método extrae los datos de una fila del ResultSet y los asigna a un nuevo objeto Proyecto.
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