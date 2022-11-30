package com.ucsal.cucoreminders.dto.lembrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AtualizarLembreteDto {
    private String titulo;
    private String mensagem;
    private Integer prioridade;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataVencimento;
}
