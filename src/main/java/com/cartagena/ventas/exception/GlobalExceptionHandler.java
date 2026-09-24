package com.cartagena.ventas.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Captura cualquier error de negocio (RuntimeException) que no fue manejado
 * dentro de un controlador y muestra una página de error clara en vez de
 * la pantalla blanca por defecto de Spring.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String manejarError(RuntimeException ex, Model model) {
        model.addAttribute("mensaje", ex.getMessage());
        return "error";
    }
}
