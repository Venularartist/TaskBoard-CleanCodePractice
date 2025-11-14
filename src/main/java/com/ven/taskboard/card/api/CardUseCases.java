package com.ven.taskboard.card.api;

import com.ven.taskboard.web.dto.AssignCardRequest;
import com.ven.taskboard.web.dto.CreateCardRequest;
import com.ven.taskboard.web.dto.MoveCardRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Deprecated
public interface CardUseCases { //GRUBY
    UUID create(CreateCardRequest req);
    void move(UUID cardId, MoveCardRequest req);
    void assign(UUID cardId, AssignCardRequest req);
    void relabel(UUID cardId, List<String> labels);
    void reschedule(UUID cardId, LocalDate dueDate);
    void comment(UUID cardId, String author, String text);
}
