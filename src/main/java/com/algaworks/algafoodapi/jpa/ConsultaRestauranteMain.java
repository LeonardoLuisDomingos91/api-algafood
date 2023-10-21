package com.algaworks.algafoodapi.jpa;

import com.algaworks.algafoodapi.AlgafoodApiApplication;
import com.algaworks.algafoodapi.domain.model.Cozinha;
import com.algaworks.algafoodapi.domain.model.Restaurante;
import com.algaworks.algafoodapi.domain.repository.CozinhaRepository;
import com.algaworks.algafoodapi.domain.repository.ResturanteRepository;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class ConsultaRestauranteMain {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        ResturanteRepository resturanteRepository = applicationContext.getBean(ResturanteRepository.class);

        List<Restaurante> restaurantes = resturanteRepository.listar();

        restaurantes.forEach(restaurante -> System.out.printf("%s - %f -%s\n", restaurante.getNome(),
                restaurante.getTaxaFrete(), restaurante.getCozinha().getNome()));

    }


}
