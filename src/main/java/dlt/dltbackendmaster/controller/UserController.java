package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.security.EmailSender;
import dlt.dltbackendmaster.security.utils.PasswordGenerator;
import dlt.dltbackendmaster.security.utils.Utility;
import dlt.dltbackendmaster.service.DAOService;
import net.bytebuddy.utility.RandomString;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    private final DAOService service;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailSender emailSender;

    @Autowired
    public UserController(DAOService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
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

        if (user == null || user.getPartners() == null || user.getProfiles() == null || user.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            String password = PasswordGenerator.generateStrongPassword();
            System.out.println("Password: " + password);
            user.setPassword(passwordEncoder.encode(password));
            user.setDateCreated(new Date());
            user.setIsCredentialsExpired(Byte.valueOf("0"));
            user.setIsExpired(Byte.valueOf("0"));
            user.setIsLocked(Byte.valueOf("0"));
            user.setIsEnabled(Byte.valueOf("1"));
            user.setNewPassword(1);
            Integer userId = (Integer) service.Save(user);
            Users createdUser = service.find(Users.class, userId);

            String email = user.getEmail() != null ? user.getEmail() : user.getUsers().getEmail();
            emailSender.sendEmail(user.getUsername(), password, email, null, true);
            
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Users> update(@RequestBody Users user) {

        if (user == null || user.getPartners() == null || user.getProfiles() == null || user.getUs() == null) {
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

    @PutMapping(path = "/update-password", produces = "application/json")
    public ResponseEntity<Users> updatePassword(HttpServletRequest request, @Param(value = "username") String username, @Param(value = "newPassword") String newPassword) {

        Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);

        if (username == null || user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            user.setRecoverPassword(passwordEncoder.encode(newPassword));
            user.setNewPassword(0);
            user.setIsEnabled(Byte.valueOf("0"));
            String token = RandomString.make(45);
            user.setRecoverPasswordToken(token);
            Users updatedUser = service.update(user);
            // Generate reset confirmation Link
            String confirmUpdatePasswordLink = Utility.getSiteURL(request) + "/api/users/confirm-update?token=" + token;
            // Send E-mail
            String email = user.getEmail() != null ? user.getEmail() : user.getUsers().getEmail();
            emailSender.sendEmail(user.getName() + " " + user.getSurname(),
                                  null,
                                  email,
                                  confirmUpdatePasswordLink,
                                  false);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/confirm-update", produces = "application/json")
    public ResponseEntity<Users> confirmPasswordUpdate(HttpServletRequest request, @Param(value = "token") String token) {

        Users user = service.GetUniqueEntityByNamedQuery("Users.findByResetPasswordToken", token);

        if (token == null || user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            user.setPassword(user.getRecoverPassword());
            user.setIsEnabled(Byte.valueOf("1"));
            user.setRecoverPassword(null);
            user.setRecoverPasswordToken(null);
            Users updatedUser = service.update(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/change-password", produces = "application/json")
    public ResponseEntity<Users> changePassword(HttpServletRequest request, @Param(value = "username") String username, @Param(value = "newPassword") String newPassword) {
  
        Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);

        if (username == null || user == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            user.setNewPassword(0);
            user.setPassword(passwordEncoder.encode(newPassword));
            Users updatedUser = service.update(user);
            
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
