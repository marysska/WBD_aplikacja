/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.screen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBossMainScreenController implements Initializable {
    private Integer workerId;
    
    @FXML
    private FXMLBoarderListScreenController FXMLBoarderListScreenController;
    
    @FXML
    private FXMLWorkerListScreenController FXMLWorkerListScreenController;

    @FXML
    private FXMLBoarderAccomodationScreenController FXMLBoarderAccomodationScreenController;

    @FXML
    private FXMLWorkerDataScreenController FXMLWorkerDataScreenController;
    
    @FXML
    void functionTabPensjonariusze() {
        try{
        FXMLBoarderAccomodationScreenController.set();
        FXMLWorkerDataScreenController.set();
        FXMLBoarderListScreenController.set();
        FXMLWorkerListScreenController.set();            
        }catch(Exception exp){
            
        }

    }    
    
    @FXML void buttonWylogujSieSetOnAction(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/retirementhome/FXMLRetirementHome.fxml"));     
            Parent root = (Parent)fxmlLoader.load();          
            Scene scene = new Scene(root);      
            //scene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
            Stage screenWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            screenWindow.setScene(scene);
            screenWindow.show(); 
    }
    
  
    
    public void setBossId( Integer id){
        this.workerId = id;
        FXMLBoarderListScreenController.setWorkerId(id);
        FXMLBoarderAccomodationScreenController.setWorkerId(id);
        FXMLWorkerDataScreenController.setWorkerId(id); 
        FXMLWorkerListScreenController.setWorkerId(id);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
