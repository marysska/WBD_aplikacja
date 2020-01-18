/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.screen;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import retirementhome.DBConnection;
import retirementhome.database.Bill;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderPaymentScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Connection conn;
    
    private int boarderId;
    
    private ObservableList<Bill> listBills;
    
    @FXML
    private TableView<Bill> tablePayment;

    @FXML
    private TableColumn<Bill, String> tableColumnName;

    @FXML
    private TableColumn<Bill, Float> tableColumnMoney;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
        //System.out.print("busi");
        // TODO
    } 
    
    public void set(){
        Bill bill = new Bill();
        listBills = bill.getBoardersBill(conn, boarderId);
        
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnMoney.setCellValueFactory(new PropertyValueFactory<>("price"));
        tablePayment.setItems(listBills);
    }
    
    public void setBoarderId(int nr){
        this.boarderId = nr;
        set();
    }
    
}
