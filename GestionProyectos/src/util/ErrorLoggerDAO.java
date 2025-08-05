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

public class ErrorLoggerDAO {

    /**
     * Guarda un nuevo error en la base de datos.
     */
    public void guardar(ErrorLogger error) throws SQLException {
        String sql = "INSERT INTO error_log (clase, metodo, mensaje) VALUES (?, ?, ?)";
        
        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, error.getClase());
            pstmt.setString(2, error.getMetodo());
            pstmt.setString(3, error.getMensaje());

            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    error.setId(rs.getInt(1));
                }
            }
        }
    }

    /**
     * Obtiene todos los errores de la base de datos.
     */
    public List<ErrorLogger> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM error_log ORDER BY fecha DESC";
        List<ErrorLogger> errores = new ArrayList<>();

        Connection conn = ConexionDB.conectar();
        if (conn == null) {
            throw new SQLException("No se pudo establecer conexión con la base de datos.");
        }

        try (conn;
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                errores.add(mapearError(rs));
            }
        }
        return errores;
    }

    /**
     * Mapea un ResultSet a un objeto ErrorLogger.
     */
    private ErrorLogger mapearError(ResultSet rs) throws SQLException {
        ErrorLogger error = new ErrorLogger();
        error.setId(rs.getInt("id"));
        error.setClase(rs.getString("clase"));
        error.setMetodo(rs.getString("metodo"));
        error.setMensaje(rs.getString("mensaje"));
        error.setFecha(rs.getObject("fecha", LocalDateTime.class));
        return error;
    }
}