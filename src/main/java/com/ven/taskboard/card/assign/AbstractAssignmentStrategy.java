package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import java.util.List;

/**
 * DIP: abstract base for assignment strategies.
 * Provides shared utilities (e.g., a default team list).
 */

public abstract class AbstractAssignmentStrategy implements AssignmentStrategy {

    protected List<String> defaultTeam(BoardEntity board) {
        // in the real app here we get the team from the db
        return List.of("user:karol", "user:dev", "user:content");
    }
}
