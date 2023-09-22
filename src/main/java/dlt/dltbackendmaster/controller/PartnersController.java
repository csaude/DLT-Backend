package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.ServiceType;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/partners")
public class PartnersController {

	@Autowired
	private final DAOService service;

	public PartnersController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Partners>> getAll() {
		try {
			List<Partners> partners = service.getAll(Partners.class);

			return new ResponseEntity<>(partners, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public ResponseEntity<Partners> get(@PathVariable Integer id) {
		if (id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Partners partner = service.find(Partners.class, id);

			return new ResponseEntity<>(partner, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{partnerType}", produces = "application/json")
	public ResponseEntity<List<Partners>> get(@PathVariable ServiceType partnerType) {

		if (partnerType == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<Partners> partners = service.GetAllEntityByNamedQuery("Partners.findByPartnerType",
					String.valueOf(partnerType.ordinal() + 1));
			return new ResponseEntity<>(partners, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{partnerType}/{district}", produces = "application/json")
	public ResponseEntity<List<Partners>> getByTypeDistrict(@PathVariable ServiceType partnerType,
			@PathVariable Integer district) {

		if (partnerType == null || district == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<Partners> partners = service.GetAllEntityByNamedQuery("Partners.findByTypeDistrict",
					String.valueOf(partnerType.ordinal() + 1), district);
			return new ResponseEntity<>(partners, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/byDistricts")
	public ResponseEntity<List<Partners>> get(@RequestParam("districts") List<String> districts){
		try {
			List<Integer> distIds = districts.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<Partners> partners = service.GetAllEntityByNamedQuery("Partners.findByDistricts", distIds);
			return ResponseEntity.ok(partners);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Partners> save(@RequestBody Partners partner) {
        if (partner == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            partner.setDateCreated(new Date());
            Integer partnerId = (Integer) this.service.Save(partner);
            Partners savedPartners = this.service.find(Partners.class, partnerId);

            return new ResponseEntity<>(savedPartners, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Partners> update(@RequestBody Partners partner) {
        if (partner == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            partner.setDateUpdated(new Date());
            Partners updatedService = this.service.update(partner);
            return new ResponseEntity<>(updatedService, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
	@GetMapping(path = "/get-partners", produces = "application/json")
	public ResponseEntity<List<Partners>>  getNames() throws AccountLockedException {
		try {
			List<Partners> user = service.GetAllEntityByNamedQuery("Partners.findPartners");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
