package com.example.ingresso_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import com.example.ingresso_backend.model.Ingresso;
import com.example.ingresso_backend.enums.StatusIngresso;

public interface IngressoRepository extends MongoRepository<Ingresso, String> {
    List<Ingresso> findByUsuarioId(String usuarioId);
    List<Ingresso> findBySessaoId(String sessaoId);
    List<Ingresso> findByUsuarioIdAndStatus(String usuarioId, StatusIngresso status);
}
