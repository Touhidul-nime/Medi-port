package com.medi.mediport;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    Stage stage;
    Parent root;
    static int user;
    static String User, Pass;

    private Connection c1, c2;
    private PreparedStatement pstmt1, pstmt2, pstmt3, pstmt4;
    private final String patientDatabase = "jdbc:sqlite:C:/db/patients.db";
    private final String doctorDatabase = "jdbc:sqlite:C:/db/doctors.db";

    @FXML
    private Pane signUpPane, loginPane, patientPane, doctorPane;

    @FXML
    private RadioButton patientSignUp, doctorSignUp, patientLogin, doctorLogin;

    @FXML
    private Button login;

    @FXML
    private TextField LoginUsername, LoginPassword, patientPhoneNumber, patientName, patientUsername, patientAge;
    @FXML
    private TextField patientHourFrom, patientHourTo, doctorUsername, doctorPhonenumber, doctorName, medicalDegree;

    @FXML
    private ChoiceBox specialistIn, sex, bloodGroup;

    static ObservableList<String> specialistInList = FXCollections.observableArrayList("Urology", "Nephrology", "Oncology", "Gynecology", "Ophthalmology", "Gastroenterology", "Cardiology", "Paediatrics", "ENT");
    static ObservableList<String> sexList = FXCollections.observableArrayList("Male", "Female");
    static ObservableList<String> bloodGroupList = FXCollections.observableArrayList("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-");

    @FXML
    private TextArea chamber;
    
    
    
    @FXML
    private PasswordField patientPassword, patientConfirmpassword, doctorPassword, doctorConfirmpassword;

    @FXML
    private Label loginMessage;
    
    @FXML
    private Text status;

    @FXML
    private void loginAction(ActionEvent event) throws Exception {
        User = LoginUsername.getText();
        Pass = LoginPassword.getText();

        if (patientLogin.isSelected()) {
            pstmt1 = c1.prepareStatement("SELECT * FROM patients WHERE username=? AND password=?");
            pstmt1.setString(1, User);
            pstmt1.setString(2, Pass);

            ResultSet rs = pstmt1.executeQuery();
            boolean check = rs.next();
            pstmt1.close();

            user = 1;
            if (check) {

                stage = (Stage) login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/fxml/Choice Menu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                loginMessage.setText("Incorrect Username and Password combination.");
            }
        } else {
            pstmt2 = c2.prepareStatement("SELECT * FROM doctors WHERE username=? AND password=?");
            pstmt2.setString(1, User);
            pstmt2.setString(2, Pass);
            ResultSet rs = pstmt2.executeQuery();
            boolean check = rs.next();

            pstmt2.close();
            user = 2;
            if (check) {

                stage = (Stage) login.getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/fxml/User Profile.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                loginMessage.setText("Incorrect Username and Password combination.");
            }
        }

    }

    @FXML
    private void signUpAction(ActionEvent event) {
        loginPane.setVisible(false);
        signUpPane.setVisible(true);
    }

    @FXML
    private void checkSignUpSelection() {
        if (doctorSignUp.isSelected()) {
            patientPane.setVisible(false);
            doctorPane.setVisible(true);
        } else {
            doctorPane.setVisible(false);
            patientPane.setVisible(true);
        }
    }

    @FXML
    private void confirmSignUpAction(ActionEvent event) throws SQLException {

        //TRY CATCH NEEDED
        if (patientSignUp.isSelected()) {
            patientPassword.setBlendMode(BlendMode.SRC_OVER);
            patientConfirmpassword.setBlendMode(BlendMode.SRC_OVER);
            if (patientPassword.getText().equals(patientConfirmpassword.getText())) {
                pstmt3 = c1.prepareStatement("INSERT INTO patients(username,password,name,age,sex,phonenumber,bloodgroup) VALUES(?,?,?,?,?,?,?)");
                pstmt3.setString(1, patientUsername.getText());
                pstmt3.setString(2, patientPassword.getText());
                pstmt3.setString(3, patientName.getText());
                pstmt3.setInt(4, Integer.parseInt(patientAge.getText()));
                pstmt3.setString(5, ("" + sex.getValue()));
                pstmt3.setString(6, patientPhoneNumber.getText());
                pstmt3.setString(7, ("" + bloodGroup.getValue()));
                try{pstmt3.executeUpdate();
                status.setText("Sign Up Success.");}
                catch(SQLException e){status.setText("Username Already Exists!!");}
                catch(NumberFormatException e){status.setText("Invalid input! Check again.");}
                
       
            } else {
                patientPassword.setBlendMode(BlendMode.RED);
                patientConfirmpassword.setBlendMode(BlendMode.RED);
                status.setText("Sign Up failed.");
            }
        } else {
            if (doctorPassword.getText().equals(doctorConfirmpassword.getText())) {
                pstmt4 = c2.prepareStatement("INSERT INTO doctors(username,password,name,specialization,degrees,phonenumber,address,hours) VALUES(?,?,?,?,?,?,?,?)");
                pstmt4.setString(1, doctorUsername.getText());
                pstmt4.setString(2, doctorPassword.getText());
                pstmt4.setString(3, doctorName.getText());
                pstmt4.setString(4, ("" + specialistIn.getValue()));
                pstmt4.setString(5, medicalDegree.getText());
                pstmt4.setString(6, doctorPhonenumber.getText());
                pstmt4.setString(7, chamber.getText());
                pstmt4.setString(8, (patientHourFrom.getText() + " to " + patientHourTo.getText()));
                try{pstmt4.executeUpdate();
                status.setText("Sign Up Success.");}
                catch(SQLException e){status.setText("Username Already Exists!!");}
                catch(NumberFormatException e){status.setText("Invalid input! Check again.");}
                
  
                
            } else {
                doctorPassword.setBlendMode(BlendMode.RED);
                doctorConfirmpassword.setBlendMode(BlendMode.RED);
                status.setText("Sign Up failed.");
    
            }
        }
        
    }

    

    @FXML
    private void loginPageAction(ActionEvent event) {
        signUpPane.setVisible(false);
        loginPane.setVisible(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Class.forName("org.sqlite.JDBC");
            c1 = DriverManager.getConnection(patientDatabase);
            c2 = DriverManager.getConnection(doctorDatabase);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.print(e.getMessage());
        }

        specialistIn.setItems(specialistInList);
        sex.setValue("Male");
        sex.setItems(sexList);
        bloodGroup.setValue("A+");
        bloodGroup.setItems(bloodGroupList);

    }
}
