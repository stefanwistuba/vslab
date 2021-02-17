package de.hska.iwi.vslab.userroleservice.controller;

import de.hska.iwi.vslab.userroleservice.dao.UserRepository;
import de.hska.iwi.vslab.userroleservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
public class UserController {

    @Autowired
    private UserRepository repo;

    // @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'ANONYMOUS')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (!repo.existsByUserName(user.getUserName())) {
            user = repo.save(user);
            return new ResponseEntity<User>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PreAuthorize("hasRole('ADMIN')")
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

    @PreAuthorize("hasAuthority('UserCoreAccessName') or hasRole('ADMIN') or (hasRole('USER') and #userName == principal)")
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable String userName) {
        User user = repo.findByUserName(userName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
