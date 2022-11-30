package com.ucsal.cucoreminders.dto.lembrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucsal.cucoreminders.entities.Lembrete;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrazerLembretesDto {

    private Long id;
    private String titulo;
    private String mensagem;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataVencimento;
    private Integer prioridade;
    private boolean finalizaHoje;

    public TrazerLembretesDto(Lembrete lembreteEntity) {
        id = lembreteEntity.getId();
        titulo = lembreteEntity.getTitulo();
        mensagem = lembreteEntity.getMensagem();
        dataVencimento = lembreteEntity.getTimeSchedule().getDueDate();
        prioridade = lembreteEntity.getPrioridade();
        finalizaHoje = lembreteEntity.isFinalizaHoje();
    }

}
