package model;

import java.util.Date;

/**
 * Represents a reservation in the database
 * @author Guy Kachur
 */
public class Reservation {
    protected int ReservationKey;
    protected String userName;
    protected int sitDownRestaurantKey;
    protected Date start;
    protected Date end;
    protected int partySize;

    @Override
    public String toString() {
        return "[ ID:" + ReservationKey + " , NAME:" + userName + "{" + partySize + "}" + " from (" + start + "," + end + ")]";
    }


    public Reservation(int reservationKey, String userName, int sitDownRestaurantKey, Date start, Date end,
                       int partySize) {
        super();

        assert (userName != null);
        assert (start != null);


        ReservationKey = reservationKey;
        this.userName = userName;
        this.sitDownRestaurantKey = sitDownRestaurantKey;
        this.start = start;
        this.end = end;
        this.partySize = partySize;
    }

    public int getReservationKey() {
        return ReservationKey;
    }

    public void setReservationKey(int reservationKey) {
        ReservationKey = reservationKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSitDownRestaurantKey() {
        return sitDownRestaurantKey;
    }

    public void setSitDownRestaurantKey(int sitDownRestaurantKey) {
        this.sitDownRestaurantKey = sitDownRestaurantKey;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getPartySize() {
        return partySize;
    }

    public void setPartySize(int partySize) {
        this.partySize = partySize;
    }

}