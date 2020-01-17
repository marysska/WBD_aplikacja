/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retirementhome.database;

/**
 *
 * @author marys
 */
public class RoomType {
    private Integer nrRoomType;
    private Integer bedsNumber;
    private Boolean isBathroom;
    private String roomStandard;

    public Integer getNrRoomType() {
        return nrRoomType;
    }

    public void setNrRoomType(Integer nrRoomType) {
        this.nrRoomType = nrRoomType;
    }

    public Integer getBedsNumber() {
        return bedsNumber;
    }

    public void setBedsNumber(Integer bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public Boolean getIsBathroom() {
        return isBathroom;
    }

    public void setIsBathroom(Boolean isBathroom) {
        this.isBathroom = isBathroom;
    }

    public String getRoomStandard() {
        return roomStandard;
    }

    public void setRoomStandard(String roomStandard) {
        this.roomStandard = roomStandard;
    }
    
    
}
