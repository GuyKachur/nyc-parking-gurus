package nyc.model;

public class Business extends Destination {
    protected String industry;
    protected String name;
    protected String addressBuilding;
    protected String addressStreet;
    protected String city;
    protected String state;
    protected int zipCode;
    protected int phoneNumber;
    protected String borough;

    public Business(long key, float lat, float lng, String industry, String name, String addressBuilding, String addressStreet, String city, String state, int zipCode, int phoneNumber, String borough) {
        super(key, lat, lng, Destination.destinationType.business);
        this.industry = industry;
        this.name = name;
        this.addressBuilding = addressBuilding;
        this.addressStreet = addressStreet;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.borough = borough;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressBuilding() {
        return addressBuilding;
    }

    public void setAddressBuilding(String addressBuilding) {
        this.addressBuilding = addressBuilding;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
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
        return "Business{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
