package de.hska.iwi.vslab.userroleservice.controller;

import de.hska.iwi.vslab.userroleservice.dao.UserRepository;
import de.hska.iwi.vslab.userroleservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        user = repo.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
