// Mudança 16/07
package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RestauranteService {
    
    /**
     * Cadastrar novo restaurante
     */
    Restaurante cadastrar(Restaurante restaurante);
    
    /**
     * Buscar restaurante por ID
     */
    Optional<Restaurante> buscarPorId(Long id);
    
    /**
     * Listar todos os restaurantes
     */
    List<Restaurante> listarTodos();
    
    /**
     * Listar apenas restaurantes ativos
     */
    List<Restaurante> listarAtivos();
    
    /**
     * Buscar restaurantes por categoria
     */
    List<Restaurante> buscarPorCategoria(String categoria);
    
    /**
     * Buscar restaurantes por avaliação mínima
     */
    List<Restaurante> buscarPorAvaliacao(BigDecimal minAvaliacao);
    
    /**
     * Buscar restaurantes por taxa de entrega máxima
     */
    List<Restaurante> buscarPorTaxaEntrega(BigDecimal maxTaxa);
    
    /**
     * Atualizar dados do restaurante
     */
    Restaurante atualizar(Long id, Restaurante restauranteAtualizado);
    
    /**
     * Inativar restaurante (soft delete)
     */
    void inativar(Long id);
}
