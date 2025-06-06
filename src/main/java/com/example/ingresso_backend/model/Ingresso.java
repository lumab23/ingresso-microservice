package com.example.ingresso_backend.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.example.ingresso_backend.enums.StatusIngresso;


@Data
@Document(collection = "ingressos")
public class Ingresso {
    @Id
    private String id;
    
    @NotNull
    private String sessaoId; 
    
    @NotNull
    private String usuarioId; 
    
    private LocalDateTime dataCompra;
    
    @NotNull
    private Double preco;
    
    @NotNull
    private String codigoAssento;
    
    @NotNull
    private StatusIngresso status; // CONFIRMADO, UTILIZADO, CANCELADO
}
