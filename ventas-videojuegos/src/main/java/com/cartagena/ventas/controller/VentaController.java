package com.cartagena.ventas.controller;

import com.cartagena.ventas.dto.VentaForm;
import com.cartagena.ventas.service.ClienteService;
import com.cartagena.ventas.service.VentaService;
import com.cartagena.ventas.service.VideojuegoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarTodas());
        return "ventas/list";
    }

    @GetMapping("/nueva")
    public String nuevaForm(Model model) {
        model.addAttribute("ventaForm", new VentaForm());
        cargarListas(model);
        return "ventas/form";
    }

    @PostMapping("/registrar")
    public String registrar(@Valid @ModelAttribute("ventaForm") VentaForm ventaForm,
                             BindingResult result,
                             Model model) {
        // 1. Validaciones básicas del formulario (campos vacíos, cantidad < 1...)
        if (result.hasErrors()) {
            cargarListas(model);
            return "ventas/form";
        }

        // 2. Validaciones de negocio (stock insuficiente, etc.) las lanza el service
        try {
            ventaService.registrarVenta(ventaForm.getClienteId(), ventaForm.getVideojuegoId(), ventaForm.getCantidad());
            return "redirect:/ventas";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            cargarListas(model);
            return "ventas/form";
        }
    }

    @GetMapping("/detalle/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("venta", ventaService.buscarPorId(id));
        return "ventas/detalle";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/ventas";
    }

    private void cargarListas(Model model) {
        model.addAttribute("clientes", clienteService.listarTodos());
        model.addAttribute("videojuegos", videojuegoService.listarTodos());
    }
}
