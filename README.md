# Sistema de Gestión de Biblioteca

Este proyecto es un sistema simple de gestión de biblioteca desarrollado en Java.
Funciona por consola y permite administrar libros, usuarios y préstamos.

La versión actual incorpora árboles binarios de búsqueda para organizar libros y usuarios por ID.
Los préstamos se mantienen en una lista, ya que representan un historial de operaciones.

## Funcionalidades

- Registrar libros
- Registrar usuarios
- Buscar libros por ID
- Prestar libros
- Devolver libros
- Visualizar libros, usuarios y préstamos
- Mostrar libros ordenados por ID
- Mostrar usuarios ordenados por ID
- Eliminar libros y usuarios desde la lógica del servicio

## Tecnologías

- Java
- Java puro, sin librerías externas
- Árboles binarios de búsqueda
- ArrayList para almacenar préstamos

## Estructura del proyecto

- model -> entidades principales: Libro, Usuario y Prestamo
- estructura -> estructuras de datos: ArbolLibros, ArbolUsuarios, NodoLibro, NodoUsuario, ListaLibros y ListaUsuarios
- service -> lógica del sistema: BibliotecaService
- ui -> interfaz por consola: Menu
- Main -> punto de entrada del programa

## Árboles binarios de búsqueda

El proyecto usa dos árboles principales:

- ArbolLibros: almacena libros ordenados por el ID del libro.
- ArbolUsuarios: almacena usuarios ordenados por el ID del usuario.

Cada árbol permite:

- Insertar registros
- Buscar por ID
- Eliminar por ID
- Recorrer en inorden
- Obtener todos los registros en orden

El recorrido inorden permite mostrar los libros y usuarios de menor a mayor ID.

## Préstamos y devoluciones

La lógica de préstamos se mantiene en BibliotecaService.
Cuando se presta un libro, el sistema:

1. Busca el libro en ArbolLibros.
2. Busca el usuario en ArbolUsuarios.
3. Valida que el libro esté disponible.
4. Registra el préstamo en una lista.
5. Marca el libro como no disponible.

Cuando se devuelve un libro, el sistema:

1. Busca el libro por ID.
2. Valida que no esté disponible.
3. Busca el préstamo activo.
4. Registra la fecha de devolución.
5. Marca el libro como disponible.

## Compilación

El proyecto está configurado para Java 17 en IntelliJ IDEA.
Si se compila desde consola con una versión superior de Java, se recomienda usar `--release 17`:

```powershell
javac --release 17 -d out/production/library-management src/Main.java src/estructura/*.java src/model/*.java src/service/*.java src/ui/*.java
```

## Ejecución

```powershell
java -cp out/production/library-management Main
```

También se puede ejecutar desde IntelliJ usando la clase Main.

## Pruebas manuales sugeridas

Para verificar el funcionamiento y tomar capturas:

1. Registrar libros con IDs desordenados, por ejemplo 30, 10 y 20.
2. Mostrar libros y comprobar que aparecen ordenados como 10, 20 y 30.
3. Registrar usuarios con IDs desordenados, por ejemplo 3, 1 y 2.
4. Mostrar usuarios y comprobar que aparecen ordenados como 1, 2 y 3.
5. Buscar un libro existente por ID.
6. Buscar un libro inexistente por ID.
7. Intentar registrar un libro con ID repetido.
8. Prestar un libro disponible a un usuario existente.
9. Intentar prestar el mismo libro nuevamente.
10. Devolver el libro.
11. Mostrar préstamos y verificar la información registrada.

## Nota sobre versiones de Java

Si aparece un error como `class file has wrong version 69.0, should be 61.0`, significa que las clases fueron compiladas con Java 25 y se están ejecutando con Java 17.
Para corregirlo, elimine los archivos compilados de `out/production/library-management` y compile nuevamente usando `--release 17`.
