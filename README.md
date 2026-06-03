# Sistema de Gestion de Biblioteca

Este proyecto es un sistema simple de gestion de biblioteca desarrollado en Java.
Funciona por consola y permite administrar libros, usuarios, préstamos y relaciones de historial mediante un grafo.

La version actual combina tres estructuras:

- Árboles binarios de búsqueda para organizar libros y usuarios por ID.
- ArrayList para conservar el historial detallado de préstamos.
- Grafo bipartito dirigido y ponderado para modelar interacciones entre usuarios y libros.

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
- Consultar libros relacionados con un usuario
- Consultar usuarios relacionados con un libro
- Mostrar relaciones del grafo con su peso

## Tecnologías

- Java
- Java puro, sin librerías externas
- Árboles binarios de búsqueda
- ArrayList para almacenar préstamos
- Grafo con listas de adyacencia usando Map

## Estructura del proyecto

- model -> entidades principales: Libro, Usuario y Préstamo
- estructura -> estructuras de datos: ArbolLibros, ArbolUsuarios, GrafoBiblioteca, NodoLibro, NodoUsuario, ListaLibros y ListaUsuarios
- service -> lógica del sistema: BibliotecaService
- ui -> interfaz por consola: Menu
- Main -> punto de entrada del programa

## Análisis de necesidades

El sistema ya permite registrar usuarios, registrar libros y crear préstamos. Sin embargo, consultar relaciones históricas entre usuarios y libros usando solo la lista de préstamos obliga a recorrer todo el historial.

El grafo mejora esta interacción porque permite mantener un índice directo de afinidad:

- Qué libros ha tomado prestado un usuario.
- Qué usuarios han tomado prestado un libro.
- Cuantas veces se ha repetido una relación usuario-libro.

Los árboles siguen siendo útiles para búsquedas por ID, mientras que el grafo se especializa en relaciones.

## Selección del tipo de grafo

Se usa un grafo bipartito, dirigido y ponderado.

- Bipartito: separa nodos de usuarios y nodos de libros.
- Dirigido: la relación va desde el usuario hacia el libro, porque el usuario realiza la acción de tomar prestado.
- Ponderado: el peso representa cuantas veces un usuario ha tomado prestado el mismo libro.

La devolución de un libro no elimina la arista, porque el grafo representa historial de interacciones, no solo préstamos activos.

## Diseño del grafo

La clase `GrafoBiblioteca` representa los nodos con claves diferenciadas:

- `U-1` representa el usuario con ID 1.
- `L-10` representa el libro con ID 10.

Las aristas se almacenan en listas de adyacencia:

```text
U-1 -> L-10 | peso: 2
```

Ese ejemplo indica que el usuario 1 ha tomado prestado el libro 10 dos veces.

## Operaciones del grafo

`GrafoBiblioteca` implementa:

- Insertar nodos de usuarios y libros.
- Buscar si existe un nodo.
- Eliminar nodos y sus aristas asociadas.
- Insertar o actualizar una relación usuario-libro.
- Buscar si existe una relación.
- Obtener el peso de una relación.
- Eliminar una relación.
- Listar libros relacionados con un usuario.
- Listar usuarios relacionados con un libro.
- Mostrar todas las relaciones registradas.

## Integración con BibliotecaService

`BibliotecaService` mantiene una instancia de `GrafoBiblioteca`.

Cuando se registra un usuario o libro, también se registra su nodo en el grafo.
Cuando se elimina un usuario o libro, también se elimina su nodo y cualquier relación asociada.
Cuando se presta un libro correctamente, se crea o incrementa la arista `usuario -> libro`.

También se agregaron consultas para:

- Mostrar libros prestados por usuario.
- Mostrar usuarios que tomaron un libro.
- Mostrar todas las relaciones del grafo.

## Árboles binarios de búsqueda

El proyecto usa dos árboles principales:

- ArbolLibros: almacena libros ordenados por el ID del libro.
- ArbolUsuarios: almacena usuarios ordenados por el ID del usuario.

Cada árbol permite:

- Insertar registros
- Buscar por ID
- Eliminar por ID
- Recorrer en inorder
- Obtener todos los registros en orden

El recorrido inorder permite mostrar los libros y usuarios de menor a mayor ID.

## Préstamos y devoluciones

Cuando se presta un libro, el sistema:

1. Busca el libro en ArbolLibros.
2. Busca el usuario en ArbolUsuarios.
3. Válida que el libro esté disponible.
4. Registra el préstamo en una lista.
5. Marca el libro como no disponible.
6. Registra o incrementa la relación en el grafo.

Cuando se devuelve un libro, el sistema:

1. Busca el libro por ID.
2. Válida que no esté disponible.
3. Busca el préstamo activo.
4. Registra la fecha de devolución.
5. Marca el libro como disponible.

## Optimización

La optimización principal consiste en separar responsabilidades:

- Los árboles permiten búsquedas eficientes por ID.
- La lista de préstamos conserva el historial completo con fechas.
- El grafo permite consultar relaciones usuario-libro sin reconstruirlas desde cero cada vez.

El grafo usa `LinkedHashMap` para mantener un orden estable de inserción al mostrar resultados por consola.

## Compilación

El proyecto está configurado para Java 17 en IntelliJ IDEA.
Si se compila desde consola con una version superior de Java, se recomienda usar `--release 17`:

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
9. Mostrar relaciones del grafo y verificar la arista `U-idUsuario -> L-idLibro`.
10. Intentar prestar el mismo libro nuevamente sin devolverlo y comprobar que el grafo no cambia.
11. Devolver el libro.
12. Prestar el mismo libro otra vez al mismo usuario y comprobar que el peso aumenta.
13. Consultar libros prestados por usuario.
14. Consultar usuarios que tomaron un libro.
15. Eliminar un usuario o libro y verificar que sus relaciones desaparecen del grafo.
16. Mostrar préstamos y verificar la información registrada.

## Nota sobre versiones de Java

Si aparece un error como `class file has wrong version 69.0, should be 61.0`, significa que las clases fueron compiladas con Java 25 y se están ejecutando con Java 17.
Para corregirlo, elimine los archivos compilados de `out/production/library-management` y compile nuevamente usando `--release 17`.
