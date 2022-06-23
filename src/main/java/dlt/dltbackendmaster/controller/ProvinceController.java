package dlt.dltbackendmaster.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.service.DAOService;

/**
 * API REST Controller Class for Province entity
 * @author derciobucuane
 *
 */
@RestController
@RequestMapping("/api")
public class ProvinceController {
	
    private final DAOService service;
    
    @Autowired
    public ProvinceController(DAOService service) {
    	this.service = service;
    }
	
	@PostMapping("/provinces")
	public ResponseEntity<Province> addProvince(@RequestBody Province province) {
		Province prov = (Province) service.Save(province); //FIXME: revew this cast
		return ResponseEntity.ok().body(prov);
	}
	
	@GetMapping("/getprovinces")
	public ResponseEntity<List<Province>> getProvinces(){
		List<Province> provinces = service.getAll(Province.class);
		return ResponseEntity.ok(provinces);
		
		//return service.getAll(Province.class);
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
}
