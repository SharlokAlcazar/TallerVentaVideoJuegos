package com.cartagena.ventas.controller;

import com.cartagena.ventas.service.ClienteService;
import com.cartagena.ventas.service.VentaService;
import com.cartagena.ventas.service.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VentaService ventaService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalVideojuegos", videojuegoService.listarTodos().size());
        model.addAttribute("totalClientes", clienteService.listarTodos().size());
        model.addAttribute("totalVentas", ventaService.listarTodas().size());
        model.addAttribute("totalIngresos", ventaService.totalVentas());
        return "index";
    }
}
