package model;

/**
 * Represents a Sit Down Restaurant in our database
 *
 * @author Guy Kachur
 */
public class SitDownRestaurant extends Restaurant {
    protected int sitDownRestaurantKey;
    protected int capacity;

    @Override
    public String toString() {
        return "[ ID:" + sitDownRestaurantKey + " , NAME:" + capacity + "]";
    }

    public SitDownRestaurant(int restaurantKey, String name, String description, String menu, String listedHours,
                             boolean isActive, String street1, String street2, String city, String state, String zip,
                             CuisineType cuisine, int companyKey, int capacity) {
        super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine,
                companyKey);
        sitDownRestaurantKey = restaurantKey;
        this.capacity = capacity;
    }

    public int getSitDownRestaurantKey() {
        return sitDownRestaurantKey;
    }

    public void setSitDownRestaurantKey(int sitDownRestaurantKey) {
        this.sitDownRestaurantKey = sitDownRestaurantKey;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}