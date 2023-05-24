package br.ufma.sppg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import br.ufma.sppg.service.exceptions.RegrasRunTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest
class ProducaoServiceTest {

    @Autowired
    ProducaoService producaoService;

    @MockBean
    ProducaoRepository producaoRepository;

    @MockBean
    DocenteRepository docenteRepository;

    @MockBean
    ProgramaRepository programaRepository;

    @MockBean
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
}
