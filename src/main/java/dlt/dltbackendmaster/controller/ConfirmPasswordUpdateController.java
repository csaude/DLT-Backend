package dlt.dltbackendmaster.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

@Controller
@RequestMapping("/confirm-update")
public class ConfirmPasswordUpdateController
{
    private final DAOService service;

    @Autowired
    public ConfirmPasswordUpdateController(DAOService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<String> confirmPasswordUpdate(HttpServletRequest request, @Param(value = "token") String token) {
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
            return new ResponseEntity<>("Confirmada a alteração da password do Utilizador " + updatedUser.getName()
                                        + " " + user.getSurname() + "!",
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
