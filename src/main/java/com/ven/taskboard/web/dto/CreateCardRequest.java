package com.ven.taskboard.web.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateCardRequest(
        UUID columnId,
        String title,
        String description,
        String assignee,
        LocalDate dueDate,
        List<String> labels
) { }
