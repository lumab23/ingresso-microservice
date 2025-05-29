package com.example.ingresso_backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.ingresso_backend.enums.StatusIngresso;

/**
 * Modelo de Ingresso que mantém apenas referências para outros microserviços
 * filmeId: referência para o microserviço de filmes
 * sessaoId: referência para o microserviço de filmes (onde a sessão é gerenciada)
 * usuarioId: referência para o usuário que comprou o ingresso
 */
@Data
@Document(collection = "ingressos")
public class Ingresso {
    @Id
    private String id;
    
    @NotNull
    private String sessaoId; // Referência para a sessão no microserviço de filmes
    
    @NotNull
    private String filmeId; // Referência para o filme no microserviço de filmes
    
    @NotNull
    private String usuarioId; // Referência para o usuário
    
    private LocalDateTime dataCompra;
    
    @NotNull
    private Double preco;
    
    @NotNull
    private String codigoAssento;
    
    @NotNull
    private StatusIngresso status; // CONFIRMADO, UTILIZADO, CANCELADO
}
