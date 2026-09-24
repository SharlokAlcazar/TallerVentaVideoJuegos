package com.cartagena.ventas.repository;


import java.util.List;
import com.cartagena.ventas.model.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
    List<Videojuego> findByTituloContainingIgnoreCaseOrPlataformaContainingIgnoreCaseOrGeneroContainingIgnoreCase(
            String titulo, String plataforma, String genero);
}
