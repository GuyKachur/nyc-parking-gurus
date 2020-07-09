package nyc.model;

import java.util.Date;

public class Graffiti extends Violation {

    protected String IncidentAddress;
    protected String Borough;
    protected String ZipCode;
    protected Date created;

    public Graffiti(long key, float lat, float lng,  String incidentAddress, String borough, String zipCode, Date created) {
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

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
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
