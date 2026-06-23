package com.duoc.clientes.service;

import com.duoc.clientes.dto.ClientesDTO;
import com.duoc.clientes.dto.ClientesRequest;
import com.duoc.clientes.exeption.ClientesNotFoundException;
import com.duoc.clientes.model.CategoriasModel;
import com.duoc.clientes.model.ClientesModel;
import com.duoc.clientes.repository.ClientesRepository;
import com.duoc.clientes.client.CategoriaCliente;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientesService {
    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private CategoriaCliente categoriaCliente;

    public ClientesDTO guardar(ClientesRequest request) {
        ClientesModel clientes = new ClientesModel();
        clientes.setNombre(request.getNombre());
        clientes.setNumero(request.getNumero());

        ClientesModel guardado = clientesRepository.save(clientes);
        log.info("Cliente almacenado correctamente: " + clientes);
        return convertirADTO(guardado);
    }
    public List<ClientesDTO> listar() {
        return clientesRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    public ClientesDTO buscarPorId(int id) {
        ClientesModel clientes = clientesRepository.findById(id).orElseThrow(() -> new ClientesNotFoundException(id));
        CategoriasModel categoria = categoriaCliente.obtenerCategorias(clientes.getId_categoria());
        ClientesDTO clienteDTO = new ClientesDTO();
        clienteDTO.setNombre(clientes.getNombre());
        clienteDTO.setNumero(clientes.getNumero());
        clienteDTO.setId_categoria(categoria.getId());
        clienteDTO.setNombreCategoria(categoria.getNombre());
        return convertirADTO(clientes, categoria);
    }
    public ClientesDTO actualizar(int id, ClientesRequest request) {
        ClientesModel clienteExistente = clientesRepository.findById(id).orElseThrow(() -> new ClientesNotFoundException(id));
        CategoriasModel categoriaExistente = categoriaCliente.obtenerCategorias(clienteExistente.getId_categoria());
        clienteExistente.setNombre(request.getNombre());
        clienteExistente.setNumero(request.getNumero());
        categoriaExistente.setId(categoriaExistente.getId());
        categoriaExistente.setNombre(categoriaExistente.getNombre());

        ClientesModel actualizado = clientesRepository.save(clienteExistente);
        return convertirADTO(actualizado);
    }

    public void eliminar(int id) {
        clientesRepository.deleteById(id);
    }

    private ClientesDTO convertirADTO(ClientesModel clientes) {
        if(clientes == null) return null;
        ClientesDTO dto = new ClientesDTO();
        dto.setId(clientes.getId());
        dto.setNombre(clientes.getNombre());
        dto.setNumero(clientes.getNumero());
        return dto;
    }

    private ClientesDTO convertirADTO(ClientesModel clientes, CategoriasModel categorias) {
        if(clientes == null) return null;
        ClientesDTO dto = new ClientesDTO();
        dto.setId(clientes.getId());
        dto.setNombre(clientes.getNombre());
        dto.setNumero(clientes.getNumero());
        dto.setId_categoria(categorias.getId());
        dto.setNombreCategoria(categorias.getNombre());
        return dto;
    }
    public List<ClientesDTO> buscarPorNombre(String nombre) {
        return clientesRepository.findClientesModelByNombreContainsIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}