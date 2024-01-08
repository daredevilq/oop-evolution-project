package project.gui;

import javafx.application.Application;

public class InitializeGUI {
    public static void main(String[] args) {
        try {
            Application.launch(InitApp.class, args);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
