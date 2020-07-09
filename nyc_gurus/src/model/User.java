package model;

/**
 * Represents a user in our database
 *
 * @author Guy Kachur
 */
public class User {
    protected String userName;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNum;
    protected String passwordHash;

    @Override
    public String toString() {
        return "[ ID:" + userName + " , NAME:" + firstName + "]";
    }

    //neither username, password, or lastname can be null
    public User(String userName, String firstName, String lastName, String email, String phoneNum,
                String passwordHash) {
        super();
        assert (userName != null);
        assert (passwordHash != null);
        assert (lastName != null);
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
