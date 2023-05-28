package br.ufma.sppg.Dto;

import lombok.Data;

@Data
public class ProducaoRequest {
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

}
