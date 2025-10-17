package com.ven.taskboard.web.dto;

import java.time.LocalDate;
import java.util.*;

public record BoardTreeDto(UUID id, String name, List<ColumnDto> columns) {

    public record ColumnDto(UUID id, String name, int wipLimit, int orderIndex, List<CardDto> cards) { }

    public record CardDto(UUID id, String title, String status, String assignee, LocalDate dueDate, List<String> labels) { }
}
