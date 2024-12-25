package com.alura.curso.literatura.servicio;

import com.alura.curso.literatura.modelo.Libro;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GutendexService {
    private static final String BASE_URL = "https://gutendex.com/books/";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Libro> buscarLibros(String query, String tipo) throws IOException {
        String url = BASE_URL + "?search=" + query;
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Error en la respuesta de la API: " + response.code());
            }

            String responseBody = response.body().string();
            JsonNode root = objectMapper.readTree(responseBody).get("results");

            List<Libro> libros = new ArrayList<>();
            for (JsonNode node : root) {
                Libro libro = new Libro();
                libro.setTitulo(node.get("title").asText());
                libro.setAutor(node.get("authors").get(0).get("name").asText());
                libro.setIdioma(node.get("languages").get(0).asText());
                libro.setDescargas(node.get("download_count").asInt());
                libros.add(libro);
            }
            return libros;
        }
    }
}

