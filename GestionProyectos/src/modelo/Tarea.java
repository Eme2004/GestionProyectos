/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;

public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private int idProyecto;
    private Integer idUsuarioAsignado;
    private String prioridad;
    private String estado;
    private LocalDate fechaAsignacion;
    private LocalDate fechaVencimiento;
    private int progreso;

    public Tarea() {}

    public Tarea(String nombre, int idProyecto) {
        this.nombre = nombre;
        this.idProyecto = idProyecto;
        this.prioridad = "Media";
        this.estado = "Pendiente";
        this.fechaAsignacion = LocalDate.now();
        this.progreso = 0;
    }

    // Getters y Setters
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

    public void setProgreso(int progreso) {
        this.progreso = Math.max(0, Math.min(100, progreso));
    }

    @Override
    public String toString() {
        return nombre + " (" + progreso + "% - " + estado + ")";
    }
}