package dlt.dltbackendmaster.controller;

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

        if (intervention == null || intervention.getBeneficiaries() == null || intervention.getSubServices() == null
            || intervention.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            /*intervention.setDateCreated(new Date());
            BeneficiaryIntervention createdIntervention = (BeneficiaryIntervention) service.Save(intervention);
            SubService subService = service.find(SubService.class, intervention.getSubService().getId());
            createdIntervention.setSubService(subService);*/
            return new ResponseEntity<>(/*createdIntervention*/null, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<BeneficiariesInterventions> update(@RequestBody BeneficiariesInterventions intervention) {

        if (intervention == null || intervention.getBeneficiaries() == null || intervention.getSubServices() == null
            || intervention.getUs() == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
        	if(service.exist(intervention)) {
        		System.out.println("Entity exists");
        	} else {
        		System.out.println("Entity doenst exists");
        	}
        	
        	
            intervention.setDateUpdated(new Date());
            BeneficiariesInterventions updatedIntervention = service.update(intervention);
            return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
