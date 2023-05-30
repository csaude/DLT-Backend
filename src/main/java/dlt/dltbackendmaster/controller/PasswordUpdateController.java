package dlt.dltbackendmaster.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.security.EmailSender;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.util.EnvConstants;
import dlt.dltbackendmaster.util.Utility;
import net.bytebuddy.utility.RandomString;

@Controller
@RequestMapping("/users")
public class PasswordUpdateController {
	private final DAOService service;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	public PasswordUpdateController(DAOService service, PasswordEncoder passwordEncoder) {
		this.service = service;
		this.passwordEncoder = passwordEncoder;
	}

	@PutMapping(path = "/update-password", produces = "application/json")
	public ResponseEntity<Users> updatePassword(HttpServletRequest request, @RequestBody Users users) {

		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", users.getUsername());


		if (user == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

        try {
            String token = RandomString.make(45);
        	// Generate reset confirmation Link
            String apiHome = Utility.getSiteURL(request);
             		
            String validatedApiHome = getApiHome(apiHome);
            String validatedOriginUrl = getValidatedOrigin(validatedApiHome);
            
            String confirmUpdatePasswordLink = validatedApiHome + "/users/confirm-update?token=" + token;

            user.setRecoverPassword(passwordEncoder.encode(users.getRecoverPassword()));
            user.setNewPassword(0);
            user.setIsEnabled(Byte.valueOf("0"));
            user.setRecoverPasswordOrigin(validatedOriginUrl);

            user.setRecoverPasswordToken(token);
            Users updatedUser = service.update(user);
                        // Send E-mail
            emailSender.sendEmail(user.getName()+" "+user.getSurname(), user.getName() + " " + user.getSurname(),
                                  null,
                                  user.getEmail(),
                                  confirmUpdatePasswordLink,
                                  false);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	private String getValidatedOrigin(String siteURL) {
		if("http://localhost:8083".equals(siteURL) || siteURL.endsWith(":8083")) {
			return EnvConstants.DEV_PASSWORD_UPDATE_ORIGIN_URL;
		}
	    return EnvConstants.PROD_PASSWORD_UPDATE_ORIGIN_URL;
	}

	private String getApiHome(String siteURL) {
		if("http://dreams/dlt-api-0.1".equals(siteURL)) {
			return EnvConstants.PROD_PASSWORD_UPDATE_API_HOME;
		}
		return siteURL;
	}

	@GetMapping(path = "/confirm-update")
	public ResponseEntity<String> confirmPasswordUpdate(HttpServletRequest request,
			@Param(value = "token") String token) {
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
			return new ResponseEntity<>(
					"Confirmada a alteração da password do Utilizador " + updatedUser.getName() + " "
							+ user.getSurname() + "!, " + " <a href=\"" + user.getRecoverPasswordOrigin() + "/dreams#/login\">Login</a>",
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
