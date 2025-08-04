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
    private static final String URL = "jdbc:postgresql://localhost:5432/gestionproyectos";
    private static final String USUARIO = "postgres"; 
    private static final String CONTRASENA = "eme12"; 

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
    public static Connection conectar() {
        try {
            // Intenta crear una conexión utilizando los datos especificados
            Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            // Mensaje de confirmación en consola si la conexión es exitosa
            System.out.println(" Conexión exitosa a PostgreSQL");

            // Devuelve el objeto Connection para su uso posterior
            return conn;

        } catch (SQLException e) {
            // Si ocurre un error al intentar conectarse, se imprime el mensaje en consola
            System.out.println(" Error de conexión: " + e.getMessage());

            // Devuelve null indicando que no se pudo establecer la conexión
            return null;
        }
    }
}
