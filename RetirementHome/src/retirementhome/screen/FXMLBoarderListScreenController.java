/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.screen;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import retirementhome.DBConnection;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import retirementhome.database.Boarder;
/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderListScreenController implements Initializable {
    
    private Connection conn;
    
    private boolean isModified; //ustawiany na true jest to modyfikacja, na false jesli dodawanie
    private int nrBoarderModified;
    
    private Integer workerId;

    @FXML
    private TextField nameFilterPole;

    @FXML
    private TextField surnameFilterPole;

    @FXML
    private Button filterButton;

    @FXML
    private CheckBox checkNotCurrent;

    @FXML
    private TableView<Boarder> tableBoarders;

    @FXML
    private TableColumn<Boarder, Integer> boardersNrCol;

    @FXML
    private TableColumn<Boarder, String> boardersNameCol;

    @FXML
    private TableColumn<Boarder, String> boardersSurnameCol;

    @FXML
    private TableColumn<Boarder, String> boardersDocumentCol;

    @FXML
    private TableColumn<Boarder, String> boardersStreetCol;

    @FXML
    private TableColumn<Boarder, String> boardersHomeNrCol;

    @FXML
    private TableColumn<Boarder, String> boardersFlatNrCol;

    @FXML
    private TableColumn<Boarder, String> boardersPostCodeCol;

    @FXML
    private TableColumn<Boarder, String> boardersCityCol;

    @FXML
    private TableColumn<Boarder, Date> boardersDateCol;

    @FXML
    private TableColumn<Boarder, String> boardersPESELCol;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button modifyButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button rollbackButton;

    @FXML
    private TextField namePole;

    @FXML
    private TextField flatNrPole;

    @FXML
    private TextField postcodePole;

    @FXML
    private TextField homeNrPole;

    @FXML
    private TextField streetPole;

    @FXML
    private TextField documentPole;

    @FXML
    private TextField surnamePole;

    @FXML
    private TextField cityPole;

    @FXML
    private TextField PESELPole;

    @FXML
    private DatePicker datePole;

    @FXML
    private CheckBox femaleCheck;

    @FXML
    private CheckBox maleCheck;
    
    @FXML 
    public void onCheckFemale(ActionEvent action){
        maleCheck.setSelected(false);
    }
    
    @FXML 
    public void onCheckMale(ActionEvent action){
        femaleCheck.setSelected(false);
    }
    
    private ObservableList<Boarder> listBoarders;

    public void setWorkerId(Integer id){
        this.workerId = id;
        set();
        //textFX.setText(workerId.toString());
    }
    
    private void deactivatePanel(boolean f){
        namePole.setDisable(f);
        surnamePole.setDisable(f);
        flatNrPole.setDisable(f);
        homeNrPole.setDisable(f);
        cityPole.setDisable(f);
        PESELPole.setDisable(f);
        datePole.setDisable(f);
        femaleCheck.setDisable(f);
        maleCheck.setDisable(f);
        documentPole.setDisable(f);
        streetPole.setDisable(f);
        postcodePole.setDisable(f);
        saveButton.setDisable(f);
        rollbackButton.setDisable(f);
        
    }
    
    private void activatePanelToModify(){
        namePole.setDisable(true);
        surnamePole.setDisable(true);
        flatNrPole.setDisable(false);
        homeNrPole.setDisable(false);
        cityPole.setDisable(false);
        PESELPole.setDisable(true);
        datePole.setDisable(true);
        femaleCheck.setDisable(true);
        maleCheck.setDisable(true);
        documentPole.setDisable(false);
        streetPole.setDisable(false);
        postcodePole.setDisable(false);
        saveButton.setDisable(false);
        rollbackButton.setDisable(false);
        
    }
    private void fillPanelToModify(Boarder boarder){
        namePole.setText(boarder.getName());
        surnamePole.setText(boarder.getLastName());
        flatNrPole.setText(boarder.getNrFlat());
        homeNrPole.setText(boarder.getNrHause());
        cityPole.setText(boarder.getCity());
        PESELPole.setText(boarder.getPesel());
        if(boarder.getSex()=='K'){
            femaleCheck.setSelected(true);
            maleCheck.setSelected(false);
        }
        else{
            femaleCheck.setSelected(false);
            maleCheck.setSelected(true);            
        }

        datePole.setValue(convertToLocalDateViaSqlDate(boarder.getBirthDate()));
        documentPole.setText(boarder.getNrDocument());
        streetPole.setText(boarder.getStreet());
        postcodePole.setText(boarder.getPostCode());
    }
    
    private void clearPanelClean(){
        namePole.setText(null);
        surnamePole.setText(null);
        flatNrPole.setText(null);
        homeNrPole.setText(null);
        cityPole.setText(null);
        PESELPole.setText(null);       
        femaleCheck.setSelected(false);
        maleCheck.setSelected(false);
        documentPole.setText(null);
        streetPole.setText(null);
        postcodePole.setText(null);
        datePole.setValue(null);
    }    
    private void setBoardersTable(){

        boardersNrCol.setCellValueFactory(new PropertyValueFactory<>("nrBoarder"));

        boardersNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        boardersSurnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        boardersDocumentCol.setCellValueFactory(new PropertyValueFactory<>("nrDocument"));

        boardersStreetCol.setCellValueFactory(new PropertyValueFactory<>("street"));

        boardersHomeNrCol.setCellValueFactory(new PropertyValueFactory<>("nrHause"));

        boardersFlatNrCol.setCellValueFactory(new PropertyValueFactory<>("nrFlat"));

        boardersPostCodeCol.setCellValueFactory(new PropertyValueFactory<>("postCode"));

        boardersCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));

        boardersDateCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        boardersPESELCol.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        tableBoarders.setItems(listBoarders);       
    }
    
    private void getBoardersTable(){
        Boarder boarder = new Boarder();
        String name = nameFilterPole.getText();
        String surname = surnameFilterPole.getText();
        if(checkNotCurrent.isSelected()){
            listBoarders = boarder.getByNameFree(conn, name, surname);
        }
        else{
            listBoarders = boarder.getByName(conn, name, surname);
        }
    }
    
    public void set(){
        //Funkcja wolana na poczatku zeby wpisac dane wszystkich userow
        deactivatePanel(true);
        isModified = false;
        setTable();
    }
    
    private void setTable(){
        getBoardersTable();
        setBoardersTable();        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn= DBConnection.getConnection();
    }    
    
    @FXML
    public void onFilterButtonClick(ActionEvent action){
        getBoardersTable();
        setBoardersTable();
        
    }
    @FXML
    public void onAddButtonClick(ActionEvent action){
        deactivatePanel(false);
        addButton.setDisable(true);
        modifyButton.setDisable(true);
        deleteButton.setDisable(true);
        isModified = false;
        clearPanelClean();
    }
    
    @FXML 
    public void onModifyButtonClick(ActionEvent action){
        //pobrac z tabeli nr wiersza co ktos kliknal
        int rowid = tableBoarders.getSelectionModel().getSelectedIndex();
        if(rowid < 0){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę zaznaczyć w tabeli pensjonariusza do modyfikacji");
            alert.show();
        }
        else{
            Boarder boarder = new Boarder();
            nrBoarderModified = tableBoarders.getSelectionModel().getSelectedItem().getNrBoarder();
            if(nrBoarderModified < 0){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Nie można znaleźć pensjonariusza w bazie danych");
                alert.show();              
            }
            boarder.setBoarderValues(conn, nrBoarderModified);
            fillPanelToModify(boarder);
            activatePanelToModify();
            modifyButton.setDisable(true);
            deleteButton.setDisable(true);
            addButton.setDisable(true);
            isModified = true;
        }
        
    }
    

    private void saveModify(){
        //modyfikacja
        String street = streetPole.getText();
        String hause = homeNrPole.getText();
        String flat = flatNrPole.getText();
        String document = documentPole.getText();
        String postcode = postcodePole.getText();
        String city = cityPole.getText();
                
        if(street.isEmpty() || hause.isEmpty() || document.isEmpty() || postcode.isEmpty() || city.isEmpty())
        {
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę uzupełnić wszystkie wymagane pola");
            alert.show();
            return;
        }
        
        boolean goodPostcode = isGoodPostcode(postcode);
        
        if(goodPostcode == false){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę podać poprawny format kodu pocztowego");
            alert.show();
            return;            
        }
        Boarder boarder = new Boarder();
        int result =boarder.updateBoarder(conn,nrBoarderModified,document,city, street,hause,flat,postcode);
        if(result ==1){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Zaktualizowano dane!");
            alert.setContentText("Udało się zaktualizować dane pensjonariuszowi "+namePole.getText()+" "+surnamePole.getText()+"!");
            alert.show();             
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nastąpił błąd!");
            alert.setContentText("Liczba zaktualizowanych pensjonariuszy: "+ Integer.toString(result));
            alert.show();             
        }
        
        //powrot
        clearPanelClean();
        deactivatePanel(true);
        modifyButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        isModified = false;  
        setTable();
    }
    
    public boolean isDigit(char a){
        if(a>='0' && a<='9'){
            return true;
        }
        return false;
    }
    
    @FXML 
    public void onSaveButtonClick(ActionEvent action){
        
        if(isModified){
            //modyfikacja
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź");
            alert.setContentText("Czy chcesz wprowadzić zmiany dla "+namePole.getText()+" "+surnamePole.getText()+"?");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.get()==ButtonType.OK){
                saveModify();
            }
            else{
                return;
            }
            
        }
        else{
            //tworazenie nowego
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź");
            alert.setContentText("Czy chcesz wprowadzić nowego pensjonariusza "+namePole.getText()+" "+surnamePole.getText()+"?");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.get()==ButtonType.OK){
                saveAdd();
            }
            else{
                return;
            }
        }
    }
    
    
    @FXML
    public void onDeleteButtonClick(ActionEvent action){
        int rowid = tableBoarders.getSelectionModel().getSelectedIndex();
        if(rowid < 0){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę zaznaczyć w tabeli pensjonariusza do usunięcia");
            alert.show();
        }
        else{
            Boarder boarder = new Boarder();
            nrBoarderModified = tableBoarders.getSelectionModel().getSelectedItem().getNrBoarder();
            if(nrBoarderModified < 0){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Nie można znaleźć pensjonariusza w bazie danych");
                alert.show();              
            }
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź");
            alert.setContentText("Czy chcesz usunąć pensjonariusza "+ tableBoarders.getSelectionModel().getSelectedItem().getName()+" "+ tableBoarders.getSelectionModel().getSelectedItem().getLastName()+"?");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.get()==ButtonType.OK){
                boarder.setBoarderValues(conn, nrBoarderModified);
                delete(boarder);
            }
            else{
                return;
            }
            
        }    
    }
    
    private void delete(Boarder boarder){
        if(boarder.isCurrent(conn, boarder.getNrBoarder())){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Nie można usunąć aktualnie zakwaterowanego pensjonariusza!");
                alert.show();     
                return;
        }
        int res = boarder.deleteBoarder(conn, boarder.getNrBoarder());
        if(res != 1){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setContentText("Operacja nie powiodła się, usunięto: "+Integer.toString(res));
            alert.show();     
            return;            
        }
        Alert alert= new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukces");
        alert.setContentText("Usunięto pensjonariusza! ");
        alert.show();
        setTable();
                   
    }
    
    
    private void saveAdd(){
        String street = streetPole.getText();
        String hause = homeNrPole.getText();
        String flat = flatNrPole.getText();
        String document = documentPole.getText();
        String postcode = postcodePole.getText();
        String city = cityPole.getText();
        String name = namePole.getText();
        String surname = surnamePole.getText();
        String pesel = PESELPole.getText();
        LocalDate date = datePole.getValue();
        //Date date = convertToDate(datePole.getValue());
        char sex = 'M';        
        if(femaleCheck.isSelected()){
            sex = 'K';
        }
        
        if(street == null || hause== null || document == null || postcode == null || city == null || name == null || surname == null || date == null || (!femaleCheck.isSelected() && !maleCheck.isSelected())){
            
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę uzupełnić wszystkie wymagane pola");
            alert.show();
            return;  
        }
        
         Date date1 = convertToDate(date);
        if(!isGoodPostcode(postcode)){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę podać poprawny kod pocztowy");
            alert.show();
            return;            
        }
        
        if(!isGoodDate(date1)){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę podać dobrą datę (wiek minimum 50 lat)");
            alert.show();
            return;              
        }
        if(pesel != null && !isGoodPesel(date1, pesel)){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę podać poprawny pesel");
            alert.show();
            return;              
        } 

        Boarder boarder = new Boarder();
        int result = boarder.insertBoarder(conn, name, surname,document, sex, city, street, hause, flat, postcode, date1, pesel );
        if(result ==1){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wstawiono pensjonariusza!");
            alert.setContentText("Udało się wstawić nowego pensjonariusza "+namePole.getText()+" "+surnamePole.getText()+"!");
            alert.show();             
        }else{
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nastąpił błąd!");
            alert.setContentText("Liczba dodanych pensjonariuszy: "+ Integer.toString(result));
            alert.show();             
        }
        
        //powrot
        clearPanelClean();
        deactivatePanel(true);
        modifyButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        isModified = false;  
        setTable();
        

    }
    
    private boolean isGoodPesel(Date date, String pesel){
        String dateString = date.toString();
        boolean isGoodPesel = true;
        if(pesel.length() != 11){
            isGoodPesel = false;
        }
        else{
            
            if(dateString.charAt(2) != pesel.charAt(0) || dateString.charAt(3) != pesel.charAt(1) || dateString.charAt(5) != pesel.charAt(2) || dateString.charAt(6) != pesel.charAt(3)
                    || dateString.charAt(8) != pesel.charAt(4) || dateString.charAt(9) != pesel.charAt(5)){
                isGoodPesel = false;
            }
        }
        return isGoodPesel;
    }
    private boolean isGoodDate(Date date){

        
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        year = year - 50;
        
        c.set(Calendar.YEAR, year);
        Date dateToCompare= new Date(c.getTime().getTime());
        int res = dateToCompare.compareTo(date);
        if(res > 0){
            return true;
        }
        return false;
    }
    
    private boolean isGoodPostcode(String postcode){
        int size = postcode.length();
        boolean goodPostcode = false;
        if (size == 6){
            char one = postcode.charAt(0);
            char two = postcode.charAt(1);
            char three = postcode.charAt(2);
            char four = postcode.charAt(3);
            char five = postcode.charAt(4);
            char six = postcode.charAt(5);
            if(isDigit(one) && isDigit(two) && isDigit(four) && isDigit(five)&& isDigit(six)&& three == '-'){
                goodPostcode = true;
            }
        }
        return goodPostcode;        
    }
    
    
    private void rollback(){
        
        clearPanelClean();
        deactivatePanel(true);
        modifyButton.setDisable(false);
        deleteButton.setDisable(false);
        addButton.setDisable(false);
        isModified = false;
        
    }
    
    @FXML 
    public void onRollbackButtonClick(ActionEvent action){
        if(isModified){
            //modyfikacja
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź");
            alert.setContentText("Czy chcesz przerwać aktualizację danych?");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.get()==ButtonType.OK){
                rollback();
            }
            else{
                return;
            }
            
        }
        else{
            //tworazenie nowego
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdź");
            alert.setContentText("Czy chcesz przerwać wprowadzanie nowego pensjonariusza?");
            Optional<ButtonType> type = alert.showAndWait();
            if(type.get()==ButtonType.OK){
                rollback();
            }
            else{
                return;
            }
        }     
    }
    
    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return dateToConvert.toLocalDate();
    } 
    
    public Date convertToDate(LocalDate dateToConvert){
        System.out.println("jestem 22");
        return Date.valueOf(dateToConvert);
    }
}
