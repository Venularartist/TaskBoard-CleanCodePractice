package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.CardEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobinAssignment extends AbstractAssignmentStrategy { // extends abstract base (DIP)

    private final Map<UUID, AtomicInteger> counters = new ConcurrentHashMap<>();

    @Override
    public String pickAssignee(BoardEntity board, CardEntity card) {
        // instead of a private teamFor(...) method, we use the hook from the abstract base class
        List<String> team = defaultTeam(board);

        AtomicInteger idx = counters.computeIfAbsent(board.getId(), k -> new AtomicInteger(0));
        int next = Math.abs(idx.getAndIncrement()) % team.size();
        return team.get(next);
    }
}
