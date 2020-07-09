package nyc.model;

public class Garden extends Destination {
    protected String name;
    protected int zipCode;
    protected String gardenType;
    protected String address;
    protected String neighborhood_name;
    protected String borough;

    public Garden(long key, float lat, float lng,
                  String name, int zipCode, String GardenType, String address,
                  String neighborhood_name, String borough) {
        super(key, lat, lng, destinationType.garden);
        this.name = name;
        this.zipCode = zipCode;
        this.gardenType = GardenType;
        this.address = address;
        this.neighborhood_name = neighborhood_name;
        this.borough = borough;
    }

    public String getGardenType() {
        return gardenType;
    }

    public void setGardenType(String gardenType) {
        this.gardenType = gardenType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNeighborhood_name() {
        return neighborhood_name;
    }

    public void setNeighborhood_name(String neighborhood_name) {
        this.neighborhood_name = neighborhood_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    @Override
    public String toString() {
        return "Garden{" +
                "name='" + name + '\'' +
                ", Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }


}
