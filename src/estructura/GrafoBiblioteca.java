package estructura;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrafoBiblioteca {
    private static final String PREFIJO_USUARIO = "U-";
    private static final String PREFIJO_LIBRO = "L-";

    private Map<String, Map<String, Integer>> adyacencias;

    public GrafoBiblioteca() {
        this.adyacencias = new LinkedHashMap<>();
    }

    public void insertarUsuario(int idUsuario) {
        insertarNodo(claveUsuario(idUsuario));
    }

    public void insertarLibro(int idLibro) {
        insertarNodo(claveLibro(idLibro));
    }

    public void insertarNodo(String clave) {
        adyacencias.putIfAbsent(clave, new LinkedHashMap<>());
    }

    public boolean existeUsuario(int idUsuario) {
        return existeNodo(claveUsuario(idUsuario));
    }

    public boolean existeLibro(int idLibro) {
        return existeNodo(claveLibro(idLibro));
    }

    public boolean existeNodo(String clave) {
        return adyacencias.containsKey(clave);
    }

    public boolean eliminarUsuario(int idUsuario) {
        return eliminarNodo(claveUsuario(idUsuario));
    }

    public boolean eliminarLibro(int idLibro) {
        return eliminarNodo(claveLibro(idLibro));
    }

    public boolean eliminarNodo(String clave) {
        if (!adyacencias.containsKey(clave)) {
            return false;
        }

        adyacencias.remove(clave);
        for (Map<String, Integer> relaciones : adyacencias.values()) {
            relaciones.remove(clave);
        }
        return true;
    }

    public void insertarOActualizarRelacion(int idUsuario, int idLibro) {
        String usuario = claveUsuario(idUsuario);
        String libro = claveLibro(idLibro);

        insertarNodo(usuario);
        insertarNodo(libro);

        Map<String, Integer> relacionesUsuario = adyacencias.get(usuario);
        int pesoActual = relacionesUsuario.getOrDefault(libro, 0);
        relacionesUsuario.put(libro, pesoActual + 1);
    }

    public boolean existeRelacion(int idUsuario, int idLibro) {
        return obtenerPesoRelacion(idUsuario, idLibro) > 0;
    }

    public int obtenerPesoRelacion(int idUsuario, int idLibro) {
        Map<String, Integer> relacionesUsuario = adyacencias.get(claveUsuario(idUsuario));
        if (relacionesUsuario == null) {
            return 0;
        }

        return relacionesUsuario.getOrDefault(claveLibro(idLibro), 0);
    }

    public boolean eliminarRelacion(int idUsuario, int idLibro) {
        Map<String, Integer> relacionesUsuario = adyacencias.get(claveUsuario(idUsuario));
        if (relacionesUsuario == null) {
            return false;
        }

        return relacionesUsuario.remove(claveLibro(idLibro)) != null;
    }

    public Map<Integer, Integer> obtenerLibrosDeUsuario(int idUsuario) {
        Map<Integer, Integer> libros = new LinkedHashMap<>();
        Map<String, Integer> relacionesUsuario = adyacencias.get(claveUsuario(idUsuario));

        if (relacionesUsuario == null) {
            return libros;
        }

        for (Map.Entry<String, Integer> relacion : relacionesUsuario.entrySet()) {
            String claveDestino = relacion.getKey();
            if (esLibro(claveDestino)) {
                libros.put(extraerId(claveDestino), relacion.getValue());
            }
        }

        return libros;
    }

    public Map<Integer, Integer> obtenerUsuariosDeLibro(int idLibro) {
        Map<Integer, Integer> usuarios = new LinkedHashMap<>();
        String libro = claveLibro(idLibro);

        for (Map.Entry<String, Map<String, Integer>> entrada : adyacencias.entrySet()) {
            String claveOrigen = entrada.getKey();
            if (esUsuario(claveOrigen) && entrada.getValue().containsKey(libro)) {
                usuarios.put(extraerId(claveOrigen), entrada.getValue().get(libro));
            }
        }

        return usuarios;
    }

    public boolean estaVacio() {
        return adyacencias.isEmpty();
    }

    public void mostrarRelaciones() {
        boolean hayRelaciones = false;

        for (Map.Entry<String, Map<String, Integer>> entrada : adyacencias.entrySet()) {
            String origen = entrada.getKey();
            if (!esUsuario(origen)) {
                continue;
            }

            for (Map.Entry<String, Integer> relacion : entrada.getValue().entrySet()) {
                String destino = relacion.getKey();
                if (esLibro(destino)) {
                    hayRelaciones = true;
                    System.out.println(origen + " -> " + destino + " | peso: " + relacion.getValue());
                }
            }
        }

        if (!hayRelaciones) {
            System.out.println("No hay relaciones registradas en el grafo.");
        }
    }

    private String claveUsuario(int idUsuario) {
        return PREFIJO_USUARIO + idUsuario;
    }

    private String claveLibro(int idLibro) {
        return PREFIJO_LIBRO + idLibro;
    }

    private boolean esUsuario(String clave) {
        return clave.startsWith(PREFIJO_USUARIO);
    }

    private boolean esLibro(String clave) {
        return clave.startsWith(PREFIJO_LIBRO);
    }

    private int extraerId(String clave) {
        return Integer.parseInt(clave.substring(2));
    }
}
