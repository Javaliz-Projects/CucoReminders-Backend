package com.ucsal.cucoreminders.repositories;

import com.ucsal.cucoreminders.entities.Lembrete;
import com.ucsal.cucoreminders.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LembreteRepository extends JpaRepository<Lembrete,Long> {
    List<Lembrete> findAllByUser(User user);

    @Query("select l from Lembrete l where lembrete = ?1 order by l.prioridade DESC")
    List<Lembrete> findByUser_LembretesOrderByPrioridadeDesc(Lembrete lembrete);









}
