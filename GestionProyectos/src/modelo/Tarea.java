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

// Clase que representa una tarea dentro de un proyecto del sistema de gestión
public class Tarea {

    // Atributos que definen las propiedades de una tarea
    private int id;                              // Identificador único de la tarea
    private String nombre;                       // Nombre o título de la tarea
    private String descripcion;                  // Descripción detallada de la tarea
    private int idProyecto;                      // ID del proyecto al que pertenece la tarea
    private Integer idUsuarioAsignado;           // ID del usuario asignado (puede ser null si no está asignada)
    private String prioridad;                    // Nivel de prioridad (Alta, Media, Baja)
    private String estado;                       // Estado actual (Pendiente, En progreso, Completada, etc.)
    private LocalDate fechaAsignacion;           // Fecha en la que se asignó la tarea
    private LocalDate fechaVencimiento;          // Fecha límite para completar la tarea
    private int progreso;                        // Porcentaje de progreso (0 a 100)

    /**
     * Constructor vacío: útil para frameworks o para inicializar sin parámetros.
     */
    public Tarea() {}

    /**
     * Constructor básico que inicializa con nombre y proyecto.
     * Se asignan valores predeterminados a otros atributos.
     */
    public Tarea(String nombre, int idProyecto) {
        this.nombre = nombre;
        this.idProyecto = idProyecto;
        this.prioridad = "Media";                    // Valor por defecto
        this.estado = "Pendiente";                   // Valor por defecto
        this.fechaAsignacion = LocalDate.now();      // Se asigna la fecha actual
        this.progreso = 0;                           // Se comienza sin progreso
    }

    // Métodos Getter y Setter para cada atributo

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

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public Integer getIdUsuarioAsignado() {
        return idUsuarioAsignado;
    }

    public void setIdUsuarioAsignado(Integer idUsuarioAsignado) {
        this.idUsuarioAsignado = idUsuarioAsignado;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getProgreso() {
        return progreso;
    }

    /**
     * Establece el progreso de la tarea, validando que esté entre 0 y 100.
     */
    public void setProgreso(int progreso) {
        this.progreso = Math.max(0, Math.min(100, progreso)); // Restringe el valor entre 0 y 100
    }

    /**
     * Devuelve una representación en texto de la tarea.
     * Útil para mostrar en interfaces gráficas o listas.
     */
    @Override
    public String toString() {
        return nombre + " (" + progreso + "% - " + estado + ")";
    }
}
