package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    List<Restaurante> findByCategoria(String categoria);
    List<Restaurante> findByAtivoTrue();
    
    @Query("SELECT r.nome as nomeRestaurante, " +
           "SUM(p.total) as totalVendas, " +
           "COUNT(p.id) as quantidePedidos " +
           "FROM Restaurante r " +
           "LEFT JOIN Pedido p ON r.id = p.restaurante.id " +
           "GROUP BY r.id, r.nome")
    List<RelatorioVendas> relatorioVendasPorRestaurante();
}

