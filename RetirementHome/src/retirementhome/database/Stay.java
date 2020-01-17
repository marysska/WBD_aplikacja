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
public class Stay {
    private Integer nrStay;
    private Date dateCheckIn;
    private Date dateCheckOut;
    private Integer nrBoarder;
    private Integer nrOffer;
    private Integer nrRoom;

    public Integer getNrStay() {
        return nrStay;
    }

    public void setNrStay(Integer nrStay) {
        this.nrStay = nrStay;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public Integer getNrBoarder() {
        return nrBoarder;
    }

    public void setNrBoarder(Integer nrBoarder) {
        this.nrBoarder = nrBoarder;
    }

    public Integer getNrOffer() {
        return nrOffer;
    }

    public void setNrOffer(Integer nrOffer) {
        this.nrOffer = nrOffer;
    }

    public Integer getNrRoom() {
        return nrRoom;
    }

    public void setNrRoom(Integer nrRoom) {
        this.nrRoom = nrRoom;
    }
    public Stay getCurrentStay(Connection conn, int nrBoarder){
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "select * from pobytu where nr_pensjonariusza = ? and (data_wypisu is null or data_wypisu > ? )";
   
        PreparedStatement stmt;
        ResultSet rs;
        Stay stay = new Stay();
        stay.nrStay= -1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nrBoarder);
            stmt.setDate(2, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                stay.nrStay = rs.getInt(1);
                stay.dateCheckIn = rs.getDate(2);
                stay.dateCheckOut = rs.getDate(3);
                stay.nrBoarder = rs.getInt(4);
                stay.nrOffer = rs.getInt(5);
                stay.nrRoom = rs.getInt(6);
                 
                
                return stay;
            }
            
            
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return stay;        
        
    }
    
    public int insertStay(Connection conn, int nrBoarder, int nrOffer, int nrRoom){
        String sql = "insert into pobyty(data_przyjecia, nr_pensjonariusza, nr_oferty, nr_pokoju) values( ? , ? , ? , ? )";
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date);
            stmt.setInt(2, nrBoarder);
            stmt.setInt(3, nrOffer);
            stmt.setInt(4, nrRoom);
            
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with updating data");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;
            
        
    }
    
}
