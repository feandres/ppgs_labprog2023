package br.ufma.sppg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;
import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import jakarta.transaction.Transactional;

@Service
public class ProducaoService {

    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    public List<Producao> obterProducoesPPG(Integer idPrograma, Integer anoInicial, Integer anoFinal) {

        // Se o usuário informa primeiro o ano maior e depois o menor, faz-se a inversão
        // dos valores:
        if (anoInicial > anoFinal) {
            Integer data = anoFinal;
            anoFinal = anoInicial;
            anoInicial = data;
        }

        Optional<Programa> programa = programaRepository.findById(idPrograma);

        if (programa.isPresent()) {

            // Verificando se o Programa possui Docentes cadastrados
            if (programaRepository.getReferenceById(idPrograma).getDocentes() == null
                    || programaRepository.getReferenceById(idPrograma).getDocentes().isEmpty())
                throw new RegrasRunTime("Docente inexistente ou não cadastrado.");

            ArrayList<Producao> producoes = new ArrayList<>();
            for (Docente docente : programaRepository.getReferenceById(idPrograma).getDocentes()) {
                if ((!docente.getProducoes().isEmpty()) && (docente.getProducoes() != null)) {
                    for (Producao producao : docente.getProducoes()) {
                        if ((producao.getAno() >= anoInicial) && (producao.getAno() <= anoFinal)) {
                            producoes.add(producao);
                        }
                    }
                    if (producoes.isEmpty())
                        throw new RegrasRunTime(
                                "Produções associadas a esse docente não foram encontradas no período inserido");

                    return producoes;
                }
                throw new RegrasRunTime("Docente não possui produções no período inserido");
            }

        }
        throw new RegrasRunTime("Programa não encontrado ou não existe");

    };

    public List<Producao> obterProducoesDocente(Integer idDocente, Integer anoInicial, Integer anoFinal) {

        // Se o usuário informa primeiro o ano maior e depois o menor, faz-se a inversão
        // dos valores:
        if (anoInicial > anoFinal) {
            Integer data = anoFinal;
            anoFinal = anoInicial;
            anoInicial = data;
        }

        Optional<Docente> docente = docenteRepository.findById(idDocente);
        if (docente.isPresent()) {

            if (docenteRepository.getReferenceById(idDocente).getProducoes() == null
                    || docenteRepository.getReferenceById(idDocente).getProducoes().isEmpty())
                throw new RegrasRunTime("O Docente não possui nenhuma Produção Registrada");

            ArrayList<Producao> producoes = new ArrayList<>();

            for (Producao producao : docenteRepository.getReferenceById(idDocente).getProducoes()) {
                if ((producao.getAno() >= anoInicial) && (producao.getAno() <= anoFinal)) {
                    producoes.add(producao);
                }
            }
            if (producoes.isEmpty())
                throw new RegrasRunTime("Não foram encontradas produções associadas a esse docente.");

            return producoes;
        }
        throw new RegrasRunTime("Docente não encontrado.");
    }


    // @Transactional
    // public Producao informarEstatisticasProducao(Producao producao) {
    //     return producaoRepository.save(producao);
    // }

    public List<Orientacao> obterOrientacaoProducao(Integer idProducao) {
        Optional<Producao> producao = producaoRepository.findById(idProducao);
        if (producao.isPresent()) {
            if (producaoRepository.getReferenceById(idProducao).getOrientacoes() != null)
                return producaoRepository.getReferenceById(idProducao).getOrientacoes();
        }
        throw new RegrasRunTime("A Producao não existe");
    }
    /*
     * public boolean excluirProducao(Integer idProducao){
     * Optional<Producao> producao = producaoRepository.findById(idProducao);
     * if(producao.isPresent()){
     * removerDocentesProducao(idProducao);
     * removerOrientacoesProducao(idProducao);
     * producaoRepository.delete(producaoRepository.getReferenceById(idProducao));
     * return true;
     * }
     * if(producaoRepository.existsById(idProducao))
     * throw new RegrasRunTime("Erro ao Excluir Produção");
     * throw new RegrasRunTime("Produção Inexistente");
     * }
     * 
     * public boolean retirarProducaoDocente(Integer idProducao, Integer idDocente){
     * Optional<Producao> opProducao = producaoRepository.findById(idProducao);
     * if(opProducao.isPresent()){
     * Producao producao = producaoRepository.getReferenceById(idProducao);
     * if(docenteRepository.existsById(idDocente)){
     * Docente docente = docenteRepository.getReferenceById(idDocente);
     * 
     * if(docente.getProducoes().remove(producao))
     * producao.getDocentes().remove(docente);
     * else
     * throw new RegrasRunTime("Producao e Docente não possuem Relação");
     * 
     * docenteRepository.save(docente);
     * producaoRepository.save(producao);
     * return true;
     * }else{
     * throw new RegrasRunTime("Docente Inexistente");
     * }
     * }
     * throw new RegrasRunTime("Producao Inexistente");
     * }
     * 
     * 
     * public boolean retirarProducaoOrientacao(Integer idProducao, Integer
     * idOrientacao){
     * Optional<Producao> opProducao = producaoRepository.findById(idProducao);
     * if(opProducao.isPresent()){
     * Producao producao = producaoRepository.getReferenceById(idProducao);
     * if(orientacaoRepository.existsById(idOrientacao)){
     * Orientacao orientacao = orientacaoRepository.getReferenceById(idOrientacao);
     * 
     * if(orientacao.getProducoes().remove(producao))
     * producao.getOrientacoes().remove(orientacao);
     * else
     * throw new RegrasRunTime("Producao e Orientacao não possuem Relação");
     * 
     * orientacaoRepository.save(orientacao);
     * producaoRepository.save(producao);
     * return true;
     * }else{
     * throw new RegrasRunTime("Orientacao Inexistente");
     * }
     * }
     * throw new RegrasRunTime("Producao Inexistente");
     * }
     * 
     * public boolean removerOrientacoesProducao(Integer idProducao){
     * Optional<Producao> producao = producaoRepository.findById(idProducao);
     * if(producao.isPresent()){
     * if(producaoRepository.getReferenceById(idProducao).getOrientacoes() != null
     * &&
     * !producaoRepository.getReferenceById(idProducao).getOrientacoes().isEmpty()){
     * for(int i = 0; i <
     * producaoRepository.getReferenceById(idProducao).getOrientacoes().size();
     * i++){
     * retirarProducaoOrientacao(idProducao,
     * producaoRepository.getReferenceById(idProducao).getOrientacoes().get(i).getId
     * ());
     * }
     * return true;
     * }
     * throw new RegrasRunTime("Não Existem Orientações na Produção");
     * 
     * }
     * throw new RegrasRunTime("Producao Inexistente");
     * }
     * 
     * public boolean removerDocentesProducao(Integer idProducao){
     * Optional<Producao> producao = producaoRepository.findById(idProducao);
     * if(producao.isPresent()){
     * if(producaoRepository.getReferenceById(idProducao).getDocentes() != null
     * && !producaoRepository.getReferenceById(idProducao).getDocentes().isEmpty()){
     * for(int i = 0; i <
     * producaoRepository.getReferenceById(idProducao).getDocentes().size(); i++){
     * retirarProducaoDocente(idProducao,
     * producaoRepository.getReferenceById(idProducao).getDocentes().get(i).getId())
     * ;
     * }
     * return true;
     * }
     * throw new RegrasRunTime("Não Existem Docentes na Produção");
     * 
     * }
     * throw new RegrasRunTime("Producao Inexistente");
     * }
     */
}
