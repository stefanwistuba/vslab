package de.hska.iwi.vslab.userroleservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.hska.iwi.vslab.userroleservice.model.Role;

@Entity
public class User {
    @javax.persistence.Id
    @javax.persistence.GeneratedValue
    @javax.persistence.Column(name = "USER_ID")
    private Long id;

    @javax.persistence.Column(name = "USER_FIRSTNAME")
    private String firstName;

    @javax.persistence.Column(name = "USER_LASTNAME")
    private String lastName;

    @javax.persistence.Column(name = "USER_USERNAME")
    private String userName;

    @javax.persistence.Column(name = "USER_PWD")
    private String password;

    @javax.persistence.Column(name = "USER_ROLE")
    private Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
