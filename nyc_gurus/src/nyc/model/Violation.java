package nyc.model;

/**
 * Represents a violation
 */
public class Violation {
    protected long Key;
    protected float lat;
    protected float lng;
    protected ViolationType type;

    public Violation(long key, float lat, float lng, ViolationType type) {
        this.Key = key;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
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

    public ViolationType getType() {
        return type;
    }

    public void setType(ViolationType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                ", type=" + type +
                '}';
    }

    public enum ViolationType {
        emergencyResponse, collision, graffiti
    }
}
