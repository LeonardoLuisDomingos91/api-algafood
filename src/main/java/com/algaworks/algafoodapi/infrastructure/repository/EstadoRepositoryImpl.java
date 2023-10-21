package com.algaworks.algafoodapi.infrastructure.repository;

import com.algaworks.algafoodapi.domain.model.Estado;

import com.algaworks.algafoodapi.domain.repository.EstadoRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;

import java.util.List;
@Component
public class EstadoRepositoryImpl implements EstadoRepository {
    @PersistenceContext
    private EntityManager manager;
    @Override
    public List<Estado> listar() {
        TypedQuery<Estado> query =  manager.createQuery("from Estado ", Estado.class);
        return query.getResultList();
    }
    @Override
    public Estado buscar(Long id) {
        return manager.find(Estado.class, id);
    }
    @Override
    public Estado salvar(Estado estado) {
        return manager.merge(estado);
    }
    @Override
    public void remover(Estado estado) {
        estado = buscar(estado.getId());
        manager.remove(estado);
    }
}