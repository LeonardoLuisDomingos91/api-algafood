package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
@Service
public class CadastroCozinhaService {
    private CozinhaRepository cozinhaRepository;
    public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
        this.cozinhaRepository = cozinhaRepository;
        System.out.println("CadastroCozinhaService chamado" + cozinhaRepository);
    }
    public Cozinha salvar(Cozinha cozinha) {
       return cozinhaRepository.salvar(cozinha);
    }
    public void excluir(Long cozinhaId) {
        try {
            cozinhaRepository.remover(cozinhaId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida, pois está " +
                    "em uso", cozinhaId));

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d",
                    cozinhaId));
        }
    }
}