package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Cozinha;

import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;

import com.algaworks.algafoodapi.domain.service.CadastroCozinhaService;

import org.springframework.beans.BeanUtils;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/cozinhas", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {
    public CozinhaRepository cozinhaRepository;
    private CadastroCozinhaService cadastroCozinhaService;
    public CozinhaController(CozinhaRepository cozinhaRepository, CadastroCozinhaService cadastroCozinhaService) {
        this.cozinhaRepository = cozinhaRepository;
        this.cadastroCozinhaService = cadastroCozinhaService;
        System.out.println("CozinhaController");
    }
    @GetMapping
    public List<Cozinha> listar() {
       return cozinhaRepository.listar();
    }
    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long cozinhaId) {
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if (cozinha != null) {
            return ResponseEntity.ok(cozinha);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adiconar(@RequestBody Cozinha  cozinha) {
       return cadastroCozinhaService.salvar(cozinha);
    }
    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.buscar(cozinhaId);

        if(cozinhaAtual !=null) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

            cadastroCozinhaService.salvar(cozinhaAtual);
            return ResponseEntity.ok().body(cozinhaAtual);
        }

        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {

        try {
            cadastroCozinhaService.excluir(cozinhaId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (EntidadeEmUsoException EntidadeEmUsoException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}