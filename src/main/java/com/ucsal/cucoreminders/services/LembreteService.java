package com.ucsal.cucoreminders.services;

import com.ucsal.cucoreminders.dto.lembrete.AtualizarLembreteDto;
import com.ucsal.cucoreminders.dto.lembrete.InserirLembreteDto;
import com.ucsal.cucoreminders.dto.lembrete.TrazerLembretesDto;
import com.ucsal.cucoreminders.entities.TimeSchedule;
import com.ucsal.cucoreminders.repositories.LembreteRepository;
import com.ucsal.cucoreminders.entities.Lembrete;
import com.ucsal.cucoreminders.services.exceptions.ForbiddenException;
import com.ucsal.cucoreminders.services.exceptions.ResourceNotFoundException;
import com.ucsal.cucoreminders.services.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LembreteService {

    @Autowired
    private LembreteRepository lembreteRepository;
    @Autowired
    private AuthService authService;

    @Transactional
    public void adicionarLembrete(InserirLembreteDto inserirLembreteDto) {
        Lembrete lembrete = new Lembrete();
        lembreteRepository.save(copiarDtoParaEntidade(lembrete, inserirLembreteDto));
    }


    @Transactional
    public void atualizarLembrete(Long lembreteId, AtualizarLembreteDto dto) {
        var user = authService.authenticated();
        if (user.getLembretes().stream().noneMatch(x -> Objects.equals(x.getId(), lembreteId))) {
            throw new ForbiddenException("Você não pode atualizar um lembrete que não é seu.");
        }
        try {
            var lembrete = atualizarAuxiliar(lembreteId, dto);
            lembreteRepository.save(lembrete);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + lembreteId);
        }
    }

    public void deletarLembrete(Long lembreteId) {
        log.info("lembrete para ser deletado {}", lembreteId);
        lembreteRepository.findById(lembreteId).orElseThrow(() -> new ResourceNotFoundException("Lembrete não encontrado"));
        try {
            var isFromCurrentUser = authService.authenticated().getLembretes().stream().anyMatch(x -> x.getId() == lembreteId);
            if (!isFromCurrentUser) {
                throw new ForbiddenException("Acesso Negado - [Esse lembrete não está relacionado com sua conta]");
            }
            lembreteRepository.deleteById(lembreteId);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado: " + lembreteId);
        }
    }

    @Transactional(readOnly = true)
    public List<TrazerLembretesDto> listarLembretes() {
        return lembreteRepository
                .findAllByUser(authService.authenticated())
                .stream()
                .sorted(new Lembrete())
                .sorted(Comparator
                        .comparingInt(Lembrete::getPrioridade)
                        .reversed())
                .map(TrazerLembretesDto::new)
                .collect(Collectors
                        .toList());
    }


    // Metodos auxiliares


    private Lembrete copiarDtoParaEntidade(Lembrete lembrete, InserirLembreteDto dto) {
        log.info("Data vencimento {}", dto.getDataVencimento());
        lembrete.setUser(authService.authenticated());
        lembrete.setTitulo(dto.getTitulo());
        lembrete.setMensagem(dto.getMensagem());
        lembrete.setTimeSchedule(mapearTimeSchedule(dto.getDataVencimento()));
        lembrete.setPrioridade(dto.getPrioridade());
        return lembrete;
    }

    private Lembrete atualizarAuxiliar(Long lembreteId, AtualizarLembreteDto dto) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        Lembrete lembrete = lembreteRepository.findById(lembreteId).orElseThrow(() -> new ResourceNotFoundException("Lembrete nao encontrado"));
        lembrete.setPrioridade(dto.getPrioridade() == null ? lembrete.getPrioridade() : dto.getPrioridade());
        lembrete.setTitulo(dto.getTitulo() == null || dto.getTitulo().isEmpty() ? lembrete.getTitulo() : dto.getTitulo());
        lembrete.setMensagem(dto.getMensagem() == null || dto.getMensagem().isEmpty() ? lembrete.getMensagem() : dto.getMensagem());
        var timeSchedule = lembrete.getTimeSchedule();
        timeSchedule.setDueDate(dto.getDataVencimento() == null || dto.getDataVencimento().isEmpty() ? timeSchedule.getDueDate() : LocalDateTime.parse(dto.getDataVencimento(),df));
        if (dto.getDataVencimento() == null) {
            return lembrete;
        }
        timeSchedule.setAtualizadoEm(Instant.now());
        return lembrete;
    }

    private TimeSchedule mapearTimeSchedule(String x) {
        log.info("Data que veio do controller ->  {}", x);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var timeScheduleEntity = new TimeSchedule();
        var localDateTime = LocalDateTime.parse(x,df);
        log.info("LocalDateTime => {}", localDateTime);
        timeScheduleEntity.setDueDate(localDateTime);
        return timeScheduleEntity;
    }


}
