package nyc.model;

import java.util.Date;

public class EmergencyResponse extends Violation {
    protected String incidentType;
    protected String location;
    protected String borough;
    protected Date creationDate;

    public EmergencyResponse(long key, float lat, float lng, String incidentType, String location, String borough, Date creationDate) {
        super(key, lat, lng, ViolationType.emergencyResponse);
        this.incidentType = incidentType;
        this.location = location;
        this.borough = borough;
        this.creationDate = creationDate;
    }

    public String getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(String incidentType) {
        this.incidentType = incidentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "EmergencyResponse{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
