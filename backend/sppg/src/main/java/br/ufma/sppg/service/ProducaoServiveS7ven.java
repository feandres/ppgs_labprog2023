package br.ufma.sppg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.ufma.sppg.repo.DocenteRepository;
import br.ufma.sppg.repo.OrientacaoRepository;
import br.ufma.sppg.repo.ProducaoRepository;
import br.ufma.sppg.repo.ProgramaRepository;
import br.ufma.sppg.service.exceptions.CustomMessageRuntimeException;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.model.Programa;

@Service
public class ProducaoServiveS7ven {
    
    @Autowired
    ProducaoRepository producaoRepository;

    @Autowired
    DocenteRepository docenteRepository;

    @Autowired
    ProgramaRepository programaRepository;

    @Autowired
    OrientacaoRepository orientacaoRepository;

    public List<Producao> obterProducoesPPG(Integer idPrograma, Integer data_inicial, Integer data_final){
        //Inverter datas caso a data de limite final seja menor que a inicial
        if(data_inicial >= data_final){
            Integer data_aux = data_final;
            data_final = data_inicial;
            data_inicial = data_aux;
        }
        
        //Aux
        Optional<Programa> programa = programaRepository.findById(idPrograma);

        if(programa.isPresent()){
            //check is list is empty or non existent
            if(programaRepository.getReferenceById(idPrograma).getDocentes().isEmpty() ||
                programaRepository.getReferenceById(idPrograma).getDocentes() == null)
            throw new CustomMessageRuntimeException("Docente inexistente ou não cadastrado");

            //LinkedList<Producao> producoes = new LinkedList<>();
            ArrayList<Producao> producoes = new ArrayList<>();
            //Itera em busca de todos os docentes
            //Itera por todas as producoes de cada docente em busca de producoes que encaixem no filtro de data
            for(Docente doc : programaRepository.getReferenceById(idPrograma).getDocentes()){
                if(!doc.getProducoes().isEmpty() && doc.getProducoes() != null){
                    for(Producao prod : doc.getProducoes()){
                        if(prod.getAno()>= data_inicial && prod.getAno()<=data_final){
                            producoes.add(prod);
                        }
                    }
                    if(producoes.isEmpty()) throw new CustomMessageRuntimeException("Produções associadas a esse docente não foram encontradas no período inserido");
                    
                    return producoes;
                } throw new CustomMessageRuntimeException("Docente não possui produções no período inserido");
            }
        
        } throw new CustomMessageRuntimeException("Programa não encontrado ou não existe");

    }


}
