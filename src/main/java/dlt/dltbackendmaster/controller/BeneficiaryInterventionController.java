package dlt.dltbackendmaster.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.BeneficiariesInterventionsId;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/beneficiary-intervention")
public class BeneficiaryInterventionController
{
    private final DAOService service;

    public BeneficiaryInterventionController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<BeneficiariesInterventions>> getAll() {

        try {
            List<BeneficiariesInterventions> interventions = service.getAll(BeneficiariesInterventions.class);
            return new ResponseEntity<>(interventions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BeneficiariesInterventions> save(@RequestBody BeneficiariesInterventions intervention) {

    	if(intervention.getId() == null || intervention.getId().getDate() == null || intervention.getUs() == null) {
    		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    	}

        try {
        	
            intervention.setDateCreated(new Date());
            service.Save(intervention);
            
            SubServices subService = service.find(SubServices.class, intervention.getId().getSubServiceId());
            intervention.setSubServices(subService);
            return new ResponseEntity<>(intervention, HttpStatus.OK);
            
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SuppressWarnings("unchecked")
	@PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity update(@RequestBody BeneficiariesInterventions intervention) {

        if (intervention.getId() == null || intervention.getId().getDate() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
        	
        	BeneficiariesInterventions currentIntervention = service.find(BeneficiariesInterventions.class, intervention.getId());
        	
        	if(currentIntervention == null) {
        		return new ResponseEntity("The Intervention was not Found!", HttpStatus.NOT_FOUND);
        	} 
        	
        	currentIntervention.getId().setBeneficiaryId(intervention.getBeneficiaries().getId());
        	currentIntervention.getId().setSubServiceId(intervention.getSubServices().getId());
        	currentIntervention.getId().setDate(intervention.getDate());
        	currentIntervention.setRemarks(intervention.getRemarks());
        	currentIntervention.setEntryPoint(intervention.getEntryPoint());
        	currentIntervention.setResult(intervention.getResult());
        	currentIntervention.setProvider(intervention.getProvider());
        	currentIntervention.setStatus(intervention.getStatus());
        	currentIntervention.setDateUpdated(new Date());
        	
            BeneficiariesInterventions updatedIntervention = service.update(currentIntervention);
            return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
