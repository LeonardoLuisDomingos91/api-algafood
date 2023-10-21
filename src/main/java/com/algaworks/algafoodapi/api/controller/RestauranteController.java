package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import com.algaworks.algafoodapi.domain.repository.ResturanteRepository;

import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private ResturanteRepository resturanteRepository;
    private CadastroRestauranteService cadastroRestauranteService;
    public RestauranteController(ResturanteRepository resturanteRepository,CadastroRestauranteService cadastroRestauranteService) {
        this.resturanteRepository = resturanteRepository;
        this.cadastroRestauranteService = cadastroRestauranteService;
    }
    @GetMapping
    public List<Restaurante> listar() {
        return resturanteRepository.listar();
    }
    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
       Restaurante restaurante = resturanteRepository.buscar(restauranteId);

       if(restaurante != null) {
           return ResponseEntity.ok(restaurante);
       }

       return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}