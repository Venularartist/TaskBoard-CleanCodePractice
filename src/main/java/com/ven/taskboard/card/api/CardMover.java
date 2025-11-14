package com.ven.taskboard.card.api;


import com.ven.taskboard.web.dto.MoveCardRequest;

import java.util.UUID;

public interface CardMover {

    void move(UUID cardId, MoveCardRequest req);
}
