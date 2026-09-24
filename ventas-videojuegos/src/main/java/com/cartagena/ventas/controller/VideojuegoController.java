package com.cartagena.ventas.controller;

import com.cartagena.ventas.model.Videojuego;
import com.cartagena.ventas.service.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public String listar(@RequestParam(required = false) String buscar, Model model) {
        model.addAttribute("videojuegos", videojuegoService.buscar(buscar));
        model.addAttribute("buscar", buscar);
        return "videojuegos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("videojuego", new Videojuego());
        return "videojuegos/form";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        model.addAttribute("videojuego", videojuegoService.buscarPorId(id));
        return "videojuegos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("videojuego") Videojuego videojuego,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "videojuegos/form";
        }
        videojuegoService.guardar(videojuego);
        return "redirect:/videojuegos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        videojuegoService.eliminar(id);
        return "redirect:/videojuegos";
    }
}
