/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author Elitebook 840 G3
 */
public class Position {
    private String namePosition;
    private Float salary;
    private Integer idPosition;
    
    public String getNamePostion() {
        return this.namePosition;
    }

    public void setNamePostion(String name) {
        this.namePosition = name;
    }

    public Float getSalary() {
        return this.salary;
    }
    
    public void setSalary(Float salary){
        this.salary = salary;
    }
    public Integer getIdPosition() {
        return this.idPosition;
    }
    
    public void setIdPosition(Integer id){
        this.idPosition = id;
    }
  
    public ObservableList<String>getNamePositionsList (Connection conn){
        ObservableList<String> listPositions = FXCollections.observableArrayList();

        String sql = "select nazwa_stanowiska from Stanowiska";
        
        PreparedStatement stmt1;
        ResultSet rs1;
        try{
            stmt1 = conn.prepareStatement(sql);
            rs1 =stmt1.executeQuery();
            while(rs1.next()){
                String position = rs1.getString(1);
                listPositions.add(position);
            }
     
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd w dostępie do bazy danych");
            alert.setContentText("Szczegóły: "+exc.getMessage());
            alert.showAndWait();
        }
        return listPositions;        
    }
    
}