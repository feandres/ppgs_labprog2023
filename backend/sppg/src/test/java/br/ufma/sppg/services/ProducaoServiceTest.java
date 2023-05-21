package br.ufma.sppg.services;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.ProducaoService;


@SpringBootTest
public class ProducaoServiceTest {
    @Autowired
    ProducaoService producaoService;

    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    @Test
    public void deveRecuperarProducoesPorPrograma() throws ParseException {
    //Cenario:
        //Programa
        Programa novoPPg = Programa.builder().nome("Programa para Teste").build();
        //Docente
        Docente novoDocente = Docente.builder().nome("Geraldo Braz Junior")
                .lattes("123")
                .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                .producoes(null)
                .build();
        //Producao
        Producao novaProducao = Producao.builder().tipo("tipo_teste")
                .ano(2019)
                .issnOuSigla("sigla_teste")
                .nomeLocal("nome_local_teste")
                .titulo("titulo_teste")
                .qualis("B1")
                .qtdGrad(900)
                .qtdMestrado(300)
                .qtdDoutorado(10)
                .percentileOuH5(10)
                .build();

                Producao novaProducao2 = Producao.builder().tipo("tipo_teste2")
                .ano(2011)
                .issnOuSigla("sigla_teste2")
                .nomeLocal("nome_local_teste2")
                .titulo("titulo_teste2")
                .qualis("B1")
                .qtdGrad(900)
                .qtdMestrado(300)
                .qtdDoutorado(10)
                .percentileOuH5(10)
                .build();
                
                Producao novaProducao3 = Producao.builder().tipo("tipo_teste3")
                .ano(2019)
                .issnOuSigla("sigla_teste3")
                .nomeLocal("nome_local_teste3")
                .titulo("titulo_teste3")
                .qualis("B1")
                .qtdGrad(900)
                .qtdMestrado(300)
                .qtdDoutorado(10)
                .percentileOuH5(10)
                .build();

                Producao novaProducao4 = Producao.builder().tipo("tipo_teste4")
                .ano(2023)
                .issnOuSigla("sigla_teste4")
                .nomeLocal("nome_local_teste4")
                .titulo("titulo_teste4")
                .qualis("B1")
                .qtdGrad(900)
                .qtdMestrado(300)
                .qtdDoutorado(10)
                .percentileOuH5(10)
                .build();
        
    //Acao:
        //Save
        Programa programaSalvo = programaRepository.save(novoPPg);
        Docente docenteSalvo = docenteRepository.save(novoDocente);
        Producao producaoSalva = producaoRepository.save(novaProducao);
        //
        
        


    }
}
