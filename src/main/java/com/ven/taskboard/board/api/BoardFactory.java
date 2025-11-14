package com.ven.taskboard.board.api;

import com.ven.taskboard.web.dto.CreateBoardRequest;
import java.util.UUID;

public interface BoardFactory {
    UUID createBoard(CreateBoardRequest req);
}
