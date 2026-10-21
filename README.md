# Sistema de gestión veterinaria

Aplicación de escritorio para administrar la información básica de una veterinaria. Está desarrollada en Java Swing y persiste los datos en MySQL/MariaDB mediante JDBC.

## Funcionalidades

El dashboard permite abrir los siguientes módulos:

- **Dueños:** registrar, consultar y eliminar dueños.
- **Mascotas:** registrar, consultar y eliminar mascotas, asociándolas a un dueño.
- **Consultas:** registrar, consultar y eliminar consultas asociadas a una mascota y un veterinario.
- **Vacunas:** registrar, consultar y eliminar vacunas asociadas a una mascota y una consulta.
- **Veterinarios:** registrar, consultar y eliminar integrantes del cuerpo veterinario.

Las tablas de los formularios no son editables directamente. Hay métodos de actualización en las clases DAO y de gestión, pero los formularios no ofrecen una acción para editar registros.

## Tecnologías y requisitos

- Windows para usar el lanzador `probar.bat`.
- JDK instalado, con `java` y `javac` disponibles en el `PATH`. Utiliza el mismo JDK para compilar y ejecutar.
- MySQL o MariaDB en ejecución; el volcado SQL se generó desde MariaDB 10.4.
- MySQL Connector/J 8.0.33, incluido en `lib/mysql-connector-j-8.0.33.jar`.

## Estructura del proyecto

```text
.
├── lib/                     # Conector JDBC de MySQL
├── src/
│   ├── App.java             # Punto de entrada
│   ├── conexion/            # Configuración y apertura de conexiones JDBC
│   ├── dao/                 # Operaciones SQL por entidad
│   ├── dominio/             # Modelos y clases de gestión
│   └── forms/               # Dashboard y formularios Swing
├── bin/                     # Clases compiladas (generadas)
├── veterinaria.sql          # Esquema y datos de ejemplo
├── PruebaConexion.java      # Prueba de conexión y lectura de dueños
└── probar.bat               # Compila y abre la aplicación
```

### Organización del código

- `dominio` contiene los modelos `Dueno`, `Mascota`, `Consulta`, `Vacuna` y `Veterinario`, junto con clases `Gestion...` que conectan algunos formularios con sus DAO.
- `dao` implementa las operaciones de persistencia con `PreparedStatement`.
- `forms` contiene el dashboard (`FormMain`) y un formulario por entidad.
- `conexion/ConexionBD.java` centraliza la URL, el usuario y la contraseña de MySQL.

## Preparar la base de datos

1. Inicia MySQL/MariaDB.
2. Crea una base de datos vacía llamada `veterinaria` si todavía no existe.
3. Selecciona esa base en phpMyAdmin y usa **Importar** para cargar `veterinaria.sql`.

El archivo SQL crea las tablas y carga registros de ejemplo. No crea ni selecciona la base de datos, así que debe importarse dentro de `veterinaria`. Para una instalación limpia, importa el volcado en una base nueva.

Relaciones principales:

```text
dueño 1 ── N mascota
mascota 1 ── N consulta
veterinario 1 ── N consulta
mascota 1 ── N vacuna
consulta 1 ── N vacuna
```

Los formularios de consultas y vacunas necesitan registros relacionados para completar sus listas de selección; el volcado incluye datos de ejemplo para esas relaciones.

## Configurar la conexión

En `src/conexion/ConexionBD.java`, los valores predeterminados son:

```text
URL:      jdbc:mysql://localhost:3306/veterinaria
Usuario:  root
Password: (vacía)
```

Si tu instalación usa otro puerto, usuario o contraseña, actualiza `URL_LOCAL`, `USUARIO` y `CONTRASENA` en esa clase antes de ejecutar la aplicación. La contraseña vacía solo es apropiada para un entorno local configurado de esa manera.

## Compilar y ejecutar

Desde la carpeta raíz del proyecto, ejecuta:

```bat
probar.bat
```

El script compila las clases de `src` en `bin` usando el conector de `lib` y luego inicia `App`, que abre el dashboard Swing. También se puede ejecutar `probar.bat` haciendo doble clic desde el Explorador de archivos.

Para probar por separado la conexión y consultar los dueños desde una terminal abierta en la raíz:

```bat
javac -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" -d bin src\conexion\ConexionBD.java PruebaConexion.java
java -cp "bin;lib\mysql-connector-j-8.0.33.jar" PruebaConexion
```

`PruebaConexion.java` solo comprueba la conexión y lee datos; no inicia el dashboard.

## Solución de problemas

- **`Driver JDBC no encontrado` / `ClassNotFoundException: com.mysql.cj.jdbc.Driver`:** comprueba que exista `lib/mysql-connector-j-8.0.33.jar` y que se ejecute la aplicación mediante `probar.bat`, que lo agrega al classpath.
- **`Communications link failure` o conexión rechazada:** verifica que MySQL/MariaDB esté iniciado y que el host y el puerto en `ConexionBD.java` sean correctos.
- **Error de acceso denegado:** verifica el usuario y la contraseña configurados en `ConexionBD.java`.
- **Base o tabla no encontrada:** crea `veterinaria` e importa `veterinaria.sql` dentro de esa base.
- **`UnsupportedClassVersionError`:** hay clases compiladas con un JDK más nuevo que el Java usado para ejecutarlas. Vuelve a compilar con `probar.bat` y ejecuta con un JRE/JDK compatible.

## Observaciones del esquema actual

Hay diferencias entre algunos tipos del volcado SQL y los modelos Java:

- `dueño.ci` y `dueño.telefono` son `INT` en SQL, aunque Java los maneja como texto. Almacenarlos como números puede eliminar ceros iniciales.
- `mascota.peso` es `INT` en SQL, pero Java permite `double`; la base no conserva fracciones.
- `veterinario.password` y `veterinario.especialidad` son `INT` en SQL, pero la interfaz y Java los tratan como texto. Valores no numéricos pueden fallar al guardar.

Si se necesitan esos formatos, conviene alinear los tipos de las columnas con los datos que acepta la aplicación antes de cargar información real.
