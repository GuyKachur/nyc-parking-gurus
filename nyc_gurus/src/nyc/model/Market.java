package nyc.model;

import java.util.Date;

public class Market extends Destination {
    protected Date created;
    protected String accountName;
    protected String tradeName;
    protected String address;
    protected String city;
    protected String state;
    protected int zipCode;
    protected String phone;
    protected String email;
    protected String market;
    protected String marketType;


    public Market(long key, float lat, float lng, Date created, String accountName, String tradeName, String address, String city, String state, int zipCode, String phone, String email, String market, String marketType) {
        super(key, lat, lng, Destination.destinationType.market);
        this.created = created;
        this.accountName = accountName;
        this.tradeName = tradeName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.phone = phone;
        this.email = email;
        this.market = market;
        this.marketType = marketType;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    @Override
    public String toString() {
        return "Market{" +
                "accountName='" + accountName + '\'' +
                ", Key=" + Key +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
