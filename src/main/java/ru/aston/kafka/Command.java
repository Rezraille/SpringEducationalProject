package ru.aston.kafka;

public enum Command {
    CREATE("create"),
    DELETE("remove");
    private final String description;

    Command(String description) {
        this.description = description;
    }
}
