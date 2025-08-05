/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import modelo.ErrorLogger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Emesis
 * Clase DAO (Data Access Object) para gestionar el registro y consulta de errores en la base de datos.
 * 
 * Esta clase se encarga de:
 * - Guardar nuevos errores (excepciones, advertencias, etc.) en la base de datos.
 * - Recuperar todos los errores registrados.
 * - Mapear los resultados de la base de datos a objetos Java (POJO).
 * 
 * Es utilizada por el sistema para mantener un historial de errores, útil para depuración y auditoría.
 */
public class ErrorLoggerDAO {

    /**
     * Guarda un nuevo registro de error en la tabla 'error_log' de la base de datos.
     * 
     * @param error El objeto ErrorLogger que contiene la información del error a guardar.
     * @throws SQLException Si ocurre un problema al conectarse o ejecutar la consulta en la base de datos.
     */
    public void guardar(ErrorLogger error) throws SQLException {
        // Consulta SQL para insertar un nuevo error
        // No se incluye 'id' ni 'fecha' porque 'id' es autoincremental y 'fecha' tiene un valor por defecto (CURRENT_TIMESTAMP)
        String sql = "INSERT INTO error_log (clase, metodo, mensaje) VALUES (?, ?, ?)";
        
        // Establece la conexión a la base de datos
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Uso de try-with-resources: cierra automáticamente PreparedStatement y ResultSet
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asigna los valores del objeto 'error' a los parámetros de la consulta
            pstmt.setString(1, error.getClase());     // Nombre de la clase donde ocurrió el error
            pstmt.setString(2, error.getMetodo());    // Nombre del método donde ocurrió el error
            pstmt.setString(3, error.getMensaje());   // Descripción del error (mensaje de excepción)

            // Ejecuta la inserción
            pstmt.executeUpdate();

            // Obtiene el ID generado automáticamente por la base de datos
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    // Asigna el ID generado al objeto 'error' para que el programador pueda usarlo
                    error.setId(rs.getInt(1));
                }
            }
        }
        // La conexión se cierra automáticamente gracias a try-with-resources
    }

    /**
     * Obtiene todos los errores registrados en la base de datos, ordenados desde el más reciente.
     * 
     * @return Una lista de objetos ErrorLogger con todos los errores encontrados.
     * @throws SQLException Si ocurre un problema al conectarse o ejecutar la consulta.
     */
    public List<ErrorLogger> obtenerTodos() throws SQLException {
        // Consulta SQL para seleccionar todos los errores
        // ORDER BY fecha DESC: muestra los errores más recientes primero
        String sql = "SELECT * FROM error_log ORDER BY fecha DESC";
        
        // Lista que almacenará los objetos ErrorLogger mapeados desde la base de datos
        List<ErrorLogger> errores = new ArrayList<>();

        // Establece la conexión
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        // Uso de try-with-resources: cierra PreparedStatement y ResultSet automáticamente
        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Recorre cada fila del resultado
            while (rs.next()) {
                // Convierte cada fila (registro) en un objeto ErrorLogger
                errores.add(mapearError(rs));
            }
        }
        // La conexión se cierra automáticamente
        return errores; // Devuelve la lista completa de errores
    }

    /**
     * Convierte una fila del ResultSet (resultado de una consulta SQL) en un objeto ErrorLogger.
     * 
     * Este método es un "mapeador" que extrae los datos de la base de datos y los asigna a un objeto Java.
     * 
     * @param rs El ResultSet posicionado en una fila válida.
     * @return Un objeto ErrorLogger con los datos de la fila actual.
     * @throws SQLException Si hay un problema al leer los datos del ResultSet.
     */
    private ErrorLogger mapearError(ResultSet rs) throws SQLException {
        // Crea un nuevo objeto ErrorLogger
        ErrorLogger error = new ErrorLogger();
        
        // Asigna cada columna de la fila a una propiedad del objeto
        error.setId(rs.getInt("id"));                     // ID del registro
        error.setClase(rs.getString("clase"));            // Clase donde ocurrió el error
        error.setMetodo(rs.getString("metodo"));          // Método donde ocurrió el error
        error.setMensaje(rs.getString("mensaje"));        // Mensaje del error
        error.setFecha(rs.getObject("fecha", LocalDateTime.class)); // Fecha y hora del error (PostgreSQL -> LocalDateTime)
        
        return error; // Devuelve el objeto completo
    }
}