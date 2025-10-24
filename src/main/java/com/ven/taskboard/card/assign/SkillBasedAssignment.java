//Strategia przypisująca użytkownika na podstawie dopasowania umiejętności do opisu karty.
package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.CardEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class SkillBasedAssignment implements AssignmentStrategy {
    // Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

    // "członek zespołu → lista jego umiejętności"
    private static final Map<String, List<String>> SKILLS = Map.of(
            "user:karol", List.of("backend", "java", "sql"),
            "user:dev", List.of("frontend", "react", "js"),
            "user:content", List.of("writing", "design", "ux")
    );

    @Override
    public String pickAssignee(BoardEntity board, CardEntity card) {
        // Najbardziej dopasowany
        String description = (card.getDescription() != null) ? card.getDescription().toLowerCase() : "";

        return SKILLS.entrySet().stream()
                .filter(e -> e.getValue().stream().anyMatch(description::contains))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("user:karol"); //brak dopasowania
    }
}