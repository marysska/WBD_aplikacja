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
import retirementhome.database.Boarder;

/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderDataScreenController implements Initializable {
    
    private Connection conn;
    
    private Integer boarderId;
        @FXML
    private Text boarderFieldImie;

    @FXML
    private Text boarderFieldNazwisko;

    @FXML
    private Text boarderFieldDokument;

    @FXML
    private Text boarderFieldPesel;

    @FXML
    private Text boarderFieldAdres1;

    @FXML
    private Text boarderFieldAdres2;

    @FXML
    private Text boarderFieldData;
    
     
    public void set(){
        Boarder boarder = new Boarder();
        boarder.setBoarderValues(conn, this.boarderId);
        boarderFieldImie.setText(boarder.getName());
        boarderFieldNazwisko.setText(boarder.getLastName());
        boarderFieldDokument.setText(boarder.getNrDocument());
        String pesel = boarder.getPesel();
        String lokal = boarder.getNrFlat();
        if(pesel == null){
            pesel = "-";
        }
        if(lokal == null){
            lokal = "";
        }
        System.out.println(boarder.getNrFlat());
        boarderFieldPesel.setText(pesel);
        boarderFieldAdres1.setText(boarder.getStreet() + " "+ boarder.getNrHause() + " " + lokal);
        boarderFieldAdres2.setText(boarder.getPostCode() + ", " + boarder.getCity());
        boarderFieldData.setText(boarder.getBirthDate().toString());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBConnection.getConnection();
    }
    public void setBoarderId(int nr){
        this.boarderId = nr;
        set();
    }    
    
}
