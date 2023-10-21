package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.ResturanteRepository;

import org.springframework.stereotype.Service;
@Service
public class CadastroRestauranteService {
    private ResturanteRepository resturanteRepository;
    private CozinhaRepository cozinhaRepository;
    public CadastroRestauranteService(ResturanteRepository resturanteRepository, CozinhaRepository cozinhaRepository) {
        this.resturanteRepository = resturanteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if(cozinha == null) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com código %d", cozinhaId)
            );
        }
        restaurante.setCozinha(cozinha);
        return resturanteRepository.salvar(restaurante);
    }
}