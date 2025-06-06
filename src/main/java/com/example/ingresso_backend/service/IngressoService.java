package com.example.ingresso_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.example.ingresso_backend.exceptions.IngressoNotFoundException;
import com.example.ingresso_backend.model.Ingresso;
import com.example.ingresso_backend.repository.IngressoRepository;
import com.example.ingresso_backend.enums.StatusIngresso;
import com.example.ingresso_backend.dto.SessaoDTO;

@Service
public class IngressoService {
    private final IngressoRepository ingressoRepository;
    private final RestClient restClient;
    private static final String SESSAO_URL = "https://sessao-service.up.railway.app";

    public IngressoService(IngressoRepository ingressoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.restClient = RestClient.create();
    }

    public Ingresso criarIngresso(Ingresso ingresso) {
        try {
            // Busca informações da sessão
            SessaoDTO sessao = restClient.get()
                .uri(SESSAO_URL + "/sessions/" + ingresso.getSessaoId())
                .retrieve()
                .body(SessaoDTO.class);
            
            if (sessao == null) {
                throw new RuntimeException("Sessão não encontrada");
            }
            
            // Verifica se a sessão está lotada
            if (sessao.getIngressosVendidos() >= sessao.getCapacidade()) {
                throw new RuntimeException("A sessão está lotada");
            }
            
            // Atualiza o preço do ingresso com o preço da sessão
            ingresso.setPreco(sessao.getPreco());
            ingresso.setDataCompra(LocalDateTime.now());
            ingresso.setStatus(StatusIngresso.CONFIRMADO);
            
            // Salva o ingresso
            Ingresso ingressoSalvo = ingressoRepository.save(ingresso);
            
            // Atualiza a quantidade de ingressos vendidos na sessão
            restClient.post()
                .uri(SESSAO_URL + "/sessions/" + ingresso.getSessaoId() + "/purchase")
                .retrieve()
                .toBodilessEntity();
            
            return ingressoSalvo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao processar ingresso: " + e.getMessage());
        }
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
