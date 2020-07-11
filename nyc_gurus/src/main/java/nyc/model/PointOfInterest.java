package nyc.model;

public class PointOfInterest extends Destination {
    protected String name;
    protected String borough;
    protected int sideOfStreet;
    protected String POIType;

    public PointOfInterest(long key, float lat, float lng,
                           String name, String borough,
                           int sideOfStreet, String POIType) {
        super(key, lat, lng, destinationType.poi);
        this.name = name;
        this.borough = borough;
        this.sideOfStreet = sideOfStreet;
        this.POIType = POIType;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public int getSideOfStreet() {
        return sideOfStreet;
    }

    public void setSideOfStreet(int sideOfStreet) {
        this.sideOfStreet = sideOfStreet;
    }

    public String getPOIType() {
        return POIType;
    }

    public void setPOIType(String POIType) {
        this.POIType = POIType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "PointOfInterest{" +
                "name='" + name + '\'' +
                ", Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
