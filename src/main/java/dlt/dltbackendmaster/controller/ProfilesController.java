package dlt.dltbackendmaster.controller;

import java.util.List;

import javax.security.auth.login.AccountLockedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/profiles")
public class ProfilesController {

	@Autowired
	private final DAOService service;

	public ProfilesController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Profiles>> getAll() {
		try {
			List<Profiles> profiles = service.getAll(Profiles.class);

			return new ResponseEntity<>(profiles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Profiles> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Profiles profile = service.find(Profiles.class, id);

			return new ResponseEntity<>(profile, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/get-profiles", produces = "application/json")
	public ResponseEntity<List<Profiles>>  getNames() throws AccountLockedException {
		try {
			List<Profiles> user = service.GetAllEntityByNamedQuery("Profiles.findProfiles");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
