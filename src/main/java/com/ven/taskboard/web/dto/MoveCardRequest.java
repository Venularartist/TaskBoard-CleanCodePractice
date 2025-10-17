package com.ven.taskboard.web.dto;

import java.util.UUID;

public record MoveCardRequest(UUID toColumnId) { }
