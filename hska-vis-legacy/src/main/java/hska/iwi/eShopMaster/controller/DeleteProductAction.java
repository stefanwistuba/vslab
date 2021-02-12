package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.impl.ProductManagerImpl;
import hska.iwi.eShopMaster.model.User;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private Long id;

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if (user != null && (user.getRole().equals(1L))) {

			ProductManagerImpl productManager = new ProductManagerImpl();
			boolean success = false;
			try {
				success = productManager.deleteProductById(id);
			} catch (Exception e) {
				addActionError(getText("error.products.delete"));
			}
			if (success) {
				res = "success";
			}
		}

		return res;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
