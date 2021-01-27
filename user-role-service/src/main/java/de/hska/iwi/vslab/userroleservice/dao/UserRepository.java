package de.hska.iwi.vslab.userroleservice.dao;

import de.hska.iwi.vslab.userroleservice.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface UserRepository extends org.springframework.data.repository.CrudRepository<User, Long> {

    User findByUserName(String userName);
}