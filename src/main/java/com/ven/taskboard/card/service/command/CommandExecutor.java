package com.ven.taskboard.card.service.command;

import org.springframework.stereotype.Component;

@Component
public class CommandExecutor {
    public <R> R execute(Command<R> command) {
        return command.execute();
    }
}