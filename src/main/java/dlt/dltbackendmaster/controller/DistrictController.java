package dlt.dltbackendmaster.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.login.AccountLockedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.service.DAOService;

/**
 * API REST Controller Class for District entity
 * @author gerzeliosaide
 *
 */
@RestController
@RequestMapping("/api")
public class DistrictController {
    
    private static final Integer ACTIVE = 1;
    
    private final DAOService service;
    
    @Autowired
    public DistrictController(DAOService service) {
    	this.service = service;
    }
		
	@GetMapping("/districts")
	public ResponseEntity<List<District>> getDistricts(){
		try {			
			List<District> districts = service.getAll(District.class);
			return new ResponseEntity<>(districts, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

    @PostMapping(path = "/districts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<District> addDistrict(@RequestBody District district) {
        if (district == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            district.setDateCreated(new Date());
            Integer districtId = (Integer) this.service.Save(district);
            District savedDistrict = this.service.find(District.class, districtId);
            
            return new ResponseEntity<>(savedDistrict, HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping(path = "/districts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<District> editDistrict(@RequestBody District district) {
        if (district == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            district.setDateUpdated(new Date());
            District prov = (District) service.update(district);
            return ResponseEntity.ok().body(prov);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @GetMapping("/getdistricts")
    public ResponseEntity<List<District>> getAllDistricts(){
        try {           
            List<District> districts = service.GetAllEntityByNamedQuery("District.findByStatus", ACTIVE);
            return new ResponseEntity<>(districts, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

	@GetMapping("/districtsIds")
	public ResponseEntity<List<District>> getIds(@RequestParam("id") List<String> districtsID){
		try {
			List<Integer> distIds = districtsID.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<District> districts = service.GetAllEntityByNamedQuery("District.findByIds", distIds);
			return ResponseEntity.ok(districts);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping(path = "/districts/get-districts", produces = "application/json")
	public ResponseEntity<List<District>>  getNames() throws AccountLockedException {
		try {
			List<District> user = service.GetAllEntityByNamedQuery("District.findDistricts");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
