package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    public BeneficiaryController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> getAll() {

        try {
            List<Beneficiaries> beneficiaries = service.getAll(Beneficiaries.class);
            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
        	//e.printStackTrace();
        	
        	System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Beneficiaries> get(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
        	Beneficiaries beneficiary = service.find(Beneficiaries.class, id);
            return new ResponseEntity<>(beneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(consumes = "application/json")
    public ResponseEntity<Beneficiaries> save(@RequestBody BeneficiarySyncModel beneficiaryParam) {
    	//System.out.println(beneficiary);
    	
    	/*Random rnd = new Random();
        int nui = rnd.nextInt(99999);
        
        Beneficiary beneficiary = new Beneficiary(beneficiaryParam);
        beneficiary.setNui(""+nui);
    	beneficiary.setNeighborhood(new Neighborhood(1));
    	//Partners partners = new Partners();
    	//partners.setId(1);
    	//beneficiary.setPartner(partners);
    	beneficiary.setDateCreated(new Date());
    	beneficiary.setName(beneficiaryParam.getNick_name());
    	beneficiary.setStatus(1);
    	Users createdBy = new Users();
    	createdBy.setId(1);
    	beneficiary.setCreatedBy(createdBy);
    	beneficiary.setUsId(1);
    	*/
    	
    	try {
		//	service.Save(beneficiary);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	
    	
    	return new ResponseEntity<>(null, HttpStatus.OK);
    	
    }
}

