package model;

/**
 * Represents a recomemendation in the database
 */
public class Recommendation {
    protected int recommendationKey;
    protected String userKey;
    protected int restaurantKey;

    @Override
    public String toString() {
        return "[ ID:" + recommendationKey + " , NAME:" + userKey + " at " + restaurantKey + "]";
    }

    public Recommendation(int recommendationKey, String userKey, int restaurantKey) {
        super();
        this.recommendationKey = recommendationKey;
        this.userKey = userKey;
        this.restaurantKey = restaurantKey;
    }

    public int getRecommendationKey() {
        return recommendationKey;
    }

    public void setRecommendationKey(int recommendationKey) {
        this.recommendationKey = recommendationKey;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getRestaurantKey() {
        return restaurantKey;
    }

    public void setRestaurantKey(int restaurantKey) {
        this.restaurantKey = restaurantKey;
    }


}