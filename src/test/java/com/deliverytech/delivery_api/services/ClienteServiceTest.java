package com.deliverytech.delivery_api.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.deliverytech.delivery_api.dto.request.ClienteRequest;
import com.deliverytech.delivery_api.dto.response.ClienteResponse;
import com.deliverytech.delivery_api.model.Cliente;
import com.deliverytech.delivery_api.exception.BusinessException;
import com.deliverytech.delivery_api.exception.ConflictException;
import com.deliverytech.delivery_api.exception.EntityNotFoundException;
import com.deliverytech.delivery_api.projection.RelatorioVendasClientes;
import com.deliverytech.delivery_api.repository.ClienteRepository;
import com.deliverytech.delivery_api.service.ClienteService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceTest {

    @MockBean
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Test
    @DisplayName("Cadastrar um cliente deve retornar um Cliente DTO")
    void testCadastrarCliente_DeveRetornarClienteResponseDTO() {
        ClienteRequest request = new ClienteRequest();
        request.setEmail("teste@email.com");
        request.setEndereco("Av Teste");
        request.setNome("teste");
        request.setTelefone("123456789");

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome(request.getNome());
        clienteMock.setEmail(request.getEmail());
        clienteMock.setTelefone(request.getTelefone());
        clienteMock.setEndereco(request.getEndereco());
        clienteMock.setDataCriacao(LocalDateTime.now());
        clienteMock.setAtivo(true);

        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        Cliente resultado = clienteService.cadastrar(request);

        assertNotNull(resultado);
        assertEquals("teste", resultado.getNome());
    }

    @Test
    @DisplayName("Cadastrar um cliente deve retornar erro que o e-mail ja existe")
    void testCadastrarCliente_DeveRetornarErroEmailJaExiste() {
        ClienteRequest request = new ClienteRequest();
        request.setEmail("teste@email.com");
        request.setEndereco("Av Teste");
        request.setNome("teste");
        request.setTelefone("123456789");

        when(clienteRepository.existsByEmail(request.getEmail())).thenReturn(true);

        var result = assertThrows(ConflictException.class,
                () -> clienteService.cadastrar(request));

        assertEquals("Cliente com email já existe", result.getMessage());
    }

    @Test
    @DisplayName("Buscar o cliente com ID deve retornar um Cliente DTO")
    void testBuscarClientePorId_DeveRetornarClienteResponseDTO() {
        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome("teste");
        clienteMock.setEmail("teste@email.com");
        clienteMock.setTelefone("123456789");
        clienteMock.setEndereco("Av Teste");
        clienteMock.setDataCriacao(LocalDateTime.now());
        clienteMock.setAtivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        
        Optional<Cliente> resultado = clienteService.buscarPorId(1L);
        
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals("teste", resultado.get().getNome());
    }

    @Test
    @DisplayName("Buscar o cliente por e-mail deve retornar um Cliente DTO")
    void testBuscarClientePorEmail_DeveRetornarClienteResponseDTO() {
        Cliente clienteMock = new Cliente(1L, "teste", "teste@email.com", "123456789",
                        "Av Teste", LocalDateTime.now(), true, null);
        when(clienteRepository.findByEmail(clienteMock.getEmail()))
                        .thenReturn(Optional.of(clienteMock));
        Optional<Cliente> resultado =
                        clienteService.buscarPorEmail(clienteMock.getEmail());
        assertNotNull(resultado);
        assertTrue(resultado.isPresent());
        assertEquals("teste@email.com", resultado.get().getEmail());
    }

    @Test
    @DisplayName("Atualizar um cliente deve retornar um Cliente DTO")
    void testAtualizarCliente_DeveRetornarClienteResponseDTO() {
        ClienteRequest request = new ClienteRequest();
        request.setEmail("teste@email.com");
        request.setEndereco("Av Teste");
        request.setNome("teste");
        request.setTelefone("123456789");

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome(request.getNome());
        clienteMock.setEmail(request.getEmail());
        clienteMock.setTelefone(request.getTelefone());
        clienteMock.setEndereco(request.getEndereco());
        clienteMock.setDataCriacao(LocalDateTime.now());
        clienteMock.setAtivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(clienteRepository.existsByEmail(clienteMock.getEmail())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        Cliente resultado = clienteService.atualizar(1L, request);

        assertNotNull(resultado);
        assertEquals("teste", resultado.getNome());
    }

    @Test
    @DisplayName("Atualizar um cliente deve retornar erro que o e-mail ja existe")
    void testAtualizarCliente_DeveRetornarErroEmailJaExiste() {
        ClienteRequest request = new ClienteRequest();
        request.setEmail("teste@email.com");
        request.setEndereco("Av Teste");
        request.setNome("teste");
        request.setTelefone("123456789");

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome(request.getNome());
        clienteMock.setEmail(request.getEmail());
        clienteMock.setTelefone(request.getTelefone());
        clienteMock.setEndereco(request.getEndereco());
        clienteMock.setDataCriacao(LocalDateTime.now());
        clienteMock.setAtivo(true);

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        clienteMock.setEmail("teste@email.com2");
        when(clienteRepository.existsByEmail(request.getEmail())).thenReturn(true);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteMock);

        var result = assertThrows(ConflictException.class,
                        () -> clienteService.atualizar(1L, request));

        assertEquals("Cliente com email já existe", result.getMessage());
    }

    @Test
    @DisplayName("Atualizar um cliente deve retornar erro cliente não foi encontrado")
    void testAtualizarCliente_DeveRetornarErroNaoEncontrado() {
        ClienteRequest request = new ClienteRequest();
        request.setEmail("teste@email.com");
        request.setEndereco("Av Teste");
        request.setNome("teste");
        request.setTelefone("123456789");

        Cliente clienteMock = new Cliente();
        clienteMock.setId(1L);
        clienteMock.setNome(request.getNome());
        clienteMock.setEmail(request.getEmail());
        clienteMock.setTelefone(request.getTelefone());
        clienteMock.setEndereco(request.getEndereco());
        clienteMock.setDataCriacao(LocalDateTime.now());
        clienteMock.setAtivo(true);

        when(clienteRepository.existsByEmail(clienteMock.getEmail()))
                        .thenReturn(true);
        when(clienteRepository.save(any(Cliente.class)))
                        .thenReturn(clienteMock);

        var result = assertThrows(EntityNotFoundException.class,
                        () -> clienteService.atualizar(1L, request));

        assertEquals("Cliente com ID 1 não encontrado", result.getMessage());
    }

    @Test
    @DisplayName("Ativar / Desativar um cliente deve retornar um Cliente DTO")
    void testToggleStatusCliente_DeveRetornarClienteDTO() {

            Cliente clienteMock = new Cliente(1L, "teste", "teste@email.com", "123456789",
                            "Av Teste", LocalDateTime.now(), true, null);

            when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
            when(clienteRepository.save(any(Cliente.class)))
                            .thenReturn(clienteMock);
            var result = clienteService.ativarDesativarCliente(1L);

            assertEquals(false, result.getAtivo());
    }

    @Test
    @DisplayName("Ativar / Desativar um cliente deve retornar erro cliente não foi encontrado")
    void testToggleStatusCliente_DeveRetornarErroNaoEncontrado() {

            var result = assertThrows(EntityNotFoundException.class,
                            () -> clienteService.ativarDesativarCliente(1L));

            assertEquals("Cliente com ID 1 não encontrado", result.getMessage());
    }

    @Test
    @DisplayName("Listar clientes ativos deve retornar uma Lista de Cliente DTO")
    void testListarClientesAtivos_DeveRetornarListaClienteDTO() {

            Cliente cliente = new Cliente();
            cliente.setId(1L);
            cliente.setNome("teste");
            cliente.setEmail("teste@email.com");
            cliente.setTelefone("123456789");
            cliente.setEndereco("Av Teste");
            cliente.setDataCriacao(LocalDateTime.now());
            cliente.setAtivo(true);

            List<Cliente> clientesAtivosMock = List.of(cliente);

            when(clienteRepository.findByAtivoTrue()).thenReturn(clientesAtivosMock);
            var result = clienteService.listarAtivos();

            assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Listar clientes por nome deve retornar uma Lista de Cliente DTO")
    void testListarPorNome_DeveRetornarListaClienteDTO() {

            Cliente cliente1 = new Cliente();
            cliente1.setId(1L);
            cliente1.setNome("teste");
            cliente1.setEmail("teste@email.com");
            cliente1.setTelefone("123456789");
            cliente1.setEndereco("Av Teste");
            cliente1.setDataCriacao(LocalDateTime.now());
            cliente1.setAtivo(true);

            Cliente cliente2 = new Cliente();
            cliente2.setId(2L);
            cliente2.setNome("teste novo");
            cliente2.setEmail("teste@email2.com");
            cliente2.setTelefone("123456789");
            cliente2.setEndereco("Av Teste");
            cliente2.setDataCriacao(LocalDateTime.now());
            cliente2.setAtivo(false);

            List<Cliente> clientesMock = List.of(cliente1, cliente2);

            when(clienteRepository.findByNomeContainingIgnoreCase("tEste")).thenReturn(clientesMock);
            var result = clienteService.buscarPorNome("tEste");

            assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Listar clientes por nome deve retornar vazio")
    void testListarPorNome_DeveRetornarVazio() {

            when(clienteRepository.findByNomeContainingIgnoreCase("tEste")).thenReturn(List.of());
            
            var result = clienteService.buscarPorNome("tEste");
            
            assertEquals(0, result.size());
    }
}
