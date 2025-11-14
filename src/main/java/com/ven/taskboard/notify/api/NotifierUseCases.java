package com.ven.taskboard.notify.api;

import com.ven.taskboard.persistence.CardEntity;

@Deprecated
public interface NotifierUseCases { //GRUBY
    void sendAssignment(CardEntity card, String assignee);
    void sendDueSoon(CardEntity card);
    void broadcast(String to, String subject, String body);
}