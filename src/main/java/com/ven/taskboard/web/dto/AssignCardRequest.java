package com.ven.taskboard.web.dto;

import com.ven.taskboard.card.assign.AssignmentPolicy;

// Either provide a policy to auto-pick assignee or give assignee explicitly.
public record AssignCardRequest(AssignmentPolicy policy, String assignee) { }
