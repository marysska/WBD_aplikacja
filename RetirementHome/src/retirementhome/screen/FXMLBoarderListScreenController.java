/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.screen;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import retirementhome.DBConnection;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderListScreenController implements Initializable {
    
    private Connection conn;
    
    private Integer workerId;

    @FXML
    private Text textToDelete;

    public void setWorkerId(Integer id){
        this.workerId = id;
        textToDelete.setText(workerId.toString());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn= DBConnection.getConnection();
    }    
    
}
