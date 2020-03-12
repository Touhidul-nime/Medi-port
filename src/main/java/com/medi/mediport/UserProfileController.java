package com.medi.mediport;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class UserProfileController implements Initializable {
    Stage stage;
    Parent root;
    
    LoginController lc = new LoginController();
    
    @FXML
    private Button choiceMenu,signOut;
    
    @FXML
    private GridPane gpPatient, gpDoctor;
    
    @FXML
    private TextField patientPhoneNumber,patientName,patientUsername,patientAge;
    @FXML
    private TextField doctorHoursFrom,doctorHoursTo,doctorUsername,doctorPhoneNumber,doctorName,doctorMedicalDegrees;
    
    @FXML
    private TextArea doctorChamber;
    
    @FXML
    private ChoiceBox doctorSpecialistIn,patientSex,patientBloodGroup;
    
    
    String oldUsername;
    
    private Connection c1,c2;
    private PreparedStatement pstmt1,pstmt2,pstmt3,pstmt4,pstmt5,pstmt6;
    private final String patientDatabase = "jdbc:sqlite:C:/db/patients.db";
    private final String doctorDatabase = "jdbc:sqlite:C:/db/doctors.db";
    
    
    @FXML
    private void choiceMenuAction(ActionEvent event) throws IOException {
            stage = (Stage) choiceMenu.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/Choice Menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    @FXML
    private void signOutAction() throws IOException {
            stage = (Stage) signOut.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/login and signup.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
    @FXML
    private void changePasswordAction(ActionEvent event) throws IOException {
        Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/ChangePassword.fxml"));
        
        Scene scene = new Scene(root1);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage stage1 = new Stage();
        stage1.setTitle("Change Password");
        stage1.setScene(scene);
        stage1.show();
    }
    @FXML
    private void viewAppointmentsAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/List of Appointments.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        Stage stage2 = new Stage();
        stage2.setTitle("Appointments");
        stage2.setScene(scene);
        stage2.show();
    }
    
    @FXML
    private void editAction(){
        if(gpPatient.isVisible()){
        patientPhoneNumber.setEditable(true);
        patientName.setEditable(true);
        patientUsername.setEditable(true);
        patientAge.setEditable(true);
        patientSex.setFocusTraversable(true);
        patientBloodGroup.setFocusTraversable(true);
        patientSex.setDisable(false);
        patientBloodGroup.setDisable(false);
        }
        else{
        doctorHoursFrom.setEditable(true);
        doctorHoursTo.setEditable(true);
        doctorUsername.setEditable(true);
        doctorPhoneNumber.setEditable(true);
        doctorName.setEditable(true);
        doctorMedicalDegrees.setEditable(true);
        doctorChamber.setEditable(true);
        doctorSpecialistIn.setFocusTraversable(true);
        doctorSpecialistIn.setDisable(false);
        }
    
        if(gpPatient.isVisible()){    
            oldUsername = patientUsername.getText();
        }
        else{
            oldUsername = doctorUsername.getText();
        }
    }
    
    @FXML
    private void saveAction() throws SQLException{
        if(gpPatient.isVisible()){
            patientPhoneNumber.setEditable(false);
            patientName.setEditable(false);
            patientUsername.setEditable(false);
            patientAge.setEditable(false);
            patientSex.setDisable(true);
            patientBloodGroup.setDisable(true);
            
            pstmt3=c1.prepareStatement("UPDATE patients SET username=?,name=?,age=?,sex=?,phonenumber=?,bloodgroup=? WHERE username=?");
            pstmt3.setString(1, patientUsername.getText());
            pstmt3.setString(2, patientName.getText()); 
            pstmt3.setInt(3, Integer.parseInt(patientAge.getText()));
            pstmt3.setString(4, ""+patientSex.getValue()); 
            pstmt3.setString(5, patientPhoneNumber.getText()); 
            pstmt3.setString(6, ""+patientBloodGroup.getValue()); 
            pstmt3.setString(7, oldUsername);
            pstmt3.executeUpdate();
            pstmt3.close();
        }
        else{
            doctorHoursFrom.setEditable(false);
            doctorHoursTo.setEditable(false);
            doctorUsername.setEditable(false);
            doctorPhoneNumber.setEditable(false);
            doctorName.setEditable(false);
            doctorMedicalDegrees.setEditable(false);
            doctorChamber.setEditable(false);
            doctorSpecialistIn.setDisable(true);
            
            pstmt4=c2.prepareStatement("UPDATE doctors SET username=?,name=?,specialization=?,degrees=?,phonenumber=?,address=?,hours=? WHERE username=?");
            pstmt4.setString(1, doctorUsername.getText());
            pstmt4.setString(2, doctorName.getText()); 
            pstmt4.setString(3, ""+doctorSpecialistIn.getValue());
            pstmt4.setString(4, doctorMedicalDegrees.getText()); 
            pstmt4.setString(5, doctorPhoneNumber.getText()); 
            pstmt4.setString(6, doctorChamber.getText()); 
            pstmt4.setString(7, (doctorHoursFrom.getText()+" to "+doctorHoursTo.getText()));
            pstmt4.setString(8, oldUsername);
            pstmt4.executeUpdate();
            pstmt4.close();
        }
        
    }
    
    @FXML
    private void deleteAccountAction() throws SQLException, IOException{
        if(gpPatient.isVisible()){
            pstmt5=c1.prepareStatement("DELETE  FROM patients WHERE username=?");
            pstmt5.setString(1, LoginController.User);
            pstmt5.executeUpdate();
        }
        else{
            pstmt6=c2.prepareStatement("DELETE FROM doctors WHERE username=?");
            pstmt6.setString(1, LoginController.User);
            pstmt6.executeUpdate();
        }
        signOutAction();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        doctorSpecialistIn.setItems(LoginController.specialistInList);
        patientSex.setItems(LoginController.sexList);
        patientBloodGroup.setItems(LoginController.bloodGroupList);
        if(LoginController.user==2){
            choiceMenu.setVisible(false);
        }
        if(LoginController.user == 1){
            gpPatient.setVisible(true);
            gpDoctor.setVisible(false);
        }
        else{
            gpDoctor.setVisible(true);
            gpPatient.setVisible(false);
        }
        try{
            Class.forName("org.sqlite.JDBC");
            c1 = DriverManager.getConnection(patientDatabase);
            c2 = DriverManager.getConnection(doctorDatabase);
        
            if(gpPatient.isVisible()){
                pstmt1 = c1.prepareStatement("SELECT * FROM patients WHERE username=?");
                pstmt1.setString(1, LoginController.User);
                ResultSet rs = pstmt1.executeQuery();
                patientUsername.setText(rs.getString("username"));
                patientName.setText(rs.getString("name"));
                patientAge.setText(""+rs.getInt("age"));
                patientPhoneNumber.setText(rs.getString("phonenumber"));
                patientSex.setValue(rs.getString("sex"));
                patientBloodGroup.setValue(rs.getString("bloodgroup"));
                ViewAppointmentsController.patient=true;
                pstmt1.close();
            }
            else{
                pstmt2 = c2.prepareStatement("SELECT * FROM doctors WHERE username=?");
                pstmt2.setString(1, LoginController.User);
                ResultSet rs = pstmt2.executeQuery();
                doctorUsername.setText(rs.getString("username"));
                doctorName.setText(rs.getString("name"));
                doctorMedicalDegrees.setText(rs.getString("degrees"));
                doctorPhoneNumber.setText(rs.getString("phonenumber"));
                doctorChamber.setText(rs.getString("address"));
                doctorHoursFrom.setText(rs.getString("hours").substring(0,rs.getString("hours").indexOf(" ")+1));
                doctorHoursTo.setText(rs.getString("hours").substring(rs.getString("hours").indexOf("t")+3));
                doctorSpecialistIn.setValue(rs.getString("specialization"));
                ViewAppointmentsController.patient=false;    
                pstmt2.close();
            }
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.print(e.getMessage());
        }
        
    }   
}
