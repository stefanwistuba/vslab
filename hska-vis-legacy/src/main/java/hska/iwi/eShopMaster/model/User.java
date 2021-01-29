package hska.iwi.eShopMaster.model;

public class User {

    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private Long role; // 1 = admin, 2 = user

    public User(String firstName, String lastName, String userName, String password, Long role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public User() {
    }

    public boolean isEmptyObject() {
        return (this.firstName == null && this.lastName == null && this.userName == null && this.password == null
                && this.role == null && this.id == null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
