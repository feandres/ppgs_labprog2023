package br.ufma.sppg.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class DocenteDTO {
    Integer id;
    String lattes;
    String nome;
    Date dataAtualizacao;
}
