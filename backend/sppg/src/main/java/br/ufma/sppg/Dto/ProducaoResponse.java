package br.ufma.sppg.Dto;

import br.ufma.sppg.model.Docente;
import br.ufma.sppg.model.Orientacao;
import br.ufma.sppg.model.Producao;
import lombok.Data;

import java.util.List;

@Data
public class ProducaoResponse {
    Integer id;
    String tipo;
    String issnOuSigla;
    String nomeLocal;
    String titulo;
    Integer ano;
    String qualis;
    Float percentileOuH5;
    Integer qtdGrad;
    Integer qtdMestrado;
    Integer qtdDoutorado;
    List<Orientacao> orientacoes;
    List<Docente> docentes;

    public ProducaoResponse(Producao producao){
        this.id = producao.getId();
        this.tipo = producao.getTipo();
        this.issnOuSigla = producao.getIssnOuSigla();
        this.nomeLocal = producao.getNomeLocal();
        this.titulo = producao.getTitulo();
        this.ano = producao.getAno();
        this.qualis = producao.getQualis();
        this.percentileOuH5 = producao.getPercentileOuH5();
        this.qtdGrad = producao.getQtdGrad();
        this.qtdMestrado = producao.getQtdMestrado();
        this.qtdDoutorado = producao.getQtdDoutorado();
        this.orientacoes = producao.getOrientacoes();
        this.docentes = producao.getDocentes();
    }
}
