/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome;

import retirementhome.database.Account;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import retirementhome.DBConnection;
import retirementhome.screen.FXMLBoarderMainScreenController;
import retirementhome.screen.FXMLBossMainScreenController;
import retirementhome.screen.FXMLWorkerMainScreenController;


/**
 *
 * @author Elitebook 840 G3
 */
public class FXMLRetirementHomeController implements Initializable {
    
    private Connection conn;
    
    @FXML
    private TextField textFieldLoginUserName;
    @FXML
    private PasswordField textFieldLoginPassword;

    @FXML
    private Text TextLoginInvalidPassword;

    private void openWorkerWindow(ActionEvent action, Integer id){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/retirementhome/screen/FXMLWorkerMainScreen.fxml"));     
            Parent root = (Parent)fxmlLoader.load();          
            FXMLWorkerMainScreenController controller = fxmlLoader.<FXMLWorkerMainScreenController>getController();
            controller.setWorkerId(id);
            Scene scene = new Scene(root);      
            //scene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
            Stage screenWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
            screenWindow.setScene(scene);
            screenWindow.show(); 
        }
        catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to worker screen connection");
            alert.setContentText("Details: "+ e.getMessage());
            alert.show();
        }
        
    }

        private void openBoarderWindow(ActionEvent action, Integer id){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/retirementhome/screen/FXMLBoarderMainScreen.fxml"));     
            Parent root = (Parent)fxmlLoader.load();          
            FXMLBoarderMainScreenController controller = fxmlLoader.<FXMLBoarderMainScreenController>getController();
            controller.setBoarderId(id);
            Scene scene = new Scene(root);      
            //scene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
            Stage screenWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
            screenWindow.setScene(scene);
            screenWindow.show(); 
        }
        catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to boarder screen connection");
            alert.setContentText("Details: "+ e.getMessage());
            alert.show();
        }
        
    }
    private void openBossWindow(ActionEvent action, Integer id){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/retirementhome/screen/FXMLBossMainScreen.fxml"));     
            Parent root = (Parent)fxmlLoader.load();          
            FXMLBossMainScreenController controller = fxmlLoader.<FXMLBossMainScreenController>getController();
            controller.setBossId(id);
            Scene scene = new Scene(root);      
            //scene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
            Stage screenWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
            screenWindow.setScene(scene);
            screenWindow.show(); 
        }
        catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to boss screen connection");
            alert.setContentText("Details: "+ e.getMessage());
            alert.show();
        }
        
    }    
    
    /*
    private void openWindow(ActionEvent action, String [] FXMLPath, Integer id, String [] menuName)throws IOException{
        
        try{
        if(FXMLPath.length == 0) return;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FXMLPath[0]));
        
        Parent screenView = loader.load();
        borderPaneControllerHandle = (FXMLBorderPaneScreenController)loader.getController();
        borderPaneControllerHandle.setId(id);
              
        for (int i = 0; i < menuName.length; i++){ 
            Menu menu1 = new Menu(menuName[i]);
            borderPaneControllerHandle.addMenu(menu1);
            
            menu1.setOnAction(e ->{
                Parent centerScreenView;
                try {
                    centerScreenView = new FXMLLoader(getClass().getResource("/retirementhome/screen/FXMLBoarderListScreen.fxml")).load();
                    borderPaneControllerHandle.setCenterPane(centerScreenView);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLRetirementHomeController.class.getName()).log(Level.SEVERE, null, ex);
                }              
            });
            
        }
        Parent centerScreenView = new FXMLLoader(getClass().getResource(FXMLPath[1])).load();
        borderPaneControllerHandle.setCenterPane(centerScreenView);
        
        Scene screenScene = new Scene(screenView);
        screenScene.getStylesheets().add( getClass().getResource("/retirementhome/screen/styles/MenuBarStyle.css").toExternalForm());
        Stage screenWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
        screenWindow.setScene(screenScene);
        
        screenWindow.show(); 
        }
        catch(Exception e){
            Alert alert= new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error to database connection");
            alert.setContentText("Details: "+ e.getMessage());
            alert.show();
        }
    }
    */
    
    
    @FXML
    public void buttonZalogujSieOnAction(ActionEvent action) throws IOException{
       String userName;
       String password;
       Integer personId;
       String accountType;
       userName = textFieldLoginUserName.getText().trim();
       password = textFieldLoginPassword.getText().trim();
       
       if(userName.length() == 0 || password.length() == 0){
            TextLoginInvalidPassword.setVisible(true);
            return;
       }
       conn= DBConnection.getConnection();
       Account account = new Account();
       account.check(conn, userName, password);
       personId = account.getIndex();
       accountType = account.getType();
       switch(accountType) {
            case "PRA": {
                //final String [] path =  new String []{"/retirementhome/screen/FXMLBorderPaneScreen.fxml", "/retirementhome/screen/FXMLBoarderListScreen.fxml" , "/retirementhome/screen/FXMLAccomodationScreen.fxml", "/retirementhome/screen/FXMLWorkerDataScreen.fxml", "/retirementhome/screen/FXMLRetirementHome.fxml"};
                TextLoginInvalidPassword.setVisible(false);
                openWorkerWindow(action, personId);
                break;
               
                //openWindow(action,path, personId, new String []{"Pensjonariusze", "Kwaterowanie", "Dane", "Wyloguj się"} );
            }
            case "PEN":{
                TextLoginInvalidPassword.setVisible(false);
                openBoarderWindow(action, personId);
                break;
            }
            case "SZE":{
                TextLoginInvalidPassword.setVisible(false);
                openBossWindow(action, personId);
                break;
            }
            default: {
                TextLoginInvalidPassword.setVisible(true);
                
            } 

        }
    }
        
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn= DBConnection.getConnection();
    }    
    
}
