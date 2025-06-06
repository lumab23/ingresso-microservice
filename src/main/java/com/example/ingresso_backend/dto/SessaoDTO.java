package com.example.ingresso_backend.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SessaoDTO {
    private String id;
    private LocalDateTime horario;
    private Double preco;
    private Integer capacidade;
    private Integer ingressosVendidos;
    private String filmeId;
    private String salaId;
} 