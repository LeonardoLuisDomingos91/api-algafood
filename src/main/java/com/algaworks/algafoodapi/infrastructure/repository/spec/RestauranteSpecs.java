package com.algaworks.algafoodapi.infrastructure.repository.spec;

import com.algaworks.algafoodapi.domain.model.Restaurante;

import org.springframework.data.jpa.domain.Specification;
public class RestauranteSpecs {
    public static Specification<Restaurante> comFreteGratis() {
        return new RestauranteComFreteGratis();
    }
    public static Specification<Restaurante> comNomeSemelhante(String nome) {
        return new RestauranteComNomeSemelhanteSpec(nome);
    }

}