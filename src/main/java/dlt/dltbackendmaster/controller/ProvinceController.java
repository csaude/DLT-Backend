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
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.service.DAOService;

/**
 * API REST Controller Class for Province entity
 * @author derciobucuane
 *
 */
@RestController
@RequestMapping("/api")
public class ProvinceController {
    
    private static final Integer ACTIVE = 1;
	
    private final DAOService service;
    
    @Autowired
    public ProvinceController(DAOService service) {
    	this.service = service;
    }

    @GetMapping(path = "/provinces", produces = "application/json")
    public ResponseEntity<List<Province>> provinces(){
        try {
                List<Province> provinces = service.getAll(Province.class);
                return ResponseEntity.ok(provinces); 
            } catch (Exception e) {
                 return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }       
    }
    
	@PostMapping(path = "/provinces", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Province> addProvince(@RequestBody Province province) {
	    if (province == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
	    try {
	        province.setCreateDate(new Date());
	        Province prov = (Province) service.Save(province);
	        return ResponseEntity.ok().body(prov);
	    }catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
    @PutMapping(path = "/provinces", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Province> editProvince(@RequestBody Province province) {
        if (province == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            province.setUpdateDate(new Date());
            Province prov = (Province) service.update(province);
            return ResponseEntity.ok().body(prov);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	@GetMapping("/getprovinces")
	public ResponseEntity<List<Province>> getProvinces(){
	    try {
    		List<Province> provinces = service.GetAllEntityByNamedQuery("Province.findByStatus", ACTIVE);
    		return ResponseEntity.ok(provinces);
    		
	    } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}
	
	@GetMapping("/provdisctricts")
	public ResponseEntity<List<District>> getDistricts(@RequestParam("provinces") List<String> provinces){
		try {
			List<Integer> provIds = provinces.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<District> districts = service.GetAllEntityByNamedQuery("District.findByProvinces", provIds);
			return ResponseEntity.ok(districts);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/distlocalities")
	public ResponseEntity<List<Locality>> getLocatities(@RequestParam("districts") List<String> districts){
		try {
			List<Integer> distIds = districts.stream().map(Integer::parseInt).collect(Collectors.toList());
			
			List<Locality> localities = service.GetAllEntityByNamedQuery("Locality.findByDistricts", distIds);
			return ResponseEntity.ok(localities);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
    @GetMapping("/localneighborhoods")
    public ResponseEntity<List<Neighborhood>> getNeighborhoods(@RequestParam("localities") List<String> localities){
        try {
            List<Integer> localIds = localities.stream().map(Integer::parseInt).collect(Collectors.toList());
            
            List<Neighborhood> neighborhoods = service.GetAllEntityByNamedQuery("Neighborhood.findByLocalities", localIds);
            return ResponseEntity.ok(neighborhoods);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
    @GetMapping("/localus")
    public ResponseEntity<List<Us>> getUss(@RequestParam("localities") List<String> localities){
        try {
            List<Integer> localIds = localities.stream().map(Integer::parseInt).collect(Collectors.toList());
            
            List<Us> us = service.GetAllEntityByNamedQuery("Us.findByLocalities", localIds);
            return ResponseEntity.ok(us);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }
    
	@GetMapping(path = "/provinces/get-provinces", produces = "application/json")
	public ResponseEntity<List<Province>>  getNames() throws AccountLockedException {
		try {
			List<Province> user = service.GetAllEntityByNamedQuery("Province.findProvinces");

			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
