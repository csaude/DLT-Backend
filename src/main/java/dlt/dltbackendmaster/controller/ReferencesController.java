package dlt.dltbackendmaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/references")
public class ReferencesController
{
    private final DAOService service;

    @Autowired
    public ReferencesController(DAOService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<References>> getall() {

        try {
            List<References> references = service.getAll(References.class);
            return new ResponseEntity<>(references, HttpStatus.OK);
        } catch (Exception e) {
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
   
}
