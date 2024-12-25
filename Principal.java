package com.alura.curso.literatura.principal;

import com.alura.curso.literatura.modelo.Libro;
import com.alura.curso.literatura.servicio.GutendexService;
import com.alura.curso.literatura.servicio.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Principal implements CommandLineRunner {

    @Autowired
    private LibroService libroService;

    @Autowired
    private GutendexService gutendexService;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Buscar libro por título");
            System.out.println("2. Buscar libros por autor");
            System.out.println("3. Buscar libros por idioma");
            System.out.println("4. Listar libros guardados");
            System.out.println("5. Listar autores guardados");
            System.out.println("6. Salir");
            System.out.print("\nDel menu anterior seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (opcion) {
                    case 1 -> buscarPor("título", scanner);
                    case 2 -> buscarPor("autor", scanner);
                    case 3 -> buscarPor("idioma", scanner);
                    case 4 -> listarLibros();
                    case 5 -> listarAutores();
                    case 6 -> System.exit(0);
                    default -> System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void buscarPor(String tipo, Scanner scanner) throws Exception {
        System.out.print("Ingrese el " + tipo + " del libro que desea buscar");
        String query = scanner.nextLine();
        List<Libro> libros = gutendexService.buscarLibros(query, tipo);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }
        for (Libro libro : libros) {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("----------");
            libroService.guardarLibro(libro);
        }
    }

    private void listarLibros() {
        libroService.listarLibros().forEach(libro -> {
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Descargas: " + libro.getDescargas());
            System.out.println("----------");
        });
    }

    private void listarAutores() {
        libroService.listarAutores().forEach(System.out::println);
    }
}


