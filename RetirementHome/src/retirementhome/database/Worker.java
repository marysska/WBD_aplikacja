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
public class Worker {
    private Integer nrWorker;
    private String name;
    private String lastName;
    private String nrDocument;
    private String city;
    private String street;
    private String nrLocation;
    private String nrHouse;
    private String postCode;
    private String nrAccount;
    private Float additionalSalary;
    private Date birthDate;
    private Date hireDate;
    private String pesel;
    private Date testExpirationDate;
    private String phoneNumber;
    private Integer nrRetirementHome;
    private Integer nrPosition;


    public Integer getNrWorker() {
        return nrWorker;
    }

    public void setNrWorker(Integer nrWorker) {
        this.nrWorker = nrWorker;
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

    public String getNrLocation() {
        return nrLocation;
    }

    public void setNrLocation(String nrLocation) {
        this.nrLocation = nrLocation;
    }

    public String getNrHouse() {
        return nrHouse;
    }

    public void setNrHouse(String nrHouse) {
        this.nrHouse = nrHouse;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getNrAccount() {
        return nrAccount;
    }

    public void setNrAccount(String nrAccount) {
        this.nrAccount = nrAccount;
    }

    public Float getAdditionalSalary() {
        return additionalSalary;
    }

    public void setAdditionalSalary(Float additionalSalary) {
        this.additionalSalary = additionalSalary;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public Date getTestExpirationDate() {
        return testExpirationDate;
    }

    public void setTestExpirationDate(Date testExpirationDate) {
        this.testExpirationDate = testExpirationDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getNrRetirementHome() {
        return nrRetirementHome;
    }

    public void setNrRetirementHome(Integer nrRetirementHome) {
        this.nrRetirementHome = nrRetirementHome;
    }

    public Integer getNrPosition() {
        return nrPosition;
    }

    public void setNrPosition(Integer nrPosition) {
        this.nrPosition = nrPosition;
    }
    
    public String getPosition(Connection conn){
        String sql = "Select nazwa_stanowiska from Stanowiska where Nr_stanowiska = "+this.nrPosition.toString();
        Statement stmt;
        ResultSet rs;
        String poss ="";
        try{
            stmt = conn.createStatement();
            rs =stmt.executeQuery(sql);
            while(rs.next()){
                poss = rs.getString(1);
            }
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return poss;
    }
    
    public float getSalary(Connection conn){
        String sql = "Select pensja_podstawowa from Stanowiska where Nr_stanowiska = "+this.nrPosition.toString();
        Statement stmt;
        float salary = this.additionalSalary;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs =stmt.executeQuery(sql);
            while(rs.next()){
                salary = salary + rs.getFloat(1);
            }
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return salary;
    }    
    
    public void setWorkersValues(Connection conn, int id ){
        String sql = "SELECT * from pracownicy where nr_pracownika = ?";
        PreparedStatement stmt;
        ResultSet rs;
        this.nrWorker = id;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);         
            rs =stmt.executeQuery();
            while(rs.next()){
                
                this.name = rs.getString(2);
                this.lastName = rs.getString(3);                
                this.nrDocument = rs.getString(4); 
                this.city = rs.getString(5);
                this.street = rs.getString(6);
                this.nrLocation = rs.getString(7);
                this.nrHouse = rs.getString(8);
                this.nrAccount = rs.getString(9);
                this.postCode = rs.getString(10);
                this.additionalSalary = rs.getFloat(11);
                this.birthDate = rs.getDate(11);
                this.hireDate = rs.getDate(12);
                this.pesel = rs.getString(13);
                this.testExpirationDate = rs.getDate(14);
                this.phoneNumber = rs.getString(15);
                this.nrRetirementHome = rs.getInt(16);
                this.nrPosition = rs.getInt(17);
            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        
    }
    
    public ObservableList<Worker> getAll(Connection conn){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy order by nr_rensjonariusza";
        Statement stmt;
        ResultSet rs;
        try{
            stmt = conn.createStatement();
            rs =stmt.executeQuery(sql);
            while(rs.next()){
                Worker worker = new Worker();
                worker.name = rs.getString(2);
                worker.lastName = rs.getString(3);                
                worker.nrDocument = rs.getString(4); 
                worker.city = rs.getString(5);
                worker.street = rs.getString(6);
                worker.nrLocation = rs.getString(7);
                worker.nrHouse = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(11);
                worker.hireDate = rs.getDate(12);
                worker.pesel = rs.getString(13);
                worker.testExpirationDate = rs.getDate(14);
                worker.phoneNumber = rs.getString(15);
                worker.nrRetirementHome = rs.getInt(16);
                worker.nrPosition = rs.getInt(17);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByName(Connection conn, String name, String lastName){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy where imie is like ? and nazwisko is like ? order by nr_rensjonariusza";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name+"%") ;
            stmt.setString(2, lastName+"%");
            rs =stmt.executeQuery();
            while(rs.next()){
                Worker worker = new Worker();
                worker.name = rs.getString(2);
                worker.lastName = rs.getString(3);                
                worker.nrDocument = rs.getString(4); 
                worker.city = rs.getString(5);
                worker.street = rs.getString(6);
                worker.nrLocation = rs.getString(7);
                worker.nrHouse = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(11);
                worker.hireDate = rs.getDate(12);
                worker.pesel = rs.getString(13);
                worker.testExpirationDate = rs.getDate(14);
                worker.phoneNumber = rs.getString(15);
                worker.nrRetirementHome = rs.getInt(16);
                worker.nrPosition = rs.getInt(17);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByPosition(Connection conn, int nrPos){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy where pos = ? order by nr_rensjonariusza";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, nrPos) ;
            rs =stmt.executeQuery();
            while(rs.next()){
                Worker worker = new Worker();
                worker.name = rs.getString(2);
                worker.lastName = rs.getString(3);                
                worker.nrDocument = rs.getString(4); 
                worker.city = rs.getString(5);
                worker.street = rs.getString(6);
                worker.nrLocation = rs.getString(7);
                worker.nrHouse = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(11);
                worker.hireDate = rs.getDate(12);
                worker.pesel = rs.getString(13);
                worker.testExpirationDate = rs.getDate(14);
                worker.phoneNumber = rs.getString(15);
                worker.nrRetirementHome = rs.getInt(16);
                worker.nrPosition = rs.getInt(17);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByTestExpiration(Connection conn){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);               
        Date date= new Date(cal.getTime().getTime());
        String sql = "SELECT * from pracownicy where data_waznosci_badan < ? order by nr_rensjonariusza";
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, date) ;
            rs =stmt.executeQuery();
            while(rs.next()){
                Worker worker = new Worker();
                worker.name = rs.getString(2);
                worker.lastName = rs.getString(3);                
                worker.nrDocument = rs.getString(4); 
                worker.city = rs.getString(5);
                worker.street = rs.getString(6);
                worker.nrLocation = rs.getString(7);
                worker.nrHouse = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(11);
                worker.hireDate = rs.getDate(12);
                worker.pesel = rs.getString(13);
                worker.testExpirationDate = rs.getDate(14);
                worker.phoneNumber = rs.getString(15);
                worker.nrRetirementHome = rs.getInt(16);
                worker.nrPosition = rs.getInt(17);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with data access");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }  
    
    
    public int setAdditionalSalary(Connection conn,int nrWorker, float newSalary){
        String sql = "update pensjonariusze set pensja_dodatkowa = ?  where nr_pracownika = ? ";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, newSalary);
            stmt.setInt(2, nrWorker);
            
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error with updating data");
            alert.setContentText("Details: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;     
    }
    public int udateWorker(Connection conn, int id,String doc,  String city, String street, String nrLoc, String nrHouse, String postCode , String acc){
        if (postCode.charAt(2) != '-'){
            return -1;
        }
        String sql = "update pracownicy set nr_dokumentu_pracownika = ? , miasto = ? , ulica= ? , m nr_domu = ? , nr_lokalu = ? , kod_pocztowy = ?, nr_konta = ?where nr_pensjonariusza = ? ";
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
            stmt.setString(7, sql);
            stmt.setString(7, acc);
            
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
