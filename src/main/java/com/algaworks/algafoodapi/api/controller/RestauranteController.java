package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaBadRequestException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Cidade;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

import com.algaworks.algafoodapi.domain.service.CadastroRestauranteService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.util.ReflectionUtils;

import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository resturanteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestauranteService;

//    public RestauranteController(ResturanteRepository resturanteRepository,CadastroRestauranteService cadastroRestauranteService) {
//        this.resturanteRepository = resturanteRepository;
//        this.cadastroRestauranteService = cadastroRestauranteService;
//    }
    @GetMapping
    public List<Restaurante> listar() {
        return resturanteRepository.findAll();
    }
    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
       Optional<Restaurante> restauranteOptional = resturanteRepository.findById(restauranteId);

       if(restauranteOptional.isPresent()) {
           return ResponseEntity.ok(restauranteOptional.get());
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
    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@PathVariable("restauranteId") Long restauranteId,
                                                 @RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestauranteService.atualizar(restauranteId,restaurante);
            return ResponseEntity.status(HttpStatus.OK).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (EntidadeNaoEncontradaBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Cidade> excluir(@PathVariable Long restauranteId) {

        try {
            cadastroRestauranteService.excluir(restauranteId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException EntidadeEmUsoException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
                                              @RequestBody Map<String, Object> campos) {

        Optional<Restaurante> restauranteOptional = resturanteRepository.findById(restauranteId);

        if (restauranteOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restauranteOptional.get());

        return atualizar(restauranteId, restauranteOptional.get());
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        System.out.println(restauranteOrigem);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {

            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(Boolean.TRUE);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }
}