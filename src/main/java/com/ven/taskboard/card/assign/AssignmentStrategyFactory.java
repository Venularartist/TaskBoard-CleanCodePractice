package com.ven.taskboard.card.assign;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

// Factory — centralizes selection of strategy by policy enum.
@Component
public class AssignmentStrategyFactory {

    private final Map<AssignmentPolicy, AssignmentStrategy> byPolicy = new EnumMap<>(AssignmentPolicy.class);

    public AssignmentStrategyFactory(RoundRobinAssignment rr /*, SkillBasedAssignment sb, LeastLoadAssignment ll */) {
        byPolicy.put(AssignmentPolicy.ROUND_ROBIN, rr);
        // add more when implemented
    }

    public AssignmentStrategy get(AssignmentPolicy policy) {
        AssignmentStrategy s = byPolicy.get(policy);
        if (s == null) throw new IllegalArgumentException("No strategy for policy: " + policy);
        return s;
    }
}
