package com.cartagena.ventas.exception;

import com.cartagena.ventas.carrito.Carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Dos responsabilidades transversales a todos los controladores:
 * 1) Capturar errores de negocio y mostrar una página clara en vez del error genérico de Spring.
 * 2) Inyectar en TODAS las vistas cuántos ítems hay en el carrito, para el contador de la navbar.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private Carrito carrito;

    @ExceptionHandler(RuntimeException.class)
    public String manejarError(RuntimeException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }

    @ModelAttribute("totalCarritoItems")
    public int totalCarritoItems() {
        return carrito.getCantidadTotalItems();
    }
}
