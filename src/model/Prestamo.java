package model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private Libro libro;
    private Usuario usuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(int id, Libro libro, Usuario usuario) {
        this.id = id;
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucion = null;
    }

    public int getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void devolver() {
        this.fechaDevolucion = LocalDate.now();
        this.libro.setDisponible(true);
    }

    public boolean estaActivo() {
        return fechaDevolucion == null;
    }

    public void mostrarInformacion() {
        System.out.println("ID Préstamo: " + id);
        System.out.println("Libro: " + libro.getTitulo());
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("Fecha préstamo: " + fechaPrestamo);
        System.out.println("Fecha devolución: " +
                (fechaDevolucion != null ? fechaDevolucion : "Pendiente"));
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", libro=" + libro.getTitulo() +
                ", usuario=" + usuario.getNombre() +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                '}';
    }
}