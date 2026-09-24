package com.cartagena.ventas.service;

import com.cartagena.ventas.model.*;
import com.cartagena.ventas.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VideojuegoService videojuegoService;

    @Autowired
    private ClienteService clienteService;

    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con id: " + id));
    }

    /**
     * Registra una venta con un solo ítem (videojuego + cantidad).
     * Valida stock disponible y lo descuenta.
     */
    @Transactional
    public Venta registrarVenta(Long clienteId, Long videojuegoId, Integer cantidad) {
        Cliente cliente = clienteService.buscarPorId(clienteId);
        Videojuego videojuego = videojuegoService.buscarPorId(videojuegoId);

        if (videojuego.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente para " + videojuego.getTitulo()
                    + " (disponible: " + videojuego.getStock() + ")");
        }

        Venta venta = new Venta();
        venta.setCliente(cliente);

        DetalleVenta detalle = new DetalleVenta(videojuego, cantidad, videojuego.getPrecio());
        venta.addDetalle(detalle);
        venta.setTotal(detalle.getSubtotal());

        videojuego.setStock(videojuego.getStock() - cantidad);
        videojuegoService.guardar(videojuego);

        return ventaRepository.save(venta);
    }

    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }

    public BigDecimal totalVentas() {
        return ventaRepository.findAll().stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
