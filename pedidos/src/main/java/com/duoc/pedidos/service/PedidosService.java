package com.duoc.pedidos.service;

import com.duoc.pedidos.client.Client;
import com.duoc.pedidos.dto.PedidosDTO;
import com.duoc.pedidos.dto.PedidosRequest;
import com.duoc.pedidos.exception.PedidoNotFoundException;
import com.duoc.pedidos.model.ClientModel;
import com.duoc.pedidos.model.PedidosModel;
import com.duoc.pedidos.model.ProductModel;
import com.duoc.pedidos.product.Product;
import com.duoc.pedidos.repository.PedidosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private Product product;

    @Autowired
    private Client client;

    public PedidosDTO guardar(PedidosRequest request) {
        log.info("Iniciando el proceso para guardar un nuevo pedido: {}", request.getPedido());
        PedidosModel pedidos = new PedidosModel();
        pedidos.setPedido(request.getPedido());

        PedidosModel guardado = pedidosRepository.save(pedidos);
        log.info("Pedido guardado exitosamente con ID: {}", guardado.getId());
        return convertirADTO(guardado);
    }
    public List<PedidosDTO> listar() {
        log.info("Solicitando el listado completo de pedidos desde la base de datos.");
        return pedidosRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public PedidosDTO buscarPorId(int id) {
        log.info("Buscando pedido por ID: {}", id);
        PedidosModel pedidos = pedidosRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        log.info("Consultando cliente asociado (ID: {}) a través de Client HTTP", pedidos.getId_cliente());
        ClientModel cliente = client.obtenerClientes(pedidos.getId_cliente());
        log.info("Consultando producto asociado (ID: {}) a través de Product HTTP", pedidos.getId_producto());
        ProductModel producto = product.obtenerProductos(pedidos.getId_producto());
        PedidosDTO pedidosDTO = new PedidosDTO();
        pedidosDTO.setPedido(pedidos.getPedido());
        pedidosDTO.setId_cliente(cliente.getId_cliente());
        pedidosDTO.setNombre_cliente(cliente.getNombre_cliente());
        pedidosDTO.setId_producto(producto.getId_producto());
        pedidosDTO.setNombre_producto(producto.getNombre_producto());
        pedidosDTO.setPrecio_producto(producto.getPrecio_producto());
        return convertirADTO(pedidos, cliente, producto);
    }
    public PedidosDTO actualizar(int id, PedidosRequest request) {
        log.info("Iniciando actualización en el pedido ID: {}. Nuevo valor: {}", id, request.getPedido());
        PedidosModel pedidoExistente = pedidosRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        log.info("Consultando cliente asociado (ID: {}) para verificación en actualización", pedidoExistente.getId_cliente());
        ClientModel clienteExistente = client.obtenerClientes(pedidoExistente.getId_cliente());
        log.info("Consultando producto asociado (ID: {}) para verificación en actualización", pedidoExistente.getId_producto());
        ProductModel productoExistente = product.obtenerProductos(pedidoExistente.getId_producto());
        pedidoExistente.setPedido(request.getPedido());
        clienteExistente.setNombre_cliente(clienteExistente.getNombre_cliente());
        productoExistente.setNombre_producto(productoExistente.getNombre_producto());
        productoExistente.setPrecio_producto(productoExistente.getPrecio_producto());

        log.info("Guardando cambios actualizados para el pedido ID: {}", id);
        PedidosModel actualizado = pedidosRepository.save(pedidoExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        log.info("Ejecutando la eliminación en la base de datos para el pedido ID: {}", id);
        pedidosRepository.deleteById(id);
    }

    private PedidosDTO convertirADTO(PedidosModel pedidos) {
        if(pedidos == null) return null;
        PedidosDTO dto = new PedidosDTO();
        dto.setId(pedidos.getId());
        dto.setPedido(pedidos.getPedido());
        return dto;
    }
    private PedidosDTO convertirADTO(PedidosModel pedidos, ClientModel clientes) {
        if(pedidos == null) return null;
        PedidosDTO dto = new PedidosDTO();
        dto.setId(pedidos.getId());
        dto.setPedido(pedidos.getPedido());
        dto.setId_cliente(clientes.getId_cliente());
        dto.setNombre_cliente(clientes.getNombre_cliente());
        return dto;
    }
    private PedidosDTO convertirADTO(PedidosModel pedidos, ClientModel clientes, ProductModel productos) {
        if(pedidos == null) return null;
        PedidosDTO dto = new PedidosDTO();
        dto.setId(pedidos.getId());
        dto.setPedido(pedidos.getPedido());
        dto.setId_cliente(clientes.getId_cliente());
        dto.setNombre_cliente(clientes.getNombre_cliente());
        dto.setId_producto(productos.getId_producto());
        dto.setNombre_producto(productos.getNombre_producto());
        dto.setPrecio_producto(productos.getPrecio_producto());
        return dto;
    }
    public List<PedidosDTO>buscarPorPedido(String pedido) {
        log.info("Filtrando pedidos que contengan el término: '{}'", pedido);
        return pedidosRepository.findPedidosModelByPedidoContainsIgnoreCase(pedido)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
