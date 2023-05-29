package br.ufma.sppg.controller;

import br.ufma.sppg.Dto.EstatisticasProducao;
import br.ufma.sppg.Dto.Indice;
import br.ufma.sppg.Dto.ProducaoResponse;
import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Producao;
import br.ufma.sppg.service.DocenteService;
import br.ufma.sppg.service.ProducaoService;
import br.ufma.sppg.service.exceptions.RegrasRunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    @Autowired
    DocenteService docenteService;

    @Autowired
    ProducaoService producaoService;

    @GetMapping("/obterIndicesCapes")
    public ResponseEntity obterIndicesCapes(
            @RequestParam("docente") Integer idDocente, Integer anoInicial, Integer anoFinal){

        try{
            Indice indicesCapes = docenteService.obterIndice(idDocente, anoInicial, anoFinal);
            return new ResponseEntity(indicesCapes, HttpStatus.OK);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterProducoesPorQualisEAno")
    public ResponseEntity obterProducoesPorQualisEAno(
            @RequestParam("docente") Integer idDocente, String qualis, Integer anoInicial, Integer anoFinal){

        try{
            List<Producao> producoes = producaoService.obterProducoesPPG(idDocente, anoInicial, anoFinal);
            List<Producao> producoesPorQualisEAno = new ArrayList<>();
            for(Producao p : producoes){
                if(p.getQualis().equals(qualis) && p.getAno() >= anoInicial && p.getAno() <= anoFinal){
                    producoesPorQualisEAno.add(p);
                }
            }
            return new ResponseEntity(producoesPorQualisEAno, HttpStatus.OK);
        }catch (RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/obterEstatisticasProducao")
    public ResponseEntity obterEstatisticasProducao(
            @RequestParam("docente") Integer idDocente, Integer anoInicial, Integer anoFinal){

        try{
            List<Producao> producoes = producaoService.obterProducoesPPG(idDocente, anoInicial, anoFinal);
            List<EstatisticasProducao> estatisticasProducoes = new ArrayList<>();
            for(Producao p : producoes){
                EstatisticasProducao ep = new EstatisticasProducao(p.getQtdGrad(), p.getQtdMestrado(), p.getQtdDoutorado());
                estatisticasProducoes.add(ep);
            }
            return new ResponseEntity(estatisticasProducoes, HttpStatus.OK);
        }catch(RegrasRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }


}
