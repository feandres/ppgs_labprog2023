package br.ufma.sppg.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.repo.TecnicaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class DocenteTest {
    
    @Autowired
    DocenteRepository repo;

    @Autowired
    ProgramaRepository prog;

    @Autowired
    OrientacaoRepository orient;

    @Autowired
    TecnicaRepository tecn;

    @Autowired
    ProducaoRepository prod;
    
    @Test
    public void deveSalvarDocente() throws ParseException {
        //cenario
            Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                                .lattes("123")
                                                .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                                .build();
        //acao
            Docente docSalvo = repo.save(novDocente);
                                                
        //teste
            Assertions.assertNotNull(docSalvo);
            Assertions.assertEquals(novDocente.getId(), docSalvo.getId());
            Assertions.assertEquals(novDocente.getNome(), docSalvo.getNome());
            Assertions.assertEquals(novDocente.getDataAtualizacao(), docSalvo.getDataAtualizacao());


    }

    @Test
    public void deveSalvarDocentComPrograma() throws ParseException {
        //cenário
        Programa novoPPg = Programa.builder().nome("PPGCC").build();

        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy"))
                                        .build();
        
        Programa progSalvo = prog.save(novoPPg);
        Docente docSalvo = repo.save(novDocente);

        //ação
        List<Programa> programas = new ArrayList<Programa>();
        programas.add(progSalvo);
        docSalvo.setProgramas(programas);
        
        Docente docSalvo2 = repo.save(docSalvo);

        //teste
        Assertions.assertNotNull(docSalvo2);
        Assertions.assertEquals(docSalvo2.getProgramas().size(), 1);

    }

    @Test
    public void deveSalvarDocenteComOrientacao() throws ParseException {
        //cenario
        Docente novDocente = Docente.builder().nome("Geraldo Braz Junior")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Orientacao novaOrientacao = Orientacao.builder().id(1).tipo("teste").discente("teste_disc")
                                        .titulo("teste_titulo")
                                        .ano(2023)
                                        .modalidade("teste_modalidade")
                                        .instituicao("teste_orientacao")
                                        .curso("teste_curso")
                                        .status("teste_status")
                                        .orientador(novDocente).build();


        Docente docSalvo = repo.save(novDocente);        
        Orientacao orientSalvo = orient.save(novaOrientacao);
        
        //acao
        ArrayList<Orientacao> orientacoes = new ArrayList<>();
        orientacoes.add(orientSalvo);
        docSalvo.setOrientacoes(orientacoes);

        //teste
        Assertions.assertNotNull(docSalvo);
        Assertions.assertEquals(docSalvo.getOrientacoes().size(), 1);
    }

    @Test
    public void deveSalvarDocenteComTecnica() throws ParseException {
        //cenario
        Docente novDocente = Docente.builder()
                                            .nome("Geraldo Braz Junior")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Tecnica novTecnica = Tecnica.builder()
                                            .tipo("teste_tipo")
                                            .titulo("teste_titulo")
                                            .ano(2023)
                                            .financiadora("teste_financiadora")
                                            .outrasInformacoes("teste_outrasInformacoes")
                                            .qtdGrad(1)
                                            .qtdMestrado(2)
                                            .qtdDoutorado(3).build();

        Docente docSalvo = repo.save(novDocente);
        Tecnica tecSalvo = tecn.save(novTecnica);

        //acao
        ArrayList<Tecnica> tecnicas = new ArrayList<>();
        tecnicas.add(tecSalvo);
        docSalvo.setTecnicas(tecnicas);

        //teste
        Assertions.assertNotNull(docSalvo);
        Assertions.assertEquals(docSalvo.getTecnicas().size(), 1);
    }


    @Test
    public void deveSalvarDocenteComProducao () throws ParseException {
        //cenário
        Producao novaProducao = Producao.builder()
                                                .tipo("teste tipo producao")
                                                .titulo("teste titulo producao")
                                                .ano(2023)
                                                .build();
        Docente novDocente = Docente.builder()
                                            .nome("Mikael Barros")
                                            .lattes("123")
                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                            .build();

        Producao prodSalva = prod.save(novaProducao);
        Docente docSalvo = repo.save(novDocente);


        //ação
        ArrayList<Producao> producoes = new ArrayList<>();
        producoes.add(prodSalva);
        docSalvo.setProducoes(producoes);

        Docente docSalvo2 = repo.save(docSalvo);

        //teste
        Assertions.assertNotNull(docSalvo2);
        Assertions.assertEquals(docSalvo2.getProducoes().size(), 1);
    }

    @Test
    public void deveAtualizarDataAtualizacaoDocente () throws ParseException {
        //cenario
        Docente novDocente = Docente.builder().nome("Mikael Barros")
                                        .lattes("123")
                                        .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
                                        .build();

        Docente docSalvo = repo.save(novDocente);

        //acao
        docSalvo.setNome("nome_teste");
        docSalvo.setDataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("08/05/2023"));

        //teste
        Assertions.assertEquals(docSalvo.getDataAtualizacao(), new SimpleDateFormat("dd/MM/yyyy").parse("08/05/2023"));
    }

