package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountLockedException;

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

import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/localities")
public class LocalityController {

	@Autowired
	private final DAOService service;

	public LocalityController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Locality>> getAll() {
		try {
			List<Locality> localities = service.getAll(Locality.class);

			return new ResponseEntity<>(localities, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<Locality> get(@PathVariable Integer id){
		if(id == null ) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}	
		
		try {
			Locality locality = service.find(Locality.class, id);

			return new ResponseEntity<>(locality, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Locality> save(@RequestBody Locality locality) {
        if (locality == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            locality.setDateCreated(new Date());
            Integer localityId = (Integer) this.service.Save(locality);
            Locality savedLocality = this.service.find(Locality.class, localityId);

            return new ResponseEntity<>(savedLocality, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Locality> update(@RequestBody Locality locality) {
        if (locality == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            locality.setDateUpdated(new Date());
            Locality updatedService = this.service.update(locality);
            return new ResponseEntity<>(updatedService, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
	
	@GetMapping(path = "/get-localities", produces = "application/json")
	public ResponseEntity<List<Locality>>  getNames() throws AccountLockedException {
		try {
			List<Locality> locality = service.GetAllEntityByNamedQuery("Locality.findLocalities");

			return new ResponseEntity<>(locality, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
