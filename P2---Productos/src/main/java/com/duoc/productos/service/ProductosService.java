package com.duoc.productos.service;

import com.duoc.productos.dto.ProductosDTO;
import com.duoc.productos.dto.ProductosRequest;
import com.duoc.productos.model.CategoriasModel;
import com.duoc.productos.model.ProductosModel;
import com.duoc.productos.product.CategoriaProducto;
import com.duoc.productos.repository.ProductosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductosService {
    @Autowired
    private ProductosRepository productosRepository;

    @Autowired
    private CategoriaProducto categoriaProducto;

    public ProductosDTO guardar(ProductosRequest request) {
        ProductosModel productos = new ProductosModel();
        productos.setNombre(request.getNombre());
        productos.setPrecio(request.getPrecio());

        ProductosModel guardado = productosRepository.save(productos);
        return convertirADTO(guardado);
    }
    public List<ProductosDTO> listar() {
        return productosRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public ProductosDTO buscarPorId(int id) {
        ProductosModel productos = productosRepository.findById(id).orElseThrow(() -> new NullPointerException("Producto no encontrado por el id: " + id));
        CategoriasModel categoria = categoriaProducto.obtenerCategorias(productos.getId_categoria());
        ProductosDTO clienteDTO = new ProductosDTO();
        clienteDTO.setNombre(productos.getNombre());
        clienteDTO.setPrecio(productos.getPrecio());
        clienteDTO.setId_categoria(categoria.getId());
        clienteDTO.setNombreCategoria(categoria.getNombre());
        return convertirADTO(productos, categoria);
    }
    public ProductosDTO actualizar(int id, ProductosRequest request) {
        ProductosModel productoExistente = productosRepository.findById(id).orElseThrow(() -> new NullPointerException("Producto no encontrado"));
        CategoriasModel categoriaExistente = categoriaProducto.obtenerCategorias(productoExistente.getId_categoria());
        productoExistente.setNombre(request.getNombre());
        productoExistente.setPrecio(request.getPrecio());
        categoriaExistente.setId(categoriaExistente.getId());
        categoriaExistente.setNombre(categoriaExistente.getNombre());

        ProductosModel actualizado = productosRepository.save(productoExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        productosRepository.deleteById(id);
    }

    private ProductosDTO convertirADTO(ProductosModel productos) {
        if(productos == null) return null;
        ProductosDTO dto = new ProductosDTO();
        dto.setId(productos.getId());
        dto.setNombre(productos.getNombre());
        dto.setPrecio(productos.getPrecio());
        return dto;
    }
    private ProductosDTO convertirADTO(ProductosModel productos, CategoriasModel categoria) {
        if(productos == null) return null;
        ProductosDTO dto = new ProductosDTO();
        dto.setId(productos.getId());
        dto.setNombre(productos.getNombre());
        dto.setPrecio(productos.getPrecio());
        dto.setId_categoria(categoria.getId());
        dto.setNombreCategoria(categoria.getNombre());
        return dto;
    }
    public List<ProductosDTO>buscarPorNombre(String nombre) {
        return productosRepository.findProductosModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}