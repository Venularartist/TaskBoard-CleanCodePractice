package com.ven.taskboard.board.template;

import com.ven.taskboard.persistence.BoardEntity;

public interface BoardTemplate {
    BoardEntity create(String name);
    String key();
}
