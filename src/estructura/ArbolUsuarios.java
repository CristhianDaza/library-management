package estructura;

import model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ArbolUsuarios {
    private NodoUsuario raiz;

    public ArbolUsuarios() {
        this.raiz = null;
    }

    public boolean insertar(Usuario usuario) {
        if (buscarPorId(usuario.getId()) != null) {
            return false;
        }

        raiz = insertarRecursivo(raiz, usuario);
        return true;
    }

    private NodoUsuario insertarRecursivo(NodoUsuario actual, Usuario usuario) {
        if (actual == null) {
            return new NodoUsuario(usuario);
        }

        if (usuario.getId() < actual.usuario.getId()) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, usuario);
        } else {
            actual.derecho = insertarRecursivo(actual.derecho, usuario);
        }

        return actual;
    }

    public Usuario buscarPorId(int id) {
        return buscarRecursivo(raiz, id);
    }

    private Usuario buscarRecursivo(NodoUsuario actual, int id) {
        if (actual == null) {
            return null;
        }

        if (id == actual.usuario.getId()) {
            return actual.usuario;
        }

        if (id < actual.usuario.getId()) {
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

    private NodoUsuario eliminarRecursivo(NodoUsuario actual, int id) {
        if (actual == null) {
            return null;
        }

        if (id < actual.usuario.getId()) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, id);
        } else if (id > actual.usuario.getId()) {
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

            NodoUsuario sucesor = buscarMenor(actual.derecho);
            actual.usuario = sucesor.usuario;
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.usuario.getId());
        }

        return actual;
    }

    private NodoUsuario buscarMenor(NodoUsuario actual) {
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }

        return actual;
    }

    public void mostrarInorden() {
        if (raiz == null) {
            System.out.println("No hay usuarios registrados.");
            return;
        }

        mostrarInordenRecursivo(raiz);
    }

    private void mostrarInordenRecursivo(NodoUsuario actual) {
        if (actual != null) {
            mostrarInordenRecursivo(actual.izquierdo);
            System.out.println("--------------------");
            actual.usuario.mostrarInformacion();
            mostrarInordenRecursivo(actual.derecho);
        }
    }

    public List<Usuario> obtenerTodosInorden() {
        List<Usuario> usuarios = new ArrayList<>();
        obtenerTodosInordenRecursivo(raiz, usuarios);
        return usuarios;
    }

    private void obtenerTodosInordenRecursivo(NodoUsuario actual, List<Usuario> usuarios) {
        if (actual != null) {
            obtenerTodosInordenRecursivo(actual.izquierdo, usuarios);
            usuarios.add(actual.usuario);
            obtenerTodosInordenRecursivo(actual.derecho, usuarios);
        }
    }
}
