/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;

public class Recurso {
    private int id;
    private String nombreArchivo;
    private String ruta;
    private Integer idTarea;
    private String tipo;
    private LocalDate fechaSubida;

    public Recurso() {}

    public Recurso(String nombreArchivo, String ruta, Integer idTarea, String tipo) {
        this.nombreArchivo = nombreArchivo;
        this.ruta = ruta;
        this.idTarea = idTarea;
        this.tipo = tipo;
        this.fechaSubida = LocalDate.now();
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return nombreArchivo + " (" + tipo + ")";
    }
}

