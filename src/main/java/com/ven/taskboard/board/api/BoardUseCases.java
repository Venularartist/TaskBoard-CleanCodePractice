package com.ven.taskboard.board.api;

import com.ven.taskboard.web.dto.AddColumnRequest;
import com.ven.taskboard.web.dto.BoardTreeDto;
import com.ven.taskboard.web.dto.CreateBoardRequest;

import java.util.UUID;


@Deprecated
public interface BoardUseCases { //GRUBY
    UUID createBoard(CreateBoardRequest req);
    UUID addColumn(UUID boardId, AddColumnRequest req);
    void renameBoard(UUID boardId, String newName);
    void setWipLimit(UUID columnId, int wip);
    BoardTreeDto getTree(UUID boardId);
}
