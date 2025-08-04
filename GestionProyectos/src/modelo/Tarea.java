/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.util.Date;
/**
 *
 * @author USER
 */
public class Tarea {
    private int id;
    private String nombre;
    private String descripcion;
    private int idProyecto; // Relación con Proyecto
    private Integer idUsuarioAsignado;// Puede ser null
    private String prioridad; // Baja, Media, Alta
    private String estado;// Pendiente, En Progreso, Completada,Bloqueada
    private Date fechaAsignacion;
    private Date fechaVencimiento;
    private int progreso;  // 0 a 100%

    public Tarea() {}

    public Tarea(int id, String nombre, String descripcion, int idProyecto, Integer idUsuarioAsignado, String prioridad, String estado, Date fechaAsignacion, Date fechaVencimiento, int progreso) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idProyecto = idProyecto;
        this.idUsuarioAsignado = idUsuarioAsignado;
        this.prioridad = prioridad;
        this.estado = estado;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaVencimiento = fechaVencimiento;
        this.progreso = progreso;
    }

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

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
    
  @Override
    public String toString() {
        return nombre + " - " + estado + " (" + progreso + "%)";
    } 
}
