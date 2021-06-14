package it.enel.kata.birthday_greetings.infrastructure;

import java.nio.file.Path;

public class FileConfig {
    private Path employeesFilePath;

    public FileConfig(Path employeesFilePath) {
        this.employeesFilePath = employeesFilePath;
    }

    public Path employeesFilePath() {
        return employeesFilePath;
    }
}
