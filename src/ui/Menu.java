package ui;

import model.Libro;
import service.BibliotecaService;

import java.util.Scanner;

public class Menu {
    private BibliotecaService bibliotecaService;
    private Scanner scanner;

    public Menu() {
        this.bibliotecaService = new BibliotecaService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;

        do {
            mostrarOpciones();
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    registrarLibro();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                    buscarLibro();
                    break;
                case 4:
                    prestarLibro();
                    break;
                case 5:
                    devolverLibro();
                    break;
                case 6:
                    bibliotecaService.mostrarLibros();
                    break;
                case 7:
                    bibliotecaService.mostrarUsuarios();
                    break;
                case 8:
                    bibliotecaService.mostrarPrestamos();
                    break;
                case 9:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

            System.out.println();
        } while (opcion != 9);
    }

    private void mostrarOpciones() {
        System.out.println("===== SISTEMA DE GESTIÓN DE BIBLIOTECA =====");
        System.out.println("1. Registrar libro");
        System.out.println("2. Registrar usuario");
        System.out.println("3. Buscar libro por ID");
        System.out.println("4. Prestar libro");
        System.out.println("5. Devolver libro");
        System.out.println("6. Mostrar libros");
        System.out.println("7. Mostrar usuarios");
        System.out.println("8. Mostrar préstamos");
        System.out.println("9. Salir");
    }

    private void registrarLibro() {
        int id = leerEntero("Ingrese el ID del libro: ");
        System.out.print("Ingrese el título del libro: ");
        String titulo = scanner.nextLine();
        System.out.print("Ingrese el autor del libro: ");
        String autor = scanner.nextLine();

        bibliotecaService.registrarLibro(id, titulo, autor);
    }

    private void registrarUsuario() {
        int id = leerEntero("Ingrese el ID del usuario: ");
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = scanner.nextLine();

        bibliotecaService.registrarUsuario(id, nombre);
    }

    private void buscarLibro() {
        int id = leerEntero("Ingrese el ID del libro a buscar: ");
        Libro libro = bibliotecaService.buscarLibroPorId(id);

        if (libro != null) {
            System.out.println("Libro encontrado:");
            libro.mostrarInformacion();
        } else {
            System.out.println("No se encontró un libro con ese ID.");
        }
    }

    private void prestarLibro() {
        int idLibro = leerEntero("Ingrese el ID del libro: ");
        int idUsuario = leerEntero("Ingrese el ID del usuario: ");

        bibliotecaService.prestarLibro(idLibro, idUsuario);
    }

    private void devolverLibro() {
        int idLibro = leerEntero("Ingrese el ID del libro a devolver: ");
        bibliotecaService.devolverLibro(idLibro);
    }

    private int leerEntero(String mensaje) {
        int numero;

        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextInt()) {
                numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            } else {
                System.out.println("Debe ingresar un número válido.");
                scanner.nextLine();
            }
        }
    }
}