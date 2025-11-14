package com.ven.taskboard.card.api;

import com.ven.taskboard.web.dto.CreateCardRequest;
import java.util.UUID;

public interface CardCreator {
    UUID create(CreateCardRequest req);
}
