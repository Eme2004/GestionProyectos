Sistema de Gestión de Proyectos 📋
Descripción General
Sistema de gestión de proyectos desarrollado en Java con interfaz gráfica Swing que permite a los equipos organizar proyectos, asignar tareas, dar seguimiento al progreso y gestionar recursos asociados. Este proyecto fue desarrollado como práctica para la materia Programación I (ITI-221) de la UTN.
🎯 Características Principales

✅ Gestión de Proyectos: Crear, editar, eliminar y visualizar proyectos
✅ Administración de Tareas: CRUD completo de tareas asociadas a proyectos
✅ Asignación de Usuarios: Asignar tareas a miembros del equipo
✅ Seguimiento de Progreso: Monitoreo del avance de tareas y proyectos
✅ Gestión de Recursos: Manejo de documentos, fechas y prioridades
✅ Búsqueda y Filtros: Filtrar por proyecto, usuario y estado de tarea
✅ Visualización de Avance: Gráficos y porcentajes de completado
✅ Registro de Logs: Sistema de logging de errores en base de datos

🛠️ Tecnologías Utilizadas

Lenguaje: Java 8+ (JDK 23)
IDE: NetBeans 24+
Interfaz Gráfica: Java Swing
Base de Datos: [PostgreSQL 16+]
Conectividad: JDBC con PreparedStatement
Gestión de Dependencias: [Maven / Ant]
Control de Versiones: Git & GitHub

🏗️ Arquitectura del Sistema
El proyecto sigue el patrón MVC (Modelo-Vista-Controlador) con la siguiente estructura:
GestionProyectos/
├── Source Packages/
│   ├── Conexion/
│   │   └── PruebaConexion.java
│   ├── Imagenes/
│   ├── dao/
│   │   ├── ProyectoDAO.java
│   │   ├── RecursoDAO.java
│   │   ├── TareaDAO.java
│   │   └── UsuarioDAO.java
│   ├── modelo/
│   │   ├── ErrorLogger.java
│   │   ├── Proyecto.java
│   │   ├── Recurso.java
│   │   ├── Tarea.java
│   │   └── Usuario.java
│   ├── ui/
│   │   ├── Inicio.java
│   │   └── InterfazGUI.java
│   └── util/
│       ├── ConexionDB.java
│       └── ErrorLoggerDAO.java
Capas del Sistema:

Paquete modelo: POJOs que representan las entidades del sistema

Proyecto.java - Entidad principal de proyectos
Tarea.java - Entidad de tareas asociadas a proyectos
Usuario.java - Entidad de usuarios del sistema
Recurso.java - Entidad de recursos y documentos
ErrorLogger.java - Manejo de logs de errores


Paquete dao: Acceso a datos con JDBC y PreparedStatement

ProyectoDAO.java - Operaciones CRUD para proyectos
TareaDAO.java - Operaciones CRUD para tareas
UsuarioDAO.java - Operaciones CRUD para usuarios
RecursoDAO.java - Operaciones CRUD para recursos


Paquete ui: Interfaz gráfica con Swing

Inicio.java - Ventana principal de inicio
InterfazGUI.java - Interfaz principal del sistema


Paquete util: Utilidades del sistema

ConexionDB.java - Gestión de conexiones a la base de datos
ErrorLoggerDAO.java - Persistencia de logs de errores


Paquete Conexion: Pruebas y configuración de conexión

PruebaConexion.java - Verificación de conectividad


Paquete Imagenes: Recursos gráficos de la aplicación

📋 Prerrequisitos

Java Development Kit (JDK) 8 o superior
NetBeans IDE 24+
Base de datos SQL Server 2022 Express+ o PostgreSQL 16+
Git 2.40+

🚀 Instalación y Configuración
1. Clonar el repositorio
bashgit clone https:https://github.com/Eme2004/GestionProyectos.git
cd sistema-gestion-proyectos
2. Configurar la Base de Datos
Para SQL Server:
sql-- Ejecutar el script: database/create_tables_sqlserver.sql
-- Ejecutar datos iniciales: database/insert_initial_data.sql
Para PostgreSQL:
sql-- Ejecutar el script: database/create_tables_postgresql.sql
-- Ejecutar datos iniciales: database/insert_initial_data.sql
3. Configurar la conexión a la BD
Editar el archivo src/util/ConexionDB.java con tus credenciales:
javaprivate static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=GestionProyectos";
private static final String DB_USER = "tu_usuario";
private static final String DB_PASSWORD = "tu_contraseña";
4. Compilar y ejecutar
Con Maven:
bashmvn clean compile
mvn exec:java -Dexec.mainClass="ui.Inicio"
Con Ant:
bashant clean
ant compile
ant run
💡 Uso del Sistema
Pantalla Principal

