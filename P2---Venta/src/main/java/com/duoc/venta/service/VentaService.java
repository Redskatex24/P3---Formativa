package com.duoc.venta.service;

import com.duoc.venta.client.Client;
import com.duoc.venta.dto.VentaDTO;
import com.duoc.venta.dto.VentaRequest;
import com.duoc.venta.exception.VentaNotFoundException;
import com.duoc.venta.model.*;
import com.duoc.venta.order.Order;
import com.duoc.venta.product.Product;
import com.duoc.venta.repository.VentaRepository;
import com.duoc.venta.saleman.Saleman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaService {
    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private Product product;

    @Autowired
    private Client client;

    @Autowired
    private Order pedido;

    @Autowired
    private Saleman vendedor;

    public VentaDTO guardar(VentaRequest request) {
        VentaModel venta = new VentaModel();
        venta.setVenta(request.getVenta());

        VentaModel guardado = ventaRepository.save(venta);
        return convertirADTO(guardado);
    }
    public List<VentaDTO> listar() {
        return ventaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public VentaDTO buscarPorId(int id) {
        VentaModel venta = ventaRepository.findById(id).orElseThrow(() -> new VentaNotFoundException(id));
        ClientModel cliente = client.obtenerClientes(venta.getId_cliente());
        ProductModel productos = product.obtenerProductos(venta.getId_producto());
        OrderModel pedidos = pedido.obtenerPedidos(venta.getId_pedido());
        SalemanModel vendedores = vendedor.obtenerVendedores(venta.getId_vendedor());
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setVenta(venta.getVenta());
        ventaDTO.setId_cliente(cliente.getId());
        ventaDTO.setNombre_cliente(cliente.getNombre());
        ventaDTO.setId_producto(productos.getId());
        ventaDTO.setNombre_producto(productos.getNombre());
        ventaDTO.setPrecio_producto(productos.getPrecio());
        ventaDTO.setId_pedido(pedidos.getId());
        ventaDTO.setPedido(pedidos.getPedido());
        ventaDTO.setId_vendedor(vendedores.getId());
        ventaDTO.setNombre_vendedor(vendedores.getNombre());
        return convertirADTO(cliente, productos, pedidos, vendedores);
    }
    public VentaDTO actualizar(int id, VentaRequest request) {
        VentaModel ventaExistente = ventaRepository.findById(id).orElseThrow(() -> new VentaNotFoundException(id));
        ClientModel clienteExistente = client.obtenerClientes(ventaExistente.getId_cliente());
        ProductModel productoExistente = product.obtenerProductos(ventaExistente.getId_producto());
        OrderModel pedidosExistente = pedido.obtenerPedidos(ventaExistente.getId_pedido());
        SalemanModel vendedoresExistente = vendedor.obtenerVendedores(ventaExistente.getId_vendedor());
        ventaExistente.setVenta(request.getVenta());
        clienteExistente.setId(clienteExistente.getId());
        clienteExistente.setNombre(clienteExistente.getNombre());
        productoExistente.setId(productoExistente.getId());
        productoExistente.setNombre(productoExistente.getNombre());
        productoExistente.setPrecio(productoExistente.getPrecio());
        pedidosExistente.setId(pedidosExistente.getId());
        pedidosExistente.setPedido(pedidosExistente.getPedido());
        vendedoresExistente.setId(vendedoresExistente.getId());
        vendedoresExistente.setNombre(vendedoresExistente.getNombre());

        VentaModel actualizado = ventaRepository.save(ventaExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        ventaRepository.deleteById(id);
    }

    private VentaDTO convertirADTO(VentaModel venta) {
        if(venta == null) return null;
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setVenta(venta.getVenta());
        return dto;
    }
    private VentaDTO convertirADTO(VentaModel venta, ClientModel clientes, ProductModel producto, OrderModel order,SalemanModel salemnan) {
        if(venta == null) return null;
        VentaDTO dto = new VentaDTO();
        dto.setId(venta.getId());
        dto.setVenta(venta.getVenta());
        dto.setId_cliente(clientes.getId());
        dto.setNombre_cliente(clientes.getNombre());
        dto.setId_producto(producto.getId());
        dto.setNombre_producto(producto.getNombre());
        dto.setPrecio_producto(producto.getPrecio());
        dto.setId_pedido(order.getId());
        dto.setPedido(order.getPedido());
        dto.setId_vendedor(salemnan.getId());
        dto.setNombre_vendedor(salemnan.getNombre());
        return dto;
    }

    //private VentaDTO convertirADTO(VentaModel venta, ClientModel clientes, ProductModel producto, OrderModel order, SalemanModel saleman) {
      //  if (venta == null) return null;
      //  VentaDTO dto = new VentaDTO();
        //dto.setId(venta.getId());
        //dto.setVenta(venta.getVenta());
        //dto.setId_cliente(clientes.getId());
        //dto.setNombre_cliente(clientes.getNombre());
        //dto.setId_producto(producto.getId());
        //dto.setNombre_producto(producto.getNombre());
        //dto.setPrecio_producto(producto.getPrecio());
        //dto.setId_pedido(order.getId());
        //dto.setPedido(order.getPedido());
        //dto.setId_vendedor(saleman.getId());
        //dto.setNombre_vendedor(saleman.getNombre());
        //return dto;
    public List<VentaDTO>buscarPorVenta(String venta) {
        return ventaRepository.findVentaModelByVentaContainsIgnoreCase(venta)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
