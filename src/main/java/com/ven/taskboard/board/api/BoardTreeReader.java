package com.ven.taskboard.board.api;


import com.ven.taskboard.web.dto.BoardTreeDto;
import java.util.UUID;

public interface BoardTreeReader {
    BoardTreeDto getTree(UUID boardId);
}
