package com.ven.taskboard.card.api;

import com.ven.taskboard.web.dto.AssignCardRequest;

import java.util.UUID;

public interface CardAssigner {
    void assign(UUID cardId, AssignCardRequest req);
}
