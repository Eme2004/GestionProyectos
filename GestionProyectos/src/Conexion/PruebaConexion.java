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
public class PruebaConexion {
    public static void main(String[] args) throws SQLException {
        // Intentamos establecer una conexión con la base de datos usando la clase ConexionBD
        Connection conn = ConexionDB.conectar();
        
        // Verificamos si la conexión fue exitosa (no es null)
        if (conn != null) {
            System.out.println("Conectado correctamente a la base de datos.");
        } else {
            System.out.println("No se pudo conectar.");
        }
    }
}

