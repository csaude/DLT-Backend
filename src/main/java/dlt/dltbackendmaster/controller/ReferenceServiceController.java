package dlt.dltbackendmaster.controller;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.ReferenceServiceService;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

@RestController
@RequestMapping("/api/reference-service")
public class ReferenceServiceController {
	private final ReferenceServiceService referenceServiceService;
    private final DAOService service;

	public ReferenceServiceController(ReferenceServiceService referenceServiceService, DAOService service) {
		this.referenceServiceService = referenceServiceService;
		this.service = service;
	}

	@GetMapping(path = "/byReferenceId/{referenceId}", produces = "application/json")
	public ResponseEntity<List<ReferencesServices>> getByBeneficiaryId(@PathVariable Integer referenceId) {
		try {
			List<ReferencesServices> interventions = referenceServiceService.findByReferenceId(referenceId);
			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/decline/{referenceId}/{serviceId}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ReferencesServices> refuse(@PathVariable Integer referenceId,
			@PathVariable Integer serviceId,
			@RequestParam String declineReason,
			@RequestParam int userId
			) {

		if (referenceId == null || serviceId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			// setting interventions counts from database before update
			ReferencesServices referencesService = referenceServiceService.findByReferenceIdAndServiceId(referenceId,serviceId);
			referencesService.setStatus(3);
			referencesService.setDeclineReason(declineReason);
			referencesService.setUpdatedBy(userId);
			referencesService.setDateUpdated(new Date());

			ReferencesServices referencesServiceUpdated = service.update(referencesService);
			
			References reference = service.find(References.class, referenceId);

		    updateReferences(reference, serviceId,userId);

			return new ResponseEntity<>(referencesServiceUpdated, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	public ReferencesServicesObject updateReferences(
			References reference, int serviceId, int userId) {
		List<References> updatedReferences = new ArrayList<>();
			
			List<BeneficiariesInterventions> interventions = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findAllByBeneficiaryAndDate",
					Instant.ofEpochMilli(reference.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
					reference.getBeneficiaries().getId());
			
			References referenceDB = service.find(References.class, reference.getId());
			Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(referenceDB, interventions);

				if (referenceStatus.intValue() != reference.getStatus()) {
					reference.setStatus(referenceStatus);
					reference.setDateUpdated(new Date());
					reference.setUpdatedBy(userId);
					reference = service.update(reference);
				}
				updatedReferences.add(reference);

		ReferencesServicesObject referenceServiceObject = new ReferencesServicesObject(new BeneficiariesInterventions(), updatedReferences);
		return referenceServiceObject;
	}

}
