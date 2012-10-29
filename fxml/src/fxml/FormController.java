package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class FormController {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.err.println("Hi there!");
    }

}