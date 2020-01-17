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
 * @author marys
 */
public class Bill {
    
    private String name;
    private float price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
    
    public ObservableList<Bill> getBoardersBill(Connection conn, int stayId){
        ObservableList<Bill> listBill = FXCollections.observableArrayList();

        String sqlOffer = "select nazwa_oferty, cena from oferty where nr_oferty = (select nr_oferty from pobyty where nr_pobytu = ? )";
        
        String sqlActivities = "select nazwa_zajecia, cena from zajecia where nr_zajecia in (select nr_zajecia from zajecia_pobyty where(data_do is null and nr_pobytu = ?))";

        PreparedStatement stmt1, stmt2;
        ResultSet rs1, rs2;
        try{
            stmt1 = conn.prepareStatement(sqlOffer);
            stmt1.setInt(1, stayId);
            rs1 =stmt1.executeQuery();
            while(rs1.next()){
                Bill bill = new Bill();
                bill.name = rs1.getString(1);
                bill.price = rs1.getFloat(2);
                listBill.add(bill);
            }
            
            stmt2 = conn.prepareStatement(sqlActivities);
            stmt2.setInt(1, stayId);
            rs2 = stmt2.executeQuery();
            while(rs2.next()){
                Bill bill = new Bill();
                bill.name = rs2.getString(1);
                bill.price = rs2.getFloat(2);
                listBill.add(bill);                
            }
                 
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBill;        
    }
    
}
