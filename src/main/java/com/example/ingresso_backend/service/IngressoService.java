package com.example.ingresso_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.example.ingresso_backend.exceptions.IngressoNotFoundException;
import com.example.ingresso_backend.model.Ingresso;
import com.example.ingresso_backend.repository.IngressoRepository;
import com.example.ingresso_backend.enums.StatusIngresso;

@Service
public class IngressoService {
    private final IngressoRepository ingressoRepository;

    public IngressoService(IngressoRepository ingressoRepository) {
        this.ingressoRepository = ingressoRepository;
    }

    public Ingresso criarIngresso(Ingresso ingresso) {
        ingresso.setDataCompra(LocalDateTime.now());
        ingresso.setStatus(StatusIngresso.CONFIRMADO); // Novo ingresso sempre começa como CONFIRMADO
        return ingressoRepository.save(ingresso);
    }

    public List<Ingresso> listarTodos() {
        return ingressoRepository.findAll();
    }

    public Ingresso buscarPorId(String id) {
        return ingressoRepository.findById(id)
            .orElseThrow(() -> new IngressoNotFoundException("Ingresso não encontrado"));
    }

    public List<Ingresso> buscarPorUsuario(String usuarioId) {
        return ingressoRepository.findByUsuarioId(usuarioId);
    }

    public List<Ingresso> buscarPorUsuarioEStatus(String usuarioId, StatusIngresso status) {
        return ingressoRepository.findByUsuarioIdAndStatus(usuarioId, status);
    }

    public Ingresso atualizarStatus(String id, StatusIngresso novoStatus) {
        return ingressoRepository.findById(id)
                .map(ingresso -> {
                    ingresso.setStatus(novoStatus);
                    return ingressoRepository.save(ingresso);
                })
                .orElseThrow(() -> new IngressoNotFoundException("Ingresso não encontrado"));
    }

    public void deletarIngresso(String id) {
        if (!ingressoRepository.existsById(id)) {
            throw new IngressoNotFoundException("Ingresso não encontrado");
        }
        ingressoRepository.deleteById(id);
    }
}
