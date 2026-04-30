package estructura;

import model.Libro;

import java.util.ArrayList;
import java.util.List;

public class ArbolLibros {
    private NodoLibro raiz;

    public ArbolLibros() {
        this.raiz = null;
    }

    public boolean insertar(Libro libro) {
        if (buscarPorId(libro.getId()) != null) {
            return false;
        }

        raiz = insertarRecursivo(raiz, libro);
        return true;
    }

    private NodoLibro insertarRecursivo(NodoLibro actual, Libro libro) {
        if (actual == null) {
            return new NodoLibro(libro);
        }

        if (libro.getId() < actual.libro.getId()) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, libro);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, libro);
        }

        return actual;
    }

    public Libro buscarPorId(int id) {
        return buscarRecursivo(raiz, id);
    }

    private Libro buscarRecursivo(NodoLibro actual, int id) {
        if (actual == null) {
            return null;
        }

        if (id == actual.libro.getId()) {
            return actual.libro;
        }

        if (id < actual.libro.getId()) {
            return buscarRecursivo(actual.izquierdo, id);
        }

        return buscarRecursivo(actual.derecho, id);
    }

    public boolean eliminarPorId(int id) {
        if (buscarPorId(id) == null) {
            return false;
        }

        raiz = eliminarRecursivo(raiz, id);
        return true;
    }

    private NodoLibro eliminarRecursivo(NodoLibro actual, int id) {
        if (actual == null) {
            return null;
        }

        if (id < actual.libro.getId()) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, id);
        } else if (id > actual.libro.getId()) {
            actual.derecho = eliminarRecursivo(actual.derecho, id);
        } else {
            if (actual.izquierdo == null && actual.derecho == null) {
                return null;
            }

            if (actual.izquierdo == null) {
                return actual.derecho;
            }

            if (actual.derecho == null) {
                return actual.izquierdo;
            }

            NodoLibro sucesor = buscarMenor(actual.derecho);
            actual.libro = sucesor.libro;
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.libro.getId());
        }

        return actual;
    }

    private NodoLibro buscarMenor(NodoLibro actual) {
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }

        return actual;
    }

    public void mostrarInorden() {
        if (raiz == null) {
            System.out.println("No hay libros registrados.");
            return;
        }

        mostrarInordenRecursivo(raiz);
    }

    private void mostrarInordenRecursivo(NodoLibro actual) {
        if (actual != null) {
            mostrarInordenRecursivo(actual.izquierdo);
            System.out.println("--------------------");
            actual.libro.mostrarInformacion();
            mostrarInordenRecursivo(actual.derecho);
        }
    }

    public List<Libro> obtenerTodosInorden() {
        List<Libro> libros = new ArrayList<>();
        obtenerTodosInordenRecursivo(raiz, libros);
        return libros;
    }

    private void obtenerTodosInordenRecursivo(NodoLibro actual, List<Libro> libros) {
        if (actual != null) {
            obtenerTodosInordenRecursivo(actual.izquierdo, libros);
            libros.add(actual.libro);
            obtenerTodosInordenRecursivo(actual.derecho, libros);
        }
    }
}
