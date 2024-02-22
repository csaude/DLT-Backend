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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.CountIntervention;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesObject;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.service.BeneficiariyInterventionService;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

@RestController
@RequestMapping("/api/beneficiary-intervention")
public class BeneficiaryInterventionController {
	private final DAOService service;
	private final BeneficiariyInterventionService beneficiariyInterventionService;

	public BeneficiaryInterventionController(DAOService service,
			BeneficiariyInterventionService beneficiariyInterventionService) {
		this.service = service;
		this.beneficiariyInterventionService = beneficiariyInterventionService;
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
	public ResponseEntity<ReferencesServicesObject> save(@RequestBody BeneficiariesInterventions intervention) {

		if (intervention.getId() == null || intervention.getId().getDate() == null || intervention.getUs() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Us us = service.find(Us.class, intervention.getUs().getId());
			// FIXME: Remover esta gambiária após resolver o issue
			Beneficiaries beneficiary = service.find(Beneficiaries.class, intervention.getId().getBeneficiaryId());
			intervention.setUs(us);
			intervention.setBeneficiaries(beneficiary);
			intervention.setDateCreated(new Date());
			service.Save(intervention);

			SubServices subService = service.find(SubServices.class, intervention.getId().getSubServiceId());
			intervention.setSubServices(subService);

			// Actualizar o status dos serviços solicitados
			Integer serviceId = subService.getServices().getId();
			List<ReferencesServices> referencesServices = service.GetAllEntityByNamedQuery(
					"ReferencesServices.findByBeneficiaryAndService", beneficiary.getId(), serviceId);

			List<References> updatedReferences = new ArrayList<>();

			for (ReferencesServices referenceServices : referencesServices) {

				// Get only interventions provided on or after referral date
				References reference = referenceServices.getReferences();
				List<BeneficiariesInterventions> interventions = service.GetAllEntityByNamedQuery(
						"BeneficiaryIntervention.findAllByBeneficiaryAndDate",
						Instant.ofEpochMilli(reference.getDate().getTime()).atZone(ZoneId.systemDefault())
								.toLocalDate(),
						beneficiary.getId());
				Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(interventions,
						serviceId);

				if (referenceServiceStatus.intValue() != (referenceServices.getStatus())) {
					referenceServices.setStatus(referenceServiceStatus);
					referenceServices.setDateUpdated(new Date());
					referenceServices.setUpdatedBy(subService.getCreatedBy());
					referenceServices = service.update(referenceServices);

					// Actualizar o status da referências
					Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference, interventions);

					if (referenceStatus.intValue() != reference.getStatus()) {
						reference.setStatus(referenceStatus);
						reference.setDateUpdated(new Date());
						reference.setUpdatedBy(subService.getCreatedBy());
						reference = service.update(reference);
					}
					updatedReferences.add(reference);
				}
			}
			ReferencesServicesObject referenceServiceObject = new ReferencesServicesObject(intervention,
					updatedReferences);
			return new ResponseEntity<>(referenceServiceObject, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity update(@RequestBody BeneficiariesInterventions intervention) {

		if (intervention.getId() == null || intervention.getId().getDate() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {

			BeneficiariesInterventions currentIntervention = service.find(BeneficiariesInterventions.class,
					intervention.getId());

			if (currentIntervention == null) {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}

			// if key was updated, entry should be disabled and a new one added
			if (intervention.getId().getSubServiceId() != intervention.getSubServices().getId()
					|| !intervention.getId().getDate().equals(intervention.getDate())) {

				currentIntervention.setStatus(0);
				currentIntervention.setDateUpdated(new Date());
				service.delete(currentIntervention); // Must remove to allow new additions with this key afterwards

				// FIXME: Remover esta gambiária após resolver o issue
				Beneficiaries beneficiary = service.find(Beneficiaries.class, intervention.getId().getBeneficiaryId());

				BeneficiariesInterventions newInterv = new BeneficiariesInterventions();
				newInterv.getId().setBeneficiaryId(beneficiary.getId());
				newInterv.getId().setSubServiceId(intervention.getSubServices().getId());
				newInterv.getId().setDate(intervention.getDate());
				newInterv.setSubServices(intervention.getSubServices());
				newInterv.setBeneficiaries(beneficiary);
				newInterv.setRemarks(intervention.getRemarks());
				newInterv.setEntryPoint(intervention.getEntryPoint());
				newInterv.setResult(intervention.getResult());
				newInterv.setUs(intervention.getUs());
				newInterv.setProvider(intervention.getProvider());
				newInterv.setStatus(intervention.getStatus());
				newInterv.setActivistId(intervention.getActivistId());
				newInterv.setCreatedBy(currentIntervention.getCreatedBy());
				newInterv.setDateCreated(currentIntervention.getDateCreated());
				newInterv.setDateUpdated(new Date());
				newInterv.setUpdatedBy(intervention.getUpdatedBy());

				service.Save(newInterv);

				SubServices subService = service.find(SubServices.class, newInterv.getId().getSubServiceId());
				newInterv.setSubServices(subService);

				return new ResponseEntity<>(newInterv, HttpStatus.OK);
			}

			// Edit fields

			currentIntervention.setRemarks(intervention.getRemarks());
			currentIntervention.setUs(intervention.getUs());
			currentIntervention.setEntryPoint(intervention.getEntryPoint());
			currentIntervention.setResult(intervention.getResult());
			currentIntervention.setProvider(intervention.getProvider());
			currentIntervention.setStatus(intervention.getStatus());
			currentIntervention.setActivistId(intervention.getActivistId());
			currentIntervention.setDateUpdated(new Date());
			currentIntervention.setUpdatedBy(intervention.getUpdatedBy());

			BeneficiariesInterventions updatedIntervention = service.update(currentIntervention);
			return new ResponseEntity<>(updatedIntervention, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byBeneficiaryId/{beneficiaryId}", produces = "application/json")
	public ResponseEntity<List<BeneficiariesInterventions>> getByBeneficiaryId(@PathVariable Integer beneficiaryId) {
		try {
			List<BeneficiariesInterventions> interventions = beneficiariyInterventionService
					.findByBeneficiaryId(beneficiaryId);
			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/countAllInterventionsAndbByServiceType", produces = "application/json")
	public ResponseEntity<List<CountIntervention>> countByBeneficiaryAndServiceType() {
		try {
			List<CountIntervention> interventions = beneficiariyInterventionService
					.countAllInterventionsAndbByServiceType();
			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/byBeneficiariesIds", produces = "application/json")
	public ResponseEntity<List<BeneficiariesInterventions>> getByBeneficiariesIds(
			@RequestParam(name = "params") Integer[] params) {
		try {
			List<BeneficiariesInterventions> interventions = beneficiariyInterventionService
					.findByBeneficiariesIds(params);

			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/countByBeneficiaryAndServiceType/{beneficiaryId}", produces = "application/json")
	public ResponseEntity<List<CountIntervention>> countByBeneficiaryAndServiceType(
			@PathVariable Integer beneficiaryId) {
		try {
			List<CountIntervention> interventions = beneficiariyInterventionService
					.countInterventionsByBeneficiaryAndServiceType(beneficiaryId);

			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/countByBeneficiaryAndAgeBandAndLevel/{beneficiaryId}/{ageBand}/{level}", produces = "application/json")
	public ResponseEntity<List<CountIntervention>> countInterventionsByBeneficiaryAndAgeBandAndLevel(
			@PathVariable Integer beneficiaryId, @PathVariable Integer ageBand, @PathVariable Integer level) {
		try {
			List<CountIntervention> interventions = beneficiariyInterventionService
					.countInterventionsByBeneficiaryAndAgeBandAndLevel(beneficiaryId, ageBand, level);

			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
