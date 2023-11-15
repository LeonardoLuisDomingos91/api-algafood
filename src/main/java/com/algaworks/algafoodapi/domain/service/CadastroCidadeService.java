package com.algaworks.algafoodapi.domain.service;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaBadRequestException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Cidade;

import com.algaworks.algafoodapi.domain.model.Estado;
import com.algaworks.algafoodapi.domain.repository.CidadeRepository;

import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CadastroCidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoRepository estadoRepository;

//    public CadastroCidadeService(CidadeRepository cidadeRepository, EstadoRepository estadoRepository) {
//        this.cidadeRepository = cidadeRepository;
//        this.estadoRepository = estadoRepository;
//    }
    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }
    public Cidade buscar(Long cidadeId) {

       Cidade cidade = cidadeRepository.findById(cidadeId)
               .orElseThrow(() -> new EntidadeNaoEncontradaException(
                       String.format("Não existe Cidade com código %d", cidadeId)));
       return cidade;
    }
    public Cidade adicionar(Cidade cidade) {

       estadoRepository.findById(cidade.getEstado().getId())
               .orElseThrow(() -> new EntidadeNaoEncontradaBadRequestException(String.format("Não existe Estado com código %d",
                       cidade.getEstado().getId())));

       return cidadeRepository.save(cidade);
    }
    public Cidade atualizar(Long cidadeId, Cidade cidade) {

        Cidade cidadeEntity = cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe Cidade com código %d",
                        cidadeId)));

       Estado estado = estadoRepository.findById(cidade.getEstado().getId())
                .orElseThrow(() -> new EntidadeNaoEncontradaBadRequestException(String.format("Não existe Estado com código %d",
                        cidade.getEstado().getId())));

        cidadeEntity.setNome(cidade.getNome());
        cidadeEntity.setEstado(estado);

        return cidadeRepository.save(cidadeEntity);
    }
    public void excluir(Long cidadeId) {

        try {
            cidadeRepository.deleteById(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está " +
                    "em uso", cidadeId));

        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com código %d",
                    cidadeId));
        }
    }
}