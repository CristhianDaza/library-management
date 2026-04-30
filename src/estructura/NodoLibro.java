package estructura;

import model.Libro;

public class NodoLibro {
    Libro libro;
    NodoLibro izquierdo;
    NodoLibro derecho;

    public NodoLibro(Libro libro) {
        this.libro = libro;
        this.izquierdo = null;
        this.derecho = null;
    }
}
