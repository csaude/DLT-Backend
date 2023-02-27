package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.ServiceType;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.util.Utility;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
	private final DAOService service;

	public ServiceController(DAOService service) {
		super();
		this.service = service;
	}

	@GetMapping(path = "/all", produces = "application/json")
	public ResponseEntity<List<Services>> findAll() {
		try {
			List<Services> services = service.getAll(Services.class);
			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Services>> getAll() {

		try {
			List<Services> services = service.GetAllEntityByNamedQuery("Service.findAll");
			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/{serviceType}", produces = "application/json")
	public ResponseEntity<List<Services>> get(@PathVariable ServiceType serviceType) {

		if (serviceType == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			List<Services> services = service.GetAllEntityByNamedQuery("Service.findByServiceType",
					String.valueOf(serviceType.ordinal() + 1));
			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "byTypeAndBeneficiary/{serviceType}/{beneficiaryId}", produces = "application/json")
	public ResponseEntity<List<Services>> get(@PathVariable ServiceType serviceType,
			@PathVariable Integer beneficiaryId) {

		if (serviceType == null || beneficiaryId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Beneficiaries beneficiary = service.find(Beneficiaries.class, beneficiaryId);
			List<Services> services = service.GetAllEntityByNamedQuery("Service.findByServiceType",
					String.valueOf(serviceType.ordinal() + 1));
			int beneficiaryAge = Utility.calculateAge(beneficiary.getDateOfBirth());

			if (beneficiaryAge < 15) {

				if (beneficiaryAge < 14 && serviceType == ServiceType.COMMUNITY) {
					// Retirar Guião de facilitação
					services = services.stream().filter(s -> s.getId() != 46 && s.getId() != 49 && s.getId() != 52)
							.collect(Collectors.toList());
				}

				if (beneficiary.getVbltSexuallyActive() != null && beneficiary.getVbltSexuallyActive() == 0
						&& serviceType == ServiceType.CLINIC) {
					// Retirar Promoção e Provisão de Preservativos e Aconselhamento e testagem em
					// saúde
					services = services.stream().filter(s -> s.getId() != 1 && s.getId() != 9)
							.collect(Collectors.toList());
				}

				if (beneficiary.getVbltIsStudent() == 0) {

					if (serviceType == ServiceType.COMMUNITY) {
						// Retirar AVANTE ESTUDANTE
						services = services.stream().filter(s -> s.getId() != 45 && s.getId() != 48 && s.getId() != 51)
								.collect(Collectors.toList());

					}
				} else {

					if (serviceType == ServiceType.COMMUNITY) {
						// Retirar AVANTE RAPARIGA
						services = services.stream().filter(s -> s.getId() != 44 && s.getId() != 47 && s.getId() != 50)
								.collect(Collectors.toList());

					}
				}
			} else if (serviceType == ServiceType.COMMUNITY) {
				// Retirar AVANTE RAPARIGA e AVANTE ESTUDANTE
				services = services.stream().filter(s -> s.getId() != 44 && s.getId() != 45 && s.getId() != 47
						&& s.getId() != 48 && s.getId() != 50 && s.getId() != 51).collect(Collectors.toList());
			}

			return new ResponseEntity<>(services, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Services> save(@RequestBody Services service) {
		if (service == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			service.setDateCreated(new Date());
			service.setCoreService(0);
			service.setHidden(0);
			Integer serviceId = (Integer) this.service.Save(service);
			Services savedService = this.service.find(Services.class, serviceId);

			return new ResponseEntity<>(savedService, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = "application/json", produces = "application/json")
	public ResponseEntity<Services> update(@RequestBody Services service) {
		if (service == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			service.setDateUpdated(new Date());
			Services updatedService = this.service.update(service);
			return new ResponseEntity<>(updatedService, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
}
