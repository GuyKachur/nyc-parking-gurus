package nyc.model;

public class AirBNB extends Destination {
    protected String borough;
    protected String neighborHood;
    protected int price;
    protected float reviewPerMonth;
    protected String roomType;
    protected int hostID;
    protected String hostName;
    protected String name;
    protected int reviews;

    public AirBNB(long Key, float lat, float lng,
                  String borough, String neighborHood, int price,
                  float reviewPerMonth, String roomType, int hostID,
                  String hostName, String name, int reviews) {
        super(Key, lat, lng, destinationType.airbnb);
        this.borough = borough;
        this.neighborHood = neighborHood;
        this.price = price;
        this.reviewPerMonth = reviewPerMonth;
        this.roomType = roomType;
        this.hostID = hostID;
        this.hostName = hostName;
        this.name = name;
        this.reviews = reviews;
    }

    public float getReviewPerMonth() {
        return reviewPerMonth;
    }

    public void setReviewPerMonth(float reviewPerMonth) {
        this.reviewPerMonth = reviewPerMonth;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getHostID() {
        return hostID;
    }

    public void setHostID(int hostID) {
        this.hostID = hostID;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getNeighborHood() {
        return neighborHood;
    }

    public void setNeighborHood(String neighborHood) {
        this.neighborHood = neighborHood;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AirBNB{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
