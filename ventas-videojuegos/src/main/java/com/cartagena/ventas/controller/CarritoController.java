package com.cartagena.ventas.controller;

import com.cartagena.ventas.carrito.Carrito;
import com.cartagena.ventas.model.Videojuego;
import com.cartagena.ventas.service.ClienteService;
import com.cartagena.ventas.service.VentaService;
import com.cartagena.ventas.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    @Autowired
    private Carrito carrito;

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public String ver(Model model) {
        model.addAttribute("carrito", carrito);
        return "carrito/ver";
    }

    @PostMapping("/agregar")
    public String agregar(@RequestParam Long videojuegoId,
                           @RequestParam Integer cantidad,
                           RedirectAttributes redirect) {
        try {
            Videojuego videojuego = videojuegoService.buscarPorId(videojuegoId);
            carrito.agregar(videojuego, cantidad);
            redirect.addFlashAttribute("mensaje", "Se agregó \"" + videojuego.getTitulo() + "\" al carrito");
        } catch (RuntimeException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/videojuegos";
    }

    @GetMapping("/eliminar/{videojuegoId}")
    public String eliminar(@PathVariable Long videojuegoId) {
        carrito.eliminar(videojuegoId);
        return "redirect:/carrito";
    }

    @GetMapping("/vaciar")
    public String vaciar() {
        carrito.vaciar();
        return "redirect:/carrito";
    }

    @GetMapping("/finalizar")
    public String finalizarForm(Model model) {
        if (carrito.estaVacio()) {
            return "redirect:/carrito";
        }
        model.addAttribute("carrito", carrito);
        model.addAttribute("clientes", clienteService.listarTodos());
        return "carrito/finalizar";
    }

    @PostMapping("/finalizar")
    public String finalizar(@RequestParam Long clienteId, Model model) {
        try {
            ventaService.registrarVentaDesdeCarrito(clienteId, carrito.getItems());
            carrito.vaciar();
            return "redirect:/ventas";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("carrito", carrito);
            model.addAttribute("clientes", clienteService.listarTodos());
            return "carrito/finalizar";
        }
    }
}
