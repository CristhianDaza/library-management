package service;

import estructura.ListaLibros;
import estructura.ListaUsuarios;
import model.Libro;
import model.Prestamo;
import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class BibliotecaService {
    private ListaLibros listaLibros;
    private ListaUsuarios listaUsuarios;
    private List<Prestamo> prestamos;
    private int contadorPrestamos;

    public BibliotecaService() {
        this.listaLibros = new ListaLibros();
        this.listaUsuarios = new ListaUsuarios();
        this.prestamos = new ArrayList<>();
        this.contadorPrestamos = 1;
    }

    public void registrarLibro(int id, String titulo, String autor) {
        Libro libroExistente = listaLibros.buscarPorId(id);
        if (libroExistente != null) {
            System.out.println("Ya existe un libro con ese ID.");
            return;
        }

        Libro nuevoLibro = new Libro(id, titulo, autor);
        listaLibros.agregarLibro(nuevoLibro);
        System.out.println("Libro registrado correctamente.");
    }

    public void registrarUsuario(int id, String nombre) {
        Usuario usuarioExistente = listaUsuarios.buscarPorId(id);
        if (usuarioExistente != null) {
            System.out.println("Ya existe un usuario con ese ID.");
            return;
        }

        Usuario nuevoUsuario = new Usuario(id, nombre);
        listaUsuarios.agregarUsuario(nuevoUsuario);
        System.out.println("Usuario registrado correctamente.");
    }

    public Libro buscarLibroPorId(int id) {
        return listaLibros.buscarPorId(id);
    }

    public Usuario buscarUsuarioPorId(int id) {
        return listaUsuarios.buscarPorId(id);
    }

    public void mostrarLibros() {
        listaLibros.mostrarLibros();
    }

    public void mostrarUsuarios() {
        listaUsuarios.mostrarUsuarios();
    }

    public void prestarLibro(int idLibro, int idUsuario) {
        Libro libro = listaLibros.buscarPorId(idLibro);
        if (libro == null) {
            System.out.println("El libro no existe.");
            return;
        }

        Usuario usuario = listaUsuarios.buscarPorId(idUsuario);
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

        System.out.println("Préstamo realizado correctamente.");
    }

    public void devolverLibro(int idLibro) {
        Libro libro = listaLibros.buscarPorId(idLibro);
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
}