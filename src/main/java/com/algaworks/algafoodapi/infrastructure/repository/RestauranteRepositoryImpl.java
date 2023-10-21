package com.algaworks.algafoodapi.infrastructure.repository;

import com.algaworks.algafoodapi.domain.model.Cozinha;

import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.ResturanteRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class RestauranteRepositoryImpl implements ResturanteRepository {
    @PersistenceContext
    private EntityManager manager;
    @Transactional
    @Override
    public Restaurante salvar(Restaurante restaurante) {
        return manager.merge(restaurante);
    }
    @Override
    public List<Restaurante> listar() {
        TypedQuery<Restaurante> query =  manager.createQuery("from Restaurante ", Restaurante.class);

        return query.getResultList();
    }
    @Override
    public Restaurante buscar(Long id) {
        return manager.find(Restaurante.class, id);
    }
    @Transactional
    @Override
    public void remover(Restaurante restaurante) {
        restaurante = buscar(restaurante.getId());
        manager.remove(restaurante);
    }
}