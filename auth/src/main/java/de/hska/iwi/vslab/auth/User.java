package de.hska.iwi.vslab.auth;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotNull;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

    final static long serialVersionUID = -718563736459445054L;

    protected String username;

    protected String firstname;

    protected String lastname;

    protected String password;

    protected Integer role;

    /**
     * Creates a new User.
     *
     */
    public User() {
        super();
    }

    /**
     * Creates a new User.
     *
     */
    public User(String username, String firstname, String lastname, String password, Integer role) {
        super();
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
    }

    /**
     * Returns the username.
     *
     * @return username
     */
    @NotNull
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the firstname.
     *
     * @return firstname
     */
    @NotNull
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the firstname.
     *
     * @param firstname the new firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the lastname.
     *
     * @return lastname
     */
    @NotNull
    public String getLastname() {
        return lastname;
    }

    /**
     * Set the lastname.
     *
     * @param lastname the new lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns the password.
     *
     * @return password
     */
    @NotNull
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the role.
     *
     * @return role
     */
    @NotNull
    public Integer getRole() {
        return role;
    }

    /**
     * Set the role.
     *
     * @param role the new role
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    public int hashCode() {
        return new HashCodeBuilder().append(username).append(firstname).append(lastname).append(password).append(role)
                .toHashCode();
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (this.getClass() != other.getClass()) {
            return false;
        }
        User otherObject = ((User) other);
        return new EqualsBuilder().append(username, otherObject.username).append(firstname, otherObject.firstname)
                .append(lastname, otherObject.lastname).append(password, otherObject.password)
                .append(role, otherObject.role).isEquals();
    }

    public String toString() {
        return new ToStringBuilder(this).append("username", username).append("firstname", firstname)
                .append("lastname", lastname).append("password", password).append("role", role).toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthority = new ArrayList<>();
        if (this.getRole() == 1) {
            grantedAuthority.add(new SimpleGrantedAuthority("admin"));
        }
        grantedAuthority.add(new SimpleGrantedAuthority("user"));
        return grantedAuthority;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}