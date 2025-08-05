/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


/**
 *
 * @author Emesis
 */

// Clase que representa un usuario dentro del sistema
public class Usuario {
    // Atributos del usuario
    private int id;           // Identificador único del usuario (generalmente asignado por la base de datos)
    private String nombre;    // Nombre completo del usuario
    private String email;     // Correo electrónico del usuario
    private String rol;       // Rol o perfil del usuario (ej. "Administrador", "Usuario")

    /**
     * Constructor vacío: útil para frameworks o creación sin parámetros iniciales.
     */
    public Usuario() {}

    /**
     * Constructor para crear un nuevo usuario sin ID (para insertar en la base de datos).
     * @param nombre Nombre del usuario
     * @param email Email del usuario
     * @param rol Rol asignado al usuario
     */
    public Usuario(String nombre, String email, String rol) {
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
    }

    /**
     * Constructor completo con ID (por ejemplo, para obtener usuarios existentes).
     * @param id Identificador del usuario
     * @param nombre Nombre del usuario
     * @param email Email del usuario
     * @param rol Rol asignado al usuario
     */
    public Usuario(int id, String nombre, String email, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rol = rol;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Devuelve una representación legible del usuario, útil para mostrar en interfaces.
     */
    @Override
    public String toString() {
        return nombre + " (" + rol + ")";
    }
}