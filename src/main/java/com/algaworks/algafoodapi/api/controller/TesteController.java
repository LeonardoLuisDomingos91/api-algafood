package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.model.Cozinha;

import com.algaworks.algafoodapi.domain.model.Restaurante;

import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

import com.algaworks.algafoodapi.infrastructure.repository.spec.RestauranteComFreteGratis;
import com.algaworks.algafoodapi.infrastructure.repository.spec.RestauranteComNomeSemelhanteSpec;
import com.algaworks.algafoodapi.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository resturanteRepository;
    @GetMapping("/cozinhas/por-nome")
    public List<Cozinha> cozinhaPorNome(@RequestParam("nome") String nome) {
       return cozinhaRepository.findByNomeContaining(nome);
    }
    @GetMapping("/restaurantes/por-nome")
    public List<Restaurante> restaurantePorNome(@RequestParam("nome") String nome, Long cozinhaId) {
        return resturanteRepository.consultarPorNome(nome, cozinhaId);
    }
    @GetMapping("/restaurantes/por-nome-e-frete")
    public List<Restaurante> restaurantesPorNomeFrete(String nome,
                                                      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
        return resturanteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
    }
    @GetMapping("/restaurantes/por-nome-e-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {

        return resturanteRepository.findComFreteGratis(nome);
    }
    @GetMapping("/restaurantes/primeiro")
    public Optional<Restaurante> restaurantesComFreteGratis() {
        return resturanteRepository.buscarPrimeiro();
    }
}