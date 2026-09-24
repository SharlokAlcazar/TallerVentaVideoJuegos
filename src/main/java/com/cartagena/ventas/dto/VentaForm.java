package com.cartagena.ventas.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * Objeto simple para recoger y validar los datos del formulario de venta
 * antes de pasarlos al servicio.
 */
public class VentaForm {

    @NotNull(message = "Selecciona un cliente")
    private Long clienteId;

    @NotNull(message = "Selecciona un videojuego")
    private Long videojuegoId;

    @NotNull(message = "Indica la cantidad")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getVideojuegoId() { return videojuegoId; }
    public void setVideojuegoId(Long videojuegoId) { this.videojuegoId = videojuegoId; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
