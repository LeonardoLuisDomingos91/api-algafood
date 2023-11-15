package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaBadRequestException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;

import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.RestauranteRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Service;
@Service
public class CadastroRestauranteService {

    @Autowired
    private RestauranteRepository resturanteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

//    public CadastroRestauranteService(ResturanteRepository resturanteRepository, CozinhaRepository cozinhaRepository) {
//        this.resturanteRepository = resturanteRepository;
//        this.cozinhaRepository = cozinhaRepository;
//    }
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe cadastro de cozinha com código %d", cozinhaId)));

        restaurante.setCozinha(cozinha);

        return resturanteRepository.save(restaurante);
    }
    public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {

        Restaurante restauranteEntity = resturanteRepository.findById(restauranteId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não existe Restaurante com código %d", restauranteId)
                ));

        cozinhaRepository.findById(restaurante.getCozinha().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaBadRequestException(
                        String.format("Não existe cadastro de cozinha com código %d", restaurante.getCozinha().getId())));

        restauranteEntity.setCozinha(restaurante.getCozinha());
        restauranteEntity.setNome(restaurante.getNome());
        restauranteEntity.setTaxaFrete(restaurante.getTaxaFrete());

       return resturanteRepository.save(restauranteEntity);

    }
    public void excluir(Long restauranteId) {

        try {
            resturanteRepository.deleteById(restauranteId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removida, pois está " +
                    "em uso", restauranteId));

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de restaurante com código %d",
                    restauranteId));
        }
    }
}