Panel de Proyectos: Crear, editar y eliminar proyectos
Panel de Tareas: Gestionar tareas asociadas a proyectos
Panel de Usuarios: Administrar miembros del equipo
Panel de Reportes: Visualizar avance y estadísticas

Funcionalidades Clave

Crear Proyecto: Definir nombre, descripción, fechas y prioridad
Asignar Tareas: Vincular tareas a proyectos y usuarios
Seguimiento: Actualizar estado y progreso de tareas
Filtros: Buscar por diferentes criterios
Reportes: Visualizar gráficos de avance

📊 Base de Datos
Modelo de Entidades

Proyectos: Información de proyectos (modelo.Proyecto)
Tareas: Tareas asociadas a proyectos (modelo.Tarea)
Usuarios: Usuarios del sistema (modelo.Usuario)
Recursos: Recursos y documentos (modelo.Recurso)
Logs: Registro de errores del sistema (modelo.ErrorLogger)

Relaciones

Un proyecto puede tener múltiples tareas (1:N)
Una tarea puede estar asignada a un usuario (N:1)
Un proyecto puede tener múltiples recursos (1:N)

🧪 Pruebas
Para probar la aplicación:

Ejecutar la aplicación
Crear un proyecto de prueba
Agregar tareas al proyecto
Asignar tareas a usuarios
Actualizar el progreso
Verificar reportes y filtros

📝 Características Técnicas

Seguridad: PreparedStatement para prevenir inyección SQL
Manejo de Errores: Try-catch con logging en BD
Transacciones: Control de transacciones JDBC
Validaciones: Validación de entrada de datos
Documentación: Código completamente documentado

🎥 Demo
[Enlace al video demostrativo de la aplicación]
📷 Capturas de Pantalla
Pantalla Inicio
<img width="590" height="182" alt="image" src="https://github.com/user-attachments/assets/01b9125a-a9eb-4f55-b2e5-750cedf04078" />

Pantalla Principal
<img width="1179" height="405" alt="image" src="https://github.com/user-attachments/assets/490fd85c-60ae-4b83-b4d0-d56f071b026b" />

Gestión de Proyectos
<img width="1177" height="433" alt="image" src="https://github.com/user-attachments/assets/eb2bbc63-ca4e-43cd-8107-14a432b8dfd2" />

Seguimiento de Tareas
<img width="1173" height="558" alt="image" src="https://github.com/user-attachments/assets/df4f9f78-d8de-4ff3-992e-9fa2245801da" />

Gestión de Recursos
<img width="1170" height="343" alt="image" src="https://github.com/user-attachments/assets/25793d5e-c4c6-4f5b-9d3e-6e6b3946da11" />

Seguimiento de Errores
<img width="1180" height="120" alt="image" src="https://github.com/user-attachments/assets/c8699170-c627-4075-a964-f28fa7bea4ef" />


🚀 Características Adicionales Implementadas
 Exportación de reportes a PDF
 Autenticación básica de usuarios


🤝 Contribución
Este proyecto fue desarrollado individualmente como práctica académica para la materia Programación I de la UTN.
📄 Licencia
Este proyecto es con fines educativos para la Universidad Tecnológica Nacional.
👤 Autor
[Emesis Mairena Sevilla]

Estudiante de [ITI]
Universidad Tecnológica Nacional
Email: [emmairenase@est.utn.ac.cr]
GitHub: Eme2004

📚 Referencias y Recursos

Oracle Java Swing Tutorial
Microsoft SQL Server Documentation
PostgreSQL Documentation
NetBeans Ant Projects Guide
Pro Git Book
Microsoft Project Reference


📋 Lista de Verificación del Proyecto

 Configuración del entorno de desarrollo
 Diseño de la base de datos
 Implementación de entidades (POJOs)
 Desarrollo de capa DAO
 Creación de la interfaz gráfica
 Integración BD-UI
 Manejo de excepciones
 Control de versiones con Git (12+ commits)
 Documentación completa
 Generación de JAR ejecutable
 Video demostrativo


Proyecto desarrollado como práctica de la materia ITI-221 Programación I - UTN
