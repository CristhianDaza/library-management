package estructura;

import model.Libro;

import java.util.ArrayList;
import java.util.List;

public class ListaLibros {
    private List<Libro> libros;

    public ListaLibros() {
        this.libros = new ArrayList<>();
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public Libro buscarPorId(int id) {
        for (Libro libro : libros) {
            if (libro.getId() == id) {
                return libro;
            }
        }
        return null;
    }

    public List<Libro> obtenerTodos() {
        return libros;
    }

    public void mostrarLibros() {
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }

        for (Libro libro : libros) {
            System.out.println("--------------------");
            libro.mostrarInformacion();
        }
    }
}