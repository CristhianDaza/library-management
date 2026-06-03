package service;

import estructura.ArbolLibros;
import estructura.ArbolUsuarios;
import estructura.GrafoBiblioteca;
import model.Libro;
import model.Prestamo;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BibliotecaService {
    private ArbolLibros arbolLibros;
    private ArbolUsuarios arbolUsuarios;
    private GrafoBiblioteca grafoBiblioteca;
    private List<Prestamo> prestamos;
    private int contadorPrestamos;

    public BibliotecaService() {
        this.arbolLibros = new ArbolLibros();
        this.arbolUsuarios = new ArbolUsuarios();
        this.grafoBiblioteca = new GrafoBiblioteca();
        this.prestamos = new ArrayList<>();
        this.contadorPrestamos = 1;
    }

    public void registrarLibro(int id, String titulo, String autor) {
        Libro libroExistente = arbolLibros.buscarPorId(id);
        if (libroExistente != null) {
            System.out.println("Ya existe un libro con ese ID.");
            return;
        }

        Libro nuevoLibro = new Libro(id, titulo, autor);
        arbolLibros.insertar(nuevoLibro);
        grafoBiblioteca.insertarLibro(id);
        System.out.println("Libro registrado correctamente.");
    }

    public void registrarUsuario(int id, String nombre) {
        Usuario usuarioExistente = arbolUsuarios.buscarPorId(id);
        if (usuarioExistente != null) {
            System.out.println("Ya existe un usuario con ese ID.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(id, nombre);
        arbolUsuarios.insertar(nuevoUsuario);
        grafoBiblioteca.insertarUsuario(id);
        System.out.println("Usuario registrado correctamente.");
    }

    public Libro buscarLibroPorId(int id) {
        return arbolLibros.buscarPorId(id);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return arbolUsuarios.buscarPorId(id);
    }

    public void mostrarLibros() {
        arbolLibros.mostrarInorden();
    }

    public void mostrarUsuarios() {
        arbolUsuarios.mostrarInorden();
    }

    public void eliminarLibroPorId(int id) {
        boolean eliminado = arbolLibros.eliminarPorId(id);
        if (eliminado) {
            grafoBiblioteca.eliminarLibro(id);
            System.out.println("Libro eliminado correctamente.");
        } else {
            System.out.println("No se encontró un libro con ese ID.");
        }
    }

    public void eliminarUsuarioPorId(int id) {
        boolean eliminado = arbolUsuarios.eliminarPorId(id);
        if (eliminado) {
            grafoBiblioteca.eliminarUsuario(id);
            System.out.println("Usuario eliminado correctamente.");
        } else {
            System.out.println("No se encontró un usuario con ese ID.");
        }
    }

    public void prestarLibro(int idLibro, int idUsuario) {
        Libro libro = arbolLibros.buscarPorId(idLibro);
        if (libro == null) {
            System.out.println("El libro no existe.");
            return;
        }

        Usuario usuario = arbolUsuarios.buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("El usuario no existe.");
            return;
        }

        if (!libro.isDisponible()) {
            System.out.println("El libro no está disponible para préstamo.");
            return;
        }

        libro.setDisponible(false);
        Prestamo nuevoPrestamo = new Prestamo(contadorPrestamos++, libro, usuario);
        prestamos.add(nuevoPrestamo);
        grafoBiblioteca.insertarOActualizarRelacion(idUsuario, idLibro);

        System.out.println("Préstamo realizado correctamente.");
    }

    public void devolverLibro(int idLibro) {
        Libro libro = arbolLibros.buscarPorId(idLibro);
        if (libro == null) {
            System.out.println("El libro no existe.");
            return;
        }

        if (libro.isDisponible()) {
            System.out.println("El libro ya se encuentra disponible.");
            return;
        }

        for (Prestamo prestamo : prestamos) {
            if (prestamo.getLibro().getId() == idLibro && prestamo.estaActivo()) {
                prestamo.devolver();
                System.out.println("Libro devuelto correctamente.");
                return;
            }
        }

        System.out.println("No se encontró un préstamo activo para ese libro.");
    }

    public void mostrarPrestamos() {
        if (prestamos.isEmpty()) {
            System.out.println("No hay préstamos registrados.");
            return;
        }

        for (Prestamo prestamo : prestamos) {
            System.out.println("--------------------");
            prestamo.mostrarInformacion();
        }
    }

    public void mostrarLibrosPrestadosPorUsuario(int idUsuario) {
        Usuario usuario = arbolUsuarios.buscarPorId(idUsuario);
        if (usuario == null) {
            System.out.println("El usuario no existe.");
            return;
        }

        Map<Integer, Integer> libros = grafoBiblioteca.obtenerLibrosDeUsuario(idUsuario);
        if (libros.isEmpty()) {
            System.out.println("El usuario no tiene libros registrados en el grafo.");
            return;
        }

        System.out.println("Libros relacionados con el usuario " + usuario.getNombre() + ":");
        for (Map.Entry<Integer, Integer> entrada : libros.entrySet()) {
            Libro libro = arbolLibros.buscarPorId(entrada.getKey());
            String titulo = libro != null ? libro.getTitulo() : "Libro eliminado";
            System.out.println("Libro ID " + entrada.getKey() + " - " + titulo
                    + " | prestamos: " + entrada.getValue());
        }
    }

    public void mostrarUsuariosQueTomaronLibro(int idLibro) {
        Libro libro = arbolLibros.buscarPorId(idLibro);
        if (libro == null) {
            System.out.println("El libro no existe.");
            return;
        }

        Map<Integer, Integer> usuarios = grafoBiblioteca.obtenerUsuariosDeLibro(idLibro);
        if (usuarios.isEmpty()) {
            System.out.println("El libro no tiene usuarios registrados en el grafo.");
            return;
        }

        System.out.println("Usuarios relacionados con el libro " + libro.getTitulo() + ":");
        for (Map.Entry<Integer, Integer> entrada : usuarios.entrySet()) {
            Usuario usuario = arbolUsuarios.buscarPorId(entrada.getKey());
            String nombre = usuario != null ? usuario.getNombre() : "Usuario eliminado";
            System.out.println("Usuario ID " + entrada.getKey() + " - " + nombre
                    + " | prestamos: " + entrada.getValue());
        }
    }

    public void mostrarRelacionesGrafo() {
        grafoBiblioteca.mostrarRelaciones();
    }
}
