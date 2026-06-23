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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;

    @Autowired
    private Product product;

    @Autowired
    private Client client;

    public PedidosDTO guardar(PedidosRequest request) {
        PedidosModel pedidos = new PedidosModel();
        pedidos.setPedido(request.getPedido());

        PedidosModel guardado = pedidosRepository.save(pedidos);
        return convertirADTO(guardado);
    }
    public List<PedidosDTO> listar() {
        return pedidosRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public PedidosDTO buscarPorId(int id) {
        PedidosModel pedidos = pedidosRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        ClientModel cliente = client.obtenerClientes(pedidos.getId_cliente());
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
        PedidosModel pedidoExistente = pedidosRepository.findById(id).orElseThrow(() -> new PedidoNotFoundException(id));
        ClientModel clienteExistente = client.obtenerClientes(pedidoExistente.getId_cliente());
        ProductModel productoExistente = product.obtenerProductos(pedidoExistente.getId_producto());
        pedidoExistente.setPedido(request.getPedido());
        clienteExistente.setNombre_cliente(clienteExistente.getNombre_cliente());
        productoExistente.setNombre_producto(productoExistente.getNombre_producto());
        productoExistente.setPrecio_producto(productoExistente.getPrecio_producto());

        PedidosModel actualizado = pedidosRepository.save(pedidoExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
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
        return pedidosRepository.findPedidosModelByPedidoContainsIgnoreCase(pedido)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
