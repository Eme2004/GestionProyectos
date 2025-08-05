/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;
import util.ConexionDB;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author Emesis
 */
// Definición de la clase principal para probar la conexión a la base de datos
public class PruebaConexion {

    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] args) throws SQLException {
        
        // Se intenta establecer una conexión con la base de datos usando el método conectar() de la clase ConexionDB
        Connection conn = ConexionDB.conectar();
        
        // Se verifica si la conexión fue exitosa (es decir, si no es null)
        if (conn != null) {
            // Si la conexión fue exitosa, se imprime un mensaje en consola
            System.out.println("Conectado correctamente a la base de datos.");
        } else {
            // Si la conexión falló (conn es null), se imprime un mensaje de error
            System.out.println("No se pudo conectar.");
        }
    }
}
