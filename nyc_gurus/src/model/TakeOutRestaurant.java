package model;

/**
 * Represents a take out restaurant
 *
 * @author Guy Kachur
 */
public class TakeOutRestaurant extends Restaurant {

    protected int takeOutRestaurantKey;
    protected int maxWaitMinutes;

    @Override
    public String toString() {
        return "[ ID:" + takeOutRestaurantKey + " , NAME:" + maxWaitMinutes + "]";
    }

    public TakeOutRestaurant(int restaurantKey, String name, String description, String menu, String listedHours,
                             boolean isActive, String street1, String street2, String city, String state, String zip,
                             CuisineType cuisine, int companyKey, int maxWaitMinutes) {
        super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine,
                companyKey);
        takeOutRestaurantKey = restaurantKey;
        this.maxWaitMinutes = maxWaitMinutes;
    }

    public int getTakeOutRestaurantKey() {
        return takeOutRestaurantKey;
    }

    public void setTakeOutRestaurantKey(int takeOutRestaurantKey) {
        this.takeOutRestaurantKey = takeOutRestaurantKey;
    }

    public int getMaxWaitMinutes() {
        return maxWaitMinutes;
    }

    public void setMaxWaitMinutes(int maxWaitMinutes) {
        this.maxWaitMinutes = maxWaitMinutes;
    }


}