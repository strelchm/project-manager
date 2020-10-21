package ru.strelchm.taskmanager.model;

import lombok.Getter;

/**
 * Тип приоритетности
 */
@Getter
public enum PriorityType {
    LOWEST(1),
    LOW(2),
    MIDDLE(3),
    HIGH(4),
    HIGHEST(5);

    private final int value;

    PriorityType(int value) {
        this.value = value;
    }
}
