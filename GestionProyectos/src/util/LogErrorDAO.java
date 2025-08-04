/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author USER
 */

/**
 * Clase para registrar errores en la base de datos.
 * Se usa en bloques catch para guardar detalles del error.
 */
public class LogErrorDAO {
    /**
     * Registra un error en la tabla error_log de la base de datos.
     *
     * @param clase    Nombre de la clase donde ocurrió el error (ej: "UsuarioDAO")
     * @param metodo   Nombre del método (ej: "listar")
     * @param mensaje  Mensaje de la excepción
     */
    public static void registrarError(String clase, String metodo, String mensaje) {
        String sql = "INSERT INTO error_log(clase, metodo, mensaje) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clase);
            stmt.setString(2, metodo);
            stmt.setString(3, mensaje);
            stmt.executeUpdate();

        } catch (SQLException e) {
            // Si falla al guardar en BD, al menos imprime en consola
            System.err.println(" No se pudo registrar el error en BD:");
            System.err.println("   Clase: " + clase);
            System.err.println("   Método: " + metodo);
            System.err.println("   Error al registrar: " + e.getMessage());
        }
    }
}
