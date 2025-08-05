/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;
/**
 *
 * @author Emesis
 */

// Clase que representa un Proyecto dentro del sistema de gestión
public class Proyecto {
    
    // Atributos del proyecto
    private int id;                        // Identificador único del proyecto (generalmente autogenerado en la BD)
    private String nombre;                 // Nombre del proyecto
    private String descripcion;            // Descripción del proyecto
    private LocalDate fechaInicio;         // Fecha de inicio del proyecto
    private LocalDate fechaFin;            // Fecha de finalización del proyecto
    private String estado;                 // Estado actual del proyecto (ej. "En progreso", "Finalizado", etc.)

    /**
     * Constructor vacío: útil cuando se quiere instanciar un proyecto sin datos iniciales.
     */
    public Proyecto() {}

    /**
     * Constructor con todos los atributos (excepto ID, que puede ser asignado por la base de datos).
     */
    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio, LocalDate fechaFin, String estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Métodos getter y setter para acceder/modificar los atributos

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Devuelve una representación en texto del proyecto,
     * útil para mostrarlo en listas o interfaces gráficas.
     */
    @Override
    public String toString() {
        return nombre + " (" + estado + ")";
    }
}