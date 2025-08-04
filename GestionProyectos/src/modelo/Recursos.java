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
public class Recursos {
    private int id;
    private String nombreArchivo;
    private String ruta; // Ruta del archivo en el sistema
    private int idTarea;// Relación con Tarea
    private String tipo; // PDF
    private Date fechaSubida;

    public Recursos() {}

    public Recursos(int id, String nombreArchivo, String ruta, int idTarea, String tipo, Date fechaSubida) {
        this.id = id;
        this.nombreArchivo = nombreArchivo;
        this.ruta = ruta;
        this.idTarea = idTarea;
        this.tipo = tipo;
        this.fechaSubida = fechaSubida;
    }

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

    public int getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(int idTarea) {
        this.idTarea = idTarea;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
   
     @Override
    public String toString() {
        return nombreArchivo + " (" + tipo + ")";
    }
    
    
    
}

