package com.ven.taskboard.board.api;

import com.ven.taskboard.web.dto.AddColumnRequest;
import java.util.UUID;

public interface ColumnManager {
    UUID addColumn(UUID boardId, AddColumnRequest req);
}
