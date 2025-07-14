package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByRestauranteId(Long restauranteId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);
    
    //=== CONSULTAS CUSTOMIZADAS ===
    @Query("SELECT p.restaurante.nome, SUM(p.total) " +
           "FROM Pedido p " +
           "GROUP BY p.restaurante.nome " +
           "ORDER BY SUM(p.total) DESC")
    List<Object[]> calcularTotalVendasPorRestaurante();
    
    @Query("SELECT p FROM Pedido p WHERE p.total > :valor ORDER BY p.total DESC")
    List<Pedido> buscarPedidosComValorAcimaDe(@Param("valor") BigDecimal valor);
    
    @Query("SELECT p FROM Pedido p " +
           "WHERE p.dataPedido BETWEEN :inicio AND :fim " +
           "AND p.status = :status " +
           "ORDER BY p.dataPedido DESC")
    List<Pedido> relatorioPedidosPorPeriodoEStatus(
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("status") StatusPedido status);
}
