
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
import javafx.stage.Stage;

public class ViewAppointmentsController implements Initializable{  
    static boolean patient;
    
    
    @FXML
    private Button done;
    
    @FXML
    private Label label;
    
    private Connection c1,c2;
    private PreparedStatement pstmt1,pstmt2;
    private final String patientDatabase = "jdbc:sqlite:C:/db/patients.db";
    private final String doctorDatabase = "jdbc:sqlite:C:/db/doctors.db";
    
    
    
    @FXML
    private void doneAction(ActionEvent event)throws IOException {
        Stage stage = (Stage) done.getScene().getWindow();
        stage.close();    
    }  
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            Class.forName("org.sqlite.JDBC");
            c1 = DriverManager.getConnection(patientDatabase);
            c2 = DriverManager.getConnection(doctorDatabase);
            
            if(patient){
                pstmt1 = c1.prepareStatement("SELECT appointments FROM patients WHERE username=?");
                pstmt1.setString(1, LoginController.User);
                ResultSet rs = pstmt1.executeQuery();
                label.setText(rs.getString("appointments"));
            }
            else{
                pstmt2 = c2.prepareStatement("SELECT appointments FROM doctors WHERE username=?");
                pstmt2.setString(1, LoginController.User);
                ResultSet rs = pstmt2.executeQuery();
                label.setText(rs.getString("appointments"));
            }
            
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.print(e.getMessage());
        }
    }
}
