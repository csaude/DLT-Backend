package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.ServiceAgeband;
import dlt.dltbackendmaster.service.DAOService;

/**
 * API REST Controller Class for ServiceAgeband entity
 * 
 * @author Francisco da Conceicao Alberto Macuacua
 *
 */
@RestController
@RequestMapping("/api/service-agebands")
public class ServiceAgebandController {

	private static final Integer ACTIVE = 1;

	private final DAOService service;

	@Autowired
	public ServiceAgebandController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<ServiceAgeband>> getServiceAgebands() {
		try {
			List<ServiceAgeband> districts = service.getAll(ServiceAgeband.class);
			return new ResponseEntity<>(districts, HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();

			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
