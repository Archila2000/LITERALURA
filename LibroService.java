package com.alura.curso.literatura.servicio;

import com.alura.curso.literatura.modelo.Libro;
import com.alura.curso.literatura.repositorio.LibroRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LibroService {
    private final LibroRepository libroRepository;
    private final GutendexService gutendexService;

    public LibroService(LibroRepository libroRepository, GutendexService gutendexService) {
        this.libroRepository = libroRepository;
        this.gutendexService = gutendexService;
    }

    public void guardarLibro(Libro libro) throws Exception {
        if (libroRepository.findByTitulo(libro.getTitulo()).isPresent()) {
            throw new Exception("Libro ya guardado");
        }
        libroRepository.save(libro);
    }

    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    public List<String> listarAutores() {
        return libroRepository.findAll().stream().map(Libro::getAutor).distinct().toList();
    }
}

