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
import javafx.scene.control.Alert;

/**
 *
 * @author marys
 */
public class RoomType {
    private Integer nrRoomType;
    private Integer bedsNumber;
    private Boolean isBathroom;
    private String roomStandard;

    public Integer getNrRoomType() {
        return nrRoomType;
    }

    public void setNrRoomType(Integer nrRoomType) {
        this.nrRoomType = nrRoomType;
    }

    public Integer getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(Integer bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public Boolean getIsBathroom() {
        return isBathroom;
    }

    public void setIsBathroom(Boolean isBathroom) {
        this.isBathroom = isBathroom;
    }

    public String getRoomStandard() {
        return roomStandard;
    }

    public void setRoomStandard(String roomStandard) {
        this.roomStandard = roomStandard;
    }
    
    public RoomType getBoardersRoomType(Connection conn, int idRoomType){

        RoomType roomType = new RoomType();
        String sql = "select * from rodzaje_pokojow where nr_rodzaju_pokoju = ? ";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idRoomType);
    
            rs =stmt.executeQuery();
            while(rs.next()){
                roomType.nrRoomType = rs.getInt(1);
                roomType.bedsNumber = rs.getInt(2);
                if(rs.getString(3).charAt(0) == 'T'){
                    roomType.isBathroom = true;
                }
                else{
                    roomType.isBathroom = false;
                }                         
                roomType.roomStandard = rs.getString(4);
            }
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }  
        return roomType;
    }
}
