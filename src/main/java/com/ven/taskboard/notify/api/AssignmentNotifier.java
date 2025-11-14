package com.ven.taskboard.notify.api;

import com.ven.taskboard.persistence.CardEntity;

public interface AssignmentNotifier {
    void notifyAssigned(CardEntity card, String assignee);
}
