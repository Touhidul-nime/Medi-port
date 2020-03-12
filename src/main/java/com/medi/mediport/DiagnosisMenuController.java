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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DiagnosisMenuController implements Initializable {

    Stage stage;
    Parent root;

    @FXML
    private Button pressDiagnose, gotoAppointment, profile;

    @FXML
    private CheckBox ReducedUrine, FrequentUrinatory, HearingDifficulty, BurningStomach, ChestPain, Nausea, BlurryVision,Sholderache;
    @FXML
    private CheckBox SwellingOrgan, SwallowingDifficulty, Fever, ExtremeFatigue, ExtremeSweating, SevereHeadache, Cough, Indigestion;

    @FXML
    private Label diagnoseLabel, alreadySelected;

    private boolean EntSelected = true,diabetesSelected = true, bpSelected = true, hdSelected = true, sdSelected = true, vpSelected = true, kdSelected = true;

    @FXML
    private void pressDiagnoseAction(ActionEvent event) throws Exception {
        alreadySelected.setText("");
        if ((Fever.isSelected() &&( HearingDifficulty.isSelected() || SwallowingDifficulty.isSelected() || Cough.isSelected()))||( SwallowingDifficulty.isSelected()&&(HearingDifficulty.isSelected()|| Cough.isSelected() || Cough.isSelected() ||Fever.isSelected()))||((Fever.isSelected() &&( HearingDifficulty.isSelected() || SwallowingDifficulty.isSelected() || Cough.isSelected())))||((Fever.isSelected() &&( HearingDifficulty.isSelected()|| SwallowingDifficulty.isSelected() || Cough.isSelected())))){
            diagnoseLabel.setText("You possibly have an ENT based disease.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult an ENT specialist");
            EntSelected = false;

          
        } else if ((FrequentUrinatory.isSelected() && (ExtremeFatigue.isSelected() || BlurryVision.isSelected()))) {
            diagnoseLabel.setText("You possibly have diabetes.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult an Endocrinologist");
            
           
        } else if ((Sholderache.isSelected()||(Sholderache.isSelected()&&(ExtremeFatigue.isSelected() || BlurryVision.isSelected() || SevereHeadache.isSelected() || ChestPain.isSelected())))) {
            diagnoseLabel.setText("You possibly have High Blood Pressure.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult a Cardiologist");
                

           
        } else if ((ChestPain.isSelected() && (ExtremeSweating.isSelected() || Nausea.isSelected()))) {
            diagnoseLabel.setText("You possibly have a heart disease.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult a Cardiologist");
            hdSelected = false;

        } else if ((BurningStomach.isSelected() || Indigestion.isSelected())) {
            diagnoseLabel.setText("You possibly have a Stomach disease.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult a Gastroenterologist");
            sdSelected = false;
            
        } else if ((BlurryVision.isSelected() && SevereHeadache.isSelected())) {
            diagnoseLabel.setText("You possibly have a vision problem.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult a ophthalmologist");
            vpSelected = false;
           
        } else if ((ReducedUrine.isSelected()||(ReducedUrine.isSelected()&& SwellingOrgan.isSelected()))) {
            diagnoseLabel.setText("You possibly have a kidney disease.");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("You should consult a nephrologist");
            kdSelected = false;
            
        } 
        else {
            diagnoseLabel.setText(" MISSMATCH of the Database!");
            gotoAppointment.setVisible(true);
            gotoAppointment.setText("Make a direct appointment");
        }

    }

    @FXML
    private void profileAction(ActionEvent event) throws IOException {
        stage = (Stage) profile.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/User Profile.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void gotoAppointmentAction(ActionEvent event) throws IOException {
        stage = (Stage) profile.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/fxml/Appointment.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
