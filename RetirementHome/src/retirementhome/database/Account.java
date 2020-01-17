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
import javafx.scene.control.Alert;

/**
 *
 * @author marys
 */
public class Account {
    private Integer nrAccount;
    private String login;
    private String password;
    private String type;
    private Integer index;

    public Integer getNrAccount() {
        return nrAccount;
    }

    public void setNrAccount(Integer nrAccount) {
        this.nrAccount = nrAccount;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    
    public void check(Connection conn,String log, String pass ){
        String sql = "SELECT * from konta where login = ? and haslo=? order by nr_konta";
        PreparedStatement stmt;
        ResultSet rs;
        this.password = pass;
        this.login = log;
        this.index = -1;
        this.type = "invalid";
        this.nrAccount = -1;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, log);
            stmt.setString(2, pass);
            rs =stmt.executeQuery();
            
            while(rs.next()){
            
                this.index = rs.getInt(1);
                this.type = rs.getString(4);
                this.index = rs.getInt(5);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        
    }
    
}