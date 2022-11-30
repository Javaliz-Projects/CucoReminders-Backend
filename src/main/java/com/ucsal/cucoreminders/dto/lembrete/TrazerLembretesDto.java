package com.ucsal.cucoreminders.dto.lembrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucsal.cucoreminders.entities.Lembrete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrazerLembretesDto {

    private Long id;
    private String titulo;
    private String mensagem;
    private Long dataVencimento;
    private Integer prioridade;

    public TrazerLembretesDto(Lembrete lembreteEntity) {
        ZonedDateTime zdt = ZonedDateTime.of(lembreteEntity.getTimeSchedule().getDueDate(), ZoneId.systemDefault());
        id = lembreteEntity.getId();
        titulo = lembreteEntity.getTitulo();
        mensagem = lembreteEntity.getMensagem();
        dataVencimento = zdt.toInstant().toEpochMilli();
        prioridade = lembreteEntity.getPrioridade();
    }

}
