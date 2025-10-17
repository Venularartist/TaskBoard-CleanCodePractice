package com.ven.taskboard.board.template;

import org.springframework.stereotype.Component;
import java.util.*;

// Prototype — this template acts as a prototype for new boards.
// Adding a new template requires no changes in BoardService (OCP)

@Component
public class TemplateRegistry {
    private final Map<String, BoardTemplate> byKey = new HashMap<>();

    public TemplateRegistry(List<BoardTemplate> templates) {
        for (BoardTemplate t : templates) byKey.put(t.key(), t);
    }

    public Optional<BoardTemplate> get(String key) {
        return Optional.ofNullable(byKey.get(key));
    }
}
