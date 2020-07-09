package model;

/**
 * Represents a food cart restaurant in the database
 * @author Guy Kachur
 */
public class FoodCartRestaurant extends Restaurant {
    protected boolean isLicensed;
    protected int foodCartKey;


    @Override
    public String toString() {
        return "[ ID:" + foodCartKey + " , NAME:" + isLicensed + "]";
    }

    public FoodCartRestaurant(int restaurantKey, String name, String description, String menu, String listedHours,
                              boolean isActive, String street1, String street2, String city, String state, String zip,
                              CuisineType cuisine, int companyKey, boolean isLicensed) {
        super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisine,
                companyKey);
        foodCartKey = restaurantKey;
        this.isLicensed = isLicensed;
    }

    public boolean getIsLicensed() {
        return isLicensed;
    }

    public void setIsLicensed(boolean isLicensed) {
        this.isLicensed = isLicensed;
    }

    public int getFoodCartKey() {
        return foodCartKey;
    }

    public void setFoodCartKey(int foodCartKey) {
        this.foodCartKey = foodCartKey;
    }

}
