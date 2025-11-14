package com.ven.taskboard.board.template;

import com.ven.taskboard.persistence.BoardEntity;
import org.springframework.stereotype.Component;

// Prototype — this template acts as a prototype for new boards.
// BoardService clones a fresh BoardEntity with default columns from here.

//builds a new BoardEntity pre-populated with three columns:

//"To Do" (wipLimit 0, order 1)
//"In Progress" (wipLimit 3, order 2)
//"Done" (wipLimit 0, order 3)

@Component
public class KanbanBasicTemplate extends AbstractBoardTemplate { // extends abstract base (DIP)

    @Override
    public BoardEntity create(String name) {
        BoardEntity b = new BoardEntity(name);
        b.addColumn("To Do", 0, 1);
        b.addColumn("In Progress", 3, 2);
        b.addColumn("Done", 0, 3);
        return b;
    }

    @Override
    public String key() { return "kanban-basic"; }
}
