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
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import retirementhome.DBConnection;
import retirementhome.database.Position;
import retirementhome.database.Worker;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLWorkerListScreenController implements Initializable {
    
     private Integer workerId;
     private Integer nrWorkerModified;
    /**
     * Initializes the controller class.
     */
    private Connection conn;
    private ObservableList<Worker> listWorkers;
    
    private ObservableList<String> listNamePositions;
     @FXML
    private TextField nameFilterPole;

    @FXML
    private TextField surnameFilterPole;

    @FXML
    private Button filterButton;

    @FXML
    private TextField positionFilterPole;

    @FXML
    private TableView<Worker> tableWorker;

    @FXML
    private TableColumn<Worker, Integer> tableWorkerNr;

    @FXML
    private TableColumn<Worker, String> tableWorkerImie;

    @FXML
    private TableColumn<Worker, String> tableWorkerNazwisko;

    @FXML
    private TableColumn<Worker, String> tableWorkerStanowisko;

    @FXML
    private TableColumn<Worker, String> tableWorkerNrDokumentu;

    @FXML
    private TableColumn<Worker, String> tableWorkerNrTelefonu;

    @FXML
    private TableColumn<Worker, Date> tableWorkerDataZatrudnienia;

    @FXML
    private TableColumn<Worker, Float> tableWorkerPensjaPodstawowa;

    @FXML
    private TableColumn<Worker, Float> tableWorkerPensjaDodatkowa;

    @FXML
    private TableColumn<Worker, Date> tablaWorkerDataBadan;

    @FXML
    private Button zatwierdzButton;

    @FXML
    private Button anulujButton;

    @FXML
    private TextField paymentPole;

    @FXML
    private DatePicker datePole;

    @FXML
    private ComboBox<String> positionPole;

    @FXML
    private Button modifyButton;
    
    @FXML
    private Text textImieWorker;

    @FXML
    private Text textNazwiskoWorker;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn= DBConnection.getConnection();
    } 
    private void blockModifyFields(boolean block){
        zatwierdzButton.setDisable(block);
        anulujButton.setDisable(block);
        modifyButton.setDisable(!block);
        paymentPole.setDisable(block);
        datePole.setDisable(block);
        positionPole.setDisable(block);
    }
    
    @FXML
    public void filterButtonOnAction(ActionEvent event){
        set();
    }  

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return dateToConvert.toLocalDate();
    } 
    private void fillPanelToModify(Worker worker){
        paymentPole.setText(worker.getAdditionalSalary().toString());         
        datePole.setValue(convertToLocalDateViaSqlDate(worker.getTestExpirationDate()));
        positionPole.setValue(worker.getPositionName());
        positionPole.setFocusTraversable(false);
        textNazwiskoWorker.setText(worker.getLastName());
        textImieWorker.setText(worker.getName());
    }
    
    private void clearPanelToModify(){
        paymentPole.setText(null);         
        datePole.setValue(null);
        positionPole.setValue(null);
        textNazwiskoWorker.setText(null);
        textImieWorker.setText(null);
   
    }
    
    @FXML
    public void modifyButtonOnAction(ActionEvent event){
        int rowid = tableWorker.getSelectionModel().getSelectedIndex();
        if(rowid < 0){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę zaznaczyć w tabeli pracownika do modyfikacji");
            alert.show();
        }
        else{
            Worker worker = new Worker();
            this.nrWorkerModified = tableWorker.getSelectionModel().getSelectedItem().getNrWorker();
            if(this.nrWorkerModified < 0){
                Alert alert= new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Nie można znaleźć pracownika w bazie danych");
                alert.show();              
            }
            worker.setWorkersValues(conn, this.nrWorkerModified);
            fillPanelToModify(worker);
            blockModifyFields(false);
            
        }
    }
   
    @FXML
    public void anulujButtonOnAction(ActionEvent event){
        clearPanelToModify();
        blockModifyFields(true);
        
    }
    
    private boolean isGoodDate(Date date){     
        Calendar c = Calendar.getInstance();
        Date dateToCompare= new Date(c.getTime().getTime());
        int res = dateToCompare.compareTo(date);        
        return res < 0;
    }
    
    
    private boolean checkIfCorrectValues(String salary, Date date){
        boolean correctValues = true;
        Float newSalary;
        try{
            newSalary = Float.parseFloat(salary);
        }catch(NumberFormatException e){
            return false;
        }
        if(Float.compare(newSalary, 0.0f) < 0){
            correctValues = false;
        }
        if(!isGoodDate(date)){
            correctValues = false;
        }
        return correctValues;
    }
    
    public Date convertToDate(LocalDate dateToConvert){
        return Date.valueOf(dateToConvert);
    }
    @FXML
    public void zatwierdzButtonOnAction(ActionEvent event){
        try{
        Alert alertMain= new Alert(Alert.AlertType.CONFIRMATION);
        alertMain.setTitle("Potwierdź");
        alertMain.setContentText("Czy chcesz wprowadzić zmiany?");
        Optional<ButtonType> type = alertMain.showAndWait();
        String salary = this.paymentPole.getText();
        Date date = convertToDate(this.datePole.getValue());
        if(type.get()==ButtonType.OK){
            if(checkIfCorrectValues(salary, date)){
                Float newSalary = Float.parseFloat(salary);
                String position = this.positionPole.getValue();
                Worker worker = new Worker();
                worker.setWorkersValues(conn, this.nrWorkerModified);
                int result = worker.updateWorkerByBoss(conn, newSalary, position, date );
                if(result == 1){
                    set();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Poprawna modyfikacja danych");
                    String contentText = "Dane pracownika: " +worker.getName() + " " +worker.getLastName()+  " zostały zmienione";
                    alert.setContentText(contentText);
                    alert.showAndWait(); 
                }
             
            } else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Zły format danych");
                alert.setContentText("Niepoprawny format lub za dużo znaków");
                alert.showAndWait();  
            }
        }
        this.nrWorkerModified = -1;
        clearPanelToModify();
        blockModifyFields(true);}
        catch(Exception e){
             Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Złe cos");
                alert.setContentText("Details" + e.getMessage());
                alert.showAndWait(); 
            
        }
    }
    
    private void setWorkersTable(){
        tableWorkerStanowisko.setCellValueFactory(new PropertyValueFactory<>("positionName"));
        tableWorkerPensjaPodstawowa.setCellValueFactory(new PropertyValueFactory<>("basicSalary"));
        tableWorkerPensjaDodatkowa.setCellValueFactory(new PropertyValueFactory<>("additionalSalary"));
        tableWorkerNrTelefonu.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        tableWorkerNrDokumentu.setCellValueFactory(new PropertyValueFactory<>("nrDocument"));
        tableWorkerNr.setCellValueFactory(new PropertyValueFactory<>("nrWorker"));
        tableWorkerNazwisko.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        tableWorkerImie.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableWorkerDataZatrudnienia.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        tablaWorkerDataBadan.setCellValueFactory(new PropertyValueFactory<>("testExpirationDate"));
        tableWorker.setItems(listWorkers);
    }
    
    private void getWorkersTable(){
        Worker worker = new Worker();
        String name = nameFilterPole.getText();
        String surname = surnameFilterPole.getText();
        String position = positionFilterPole.getText();
        listWorkers = worker.getWorkersList(conn, name, surname, position);
    }
    
    
    private void getNamePositions(){
        Position position = new Position();
        listNamePositions = position.getNamePositionsList(conn);
    }
    
    private void set(){
        getWorkersTable();
        setWorkersTable(); 
        getNamePositions();
    }
    
    private void fillPositionComboBox(){
        getNamePositions();
        positionPole.setItems(listNamePositions);

        
    }
    
    public void setWorkerId(int id){
        workerId = id;
        blockModifyFields(true);
        clearPanelToModify();
        set();
        fillPositionComboBox();        
    }
    
}
