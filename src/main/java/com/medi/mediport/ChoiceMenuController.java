
package com.medi.mediport;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ChoiceMenuController implements Initializable {
    Stage stage;
    Parent root;
    @FXML
    private Button diagnose;

    @FXML
    private Button profile;

    @FXML
    private Button appointment;

    @FXML
    private void diagnoseAction(ActionEvent event) throws IOException{
            stage = (Stage) diagnose.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/diagnose.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();  
    }

    @FXML
    private void appointmentAction(ActionEvent event) throws IOException{
            stage = (Stage) appointment.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    @FXML
    private void profileAction(ActionEvent event) throws IOException{
            stage = (Stage) profile.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/User Profile.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
}
