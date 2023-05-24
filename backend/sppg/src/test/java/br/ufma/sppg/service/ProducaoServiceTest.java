package br.ufma.sppg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.exceptions.RegrasRunTime;

@SpringBootTest
class ProducaoServiceTest {

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
    void obterProducoesPPG_DeveRetornarListaProducoes() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        Programa programa = new Programa();
        programa.setId(idPrograma);
        Docente docente = new Docente();
        Producao producao1 = new Producao();
        producao1.setId(1);
        producao1.setAno(2020);
        Producao producao2 = new Producao();
        producao2.setId(2);
        producao2.setAno(2021);
        List<Docente> docentes = new ArrayList<>();
        docentes.add(docente);
        List<Producao> producoes = new ArrayList<>();
        producoes.add(producao1);
        producoes.add(producao2);

        programaRepository.save(programa);

        programa.setDocentes(docentes);
        docente.setProducoes(producoes);

        Optional<Programa> programaOptional = Optional.of(programa);

        when(programaRepository.findById(idPrograma)).thenReturn(programaOptional);

        List<Producao> result = producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal);

        assertEquals(2, result.size());
        assertTrue(result.contains(producao1));
        assertTrue(result.contains(producao2));
    }

    @Test
    void obterProducoesPPG_SemProducoesDeveLancarExcecao() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        Programa programa = new Programa();
        programa.setId(idPrograma);
        Docente docente = new Docente();
        List<Docente> docentes = new ArrayList<>();
        docentes.add(docente);

        programa.setDocentes(docentes);

        Optional<Programa> programaOptional = Optional.of(programa);
        when(programaRepository.findById(idPrograma)).thenReturn(programaOptional);

        assertThrows(RegrasRunTime.class, () ->
                producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal)
        );
    }

    @Test
    void obterProducoesPPG_ProgramaNaoEncontradoDeveLancarExcecao() {
        Integer idPrograma = 1;
        Integer anoInicial = 2020;
        Integer anoFinal = 2022;

        when(programaRepository.findById(idPrograma)).thenReturn(Optional.empty());

        assertThrows(RegrasRunTime.class, () ->
                producaoService.obterProducoesPPG(idPrograma, anoInicial, anoFinal)
        );
    }

    // Outros testes para os demais métodos do serviço ProducaoService
    @Test
    public void obterProducoesDocente_DeveRetornarListaDeProducoes() {
        // Criar dados de entrada
        Integer idDocente = 1;
        Integer anoInicial = 2019;
        Integer anoFinal = 2021;
        
        // Criar uma lista simulada de produções
        List<Producao> producoesSimuladas = new ArrayList<>();
        producoesSimuladas.add(new Producao());
        producoesSimuladas.add(new Producao());

        // Configurar o comportamento do mock do DocenteRepository
        Docente docenteSimulado = new Docente();
        docenteSimulado.setId(idDocente);
        docenteSimulado.setProducoes(producoesSimuladas);
        when(docenteRepository.findById(idDocente)).thenReturn(Optional.of(docenteSimulado));

        // Executar o método sendo testado
        List<Producao> result = producaoService.obterProducoesDocente(idDocente, anoInicial, anoFinal);

        // Verificar o resultado
        assertEquals(producoesSimuladas.size(), result.size());
    }


    @Test
    public void informarEstatisticasProducao_DeveAtualizarEstatisticasDaProducao() {
        // Criar dados de entrada
        Integer idProducao = 1;
        Integer qtdGrad = 5;
        Integer qtdMestrado = 3;
        Integer qtdDoutorado = 2;

        // Criar um objeto simulado da Producao
        Producao producaoSimulada = new Producao();

        // Configurar o comportamento do mock do ProducaoRepository
        when(producaoRepository.findById(idProducao)).thenReturn(Optional.of(producaoSimulada));

        // Executar o método sendo testado
        producaoService.informarEstatisticasProducao(idProducao, qtdGrad, qtdMestrado, qtdDoutorado);

        // Verificar se as estatísticas foram atualizadas corretamente
        assertEquals(qtdGrad, producaoSimulada.getQtdGrad());
        assertEquals(qtdMestrado, producaoSimulada.getQtdMestrado());
        assertEquals(qtdDoutorado, producaoSimulada.getQtdDoutorado());
    }
    
    @Test
    public void deveObterOrientacaoProducaoExistente() {
        // Criar uma produção de exemplo no banco de dados
        Producao producao = Producao.builder().build();
        producaoRepository.save(producao);

        // Obter as orientações associadas à produção
        List<Orientacao> orientacoes = producaoService.obterOrientacaoProducao(producao.getId());

        // Verificar se as orientações foram obtidas corretamente
        Assertions.assertNotNull(orientacoes);
        Assertions.assertTrue(orientacoes.isEmpty());
    }

}
