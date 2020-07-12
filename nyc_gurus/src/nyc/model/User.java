package nyc.model;

public class User {
    protected String userName;
    protected String passwordHash;
    protected String firstName;
    protected String lastName;
    

    public User( String userName, String passwordHash, String firstName, String lastName) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String userName) {
		this.userName = userName;
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

	@Override
    public String toString() {
        return "User{" +
                ", userName='" + userName + '\'' +
                '}';
    }
}
