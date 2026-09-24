package com.cartagena.ventas.service;

import com.cartagena.ventas.model.Videojuego;
import com.cartagena.ventas.repository.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideojuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository;

    public List<Videojuego> listarTodos() {
        return videojuegoRepository.findAll();
    }

    public List<Videojuego> buscar(String texto) {
        if (texto == null || texto.isBlank()) {
            return listarTodos();
        }
        return videojuegoRepository
                .findByTituloContainingIgnoreCaseOrPlataformaContainingIgnoreCaseOrGeneroContainingIgnoreCase(
                        texto, texto, texto);
    }

    public Videojuego buscarPorId(Long id) {
        return videojuegoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Videojuego no encontrado con id: " + id));
    }

    public Videojuego guardar(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    public void eliminar(Long id) {
        try {
            videojuegoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar: este videojuego tiene ventas registradas.");
        }
    }
}
