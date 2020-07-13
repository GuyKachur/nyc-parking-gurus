package nyc.model;

import java.util.Date;

public class Trip {
    protected int key;
    protected Date start;
    protected Date end;
    protected String userName;
    protected int destinationID;

    public Trip(int key, Date start, Date end, String userName, int destinationID) {
		this.key = key;
		this.start = start;
		this.end = end;
		this.userName = userName;
		this.destinationID = destinationID;
	}

	public Trip(Date start, Date end, String userID, int destinationID) {
        this.start = start;
        this.end = end;
        this.userName = userID;
        this.destinationID = destinationID;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
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

    public String getUserID() {
        return userName;
    }

    public void setUserID(String userID) {
        this.userName = userID;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "key=" + key +
                ", start=" + start +
                ", end=" + end +
                ", userID=" + userName +
                ", destinationID=" + destinationID +
                '}';
    }
}
