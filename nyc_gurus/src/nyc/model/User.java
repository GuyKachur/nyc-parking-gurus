package nyc.model;

public class User {
    protected String userName;
    protected String passwordHash;

    public User( String userName, String passwordHash) {
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                '}';
    }
}
