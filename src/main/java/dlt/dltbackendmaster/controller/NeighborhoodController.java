package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/api/neighborhood")
public class NeighborhoodController {

	@Autowired
	private final DAOService service;
	
	public NeighborhoodController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<Neighborhood>> getAll() {
		try {
			List<Neighborhood> neigborhoods = service.getAll(Neighborhood.class);

			return new ResponseEntity<>(neigborhoods, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Neighborhood> save(@RequestBody Neighborhood neighborhood) {
        if (neighborhood == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            neighborhood.setDateCreated(new Date());
            Integer neighborhoodId = (Integer) this.service.Save(neighborhood);
            Neighborhood savedNeighborhood = this.service.find(Neighborhood.class, neighborhoodId);

            return new ResponseEntity<>(savedNeighborhood, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Neighborhood> update(@RequestBody Neighborhood neighborhood) {
        if (neighborhood == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            neighborhood.setDateUpdated(new Date());
            Neighborhood updatedNeighborhood = this.service.update(neighborhood);
            return new ResponseEntity<>(updatedNeighborhood, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
