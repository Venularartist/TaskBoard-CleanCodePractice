package com.ven.taskboard.card.service.command;

public interface Command<R> {
    R execute();
}