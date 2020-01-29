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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import retirementhome.DBConnection;
import retirementhome.database.Worker;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLWorkerDataScreenController implements Initializable {
    
     private Connection conn;
     
    @FXML
    private Button buttonZmienDaneWorker;

    @FXML
    private Button buttonZatwierdzDaneWorker;

    @FXML
    private Button buttonAnulujDaneWorker;

    @FXML
    private Text textImieWorker;

    @FXML
    private TextField textFieldUlicaWorker;

    @FXML
    private Text textNazwiskoWorker;

    @FXML
    private Text textNrDokumentuWorker;

    @FXML
    private Text textPensjaWorker;

    @FXML
    private Text textNrKontaWorker;

    @FXML
    private TextField textFieldNrKontaWorker;
    
    @FXML
    private Text textNrTelefonuWorker;

    @FXML
    private TextField textFieldNrTelefonuWorker;
        
    @FXML
    private Text textUlicaWorker;

    @FXML
    private Text textNrMieszkaniaWorker;

    @FXML
    private Text textKodPocztowyWorker;

    @FXML
    private Text textMiastoWorker;


    @FXML
    private Text textDataBadanWorker;

    @FXML
    private Text textNrDomuWorker;

    @FXML
    private TextField textFieldNrDomuWorker;

    @FXML
    private TextField textFieldNrMieszkaniaWorker;

    @FXML
    private TextField textFieldKodPocztowyWorker;

    @FXML
    private TextField textFieldMiastoWorker;

    @FXML
    private TextField textFieldNrDokumentuWorker;
    
    
    private Integer workerId;
    
    @FXML
    public void buttonZmienDaneOnAction(ActionEvent action){
        boolean change = false;
        
        buttonAnulujDaneWorker.setVisible(!change);
        buttonZmienDaneWorker.setVisible(change);
        buttonZatwierdzDaneWorker.setVisible(!change);

        textFieldKodPocztowyWorker.setVisible(!change);
        textFieldKodPocztowyWorker.setText(textKodPocztowyWorker.getText());
        textKodPocztowyWorker.setVisible(change);
        textFieldMiastoWorker.setVisible(!change);
        textFieldMiastoWorker.setText(textMiastoWorker.getText());
        textMiastoWorker.setVisible(change);
        textFieldNrDokumentuWorker.setVisible(!change);
        textFieldNrDokumentuWorker.setText(textNrDokumentuWorker.getText());
        textNrDokumentuWorker.setVisible(change);
        textFieldNrDomuWorker.setVisible(!change);
        textFieldNrDomuWorker.setText(textNrDomuWorker.getText());
        textNrDomuWorker.setVisible(change);
        textNrKontaWorker.setVisible(change);
        textFieldNrKontaWorker.setVisible(!change);
        textFieldNrKontaWorker.setText(textNrKontaWorker.getText());
        textNrTelefonuWorker.setVisible(change);
        textFieldNrTelefonuWorker.setVisible(!change);
        String nrPhone = textNrTelefonuWorker.getText();
        if("-".equals(nrPhone)){
            nrPhone = "";
        }
        textFieldNrTelefonuWorker.setText(nrPhone);
        textFieldNrMieszkaniaWorker.setVisible(!change);
        String nrFlat = textNrMieszkaniaWorker.getText();
        if("-".equals(nrFlat)){
            nrFlat = "";
        }
        textFieldNrMieszkaniaWorker.setText(nrFlat);
        textNrMieszkaniaWorker.setVisible(change);
        textFieldUlicaWorker.setVisible(!change);
        textFieldUlicaWorker.setText(textUlicaWorker.getText());
        textUlicaWorker.setVisible(change);

    }
        
    @FXML
    public void buttonAnulujZmianyOnAction(ActionEvent action){
        boolean change = true;
        
        buttonAnulujDaneWorker.setVisible(!change);
        buttonZmienDaneWorker.setVisible(change);
        buttonZatwierdzDaneWorker.setVisible(!change);
        textFieldKodPocztowyWorker.setVisible(!change);
        textKodPocztowyWorker.setVisible(change);
        textFieldMiastoWorker.setVisible(!change);
        textMiastoWorker.setVisible(change);
        textFieldNrDokumentuWorker.setVisible(!change);
        textNrDokumentuWorker.setVisible(change);
        textFieldNrDomuWorker.setVisible(!change);
        textNrDomuWorker.setVisible(change);
        textFieldNrMieszkaniaWorker.setVisible(!change);
        textNrMieszkaniaWorker.setVisible(change);
        textNrTelefonuWorker.setVisible(change);
        textFieldNrTelefonuWorker.setVisible(!change);
        textFieldUlicaWorker.setVisible(!change);
        textUlicaWorker.setVisible(change);
        textFieldNrKontaWorker.setVisible(!change);
        textNrKontaWorker.setVisible(change);
    }
    
    public void set(){
        Worker worker = new Worker();
        worker.setWorkersValues(conn, this.workerId);
        textImieWorker.setText(worker.getName());
        textNazwiskoWorker.setText(worker.getLastName());
        textNrDokumentuWorker.setText(worker.getNrDocument());
        textUlicaWorker.setText(worker.getStreet());
        String nrFlat = worker.getNrFlat();
        if(nrFlat == null){
            nrFlat = "-";
        }
        textNrMieszkaniaWorker.setText(nrFlat);
        textNrDomuWorker.setText(worker.getNrHouse());
        textKodPocztowyWorker.setText(worker.getPostCode());
        textMiastoWorker.setText(worker.getCity());
        textNrKontaWorker.setText(worker.getNrAccount());
        String nrPhone = worker.getPhoneNumber();
        if(nrPhone == null){
            nrPhone = "-";
        }
        textNrTelefonuWorker.setText(nrPhone);
        Float salary = worker.getSalary(conn);
        textPensjaWorker.setText(salary.toString()+ " zł");
        
        textDataBadanWorker.setText(worker.getTestExpirationDate().toString());
    }
    
    private boolean checkLength(String text, int maxLength, int minLength){
         return (text.length() <= maxLength  && text.length() >= minLength) ;
    }
    
    private boolean checkIfDigits(String text){
         for (char c : text.toCharArray())
    {
        if (!Character.isDigit(c)) return false;
    }
    return true;
    }
    
    private boolean checkPostCodeFormat(String text){
        boolean result = true;
        if(text.length() != 6 || text.charAt(2) != '-' || !Character.isDigit(text.charAt(0)) || !Character.isDigit(text.charAt(1)) || !Character.isDigit(text.charAt(3))|| !Character.isDigit(text.charAt(4))|| !Character.isDigit(text.charAt(5))){
            result = false;
        }
        return result;
    }
        
  
    private boolean checkIfCorrectValues(){
 
        boolean result = true;
        if(!checkLength(textFieldNrDokumentuWorker.getText(), 20, 1) || !checkLength(textFieldMiastoWorker.getText(), 20, 1) || !checkLength(textFieldUlicaWorker.getText(), 20, 1) || !checkLength(textFieldNrMieszkaniaWorker.getText(), 6, 0) || !checkLength(textFieldNrDomuWorker.getText(), 6 ,1 )|| !checkLength(textFieldNrTelefonuWorker.getText(), 15 ,0)|| !checkLength(textFieldNrKontaWorker.getText(), 26 , 1)){
            result = false;
        }
        if(!checkIfDigits(textFieldNrKontaWorker.getText() ) || !checkPostCodeFormat(textFieldKodPocztowyWorker.getText())){
            result = false;
        }
        
        return result;
    }

    @FXML
    public void buttonZatwierdzZmianyOnAction(ActionEvent action){
       
        Alert alertMain= new Alert(Alert.AlertType.CONFIRMATION);
            alertMain.setTitle("Potwierdź");
            alertMain.setContentText("Czy chcesz wprowadzić zmiany?");
            Optional<ButtonType> type = alertMain.showAndWait();
            if(type.get()==ButtonType.OK){
                        if(checkIfCorrectValues()){
            Worker worker = new Worker();
            int result = worker.updateWorker(conn, this.workerId, textFieldNrDokumentuWorker.getText(), textFieldMiastoWorker.getText(), textFieldUlicaWorker.getText(), textFieldNrDomuWorker.getText(), textFieldNrMieszkaniaWorker.getText(), textFieldKodPocztowyWorker.getText(), textFieldNrKontaWorker.getText(), textFieldNrTelefonuWorker.getText());
            buttonAnulujZmianyOnAction(action);
            if(result == 1){
                set();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Poprawna modyfikacja danych");
                alert.setContentText("Twoje dane zostały zmienione");
                alert.showAndWait(); 
            }
             
        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Zły format danych");
            alert.setContentText("Niepoprawny format lub za dużo znaków");
            alert.showAndWait();  
        }
            }
            else{
                return;
            }
         
    }
    
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
}
