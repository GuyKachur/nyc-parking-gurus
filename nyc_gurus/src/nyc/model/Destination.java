package nyc.model;

public class Destination {
    protected long Key;
    protected float lat;
    protected float lng;
    protected destinationType destType;

    public Destination(long key, float lat, float lng, destinationType type) {
        this.Key = key;
        this.lat = lat;
        this.lng = lng;
        this.destType = type;
    }

    public long getKey() {
        return Key;
    }

    public void setKey(long key) {
        Key = key;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public destinationType getType() {
        return destType;
    }

    public void setType(destinationType type) {
        this.destType = type;
    }

    @Override
    public String toString() {
        return "Destination{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                ", type=" + destType +
                '}';
    }

    public enum destinationType {
        park, business, market, airbnb, garden, poi
    }
}
