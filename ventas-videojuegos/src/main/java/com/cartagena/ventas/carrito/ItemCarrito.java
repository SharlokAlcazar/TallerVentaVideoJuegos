package com.cartagena.ventas.carrito;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Una línea del carrito: un videojuego + cantidad, con el precio
 * congelado al momento de agregarlo (por si el precio cambia después).
 */
public class ItemCarrito implements Serializable {

    private Long videojuegoId;
    private String titulo;
    private BigDecimal precioUnitario;
    private Integer cantidad;

    public ItemCarrito() {}

    public ItemCarrito(Long videojuegoId, String titulo, BigDecimal precioUnitario, Integer cantidad) {
        this.videojuegoId = videojuegoId;
        this.titulo = titulo;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public Long getVideojuegoId() { return videojuegoId; }
    public void setVideojuegoId(Long videojuegoId) { this.videojuegoId = videojuegoId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
}
