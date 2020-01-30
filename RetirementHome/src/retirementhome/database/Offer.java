/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.database;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author marys
 */
public class Offer {
    private Integer nrOffer;
    private String name;
    private Date data;
    private Float price;
    private String description;
    private Integer nrRetirementHome;
    private Integer nrRoomType;

    public Integer getNrOffer() {
        return nrOffer;
    }

    public void setNrOffer(Integer nrOffer) {
        this.nrOffer = nrOffer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNrRetirementHome() {
        return nrRetirementHome;
    }

    public void setNrRetirementHome(Integer nrRetirementHome) {
        this.nrRetirementHome = nrRetirementHome;
    }

    public Integer getNrRoomType() {
        return nrRoomType;
    }

    public void setNrRoomType(Integer nrRoomType) {
        this.nrRoomType = nrRoomType;
    }
    
    
    public Offer getBoardersOffer(Connection conn, int idBoarder){
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "select * from oferty where nr_oferty = (select nr_oferty from pobyty where nr_pensjonariusza = ? and (data_wypisu is null or data_wypisu > ? ))";

        PreparedStatement stmt;
        ResultSet rs;
        Offer offer = new Offer();
        offer.nrOffer = -1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idBoarder);
            stmt.setDate(2, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                
                offer.nrOffer = rs.getInt(1);
                offer.name = rs.getString(2);
                offer.data = rs.getDate(3);
                offer.price = rs.getFloat(4);
                offer.description = rs.getString(5);
                offer.nrRetirementHome = rs.getInt(6);
                offer.nrRoomType = rs.getInt(7);
                
                return offer;
            }
            
            
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return offer;
    }
    
    
     public ObservableList<Offer> getCurrentOffers(Connection conn){
        ObservableList<Offer> listOffer = FXCollections.observableArrayList();
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "select * from oferty where data_waznosci is null or data_waznosci > ? ";

        
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                Offer offer = new Offer();
                
                offer.nrOffer = rs.getInt(1);
                offer.name = rs.getString(2);
                offer.data = rs.getDate(3);
                offer.price = rs.getFloat(4);
                offer.description = rs.getString(5);
                offer.nrRetirementHome = rs.getInt(6);
                offer.nrRoomType = rs.getInt(7);
                
                listOffer.add(offer);
            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listOffer;        
    }
     public String toString(){
         return name;
     }
    
}
