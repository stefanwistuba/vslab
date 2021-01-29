package hska.iwi.eShopMaster.model.businessLogic.manager;

import hska.iwi.eShopMaster.model.User;
import org.springframework.http.HttpEntity;


public interface UserManager {
    
    public boolean registerUser(String username, String name, String lastname, String password, Long role);
    
    public String authorizeUser(String username, String password);

    public User getUserByUsername(String username);
    
    public boolean deleteUserById(Long id);
    
    public boolean doesUserAlreadyExist(String username);

    public HttpEntity getRequestEntity();

}
