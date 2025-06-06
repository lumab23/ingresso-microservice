package com.example.ingresso_backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ingresso")
public class IngressoConfig {
    private Double precoFixo;
    
    public Double getPrecoFixo() {
        return precoFixo;
    }
    
    public void setPrecoFixo(Double precoFixo) {
        this.precoFixo = precoFixo;
    }
} 