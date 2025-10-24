package com.ven.taskboard.card.service.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Template Method is a behavioral design pattern that defines the skeleton of an algorithm in the superclass
// but lets subclasses override specific steps of the algorithm without changing its structure.
public abstract class CardOperationTemplate<R> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public final R execute() {
        try {
            validate();
            R result = doOperation();
            postProcess();
            log.info("{} executed successfully.", getClass().getSimpleName());
            return result;
        } catch (Exception e) {
            log.error("{} failed: {}", getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

    protected void validate() {

    }

    protected abstract R doOperation();

    protected void postProcess() {

    }
}
