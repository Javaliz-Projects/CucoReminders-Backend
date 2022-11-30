package com.ucsal.cucoreminders.scheduler;

import com.ucsal.cucoreminders.entities.Lembrete;
import com.ucsal.cucoreminders.repositories.LembreteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@RequiredArgsConstructor
@Slf4j
@Component
public class ReminderColorScheduled {

    private final LembreteRepository lembreteRepository;

    @Scheduled(fixedDelay = 60000)
    public void verifyReminderColor() {
        var dataAtualFormatada = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        var lembretesVencemHoje = new ArrayList<Lembrete>();
        log.info("Executando a verificação de Cor para os lembretes com base na data atual e a de vencimento...");
        lembreteRepository.findAll().forEach(lembrete -> {
            log.info("Lembretes do forEach => {}",lembrete.getId());
            var dataRegistradaNoLembreteFormatada = lembrete.getTimeSchedule().getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if (lembrete.getPrioridade() > 5 && dataAtualFormatada.equals(dataRegistradaNoLembreteFormatada)) {
                lembrete.setFinalizaHoje(true);
                lembreteRepository.save(lembrete);
                lembretesVencemHoje.add(lembrete);
            }
        });

        log.info("Lembretes que vencem hoje {} -> {}", dataAtualFormatada, lembretesVencemHoje);
    }


}
