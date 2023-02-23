package dlt.dltbackendmaster.controller;

import static dlt.dltbackendmaster.util.ProfilesConstants.COUNSELOR;
import static dlt.dltbackendmaster.util.ProfilesConstants.MANAGER;
import static dlt.dltbackendmaster.util.ProfilesConstants.MENTOR;
import static dlt.dltbackendmaster.util.ProfilesConstants.NURSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesId;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/references")
public class ReferencesController {
	private final DAOService service;

	@Autowired
	public ReferencesController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<References>> getall() {
		try {
			List<References> references = service.GetAllEntityByNamedQuery("References.findAll");
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{Id}", produces = "application/json")
	public ResponseEntity<References> get(@PathVariable Integer Id) {

		if (Id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			References references = service.find(References.class, Id);
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byUser/{userId}", produces = "application/json")
	public ResponseEntity<List<References>> getAllByUser(@PathVariable Integer userId,
			@RequestParam(name = "pageIndex") int pageIndex,
    		@RequestParam(name = "pageSize") int pageSize
			) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			List<References> references = null;

			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId())) {
				references = service.GetAllPagedEntityByNamedQuery("References.findAllByUserPermission", pageIndex, pageSize, userId);
			} else {
				references = service.GetAllPagedEntityByNamedQuery("References.findAll", pageIndex, pageSize);
			}

			return new ResponseEntity<>(references, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<References> save(@RequestBody References reference) {

		if (reference == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			reference.setCancelReason(null);
			reference.setOtherReason(null);
			reference.setDateCreated(new Date());

			service.Save(reference);

			List<ReferencesServices> registeredServices = new ArrayList<ReferencesServices>();

			for (ReferencesServices services : reference.getReferencesServiceses()) {
				services.setId(new ReferencesServicesId());
				services.getId().setReferenceId(reference.getId());
				services.getId().setServiceId(services.getServices().getId());
				services.setDateCreated(new Date());
				service.Save(services);
				registeredServices.add(services);
			}

			reference.setReferencesServiceses(Set.copyOf(registeredServices));

			return new ResponseEntity<>(reference, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<References> update(@RequestBody References reference) {

		if (reference == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			reference.setDateUpdated(new Date());

			service.update(reference);

			// remove all previous entries
			service.UpdateEntitiesByNamedQuery("ReferencesServices.removeByReferenceId", reference.getId());

			// save new ones
			List<ReferencesServices> registeredServices = new ArrayList<ReferencesServices>();

			for (ReferencesServices services : reference.getReferencesServiceses()) {
				services.setId(new ReferencesServicesId());
				services.getId().setReferenceId(reference.getId());
				services.getId().setServiceId(services.getServices().getId());
				services.setDateCreated(new Date());
				service.Save(services);
				registeredServices.add(services);
			}

			reference.setReferencesServiceses(Set.copyOf(registeredServices));

			return new ResponseEntity<>(reference, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/user/{Id}", produces = "application/json")
	public ResponseEntity<List<References>> getRefByUser(@PathVariable String Id) {

		if (Id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<References> references = service.GetAllEntityByNamedQuery("References.findAllByUser", Id);
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@GetMapping(path = "/byUser/{userId}/count", produces = "application/json")
	public ResponseEntity<Long> getCountByUserPermission(@PathVariable Integer userId) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			Long referencesTotal;
			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId())) {
				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountByUserPermission", userId);
			} else {
				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountAll");
			}

			return new ResponseEntity<>(referencesTotal, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
