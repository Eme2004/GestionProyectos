/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDateTime;

public class ErrorLogger {
    private int id;
    private String clase;
    private String metodo;
    private String mensaje;
    private java.time.LocalDateTime fecha;

    // Constructor vacío
    public ErrorLogger() {}

    // Constructor completo
    public ErrorLogger(int id, String clase, String metodo, String mensaje, java.time.LocalDateTime fecha) {
        this.id = id;
        this.clase = clase;
        this.metodo = metodo;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getClase() { return clase; }
    public void setClase(String clase) { this.clase = clase; }

    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public java.time.LocalDateTime getFecha() { return fecha; }
    public void setFecha(java.time.LocalDateTime fecha) { this.fecha = fecha; }

    @Override
    public String toString() {
        return "[" + fecha + "] " + clase + "." + metodo + " - " + mensaje;
    }
}