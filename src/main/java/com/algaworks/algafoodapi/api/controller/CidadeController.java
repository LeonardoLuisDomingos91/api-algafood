package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaBadRequestException;
import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Cidade;

import com.algaworks.algafoodapi.domain.service.CadastroCidadeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

//    public CidadeController(CadastroCidadeService cadastroCidadeService) {
//        this.cadastroCidadeService = cadastroCidadeService;
//    }
    @GetMapping
    public List<Cidade> listar() {
        return cadastroCidadeService.listar();
    }
    @GetMapping("/{cidadeId}")
    public ResponseEntity<?> buscar(@PathVariable Long cidadeId) {
        try {
           Cidade cidade = cadastroCidadeService.buscar(cidadeId);
            return ResponseEntity.status(HttpStatus.OK).body(cidade);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
        try {
            cidade = cadastroCidadeService.adicionar(cidade);
            return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
        } catch (EntidadeNaoEncontradaBadRequestException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PutMapping("/{cidadeId}")
    public ResponseEntity<?> atualizar(@PathVariable("cidadeId") Long cidadeId, @RequestBody Cidade cidade) {

        try {
            cidade = cadastroCidadeService.atualizar(cidadeId, cidade);
            return ResponseEntity.status(HttpStatus.OK).body(cidade);

        } catch (EntidadeNaoEncontradaBadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> excluir(@PathVariable Long cidadeId) {

        try {
            cadastroCidadeService.excluir(cidadeId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (EntidadeEmUsoException EntidadeEmUsoException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

}