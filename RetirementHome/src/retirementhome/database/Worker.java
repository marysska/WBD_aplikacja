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
    private String nrHouse;
    private String nrFlat;
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
    private String positionName;
    private Float basicSalary;
    

    public String getPositionName(){
        return this.positionName;
    }
    public void setPositionName(String position){
        this.positionName = position;
    }
    
    public Float getBasicSalary(){
        return this.basicSalary;
    }
    public void setBasicSalary(Float salary){
        this.basicSalary = salary;
    }
    
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

    public String getNrHouse() {
        return nrHouse;
    }

    public void setNrHouse(String nrHouse) {
        this.nrHouse = nrHouse;
    }

    public String getNrFlat() {
        return nrFlat;
    }

    public void setNrFlat(String nrFlat) {
        this.nrFlat = nrFlat;
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
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();            
        }
        return poss;
    }
    
    public float getSalary(Connection conn){
        String sql = "Select pensja_podstawowa from Stanowiska where nr_stanowiska = "+this.nrPosition.toString();
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
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();            
        }
        return salary;
    }    
    
    public void setWorkersValues(Connection conn, int id ){
        String sql = "select p.*, s.nazwa_stanowiska, s.pensja_podstawowa from stanowiska s, pracownicy  p where p.nr_stanowiska = s.nr_stanowiska and p.nr_pracownika = ?";
        PreparedStatement stmt;
        ResultSet rs;
        this.nrWorker = id;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);         
            rs =stmt.executeQuery();
            while(rs.next()){
                
                this.nrWorker = rs.getInt(1);
                this.name = rs.getString(2);
                this.lastName = rs.getString(3);                
                this.nrDocument = rs.getString(4); 
                this.city = rs.getString(5);
                this.street = rs.getString(6);
                this.nrHouse = rs.getString(7);
                this.nrFlat = rs.getString(8);
                this.nrAccount = rs.getString(9);
                this.postCode = rs.getString(10);
                this.additionalSalary = rs.getFloat(11);
                this.birthDate = rs.getDate(12);
                this.hireDate = rs.getDate(13);
                this.pesel = rs.getString(14);
                this.testExpirationDate = rs.getDate(15);
                this.phoneNumber = rs.getString(16);
                this.nrRetirementHome = rs.getInt(17);
                this.nrPosition = rs.getInt(18);
                this.positionName = rs.getString(19);
                this.basicSalary = rs.getFloat(20);
            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();
        }
        
    }
  
    public ObservableList<Worker> getWorkersList(Connection conn, String name, String surname, String position){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "select p.*, s.nazwa_stanowiska, s.pensja_podstawowa from stanowiska s, pracownicy  p where p.nr_stanowiska = s.nr_stanowiska and p.imie like ? and p.nazwisko like ? and s.nazwa_stanowiska like ? order by p.nr_pracownika";
       
        PreparedStatement stmt;
        ResultSet rs;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, name+"%") ;
            stmt.setString(2, surname+"%");
            stmt.setString(3, position+"%");
            rs =stmt.executeQuery();
            while(rs.next()){
                Worker worker = new Worker();
                worker.nrWorker = rs.getInt(1);
                worker.name = rs.getString(2);
                worker.lastName = rs.getString(3);                
                worker.nrDocument = rs.getString(4); 
                worker.city = rs.getString(5);
                worker.street = rs.getString(6);
                worker.nrHouse = rs.getString(7);
                worker.nrFlat = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(12);
                worker.hireDate = rs.getDate(13);
                worker.pesel = rs.getString(14);
                worker.testExpirationDate = rs.getDate(15);
                worker.phoneNumber = rs.getString(16);
                worker.nrRetirementHome = rs.getInt(17);
                worker.nrPosition = rs.getInt(18);
                worker.positionName = rs.getString(19);
                worker.basicSalary = rs.getFloat(20);
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }    
    
    
    
    public ObservableList<Worker> getAll(Connection conn){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy order by nr_pracownika";
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
                worker.nrHouse = rs.getString(7);
                worker.nrFlat = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(12);
                worker.hireDate = rs.getDate(13);
                worker.pesel = rs.getString(14);
                worker.testExpirationDate = rs.getDate(15);
                worker.phoneNumber = rs.getString(16);
                worker.nrRetirementHome = rs.getInt(17);
                worker.nrPosition = rs.getInt(18);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByName(Connection conn, String name, String lastName){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy where imie is like ? and nazwisko is like ? order by nr_pracownika";
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
                worker.nrHouse = rs.getString(7);
                worker.nrFlat = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(12);
                worker.hireDate = rs.getDate(13);
                worker.pesel = rs.getString(14);
                worker.testExpirationDate = rs.getDate(15);
                worker.phoneNumber = rs.getString(16);
                worker.nrRetirementHome = rs.getInt(17);
                worker.nrPosition = rs.getInt(18);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByPosition(Connection conn, int nrPos){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        String sql = "SELECT * from pracownicy where pos = ? order by nr_pracownika";
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
                worker.nrHouse = rs.getString(7);
                worker.nrFlat = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(12);
                worker.hireDate = rs.getDate(13);
                worker.pesel = rs.getString(14);
                worker.testExpirationDate = rs.getDate(15);
                worker.phoneNumber = rs.getString(16);
                worker.nrRetirementHome = rs.getInt(17);
                worker.nrPosition = rs.getInt(18);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();
        }
        return listWorker;
    }
    
    public ObservableList<Worker> getByTestExpiration(Connection conn){
        ObservableList<Worker> listWorker = FXCollections.observableArrayList();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 3);               
        Date date= new Date(cal.getTime().getTime());
        String sql = "SELECT * from pracownicy where data_waznosci_badan < ? order by nr_pracownika";
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
                worker.nrHouse = rs.getString(7);
                worker.nrFlat = rs.getString(8);
                worker.nrAccount = rs.getString(9);
                worker.postCode = rs.getString(10);
                worker.additionalSalary = rs.getFloat(11);
                worker.birthDate = rs.getDate(12);
                worker.hireDate = rs.getDate(13);
                worker.pesel = rs.getString(14);
                worker.testExpirationDate = rs.getDate(15);
                worker.phoneNumber = rs.getString(16);
                worker.nrRetirementHome = rs.getInt(17);
                worker.nrPosition = rs.getInt(18);
                
                listWorker.add(worker);

            }
  
        }catch (SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
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
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();            
        }
        return res;     
    }
    
    public int updateWorkerByBoss(Connection conn, Float newSalary, String position, Date date ){
        String sql = "update pracownicy p set p.pensja_dodatkowa = ? , p.data_waznosci_badan = ? , p.nr_stanowiska = (select s.nr_stanowiska from stanowiska s where s.nazwa_stanowiska = ?) where nr_pracownika = ? ";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, newSalary);
            stmt.setDate(2, date);
            stmt.setString(3, position);
            stmt.setInt(4, this.nrWorker);
            res = stmt.executeUpdate();
            
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();  
            res = -1;
        }
        return res;
    }
    
    public int updateWorker(Connection conn, int id,String doc,  String city, String street, String nrLoc, String nrFlat, String postCode , String acc, String nrTel){
        if (postCode.charAt(2) != '-'){
            return -1;
        }
        
        String sql = "update pracownicy set nr_dokumentu_pracownika = ? , miasto = ? , ulica= ? , nr_posesji = ? , nr_lokalu = ? , kod_pocztowy = ?, nr_konta = ?, nr_telefonu = ? where nr_pracownika = ? ";
        PreparedStatement stmt;
        Integer res = 0;
        try{
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, doc);
            stmt.setString(2, city);
            stmt.setString(3, street);
            stmt.setString(4, nrLoc);
            stmt.setString(5, nrFlat);
            stmt.setString(6, postCode);
            stmt.setString(7, acc);
            stmt.setString(8, nrTel);
            stmt.setInt(9, id);
            res = stmt.executeUpdate();
        }catch(SQLException exc){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd przy dostępie do bazy danych");
            alert.setContentText("Szczegoły: "+exc.getMessage());
            alert.showAndWait();  
            res = -1;
        }
        return res;
    }    
    
    
}
