package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.CardEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobinAssignment implements AssignmentStrategy {

    private final Map<UUID, AtomicInteger> counters = new ConcurrentHashMap<>();

    private List<String> teamFor(BoardEntity board) {
        return List.of("user:karol", "user:dev", "user:content");
    }

    @Override
    public String pickAssignee(BoardEntity board, CardEntity card) {
        List<String> team = teamFor(board);
        AtomicInteger idx = counters.computeIfAbsent(board.getId(), k -> new AtomicInteger(0));
        int next = Math.abs(idx.getAndIncrement()) % team.size();
        return team.get(next);
    }
}
