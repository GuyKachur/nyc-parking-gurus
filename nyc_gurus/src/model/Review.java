package model;

import java.util.Date;

/**
 * Represents a Review in the database
 *
 * @author Guy Kachur
 */
public class Review {
    protected int reviewKey;
    protected String userName;
    protected int restaurantKey;
    protected Date createdWhen;
    protected String writtenContent;
    protected float rating;

    @Override
    public String toString() {
        return "[ ID:" + reviewKey + " , NAME:" + userName + "-" + rating + "]";
    }

    public Review(int reviewKey, String userName, int restaurantKey, Date createdWhen, String writtenContent,
                  float rating) {
        super();


        assert (createdWhen != null);


        this.reviewKey = reviewKey;
        this.userName = userName;
        this.restaurantKey = restaurantKey;
        this.createdWhen = createdWhen;
        this.writtenContent = writtenContent;
        this.rating = rating;
    }

    public int getReviewKey() {
        return reviewKey;
    }

    public void setReviewKey(int reviewKey) {
        this.reviewKey = reviewKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRestaurantKey() {
        return restaurantKey;
    }

    public void setRestaurantKey(int restaurantKey) {
        this.restaurantKey = restaurantKey;
    }

    public Date getCreatedWhen() {
        return createdWhen;
    }

    public void setCreatedWhen(Date createdWhen) {
        this.createdWhen = createdWhen;
    }

    public String getWrittenContent() {
        return writtenContent;
    }

    public void setWrittenContent(String writtenContent) {
        this.writtenContent = writtenContent;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }


}