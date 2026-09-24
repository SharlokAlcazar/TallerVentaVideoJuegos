package com.cartagena.ventas.carrito;

import com.cartagena.ventas.model.Videojuego;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Carrito de compras. @SessionScope significa que Spring crea UNA instancia
 * por cada sesión de navegador: cada usuario tiene su propio carrito,
 * sin que se mezclen entre sí, y se vacía solo al cerrar el navegador.
 */
@Component
@SessionScope
public class Carrito implements Serializable {

    private final List<ItemCarrito> items = new ArrayList<>();

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void agregar(Videojuego videojuego, Integer cantidad) {
        if (cantidad == null || cantidad < 1) {
            throw new RuntimeException("La cantidad debe ser al menos 1");
        }

        // Si el juego ya estaba en el carrito, sumamos en vez de duplicar la línea
        for (ItemCarrito item : items) {
            if (item.getVideojuegoId().equals(videojuego.getId())) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                if (nuevaCantidad > videojuego.getStock()) {
                    throw new RuntimeException("Solo hay " + videojuego.getStock()
                            + " unidades disponibles de " + videojuego.getTitulo());
                }
                item.setCantidad(nuevaCantidad);
                return;
            }
        }

        if (cantidad > videojuego.getStock()) {
            throw new RuntimeException("Solo hay " + videojuego.getStock()
                    + " unidades disponibles de " + videojuego.getTitulo());
        }

        items.add(new ItemCarrito(videojuego.getId(), videojuego.getTitulo(), videojuego.getPrecio(), cantidad));
    }

    public void eliminar(Long videojuegoId) {
        items.removeIf(i -> i.getVideojuegoId().equals(videojuegoId));
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int getCantidadTotalItems() {
        return items.stream().mapToInt(ItemCarrito::getCantidad).sum();
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
