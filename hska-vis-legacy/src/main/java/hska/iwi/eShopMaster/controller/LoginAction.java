package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;
import hska.iwi.eShopMaster.model.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.http.HttpHeaders;

public class LoginAction extends ActionSupport {

	/**
	 *
	 */
	private static final long serialVersionUID = -983183915002226000L;
	private String username = null;
	private String password = null;
	private String firstname;
	private String lastname;
	private String role;

	@Override
	public String execute() throws Exception {
		// Return string:
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();

		UserManager myCManager = new UserManagerImpl();
		String token = "";
		// Sende username und password an client
		try {
			token = myCManager.authorizeUser(getUserName(), getPassword());
		} catch (Exception e) {
			addActionError(getText("error.username.wrong"));
			return result;
		}
		// Does token exist?
		if (token != null) {
			session.put("token", token);
			try {
				User user = myCManager.getUserByUsername(getUserName());
				session.put("webshop_user", user);

			} catch (Exception e) {
				addActionError(getText("error.username.wrong"));
				return result;
			}
			result = "success";
		} else {
			addActionError(getText("error.username.wrong"));
		}

		return result;
	}

	@Override
	public void validate() {
		if (getUserName().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword().length() == 0) {
			addActionError(getText("error.password.required"));
		}
	}

	public String getUserName() {
		return (this.username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return (this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
