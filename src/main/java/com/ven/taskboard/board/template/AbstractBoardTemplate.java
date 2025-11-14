package com.ven.taskboard.board.template;

import com.ven.taskboard.persistence.BoardEntity;

public abstract class AbstractBoardTemplate implements BoardTemplate {

    protected void addColumn(BoardEntity b, String name, int wipLimit, int orderIndex) {
        b.addColumn(name, wipLimit, orderIndex);
    }
}
