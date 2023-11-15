package com.algaworks.algafoodapi.api.controller;

import com.algaworks.algafoodapi.domain.exception.EntidadeNaoEncontradaException;

import com.algaworks.algafoodapi.domain.model.Estado;

import com.algaworks.algafoodapi.domain.repository.EstadoRepository;

import com.algaworks.algafoodapi.domain.service.CadastroEstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = "/estados", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CadastroEstadoService cadastroEstadoService;

//    public EstadoController(EstadoRepository estadoRepository, CadastroEstadoService cadastroEstadoService) {
//        this.estadoRepository = estadoRepository;
//        this.cadastroEstadoService = cadastroEstadoService;
//    }
    @GetMapping
    public List<Estado> listar() {
        return estadoRepository.findAll();
    }
    @GetMapping("/{estadoId}")
    public ResponseEntity<?> buscar(@PathVariable Long estadoId) {

        try {
            Estado estado = cadastroEstadoService.buscar(estadoId);
            return ResponseEntity.status(HttpStatus.OK).body(estado);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
        Estado estadoEntity = cadastroEstadoService.adicionar(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(estadoEntity);
    }
    @PutMapping("/{estadoId}")
    public ResponseEntity<?> alterar(@PathVariable Long estadoId, @RequestBody Estado estado) {

        try {
           Estado estadoEntity = cadastroEstadoService.alterar(estadoId,estado);
           return ResponseEntity.status(HttpStatus.OK).body(estadoEntity);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> excluir(@PathVariable Long estadoId) {

        try {
            cadastroEstadoService.excluir(estadoId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (com.algaworks.algafoodapi.domain.exception.EntidadeEmUsoException EntidadeEmUsoException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }
}