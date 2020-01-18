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
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderMainScreenController implements Initializable {
    private Integer boarderId;
    
    
    @FXML
    private FXMLBoarderPaymentScreenController FXMLBoarderPaymentScreenController;
    
    @FXML
    private FXMLBoarderStayScreenController FXMLBoarderStayScreenController;

    @FXML
    private FXMLBoarderDataScreenController FXMLBoarderDataScreenController;    

    @FXML
    private Tab tabPayment;
    
    @FXML void buttonWylogujSieSetOnAction(ActionEvent event) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/retirementhome/FXMLRetirementHome.fxml"));     
            Parent root = (Parent)fxmlLoader.load();          
            Scene scene = new Scene(root);      
            //scene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
            Stage screenWindow = (Stage)((Node)event.getSource()).getScene().getWindow();
            screenWindow.setScene(scene);
            screenWindow.show(); 
    }
    
  
    
    public void setBoarderId( Integer id){
        this.boarderId = id;
        FXMLBoarderPaymentScreenController.setBoarderId(id);
        FXMLBoarderDataScreenController.setBoarderId(id);
        FXMLBoarderStayScreenController.setBoarderId(id);
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //FXMLBoarderPaymentScreenController.setBoarderId(boarderId);
        // TODO
    }    
    
}