//    @Test
//    public void deveImpedirRemoverDocenteComDependencia() throws ParseException {
//        //cenario
//        Docente novDocente = Docente.builder()
//                                            .nome("Geraldo Braz Junior")
//                                            .lattes("123")
//                                            .dataAtualizacao(new SimpleDateFormat("dd/MM/yyyy").parse("23/04/2023"))
//                                            .build();
//
//        Tecnica novTecnica = Tecnica.builder()
//                                            .tipo("teste_tipo")
//                                            .titulo("teste_titulo")
//                                            .ano(2023)
//                                            .financiadora("teste_financiadora")
//                                            .outrasInformacoes("teste_outrasInformacoes")
//                                            .qtdGrad(1)
//                                            .qtdMestrado(2)
//                                            .qtdDoutorado(3).build();
//
//        Orientacao novaOrientacao = Orientacao.builder()
//                                            .tipo("teste")
//                                            .discente("teste_disc")
//                                            .titulo("teste_titulo")
//                                            .ano(2023)
//                                            .modalidade("teste_modalidade")
//                                            .instituicao("teste_orientacao")
//                                            .curso("teste_curso")
//                                            .status("teste_status")
//                                            .orientador(novDocente).build();
//
//        Producao novaProducao = Producao.builder()
//                                            .tipo("teste tipo producao")
//                                            .titulo("teste titulo producao")
//                                            .ano(2023)
//                                            .build();
//
//        Programa novoPPg = Programa.builder()
//                                            .nome("PPGCC").build();
//
//        Docente docSalvo = repo.save(novDocente);
//        Tecnica tecSalvo = tecn.save(novTecnica);
//        Orientacao orientSalvo = orient.save(novaOrientacao);
//        Producao prodSalva = prod.save(novaProducao);
//        Programa progSalvo = prog.save(novoPPg);
//
//        ArrayList<Tecnica> tecnicas = new ArrayList<>();
//        tecnicas.add(tecSalvo);
//        docSalvo.setTecnicas(tecnicas);
//
//        ArrayList<Orientacao> orientacoes = new ArrayList<>();
//        orientacoes.add(orientSalvo);
//        docSalvo.setOrientacoes(orientacoes);
//
//        ArrayList<Programa> programas = new ArrayList<>();
//        programas.add(progSalvo);
//        docSalvo.setProgramas(programas);
//
//        ArrayList<Producao> producoes = new ArrayList<>();
//        producoes.add(prodSalva);
//        docSalvo.setProducoes(producoes);
//
//        //programa
//        Docente docenteSalvoSemPrograma = repo.save(novDocente);
//        programas.add(progSalvo);
//        docenteSalvoSemPrograma.setProgramas(programas);
//        Docente docenteSalvoComPrograma = repo.save(docenteSalvoSemPrograma);
//
//        //tecnica
//        Docente docenteSalvoSemTecnica = repo.save(novDocente);
//        docenteSalvoSemTecnica.setTecnicas(tecnicas);
//        Docente docenteSalvoComTecnica = repo.save(docenteSalvoSemTecnica);
//
//        //orientacao
//        Docente docenteSalvoSemOrientacao = repo.save(novDocente);
//        orientacoes.add(orientSalvo);
//        docenteSalvoSemOrientacao.setOrientacoes(orientacoes);
//        Docente docenteSalvoComOrientacao = repo.save(docenteSalvoSemOrientacao);
//
//        //producao
//        Docente docenteSalvoSemProducao = repo.save(novDocente);
//        producoes.add(prodSalva);
//        docenteSalvoSemProducao.setProducoes(producoes);
//        Docente docenteSalvoComProducao = repo.save(docenteSalvoSemProducao);
//
//        //acao
//        repo.delete(docenteSalvoComPrograma);
//
//        repo.delete(docenteSalvoComTecnica);
//
//        repo.delete(docenteSalvoComOrientacao);
//
//        repo.delete(docenteSalvoComProducao);
//
//        //teste
//        // Programa
//        Optional<Docente> docenteComPrograma = repo.findById(docenteSalvoComPrograma.id);
//        Assertions.assertNotNull(docenteComPrograma);
//
//        // Orientacao
//        Optional<Docente> docenteComOrientacao = repo.findById(docenteSalvoComOrientacao.id);
//        Assertions.assertNotNull(docenteComOrientacao);
//
//        // Tecnica
//        Optional<Docente> docenteComTecnica = repo.findById(docenteSalvoComTecnica.id);
//        Assertions.assertNotNull(docenteComTecnica);
//
//        // Producao
//        Optional<Docente> docenteComProducao = repo.findById(docenteSalvoComProducao.id);
//        Assertions.assertNotNull(docenteComProducao);
//    }
}
