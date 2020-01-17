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
import java.sql.Statement;
import java.util.Calendar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author marys
 */
public class Room {
    private Integer nrRoom;
    private String roomNumber;
    private Integer floor;
    private Float area;
    private Integer nrRoomType;
    private Integer nrRetirenmentHome;

    public Integer getNrRoom() {
        return nrRoom;
    }

    public void setNrRoom(Integer nrRoom) {
        this.nrRoom = nrRoom;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public Integer getNrRoomType() {
        return nrRoomType;
    }

    public void setNrRoomType(Integer nrRoomType) {
        this.nrRoomType = nrRoomType;
    }

    public Integer getNrRetirenmentHome() {
        return nrRetirenmentHome;
    }

    public void setNrRetirenmentHome(Integer nrRetirenmentHome) {
        this.nrRetirenmentHome = nrRetirenmentHome;
    }

    public ObservableList<Room> getCurrentsFreeRooms(Connection conn, int type){
        ObservableList<Room> listRoom = FXCollections.observableArrayList();
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "select * from pokoje p where  (nr_rodzaju_pokoju = ? and (select liczba_lozek from rodzaje_pokojow rp where rp.nr_rodzaju_pokoju = p.nr_rodzaju_pokoju) > " +
            "(select count(*) from pobyty pob where pob.nr_pokoju = p.nr_pokoju and (pob.data_wypisu is null "
                + "or pob.data_wypisu > ?)))";
        
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, type);
            stmt.setDate(2, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                Room room = new Room();
                room.nrRoom = rs.getInt(1);
                room.roomNumber = rs.getString(2);
                room.floor = rs.getInt(3);
                room.area = rs.getFloat(4);
                room.nrRoomType = rs.getInt(5);
                room.nrRetirenmentHome = rs.getInt(6);
                
                listRoom.add(room);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listRoom;        
    }
    
    public Room getBoarderRoom(Connection conn, int nrBoarder){
        Room room = new Room();
        Date date= new Date(Calendar.getInstance().getTime().getTime());

        String sql = "select * from pokoje p where nr_pokoju in (select nr_pokoju from pobyty pob where pob.nr_pensjonariusza = ? "
                + " and pob.data_wypisu is null or pob.data_wypisu >  ?)";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nrBoarder);
            stmt.setDate(2, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                room.nrRoom = rs.getInt(1);
                room.roomNumber = rs.getString(2);
                room.floor = rs.getInt(3);
                room.area = rs.getFloat(4);
                room.nrRoomType = rs.getInt(5);
                room.nrRetirenmentHome = rs.getInt(6);
            }
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }  
        return room;
    }
}
