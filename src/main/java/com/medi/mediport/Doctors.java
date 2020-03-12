
package com.medi.mediport;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.Initializable;

public class Doctors implements Initializable{
    
    private final SimpleStringProperty doctors;
    
   
    public Doctors(String doc){
        doctors = new SimpleStringProperty(doc);
    }
    
    public String getDoctors() {
        return doctors.get();
    }
    public void setDoctors(String doc) {
        doctors.set(doc);
    }
    
    
    @Override
    public void initialize(URL url,ResourceBundle rb){
        
        
        
    }
}
