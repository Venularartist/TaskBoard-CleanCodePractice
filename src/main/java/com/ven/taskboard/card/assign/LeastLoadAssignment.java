//Strategia wybierająca osobę z najmniejszą liczbą przypisanych kart.
package com.ven.taskboard.card.assign;

import com.ven.taskboard.persistence.BoardEntity;
import com.ven.taskboard.persistence.CardEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Component
public class LeastLoadAssignment implements AssignmentStrategy {
    // Strategy is a behavioral design pattern that lets you define a family of algorithms, put each of them into a separate class, and make their objects interchangeable.

    //liczba kart przypisanych do użytkownika
    private static final Map<String, Integer> LOADS = Map.of(
            "user:karol", 5,
            "user:dev", 2,
            "user:content", 3
    );

    private List<String> teamFor(BoardEntity board) {
        return List.of("user:karol", "user:dev", "user:content");
    }

    @Override
    public String pickAssignee(BoardEntity board, CardEntity card) {
        List<String> team = teamFor(board);
        return team.stream() //Krystian  wykorzystanie programowania funkcyjnego w strumieniowym przetwarzaniu
                .min(Comparator.comparingInt(u -> LOADS.getOrDefault(u, 0)))
                .orElse("user:karol");
    }
}