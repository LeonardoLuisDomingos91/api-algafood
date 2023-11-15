package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Estado;

import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Service;
@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

//    public CadastroEstadoService(EstadoRepository estadoRepository) {
//        this.estadoRepository = estadoRepository;
//    }
    public Estado buscar(Long estadoId) {

       Estado estado = estadoRepository.findById(estadoId)
               .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Código do estado  informado não existe" +
                       "%d", estadoId)));
       return estado;
    }
    public Estado adicionar(Estado estado) {
       return estadoRepository.save(estado);
    }
    public Estado alterar(Long estadoId, Estado estado) {
        Estado estadoEntity = estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Código do estado  informado não existe" +
                        "%d", estadoId)));

        estadoEntity.setNome(estado.getNome());
        return estadoRepository.save(estadoEntity);
    }

    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida, pois está " +
                    "em uso", estadoId));

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com código %d",
                    estadoId));
        }
    }
}