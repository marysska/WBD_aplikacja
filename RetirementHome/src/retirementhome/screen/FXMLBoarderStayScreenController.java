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
import javafx.scene.text.Text;
import retirementhome.DBConnection;

import retirementhome.database.Boarder;
import retirementhome.database.Offer;
import retirementhome.database.Room;
import retirementhome.database.RoomType;
import retirementhome.database.Stay;
/**
 * FXML Controller class
 *
 * @author Elitebook 840 G3
 */
public class FXMLBoarderStayScreenController implements Initializable {
    private Integer boarderId;
    
    private Connection conn;
    
    @FXML
    private Text poleDateFrom;

    @FXML
    private Text poleDateTo;

    @FXML
    private Text poleNameOffer;

    @FXML
    private Text polePrice;

    @FXML
    private Text poleFloor;

    @FXML
    private Text poleStandard;

    @FXML
    private Text poleNrRoom;

    @FXML
    private Text poleBeds;

    @FXML
    private Text poleBath;

    @FXML
    private Text poleName;

    @FXML
    private Text poleSurname;
    /**
     * Initializes the controller class.
     */
    public void set(){
        Offer offer = new Offer();
        Room room = new Room();
        RoomType roomType = new RoomType();
        Boarder roomMate = new Boarder();
        Stay stay = new Stay();
        
        stay = stay.getCurrentStay(conn, boarderId);
        
        if(stay.getNrStay()<0){
            setZero();
            return;
        }
        offer = offer.getBoardersOffer(conn, boarderId);
        room = room.getBoarderRoom(conn, boarderId);
        roomType = roomType.getBoardersRoomType(conn, room.getNrRoomType());
        ObservableList<Boarder> listMates = roomMate.getRoommates(conn, boarderId, room.getNrRoom());
        
        
        poleDateFrom.setText(stay.getDateCheckIn().toString());
        if(stay.getDateCheckOut() != null){
            poleDateTo.setText(stay.getDateCheckOut().toString());
        }
        else{
            poleDateTo.setText("-");
        }
        
        poleNameOffer.setText(offer.getName());
        polePrice.setText(offer.getPrice().toString());
        poleFloor.setText(room.getFloor().toString());
        poleStandard.setText(roomType.getRoomStandard());
        poleNrRoom.setText(room.getRoomNumber());
        poleBeds.setText(roomType.getBedsNumber().toString());
        poleBath.setText(roomType.getIsBathroom().toString());
        
        if(listMates.size() == 1){
            poleName.setText(listMates.get(0).getName());
            poleSurname.setText(listMates.get(0).getLastName());            
        }
        else{
            poleName.setText("-");
            poleSurname.setText("-");
        }


    }
    
    public void setZero(){
        poleDateFrom.setText("-");
        poleDateTo.setText("-");
        poleNameOffer.setText("-");
        polePrice.setText("-");
        poleFloor.setText("-");
        poleStandard.setText("-");
        poleNrRoom.setText("-");
        poleBeds.setText("-");
        poleBath.setText("-");
        poleName.setText("-");
        poleSurname.setText("-");          
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        conn = DBConnection.getConnection();
    }    
     public void setBoarderId(int nr){
        this.boarderId = nr;
        set();
    }    
}
