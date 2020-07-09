package model;

/**
 * Represents a restaurant in the database
 * @author Guy Kachur
 */
public class Restaurant {

    protected int restaurantKey;
    protected String name;
    protected String description;
    protected String Menu;
    protected String listedHours;
    protected boolean isActive;
    protected String street1;
    protected String street2;
    protected String city;
    protected String state;
    protected String zip;
    protected CuisineType cuisine;
    protected int companyKey;

    public int getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(int companyKey) {
        this.companyKey = companyKey;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * holds teh cuisinetypes supported by the database
     */
    public enum CuisineType {
        african, american, asian, european, hispanic
    }

    @Override
    public String toString() {
        return "[ ID:" + restaurantKey + " , NAME:" + name + ", COMP:" + companyKey + "]";
    }


    public Restaurant(int restaurantKey, String name, String description, String menu, String listedHours,
                      boolean isActive, String street1, String street2, String city, String state, String zip,
                      CuisineType cuisine, int companyKey) {
        super();


        assert (name != null);


        this.restaurantKey = restaurantKey;
        this.name = name;
        this.description = description;
        Menu = menu;
        this.listedHours = listedHours;
        this.isActive = isActive;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.cuisine = cuisine;
        this.companyKey = companyKey;
    }

    public int getRestaurantKey() {
        return restaurantKey;
    }

    public void setRestaurantKey(int restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    public String getListedHours() {
        return listedHours;
    }

    public void setListedHours(String listedHours) {
        this.listedHours = listedHours;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
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

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public CuisineType getCuisine() {
        return cuisine;
    }

    public void setCuisine(CuisineType cuisine) {
        this.cuisine = cuisine;
    }


}