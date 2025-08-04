/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Emesis
 */
// Clase encargada de establecer la conexión con la base de datos PostgreSQL

public class ConexionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/gestorproyectos";
    private static final String USUARIO = "postgres"; // Cambia si usas otro usuario
    private static final String CONTRASENA = "tu_contraseña"; // ⚠️ Cambia esto

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de PostgreSQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
