package com.example.ingresso_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.example.ingresso_backend.model.Ingresso;
import com.example.ingresso_backend.service.IngressoService;
import com.example.ingresso_backend.enums.StatusIngresso;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {
    private final IngressoService ingressoService;

    public IngressoController(IngressoService ingressoService) {
        this.ingressoService = ingressoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Ingresso criar(@Valid @RequestBody Ingresso ingresso) {
        return ingressoService.criarIngresso(ingresso);
    }

    @GetMapping
    public List<Ingresso> listarTodos() {
        return ingressoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Ingresso buscarPorId(@PathVariable String id) {
        return ingressoService.buscarPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Ingresso> buscarPorUsuario(
            @PathVariable String usuarioId,
            @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            try {
                StatusIngresso statusIngresso = StatusIngresso.valueOf(status.toUpperCase());
                return ingressoService.buscarPorUsuarioEStatus(usuarioId, statusIngresso);
            } catch (IllegalArgumentException e) {
                // Se o status não for válido, retorna todos os ingressos do usuário
                return ingressoService.buscarPorUsuario(usuarioId);
            }
        }
        return ingressoService.buscarPorUsuario(usuarioId);
    }

    @PatchMapping("/{id}/status")
    public Ingresso atualizarStatus(
            @PathVariable String id,
            @RequestParam StatusIngresso status) {
        return ingressoService.atualizarStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id) {
        ingressoService.deletarIngresso(id);
    }
}
