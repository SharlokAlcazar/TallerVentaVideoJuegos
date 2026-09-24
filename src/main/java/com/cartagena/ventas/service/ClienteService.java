package com.cartagena.ventas.service;

import com.cartagena.ventas.model.Cliente;
import com.cartagena.ventas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Cliente guardar(Cliente cliente) {
        // Solo validamos duplicado al crear (id nulo); al editar, el email ya es el suyo.
        if (cliente.getId() == null && clienteRepository.existsByEmailIgnoreCase(cliente.getEmail())) {
            throw new RuntimeException("Ya existe un cliente registrado con ese correo.");
        }
        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        try {
            clienteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("No se puede eliminar: este cliente tiene ventas registradas.");
        }
    }
}
