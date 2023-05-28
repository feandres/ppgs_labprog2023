package br.ufma.sppg.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstatisticasProducao {
    Integer qtd_grad;
    Integer qtd_mestrado;
    Integer qtd_doutorado;
}
