
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController implements Initializable {
    
    private Connection c1,c2;
    private PreparedStatement pstmt1,pstmt2,pstmt3,pstmt4;
    private final String patientDatabase = "jdbc:sqlite:C:/db/patients.db";
    private final String doctorDatabase = "jdbc:sqlite:C:/db/doctors.db";
    
    @FXML
    private Button changePassword,cancel;
    
    @FXML
    private Label label;
    
    @FXML
    private PasswordField oldPass,newPass,confirmPass;
    
    String dbPassword;
    
    @FXML
    private void changePasswordAction(ActionEvent event) throws SQLException {
        if((oldPass.getText().equals(dbPassword))&&(newPass.getText().equals(confirmPass.getText()))){
            if(newPass.getText().equals(dbPassword)){
                label.setText("No changes necessary");
            }
            if(LoginController.user==1){
                pstmt3=c1.prepareStatement("UPDATE patients SET password=? WHERE username=?");
                pstmt3.setString(1, newPass.getText());
                pstmt3.setString(2, LoginController.User);  
                pstmt3.executeUpdate();
                pstmt3.close();
                c1.close();
            }
            else{    
                pstmt4=c2.prepareStatement("UPDATE doctors SET password=? WHERE username=?");
                pstmt4.setString(1, newPass.getText());
                pstmt4.setString(2, LoginController.User); 
                pstmt4.executeUpdate();
                pstmt4.close();
                c2.close();
            }
            label.setText("Password Changed! You may close this window.");
        }
        else{
            label.setText("The passwords don't match!");
        }
    }
    
    
    @FXML
    private void cancelAction(ActionEvent event) throws IOException {
        Stage stage2 = (Stage) cancel.getScene().getWindow();
        stage2.close();
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try{
            Class.forName("org.sqlite.JDBC");
            c1 = DriverManager.getConnection(patientDatabase);
            c2 = DriverManager.getConnection(doctorDatabase);
            
            if(LoginController.user==1){
                pstmt1 = c1.prepareStatement("SELECT password FROM patients WHERE username=?");
                pstmt1.setString(1, LoginController.User);
                ResultSet rs = pstmt1.executeQuery();
                dbPassword=rs.getString("password");
            }
            else{
                pstmt2 = c2.prepareStatement("SELECT password FROM doctors WHERE username=?");
                pstmt2.setString(1, LoginController.User);
                ResultSet rs = pstmt2.executeQuery();
                dbPassword=rs.getString("password");
            }
            
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.print(e.getMessage());
        }
        
    }   
}