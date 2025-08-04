/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de establecer la conexión con la base de datos PostgreSQL
 * Conecta a la base de datos correcta: gestionproyectos
 */
public class ConexionDB {

    // URL de conexión al servidor PostgreSQL
    // Nombre de la base de datos: gestionproyectos (sin espacios)
    private static final String URL = "jdbc:postgresql://localhost:5432/gestionproyectos";

    // Nombre de usuario de la base de datos
    private static final String USUARIO = "postgres";  

    // Contraseña del usuario
    private static final String CONTRASENA = "eme12";  

    /**
     * Método estático que intenta establecer una conexión con la base de datos PostgreSQL.
     *
     * @return Objeto Connection si la conexión es exitosa, o null si ocurre un error.
     */
    public static Connection conectar() {
        try {
            // Cargar explícitamente el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Intenta crear la conexión
            Connection conn = DriverManager.getConnection(URL, USUARIO, CONTRASENA);

            // Mensaje de éxito
            System.out.println("✅ Conexión exitosa a la base de datos: " + conn.getCatalog());

            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver de PostgreSQL no encontrado. ¿Agregaste el JAR?");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión a 'gestionproyectos': " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}