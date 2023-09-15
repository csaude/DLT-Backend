package dlt.dltbackendmaster.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountLockedException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.security.EmailSender;
import dlt.dltbackendmaster.security.UserDetailsServiceImpl;
import dlt.dltbackendmaster.security.utils.PasswordGenerator;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	Logger logger = LoggerFactory.getLogger(BeneficiaryController.class);

	private static final String QUERY_FIND_USER_BY_USERNAME = "select u from Users u where u.username = :username";

	private final DAOService service;

	private final PasswordEncoder passwordEncoder;

	private final UserDetailsServiceImpl userDetailsService;
	@Autowired
	private EmailSender emailSender;

	@Autowired
	public UserController(DAOService service, PasswordEncoder passwordEncoder,
			UserDetailsServiceImpl userDetailsService) {
		this.service = service;
		this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
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

		Map<String, Object> todo = new HashMap<String, Object>();
		todo.put("username", user.getUsername());
		try {
			List<Users> users = service.findByJPQuery(QUERY_FIND_USER_BY_USERNAME, todo);

			if (users != null && !users.isEmpty()) {
				logger.warn("User " + user.getUsername()
						+ "tried to register, but this user already exists, check error code returned "
						+ HttpStatus.FORBIDDEN);
				return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
			}

		} catch (Exception e) {
			logger.warn("User " + user.getUsername()
					+ " tried to register, but the system had unkown error, check error code returned "
					+ HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

		try {
			String password = PasswordGenerator.generateStrongPassword();
			user.setPassword(passwordEncoder.encode(password));
			user.setDateCreated(new Date());
			user.setIsCredentialsExpired(Byte.valueOf("0"));
			user.setIsExpired(Byte.valueOf("0"));
			user.setIsLocked(Byte.valueOf("0"));
			user.setIsEnabled(Byte.valueOf("1"));
			user.setNewPassword(1);
			user.setPasswordLastChangeDate(new Date());
			Integer userId = (Integer) service.Save(user);
			Users createdUser = service.find(Users.class, userId);

			if (user.getEmail() != null) {
				String email = user.getEmail();
				emailSender.sendEmail(user.getName() + " " + user.getSurname(), user.getUsername(), password, email,
						null, true);
			}
			logger.warn("User " + user.getUsername() + " logged to system ");
			return new ResponseEntity<>(createdUser, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("User " + user.getUsername()
					+ " tried to register, but the system had unkown error, check error code returned "
					+ HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Users> update(@RequestBody Users user) {

		if (user == null || user.getPartners() == null || user.getProfiles() == null || user.getUs() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users u = service.find(Users.class, user.getId());
			user.setDateUpdated(new Date());
			user.setPassword(u.getPassword());
			Users updatedUser = service.update(user);
			logger.warn("User " + user.getUsername() + " updated the user information ");
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch (Exception e) {
			logger.warn("User " + user.getUsername()
					+ " tried to update user, but the system had unkown error, check error code returned "
					+ HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/change-password", produces = "application/json")
	public ResponseEntity<Users> changePassword(HttpServletRequest request, @RequestBody Users users) {

		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", users.getUsername());

		if (user == null) {
			logger.warn("User " + users.getUsername()
					+ " tried to change password, but the user does not exists, check error code returned "
					+ HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			user.setNewPassword(0);
			user.setPassword(passwordEncoder.encode(users.getRecoverPassword()));
			user.setPasswordLastChangeDate(new Date());
			Users updatedUser = service.update(user);
			logger.warn("User " + user.getUsername() + " changed password ");
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} catch (Exception e) {
			logger.warn("User " + user.getUsername()
					+ " tried to change password, but the system had unkown error, check error code returned "
					+ HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/us/{Id}", produces = "application/json")
	public ResponseEntity<List<Users>> getByUs(@PathVariable Integer Id) {

		if (Id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<Users> user = service.GetAllEntityByNamedQuery("Users.findByUsId", Id);

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/locality/{Id}", produces = "application/json")
	public ResponseEntity<List<Users>> getByLocality(@PathVariable Integer Id) {

		if (Id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<Users> user = service.GetAllEntityByNamedQuery("Users.findByLocalityId", Id);

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byProfilesAndUser/{profiles}/{userId}", produces = "application/json")
	public ResponseEntity<List<Users>> getByProfilesAndUser(@PathVariable List<String> profiles,
			@PathVariable Integer userId) {

		if (profiles == null || userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);
			List<Integer> profilesIds = profiles.stream().map(Integer::parseInt).collect(Collectors.toList());
			List<Integer> localitiesIds = user.getLocalities().stream().map(Locality::getId)
					.collect(Collectors.toList());
			List<Users> users = null;
			if (user.getLocalities().isEmpty()) {
				users = service.GetAllEntityByNamedQuery("Users.findByProfiles", profilesIds);
			} else {
				users = service.GetAllEntityByNamedQuery("Users.findByProfilesAndOrganization",
						user.getPartners().getId(), profilesIds);
			}

			if (localitiesIds.isEmpty()) {
				return new ResponseEntity<>(users, HttpStatus.OK);
			} else {
				List<Users> filteredUsers = new ArrayList<>();

				for (Users users2 : users) {
					List<Integer> userLocalitiesIds = users2.getLocalities().stream().map(Locality::getId)
							.collect(Collectors.toList());
					if (containsAny(userLocalitiesIds, localitiesIds)) {
						filteredUsers.add(users2);
					}
				}

				return new ResponseEntity<>(filteredUsers, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/username/{username}", produces = "application/json")
	public ResponseEntity<Users> verifyUserByUsername(@PathVariable String username) throws AccountLockedException {
		return userDetailsService.verifyUserByUsername(username);
	}

	@GetMapping(path = "/username/{username}/password-validity", produces = "application/json")
	public ResponseEntity<Users> checkPasswordValidity(@PathVariable String username) throws AccountLockedException {
		return userDetailsService.checkPasswordValidity(username);
	}

	public static <T> boolean containsAny(List<T> l1, List<T> l2) {

		for (T elem : l1) {

			if (l2.contains(elem)) {
				return true;
			}
		}
		return false;
	}
	
	@GetMapping(path = "/get-usernames", produces = "application/json")
	public ResponseEntity<List<Users>>  getNames() throws AccountLockedException {
		try {
			List<Users> user = service.GetAllEntityByNamedQuery("Users.findUsernames");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/provinces")
	public ResponseEntity<List<Users>> getUsersPrv(@RequestParam("provinces") List<String> provinces){
		try {
			List<Integer> provIds = provinces.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<Users> user = service.GetAllEntityByNamedQuery("Users.findByAlocatProvinces", provIds);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}

	@GetMapping("/districts")
	public ResponseEntity<List<Users>> getUsers(@RequestParam("districts") List<String> districts){
		try {
			List<Integer> distIds = districts.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<Users> user = service.GetAllEntityByNamedQuery("Users.findByAlocatDistricts", distIds);
			return ResponseEntity.ok(user);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}	

}
