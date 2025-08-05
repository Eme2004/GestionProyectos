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

// Clase que representa un recurso (archivo) asociado a una tarea dentro del sistema
public class Recurso {

    // Atributos del recurso
    private int id;                         // Identificador único del recurso (asignado por la base de datos)
    private String nombreArchivo;           // Nombre del archivo (ej. "documento.pdf")
    private String ruta;                    // Ruta donde está almacenado el archivo (en disco o en servidor)
    private Integer idTarea;                // ID de la tarea a la que está vinculado (puede ser null)
    private String tipo;                    // Tipo del archivo (ej. "PDF", "Imagen", etc.)
    private LocalDate fechaSubida;          // Fecha en que se subió el archivo

    /**
     * Constructor vacío: necesario para frameworks o para inicializar sin parámetros.
     */
    public Recurso() {}

    /**
     * Constructor con parámetros básicos.
     * Asigna la fecha de subida automáticamente al día actual.
     */
    public Recurso(String nombreArchivo, String ruta, Integer idTarea, String tipo) {
        this.nombreArchivo = nombreArchivo;
        this.ruta = ruta;
        this.idTarea = idTarea;
        this.tipo = tipo;
        this.fechaSubida = LocalDate.now();  // Asignación automática de la fecha actual
    }

    // Métodos Getter y Setter para cada atributo

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    /**
     * Representación textual del recurso, útil para mostrar en listas o tablas.
     */
    @Override
    public String toString() {
        return nombreArchivo + " (" + tipo + ")";
    }
}