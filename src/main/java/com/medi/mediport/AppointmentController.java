
package com.medi.mediport;


import java.io.IOException;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AppointmentController implements Initializable {
    Stage stage;
    Parent root;
    String urrrl,patientData, doctorAppointmentData;
    String doctorName;
    ObservableList<Doctors> data= FXCollections.observableArrayList();
    
    private Connection c1,c2;
    private PreparedStatement pstmt,pstmt2,pstmt3,pstmt4,pstmt5;
    private final String patientDatabase = "jdbc:sqlite:C:/db/patients.db";
    private final String doctorDatabase = "jdbc:sqlite:C:/db/doctors.db";
  
    @FXML
    private Button takeAppointment;

    @FXML
    private Button back;
    int i = 0;
    @FXML
    private ChoiceBox specialistList;
    
    @FXML
    private Label label;
    
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private TableView<Doctors> doctorsTbl;
    @FXML
    private TableColumn<Doctors, String> doctorsCol;
   
    
    @FXML
    private void takeAppointmentAction(ActionEvent event)  {
        TablePosition pos = doctorsTbl.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Doctors item = doctorsTbl.getItems().get(row);
        TableColumn col = pos.getTableColumn();
        String rdata = (String) col.getCellObservableValue(item).getValue();
        label.setText(rdata);
        doctorName=rdata.substring(0, rdata.indexOf("\n"));
        setAppointment();
        
        
    }
    private void setAppointment() {
        label.setText(label.getText()+"\n\nAppointment Taken! You will get notified of your appointment time through sms.");
        
        try{
            pstmt=c1.prepareStatement("SELECT * FROM patients WHERE username=?");
            pstmt.setString(1, LoginController.User);
            ResultSet prs = pstmt.executeQuery();
            patientData = ("Name: "+prs.getString("name")+"\nAge: "+prs.getInt("age")+"\nSex: "+prs.getString("sex")+"\nPhone Number: "+prs.getString("phonenumber")+"\nBloodgroup: "+prs.getString("bloodgroup"));
            
            
            pstmt4=c2.prepareStatement("SELECT * FROM doctors WHERE name=?");
            pstmt4.setString(1, doctorName);
            ResultSet drs = pstmt4.executeQuery();
            doctorAppointmentData=("Name: "+drs.getString("name")+"\nAddress: "+drs.getString("address")+"\nPhone Number: "+drs.getString("phonenumber"));
            
        }
        catch(SQLException e){
            e.getMessage();
        }
        
        try{pstmt2 = c1.prepareStatement("UPDATE patients SET appointments=? WHERE username=?");
        pstmt2.setString(1, doctorAppointmentData);
        pstmt2.setString(2, LoginController.User);
        pstmt2.executeUpdate();
        //pstmt2.close();
        
        pstmt3 = c2.prepareStatement("UPDATE doctors SET appointments=? WHERE name=?");
        pstmt3.setString(1, patientData);
        pstmt3.setString(2, doctorName);
        pstmt3.executeUpdate();
        //pstmt3.close();
        }
        catch(SQLException e){
                e.getMessage();
                }
    }
    @FXML
    private void openChoiceMenu(ActionEvent event) throws IOException {
            stage = (Stage) back.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("/fxml/Choice Menu.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
   
    @FXML
    public void setTable(){
        data.removeAll(data);
        doctorsTbl.setItems(null);
        scrollPane.setVisible(true);
        
        try{
            Class.forName("org.sqlite.JDBC");
            c1 = DriverManager.getConnection(patientDatabase);
            c2 = DriverManager.getConnection(doctorDatabase);   
                    
            String choice = ""+specialistList.getValue();
            pstmt5=c2.prepareStatement("SELECT * FROM doctors WHERE specialization=?");
            pstmt5.setString(1, choice);            
            ResultSet rs = pstmt5.executeQuery();
            
            while(rs.next()){
                urrrl = (rs.getString("name")+("\n")+rs.getString("degrees")+"\n"+rs.getString("phonenumber")+"\n"+rs.getString("address")+"\n"+rs.getString("hours")+"\n");
                data.add(new Doctors(urrrl));
            }
            
        }
        catch(SQLException | ClassNotFoundException e){
            System.out.print(e.getMessage());
        }
        
        
        doctorsCol.setCellValueFactory(new PropertyValueFactory<Doctors, String>("doctors"));
         doctorsTbl.setItems(null);
        doctorsTbl.setItems(data);
       }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       specialistList.setItems(LoginController.specialistInList);
            
       
    }
}
