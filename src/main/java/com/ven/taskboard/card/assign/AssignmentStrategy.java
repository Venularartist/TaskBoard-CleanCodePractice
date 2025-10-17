package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.CardEntity;

public interface AssignmentStrategy {
    String pickAssignee(BoardEntity board, CardEntity card);
}
