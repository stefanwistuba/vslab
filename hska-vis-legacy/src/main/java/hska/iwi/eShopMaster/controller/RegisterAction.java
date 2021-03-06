package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3655805600703279195L;
    private String username;
    private String password1;
    private String password2;
    private String firstname;
    private String lastname;

    private Long role = null;

    @Override
    public String execute() throws Exception {

        // Return string:
        String result = "input";

        UserManager userManager = new UserManagerImpl();

        this.role = 2L; // 1 -> admin, 2-> user

        // save it to database
        boolean success = userManager.registerUser(this.username, this.firstname, this.lastname, this.password1,
                this.role);
        // User has been saved successfully to databse:
        addActionMessage("user registered, please login");
        addActionError("user registered, please login");
        Map<String, Object> session = ActionContext.getContext().getSession();
        session.put("message", "user registered, please login");
        if (success) {
            result = "success";
        } else {
            addActionError(getText("error.username.alreadyInUse"));
        }
        return result;
    }

    @Override
    public void validate() {
        if (getFirstName().length() == 0) {
            addActionError(getText("error.firstname.required"));
        }
        if (getLastName().length() == 0) {
            addActionError(getText("error.lastname.required"));
        }
        if (getUserName().length() == 0) {
            addActionError(getText("error.username.required"));
        }
        if (getPassword1().length() == 0) {
            addActionError(getText("error.password.required"));
        }
        if (getPassword2().length() == 0) {
            addActionError(getText("error.password.required"));
        }

        if (!getPassword1().equals(getPassword2())) {
            addActionError(getText("error.password.notEqual"));
        }
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUserName() {
        return (this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return (this.password1);
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }

    public String getPassword2() {
        return (this.password2);
    }

    public void setPassword2(String password) {
        this.password2 = password;
    }

    public Long getRole() {
        return (this.role);
    }

    public void setRole(Long role) {
        this.role = role;
    }

}
