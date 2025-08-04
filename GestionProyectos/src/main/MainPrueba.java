/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;
import util.ErrorLoggerDAO;
import dao.*;
import modelo.*;
import util.ConexionDB; // ✅ Correcto: tu clase de conexión
import java.sql.SQLException;
import java.time.LocalDate;

public class MainPrueba {

    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DEL SISTEMA DE GESTIÓN ===\n");

        try {
            // 1. Crear DAOs
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            ProyectoDAO proyectoDAO = new ProyectoDAO();
            TareaDAO tareaDAO = new TareaDAO();
            RecursoDAO recursoDAO = new RecursoDAO();
            ErrorLoggerDAO errorDAO = new ErrorLoggerDAO();

            // 2. Crear y guardar un usuario
            Usuario usuario = new Usuario("Carlos Ruiz", "carlos@empresa.com", "Desarrollador");
            usuarioDAO.guardar(usuario);
            System.out.println("✅ Usuario guardado: " + usuario);

            // 3. Crear y guardar un proyecto
            Proyecto proyecto = new Proyecto(
                "App Móvil Finanzas",
                "Aplicación móvil para gestión de gastos personales",
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 9, 30),
                "Activo"
            );
            proyectoDAO.guardar(proyecto);
            System.out.println("✅ Proyecto guardado: " + proyecto);

            // 4. Crear y guardar una tarea asignada al usuario
            Tarea tarea = new Tarea("Desarrollar login", proyecto.getId());
            tarea.setDescripcion("Implementar autenticación con JWT");
            tarea.setIdUsuarioAsignado(usuario.getId());
            tarea.setPrioridad("Alta");
            tarea.setEstado("En Progreso");
            tarea.setFechaVencimiento(LocalDate.of(2025, 4, 15));
            tarea.setProgreso(40);
            tareaDAO.guardar(tarea);
            System.out.println("✅ Tarea guardada: " + tarea);

            // 5. Subir un recurso para la tarea
            Recurso recurso = new Recurso(
                "diseño_login.fig",
                "/uploads/disenos/diseño_login.fig",
                tarea.getId(),
                "IMAGEN"
            );
            recursoDAO.guardar(recurso);
            System.out.println("✅ Recurso guardado: " + recurso);

            // 6. Simular un error y registrarlo
            try {
                int[] arr = new int[2];
                arr[5] = 1; // ArrayIndexOutOfBoundsException
            } catch (Exception e) {
                ErrorLogger error = new ErrorLogger(
                    "MainPrueba",
                    "simularError",
                    "Acceso inválido a array: " + e.getMessage()
                );
                errorDAO.registrar(error);
                System.out.println("✅ Error registrado: " + error);
            }

            // 7. Consultar y mostrar resultados
            System.out.println("\n=== DATOS OBTENIDOS DE LA BASE DE DATOS ===");

            System.out.println("\n📋 Usuarios:");
            usuarioDAO.obtenerTodos().forEach(u -> System.out.println("  → " + u));

            System.out.println("\n📁 Proyectos:");
            proyectoDAO.obtenerTodos().forEach(p -> System.out.println("  → " + p));

            System.out.println("\n📌 Tareas del proyecto " + proyecto.getId() + ":");
            tareaDAO.obtenerPorProyecto(proyecto.getId())
                    .forEach(t -> System.out.println("  → " + t));

            System.out.println("\n📎 Recursos de la tarea " + tarea.getId() + ":");
            recursoDAO.obtenerPorTarea(tarea.getId())
                    .forEach(r -> System.out.println("  → " + r));

            System.out.println("\n🐞 Últimos errores registrados:");
            errorDAO.obtenerTodos().stream()
                    .limit(5)
                    .forEach(e -> System.out.println("  → " + e));

            System.out.println("\n✅ Prueba completada con éxito.");

        } catch (SQLException e) {
            System.err.println("❌ Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
        // ✅ No hay finally ni cierre manual: la conexión se cierra en try-with-resources
    }
}