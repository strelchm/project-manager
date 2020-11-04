package ru.strelchm.taskmanager.model.dbo;

import lombok.Getter;

/**
 * Тип приоритетности
 */
@Getter
public enum TaskPriorityType {
    LOWEST(1),
    LOW(2),
    MIDDLE(3),
    HIGH(4),
    HIGHEST(5);

    private final int value;

    TaskPriorityType(int value) {
        this.value = value;
    }

    public static TaskPriorityType getPriorityTypeByValue(int val) {
        for (TaskPriorityType pt : values()) {
            if (pt.getValue() == val) {
                return pt;
            }
        }
        return null;
    }

    public static TaskPriorityType getDefaultPriorityType() {
        return LOWEST;
    }
}
