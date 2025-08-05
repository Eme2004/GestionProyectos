/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;
/**
 *
 * @author Emesis
 */

// Clase que representa un registro de error para almacenar información detallada sobre errores ocurridos en el sistema
public class ErrorLogger {

    // Atributos que almacenan los datos del error
    private int id;                         // Identificador único del error (opcional si es autogenerado en BD)
    private String clase;                   // Nombre de la clase donde ocurrió el error
    private String metodo;                  // Nombre del método donde ocurrió el error
    private String mensaje;                 // Mensaje descriptivo del error
    private java.time.LocalDateTime fecha;  // Fecha y hora en que ocurrió el error

    // Constructor vacío: útil para frameworks o para instanciar y luego setear atributos
    public ErrorLogger() {}

    // Constructor completo: inicializa todos los atributos de una vez
    public ErrorLogger(int id, String clase, String metodo, String mensaje, java.time.LocalDateTime fecha) {
        this.id = id;
        this.clase = clase;
        this.metodo = metodo;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    // Métodos getter y setter para acceder y modificar los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    

    // el método toString es para mostrar una representación legible del error
    @Override
    public String toString() {
        return "[" + fecha + "] " + clase + "." + metodo + " - " + mensaje;
    }
}
