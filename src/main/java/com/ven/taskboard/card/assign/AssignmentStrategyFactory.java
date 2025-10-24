package com.ven.taskboard.card.assign;

import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

// Factory — centralizes selection of strategy by policy enum.

// Maps an AssignmentPolicy (e.g., ROUND_ROBIN) to a concrete AssignmentStrategy bean. get(policy)
// returns the correct strategy or throws if unknown

// Instead of a big if-else block we make a factory method that holds a map for the decision
// get{policy} goes through the block and returns the right picker strategy for that policy

// POST /api/cards/{id}/assign without an explicit assignee → CardService#assign calls
// strategies.get(policy).pickAssignee(...).


@Component
public class AssignmentStrategyFactory {

    private final Map<AssignmentPolicy, AssignmentStrategy> byPolicy = new EnumMap<>(AssignmentPolicy.class);

    public AssignmentStrategyFactory(
            RoundRobinAssignment rr,
            SkillBasedAssignment sb,
            LeastLoadAssignment ll
    ) {
        byPolicy.put(AssignmentPolicy.ROUND_ROBIN, rr); //Strategy użycie
        byPolicy.put(AssignmentPolicy.SKILL_BASED, sb); //Strategy użycie
        byPolicy.put(AssignmentPolicy.LEAST_LOAD, ll); //Strategy użycie
    }

    public AssignmentStrategy get(AssignmentPolicy policy) {
        AssignmentStrategy s = byPolicy.get(policy);
        if (s == null) throw new IllegalArgumentException("No strategy for policy: " + policy);
        return s;
    }
}


//old
//@Component
//public class AssignmentStrategyFactory {
//
//    private final Map<AssignmentPolicy, AssignmentStrategy> byPolicy = new EnumMap<>(AssignmentPolicy.class);
//
//    public AssignmentStrategyFactory(RoundRobinAssignment rr /*, SkillBasedAssignment sb, LeastLoadAssignment ll */) {
//        byPolicy.put(AssignmentPolicy.ROUND_ROBIN, rr);
//        // add more when implemented
//    }
//
//    public AssignmentStrategy get(AssignmentPolicy policy) {
//        AssignmentStrategy s = byPolicy.get(policy);
//        if (s == null) throw new IllegalArgumentException("No strategy for policy: " + policy);
//        return s;
//    }
//}
