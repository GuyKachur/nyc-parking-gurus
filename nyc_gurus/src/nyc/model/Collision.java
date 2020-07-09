package nyc.model;

import java.util.Date;

public class Collision extends Violation {

    protected Date date;
    protected String Borough;
    protected String ZipCode;
    protected String StreetName;
    protected int PeopleInjured;
    protected int PeopleKilled;
    protected int PedestriansInjured;
    protected int PedestriansKilled;
    protected int CyclistsInjured;
    protected int CyclistsKilled;
    protected int MotoristsInjured;
    protected int MotoristsKilled;

    public Collision(long key, float lat, float lng,
                     Date date, String borough, String zipCode, String streetName,
                     int peopleInjured, int peopleKilled, int pedestriansInjured,
                     int pedestriansKilled, int cyclistsInjured, int cyclistsKilled,
                     int motoristsInjured, int motoristsKilled) {
        super(key, lat, lng, ViolationType.collision);
        this.date = date;
        Borough = borough;
        ZipCode = zipCode;
        StreetName = streetName;
        PeopleInjured = peopleInjured;
        PeopleKilled = peopleKilled;
        PedestriansInjured = pedestriansInjured;
        PedestriansKilled = pedestriansKilled;
        CyclistsInjured = cyclistsInjured;
        CyclistsKilled = cyclistsKilled;
        MotoristsInjured = motoristsInjured;
        MotoristsKilled = motoristsKilled;
    }

    public String getBorough() {
        return Borough;
    }

    public void setBorough(String borough) {
        Borough = borough;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getStreetName() {
        return StreetName;
    }

    public void setStreetName(String streetName) {
        StreetName = streetName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPeopleInjured() {
        return PeopleInjured;
    }

    public void setPeopleInjured(int peopleInjured) {
        PeopleInjured = peopleInjured;
    }

    public int getPeopleKilled() {
        return PeopleKilled;
    }

    public void setPeopleKilled(int peopleKilled) {
        PeopleKilled = peopleKilled;
    }

    public int getPedestriansInjured() {
        return PedestriansInjured;
    }

    public void setPedestriansInjured(int pedestriansInjured) {
        PedestriansInjured = pedestriansInjured;
    }

    public int getPedestriansKilled() {
        return PedestriansKilled;
    }

    public void setPedestriansKilled(int pedestriansKilled) {
        PedestriansKilled = pedestriansKilled;
    }

    public int getCyclistsInjured() {
        return CyclistsInjured;
    }

    public void setCyclistsInjured(int cyclistsInjured) {
        CyclistsInjured = cyclistsInjured;
    }

    public int getCyclistsKilled() {
        return CyclistsKilled;
    }

    public void setCyclistsKilled(int cyclistsKilled) {
        CyclistsKilled = cyclistsKilled;
    }

    public int getMotoristsInjured() {
        return MotoristsInjured;
    }

    public void setMotoristsInjured(int motoristsInjured) {
        MotoristsInjured = motoristsInjured;
    }

    public int getMotoristsKilled() {
        return MotoristsKilled;
    }

    public void setMotoristsKilled(int motoristsKilled) {
        MotoristsKilled = motoristsKilled;
    }

    @Override
    public String toString() {
        return "Collision{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
