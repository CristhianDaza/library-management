package estructura;

import model.Usuario;

public class NodoUsuario {
    Usuario usuario;
    NodoUsuario izquierdo;
    NodoUsuario derecho;

    public NodoUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.izquierdo = null;
        this.derecho = null;
    }
}
