package de.hska.iwi.vslab.userroleservice.controller;

import de.hska.iwi.vslab.userroleservice.dao.UserRepository;
import de.hska.iwi.vslab.userroleservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "Login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<User[]> getUsers() {
        Iterable<User> allPolls = repo.findAll();

        List<User> users = new ArrayList<User>();
        for (User user : allPolls) {
            users.add(user);
        }

        User[] usersArray = users.toArray(new User[0]);

        return new ResponseEntity<User[]>(usersArray, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        user = repo.save(user);
        return new ResponseEntity<User>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<Optional<User>> getUser(@PathVariable String userName) {
        Optional<User> user = repo.findByUserName(userName);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
