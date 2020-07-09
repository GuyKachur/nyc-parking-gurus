package model;

/**
 * Company represents the company row in our database
 * @author Guy Kachur
 */
public class Company {
    protected int companyKey;
    protected String name;
    protected String description;

    public Company(int companyKey, String name, String description) {
        assert (name != null && name.length() <= 45);
        assert (description != null);
        this.companyKey = companyKey;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "[ ID:" + companyKey + " , NAME:" + name + "]";
    }

    public int getCompanyKey() {
        return companyKey;
    }

    public void setCompanyKey(int companyKey) {
        this.companyKey = companyKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
