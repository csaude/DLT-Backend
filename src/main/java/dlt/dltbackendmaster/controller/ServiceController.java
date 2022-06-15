package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.ServiceType;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/services")
public class ServiceController
{
    private final DAOService service;

    public ServiceController(DAOService service) {
        super();
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Services>> getAll() {

        try {
            List<Services> services = service.getAll(Services.class);
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
            List<Services> services = service.GetAllEntityByNamedQuery("Service.findByServiceType", String.valueOf(serviceType.ordinal()+1));
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
