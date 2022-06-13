package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    private final DAOService service;

    @Autowired
    public UserController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Users>> getall() {

        try {
            List<Users> users = service.getAll(Users.class);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{Id}", produces = "application/json")
    public ResponseEntity<Users> get(@PathVariable Integer Id) {

        if (Id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Users user = service.find(Users.class, Id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> save(@RequestBody Users user) {

        if (user == null || user.getLocality() == null || user.getPartners() == null || user.getProfiles() == null
            || user.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            user.setDateCreated(new Date());
            user.setIsCredentialsExpired(Byte.valueOf("0"));
            user.setIsExpired(Byte.valueOf("0"));
            user.setIsLocked(Byte.valueOf("0"));
            user.setIsEnabled(Byte.valueOf("0"));
            Integer userId = (Integer) service.Save(user);
            
            Users createdUser = service.find(Users.class, userId);
            
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> update(@RequestBody Users user) {

        if (user == null || user.getLocality() == null || user.getPartners() == null || user.getProfiles() == null
            || user.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            user.setDateUpdated(new Date());
            Users updatedUser = service.update(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
