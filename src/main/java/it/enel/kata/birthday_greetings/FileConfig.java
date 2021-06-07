package it.enel.kata.birthday_greetings;

import java.nio.file.Path;

public class FileConfig {
    private Path employeesFilePath;

    public FileConfig(Path employeesFilePath) {
        this.employeesFilePath = employeesFilePath;
    }

    public Path getEmployeesFilePath() {
        return employeesFilePath;
    }
}
