package nyc.model;

public class Park extends Destination {
    protected String name;
    protected String landUse;


    public Park(long key, float lat, float lng, String name, String landUse) {
        super(key, lat, lng, destinationType.park);
        this.name = name;
        this.landUse = landUse;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLandUse() {
        return landUse;
    }

    public void setLandUse(String landUse) {
        this.landUse = landUse;
    }

    @Override
    public String toString() {
        return "Park{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
