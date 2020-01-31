/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.screen;

import java.net.URL;
import java.sql.Connection;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import retirementhome.DBConnection;
import retirementhome.database.Boarder;
import retirementhome.database.Offer;
import retirementhome.database.Room;
import retirementhome.database.Stay;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderAccomodationScreenController implements Initializable {

    private Integer workerId;
    
    private Connection conn;
    
    private boolean mode; // wykwateruj - zakwateruj
    
    private ObservableList<Boarder> listBoarders;
    
    private ObservableList<Room> listRooms;
    
    private int nrBoarder;
    private int nrOffer;
    private int nrRoom;
    private int nrRoomType;
    
    @FXML
    private Text textStep1;

    @FXML
    private Button dalej1;

    @FXML
    private CheckBox checkZakwateruj;

    @FXML
    private CheckBox checkWykwateruj;

    @FXML
    private Text textStep2;

    @FXML
    private Button dalej2;

    @FXML
    private Button cofnij2;

    @FXML
    private TableView<Boarder> tabelPensjonariusz;

    @FXML
    private TableColumn<Boarder, Integer> colPenNr;

    @FXML
    private TableColumn<Boarder, String> colPenNam;

    @FXML
    private TableColumn<Boarder, String> colPenSur;

    @FXML
    private TableColumn<Boarder, String> colPenDoc;

    @FXML
    private TableColumn<Boarder, String> colPenDate;

    @FXML
    private TableColumn<Boarder, String> colPenPESEL;

    @FXML
    private Text textStep5;

    @FXML
    private Button cofnij5;

    @FXML
    private Button save;

    @FXML
    private Button rollback;

    @FXML
    private Text textStep3;

    @FXML
    private Button dalej3;

    @FXML
    private Button cofnij3;

    @FXML
    private ChoiceBox<Offer> selectOferta;

    @FXML
    private Text textStep4;

    @FXML
    private Button dalej4;

    @FXML
    private Button cofnij4;

    @FXML
    private TableView<Room> tabelPokoj;

    @FXML
    private TableColumn<Room, Integer> colPokojNr;

    @FXML
    private TableColumn<Room, String> colPokojPomiesz;

    @FXML
    private TableColumn<Room, Integer> colPokojPietro;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
    } 
    
    public void setWorkerId(int id){
        this.workerId = id;
        set();
    }
    
    private void disableStep1(boolean b){
        dalej1.setDisable(b);
        textStep1.setUnderline(!b);
        checkZakwateruj.setDisable(b);
        checkWykwateruj.setDisable(b);
    }
    
    private void clearStep1(){
        checkWykwateruj.setSelected(false);
        checkZakwateruj.setSelected(false);
    }
    
    private void clearStep2(){
        for (int i = 0 ; i <tabelPensjonariusz.getItems().size(); ++i){
            tabelPensjonariusz.getItems().clear();
        }
    }
    
    private void clearStep3(){
        for (int i = 0 ; i <selectOferta.getItems().size(); ++i){
            selectOferta.getItems().clear();
        }
    }
    
    private void clearStep4(){
        for (int i = 0 ; i <tabelPokoj.getItems().size(); ++i){
            tabelPokoj.getItems().clear();
        }
    }
    
    private void disableStep2(boolean b){
        dalej2.setDisable(b);
        cofnij2.setDisable(b);
        textStep2.setUnderline(!b);
        tabelPensjonariusz.setDisable(b);       
    }
    
    private void disableStep3(boolean b){
        dalej3.setDisable(b);
        cofnij3.setDisable(b);
        textStep3.setUnderline(!b);  
        selectOferta.setDisable(b);
    }
    
    private void disableStep4(boolean b){
        dalej4.setDisable(b);
        cofnij4.setDisable(b);
        textStep4.setUnderline(!b);  
        tabelPokoj.setDisable(b);
    }
    private void disableStep5(boolean b){
        cofnij5.setDisable(b);
        textStep5.setUnderline(!b);   
        save.setDisable(b);
    }
    
    public void set(){
        clearStep1();
        clearStep2();
        clearStep3();
        clearStep4();
        disableStep5(true);
        disableStep4(true);
        disableStep3(true);
        disableStep2(true);
        disableStep1(false);
    }
    
    @FXML 
    public void onWykwateruj(ActionEvent action){
        checkZakwateruj.setSelected(false);
    }
    
    @FXML 
    public void onZakwateruj(ActionEvent action){
        checkWykwateruj.setSelected(false);
    }
    
    @FXML 
    public void onDalej1(ActionEvent action){
        if(checkWykwateruj.isSelected() == false && checkZakwateruj.isSelected() == false){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę wybrać akcję");
            alert.show();              
        }
        else{
            if (checkZakwateruj.isSelected()){
                mode = true;
                fillStep2Zakwateruj();
            }
            else{
                mode = false;
                fillStep2Wykwateruj();
            }
            disableStep1(true);
            disableStep2(false);
        }
    }
    
    @FXML
    public void onCofnij2(ActionEvent action){
        clearStep2();
        disableStep2(true);
        disableStep1(false);
    }
    
    @FXML void onDalej2(ActionEvent action){
        int rowid = tabelPensjonariusz.getSelectionModel().getSelectedIndex();
        if(rowid < 0){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę zaznaczyć w tabeli pensjonariusza");
            alert.show();
            return;
        }     
        disableStep2(true);
        
        nrBoarder = tabelPensjonariusz.getSelectionModel().getSelectedItem().getNrBoarder();
        
        if(mode){ //zakwateruj
            disableStep3(false);
            fillStep3();
            
        }else{ // wykwateruj        
            disableStep5(false);            
        }
    }
    
    @FXML
    public void onCofnij3(ActionEvent action){
        disableStep3(true);
        disableStep2(false);        
        clearStep2();
        fillStep2Zakwateruj();
        clearStep3();
    }
    @FXML 
    public void onDalej3(ActionEvent action){
        Offer oferta = selectOferta.getValue();
        if(oferta == null){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę wybrać ofertę");
            alert.show();
            return;            
        }
        nrOffer = oferta.getNrOffer();
        disableStep3(true);
        disableStep4(false);
        nrRoomType = oferta.getNrRoomType();
        fillStep4(nrRoomType);
    }
    
    @FXML 
    public void onCofnij4(ActionEvent action){
        clearStep4();
        disableStep4(true);
        disableStep3(false);
        clearStep3();
        fillStep3();
    }
    @FXML
    public void onDalej4(ActionEvent action){
        int rowid = tabelPokoj.getSelectionModel().getSelectedIndex();
        if(rowid < 0){
            Alert alert= new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Błąd");
            alert.setContentText("Proszę wybrać pokoj");
            alert.show();
            return;
        }    
        nrRoom = tabelPokoj.getSelectionModel().getSelectedItem().getNrRoom();
        disableStep4(true);
        disableStep5(false);
    }
    @FXML
    public void onCofnij5(ActionEvent action){
        disableStep5(true);
        if(mode){
            clearStep4();
            fillStep4(nrRoomType);
            disableStep4(false);
            return;
        }
        clearStep2();
        fillStep2Wykwateruj();
        disableStep2(false);       
    }
    
    @FXML
    public void onAnuluj(ActionEvent action){
        set();
    }
    
    @FXML
    public void onSave(ActionEvent action){
        if(mode){
            saveZakwateruj();
        }
        else{
            saveWykwateruj();
        }
        
    }
    
    private void saveZakwateruj(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź");
        alert.setContentText("Czy chcesz zakwaterować pensjonariusza " +tabelPensjonariusz.getSelectionModel().getSelectedItem().getName()+" "+tabelPensjonariusz.getSelectionModel().getSelectedItem().getLastName() +"?");
        Optional<ButtonType> type = alert.showAndWait();
        if(type.get()==ButtonType.OK){
            Stay stay = new Stay();
            int result = stay.insertStay(conn, nrBoarder, nrOffer, nrRoom);
            if(result == 1){
                Alert alert1= new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Sukces");
                alert1.setContentText("Zakwaterowano " +tabelPensjonariusz.getSelectionModel().getSelectedItem().getName()+" "+tabelPensjonariusz.getSelectionModel().getSelectedItem().getLastName() +"!");
                alert1.show(); 
            }
            else{
                Alert alert1= new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Błąd");
                alert1.setContentText("Zmienione zostało "+ Integer.toString(result) +" rekordów");
                alert1.show();                 
            }
            set();
        }
        else{
            return;
        }        
    }
    
    private void saveWykwateruj(){
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Potwierdź");
        alert.setContentText("Czy chcesz wykwaterować pensjonariusza " +tabelPensjonariusz.getSelectionModel().getSelectedItem().getName()+" "+tabelPensjonariusz.getSelectionModel().getSelectedItem().getLastName() +"?");
        Optional<ButtonType> type = alert.showAndWait();
        if(type.get()==ButtonType.OK){
            Stay stay = new Stay();
            stay = stay.getCurrentStay(conn,nrBoarder);
            int result = stay.changeStay(conn, stay.getNrStay());
            if(result == 1){
                Alert alert1= new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Sukces");
                alert1.setContentText("Wykwaterowano " +tabelPensjonariusz.getSelectionModel().getSelectedItem().getName()+" "+tabelPensjonariusz.getSelectionModel().getSelectedItem().getLastName() +"!");
                alert1.show(); 
            }
            else{
                Alert alert1= new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Błąd");
                alert1.setContentText("Zmienione zostało "+ Integer.toString(result) +" rekordów");
                alert1.show();                 
            }
            set();
        }
        else{
            return;
        }        
    }
    
    private void fillStep3(){
        Offer offer = new Offer();
        ObservableList<Offer> offerts = offer.getCurrentOffers(conn);
        selectOferta.setItems(offerts);        
    }
    
    private void fillStep4(int id){
        Room room = new Room();
        listRooms = room.getCurrentsFreeRooms(conn, id);
        colPokojNr.setCellValueFactory(new PropertyValueFactory<>("nrRoom"));
        colPokojPomiesz.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        colPokojPietro.setCellValueFactory(new PropertyValueFactory<>("floor"));
        tabelPokoj.setItems(listRooms);
            
    }
    
    private void fillStep2Zakwateruj(){
        Boarder boarder = new Boarder();
        listBoarders = boarder.getCurrentsFreeBoarders(conn);
        fillPensjonariusze();
        
    }
    
    private void fillPensjonariusze(){
        colPenNr.setCellValueFactory(new PropertyValueFactory<>("nrBoarder"));

        colPenNam.setCellValueFactory(new PropertyValueFactory<>("name"));

        colPenSur.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        colPenDoc.setCellValueFactory(new PropertyValueFactory<>("nrDocument"));

        colPenDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        colPenPESEL.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        tabelPensjonariusz.setItems(listBoarders);         
    }
    
    private void fillStep2Wykwateruj(){
        Boarder boarder = new Boarder();
        listBoarders = boarder.getCurrentsBoarders(conn);
        fillPensjonariusze();        
    }
}
