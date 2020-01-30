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
public class Boarder {
    private Integer nrBoarder;
    private String name;
    private String lastName;
    private String nrDocument;
    private char sex;
    private String city;
    private String street;
    private String nrHause;
    private String nrFlat;
    private String postCode;
    private Date birthDate;
    private String pesel;
    private String additionalInfo;
    private Integer nrRetirementHome;

    public Integer getNrBoarder() {
        return nrBoarder;
    }

    public void setNrBoarder(Integer nrBoarder) {
        this.nrBoarder = nrBoarder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNrDocument() {
        return nrDocument;
    }

    public void setNrDocument(String nrDocument) {
        this.nrDocument = nrDocument;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNrHause() {
        return nrHause;
    }

    public void setNrHause(String nrHause) {
        this.nrHause = nrHause;
    }

    public String getNrFlat() {
        return nrFlat;
    }

    public void setNrFlat(String nrHouse) {
        this.nrFlat = nrHouse;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public Integer getNrRetirementHome() {
        return nrRetirementHome;
    }

    public void setNrRetirementHome(Integer nrRetirementHome) {
        this.nrRetirementHome = nrRetirementHome;
    }

    public ObservableList<Boarder> getAll(Connection conn){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        String sql = "SELECT * from pensjonariusze order by nr_pensjonariusza";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs =stmt.executeQuery(sql);
            while(rs.next()){
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(9);
                boarder.nrFlat = rs.getString(8);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;
    }
    
    public ObservableList<Boarder> getByName(Connection conn, String name, String lastName){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        String sql = "SELECT * from pensjonariusze where imie like ? and nazwisko like ? order by nr_pensjonariusza";
        System.out.println(name);
        System.out.println(lastName);
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name+"%") ;
            stmt.setString(2, lastName+"%");
            rs =stmt.executeQuery();
            while(rs.next()){
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(8);
                boarder.nrFlat = rs.getString(9);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;
    }
    
    
    
    public ObservableList<Boarder> getByNameFree(Connection conn, String name, String lastName){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        String sql = "SELECT * from pensjonariusze where imie like ? and nazwisko like ? "
                + "and nr_pensjonariusza not in (select nr_pensjonariusza from Pobyty where data_wypisu is null or data_wypisu > ?)"
                + "order by nr_pensjonariusza";
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name+"%") ;
            stmt.setString(2, lastName+"%");
            stmt.setDate(3, date);
            rs =stmt.executeQuery();
            while(rs.next()){
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(8);
                boarder.nrFlat = rs.getString(9);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;
    }
    public boolean isCurrent(Connection conn, int id)
    {
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "SELECT count(*) from pobyty where nr_pensjonariusza = ? and"
                + "(data_wypisu is null or data_wypisu > ? )";
        PreparedStatement stmt;
        boolean result = false;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            stmt.setDate(2, date) ;
            rs =stmt.executeQuery();
            while(rs.next()){
                int num = rs.getInt(1);
                if(num == 1){
                    result = true;
                }
            }
            
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access ");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return result;
    }
    public ObservableList<Boarder> getCurrentsBoarders(Connection conn){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "SELECT * from pensjonariusze where nr_pensjonariusza in"
                +"(select nr_pensjonariusza from Pobyty where data_wypisu is null "
                +"or data_wypisu > ?)"
                +"order by nr_pensjonariusza";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date) ;
            rs =stmt.executeQuery();
            while(rs.next()){
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(8);
                boarder.nrFlat = rs.getString(9);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;
    }
    
    public int deleteBoarder(Connection conn, int id){
        String sql = "Delete from pensjonariusze where nr_pensjonariusza = ? ";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);           
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with deleting data 33");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;        
    }
    
    public ObservableList<Boarder> getCurrentsFreeBoarders(Connection conn){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql = "SELECT * from pensjonariusze where nr_pensjonariusza not in"
                +"(select nr_pensjonariusza from Pobyty where data_wypisu is null "
                +"or data_wypisu > ?)"
                +"order by nr_pensjonariusza";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date) ;
            rs =stmt.executeQuery();
            while(rs.next()){
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(8);
                boarder.nrFlat = rs.getString(9);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;        
    }

    public int insertBoarder(Connection conn, String name, String lastName, String doc, char sex, String city, String street, String nrLoc, String nrHouse, String postCode, Date birthDate, String pesel){
        String sql = "insert into Pensjonariusze(imie, nazwisko, nr_dokumentu_pensjonariusza, plec, miasto, ulica, nr_posesji, nr_lokalu, kod_pocztowy, data_urodzenia, pesel, nr_domu_seniora)"+
                " values(?,?,?,?,?,?,?,?,?,?,?,1)";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setString(3, doc);
            stmt.setString(4, Character.toString(sex));
            stmt.setString(5, city);
            stmt.setString(6, street);
            stmt.setString(7, nrLoc);
            stmt.setString(8, nrHouse);
            stmt.setString(9, postCode);
            stmt.setDate(10, birthDate);
            stmt.setString(11, pesel);
            
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with updating data");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;
            
        
    }
    
    public int updateBoarder(Connection conn, int id,String doc,  String city, String street, String nrLoc, String nrHouse, String postCode ){
        if (postCode.charAt(2) != '-'){
            return -1;
        }
        String sql = "update pensjonariusze set nr_dokumentu_pensjonariusza = ? , miasto = ? , ulica = ? , nr_posesji = ? , nr_lokalu = ? , kod_pocztowy = ? where nr_pensjonariusza = ? ";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, doc);
            stmt.setString(2, city);
            stmt.setString(3, street);
            stmt.setString(4, nrLoc);
            stmt.setString(5, nrHouse);
            stmt.setString(6, postCode);
            stmt.setInt(7, id);
            
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with updating data");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;
    }
    
    public void setBoarderValues(Connection conn, int id ){
        String sql = "SELECT * from pensjonariusze where nr_pensjonariusza = ?";
        PreparedStatement stmt;
        ResultSet rs;
        this.nrBoarder = id;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);         
            rs =stmt.executeQuery();
            while(rs.next()){
                
                this.name = rs.getString(2);
                this.lastName = rs.getString(3);                
                this.nrDocument = rs.getString(4); 
                this.sex = rs.getString(5).charAt(0);
                this.city = rs.getString(6);
                this.street = rs.getString(7);
                this.nrHause = rs.getString(8);
                this.nrFlat = rs.getString(9);
                this.postCode = rs.getString(10);
                this.birthDate = rs.getDate(11);
                this.pesel = rs.getString(12);
                this.additionalInfo = rs.getString(13);
                this.nrRetirementHome = rs.getInt(14);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        
    }
    
    
    public ObservableList<Boarder> getRoommates(Connection conn, int myId, int idRoom){
        ObservableList<Boarder> listBoarder = FXCollections.observableArrayList();
        Date date= new Date(Calendar.getInstance().getTime().getTime());
        String sql ="select * from pensjonariusze where nr_pensjonariusza  not in ( ? ) and nr_pensjonariusza in (select nr_pensjonariusza from pobyty pob where nr_pokoju = ? "
                + "and (data_wypisu is null or data_wypisu > ?))";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, myId);
            stmt.setInt(2, idRoom);
            stmt.setDate(3, date);
            rs =stmt.executeQuery();
            while(rs.next())
            {
                Boarder boarder = new Boarder();
                boarder.nrBoarder = rs.getInt(1);
                boarder.name = rs.getString(2);
                boarder.lastName = rs.getString(3);
                boarder.nrDocument = rs.getString(4);
                boarder.sex = rs.getString(5).charAt(0);
                boarder.city = rs.getString(6);
                boarder.street = rs.getString(7);
                boarder.nrHause = rs.getString(8);
                boarder.nrFlat = rs.getString(9);
                boarder.postCode = rs.getString(10);
                boarder.birthDate = rs.getDate(11);
                boarder.pesel = rs.getString(12);
                boarder.additionalInfo = rs.getString(13);
                boarder.nrRetirementHome = rs.getInt(14);
                
                listBoarder.add(boarder);
            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access 99");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listBoarder;        
    }
}




