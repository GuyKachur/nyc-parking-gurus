package nyc.model;

import java.util.Date;

public class Graffiti extends Violation {

    protected String IncidentAddress;
    protected String Borough;
    protected int ZipCode;
    protected Date created;

    public Graffiti(long key, float lat, float lng,  String incidentAddress, String borough, int zipCode, Date created) {
        super(key, lat, lng, ViolationType.graffiti);
        IncidentAddress = incidentAddress;
        Borough = borough;
        ZipCode = zipCode;
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getIncidentAddress() {
        return IncidentAddress;
    }

    public void setIncidentAddress(String incidentAddress) {
        IncidentAddress = incidentAddress;
    }

    public String getBorough() {
        return Borough;
    }

    public void setBorough(String borough) {
        Borough = borough;
    }

    public int getZipCode() {
        return ZipCode;
    }

    public void setZipCode(int zipCode) {
        ZipCode = zipCode;
    }

    @Override
    public String toString() {
        return "Graffiti{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
