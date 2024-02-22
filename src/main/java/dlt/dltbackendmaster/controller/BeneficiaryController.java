package dlt.dltbackendmaster.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.NumericSequence;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;
import dlt.dltbackendmaster.service.VulnerabilityHistoryService;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    private SequenceGenerator generator;
    
    @Autowired
    private VulnerabilityHistoryService vulnerabilityHistoryService;

    public BeneficiaryController(DAOService service) {
        this.service = service;
        this.generator = new SequenceGenerator(service);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> get(
    		@RequestParam(name = "userId") Integer userId, 
    		@RequestParam(name = "level") String level, 
    		@RequestParam(name = "params",required = false) @Nullable Integer[] params,
    		@RequestParam(name = "pageIndex") int pageIndex,
    		@RequestParam(name = "pageSize") int pageSize,
    		@RequestParam(name = "searchNui", required = false) @Nullable String searchNui,
    		@RequestParam(name = "searchName", required = false) @Nullable String searchName,
    		@RequestParam(name = "searchUserCreator", required = false) @Nullable Integer searchUserCreator,
    		@RequestParam(name = "searchDistrict", required = false) @Nullable Integer searchDistrict
    		) {

        try {
            List<Beneficiaries> beneficiaries = null;
            
            searchName = searchName.replaceAll(" ", "%");

            if (level.equals("CENTRAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAll", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict);
            } else if (level.equals("PROVINCIAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByProvinces", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByDistricts", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByLocalitiesOrReferenceNotifyTo", pageIndex, pageSize, searchNui, searchName,  searchUserCreator, searchDistrict, Arrays.asList(params), userId);
            }

            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	@GetMapping(path = "/any", produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> getAny(
    		@RequestParam(name = "userId") Integer userId, 
    		@RequestParam(name = "level") String level, 
    		@RequestParam(name = "params",required = false) @Nullable Integer[] params,
    		@RequestParam(name = "pageIndex") int pageIndex,
    		@RequestParam(name = "pageSize") int pageSize,
    		@RequestParam(name = "searchNui", required = false) @Nullable String searchNui,
    		@RequestParam(name = "searchName", required = false) @Nullable String searchName,
    		@RequestParam(name = "searchUserCreator", required = false) @Nullable Integer searchUserCreator,
    		@RequestParam(name = "searchDistrict", required = false) @Nullable Integer searchDistrict
    		) {

        try {
            List<Beneficiaries> beneficiaries = null;
            
            searchName = searchName.replaceAll(" ", "%");

            if (level.equals("CENTRAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAny", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict);
            } else if (level.equals("PROVINCIAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAnyByProvinces", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAnyByDistricts", pageIndex, pageSize, searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAnyByLocalitiesOrReferenceNotifyTo", pageIndex, pageSize, searchNui, searchName,  searchUserCreator, searchDistrict, Arrays.asList(params), userId);
            }

            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<Beneficiaries> get(@PathVariable Integer id) {

        if (id == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            Beneficiaries beneficiary = service.find(Beneficiaries.class, id);
            return new ResponseEntity<>(beneficiary, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Beneficiaries> save(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            }
            NumericSequence nextNUI = generator.getNextNumericSequence();
            beneficiary.setNui(nextNUI.getSequence());
            Integer beneficiaryId = (Integer) service.Save(beneficiary);
            Beneficiaries createdBeneficiary = service.find(Beneficiaries.class, beneficiaryId);
            
            vulnerabilityHistoryService.saveVulnerabilityHistory(createdBeneficiary);
            
            return new ResponseEntity<>(createdBeneficiary, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Beneficiaries> update(@RequestBody Beneficiaries beneficiary) {

        if (beneficiary == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            String partnerNUI = beneficiary.getPartnerNUI();

            if (partnerNUI != null && partnerNUI != "") {
                Beneficiaries partner = service.GetUniqueEntityByNamedQuery("Beneficiary.findByNui", partnerNUI);
                // TODO: Throw exception for NUI not found
                if (partner != null)
                    beneficiary.setPartnerId(partner.getId());
            } else {
				beneficiary.setPartnerId(null);
			}
			Beneficiaries updatedBeneficiary = service.update(beneficiary);

			vulnerabilityHistoryService.saveVulnerabilityHistory(updatedBeneficiary);

			return new ResponseEntity<>(updatedBeneficiary, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/countByFilters", produces = "application/json")
	public ResponseEntity<Long> countByBeneficiary(
			@RequestParam(name = "userId") Integer userId, 
			@RequestParam(name = "level") String level, 
			@RequestParam(name = "params", required = false) @Nullable Integer[] params,
			@RequestParam(name = "searchNui", required = false) @Nullable String searchNui,
			@RequestParam(name = "searchName", required = false) @Nullable String searchName,
    		@RequestParam(name = "searchUserCreator", required = false) @Nullable Integer searchUserCreator,
    		@RequestParam(name = "searchDistrict", required = false) @Nullable Integer searchDistrict
			) {
		try {
			Long beneficiariesTotal;
            
            searchName = searchName.replaceAll(" ", "%");
			
			if (level.equals("CENTRAL")) {
				beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountAll", searchNui, searchName, searchUserCreator, searchDistrict);
            } else if (level.equals("PROVINCIAL")) {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByProvinces", searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistricts", searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params));
            } else {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByLocalitiesOrReferenceNotifyTo", searchNui, searchName, searchUserCreator, searchDistrict, Arrays.asList(params), userId);
            }
		
			return new ResponseEntity<>(beneficiariesTotal, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/findByNui", produces = "application/json")
	public ResponseEntity<List<Beneficiaries>> getBeneficiariesByNui(@RequestParam(name = "nui") String nui,@RequestParam(name = "userId") Integer userId) {
		try {
			List<Users> users = service.GetAllEntityByNamedQuery("Users.findById",userId);
			List <Integer> localitiesIds = new ArrayList<>();
			Set<Locality> localities = users.get(0).getLocalities();
			localities.forEach(locality->{
				localitiesIds.add(locality.getId());
				});
			
			List<Beneficiaries> beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.getBeneficiariesByNui",nui, localitiesIds);
		
			return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "ids", produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> getByIds(
    		@RequestParam(name = "pageIndex") int pageIndex,
    		@RequestParam(name = "pageSize") int pageSize,
    		@RequestParam(name = "params") Integer[] params
    		) {

        if (params.length < 1) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        try {
            List<Beneficiaries> beneficiaries = service.GetByParamsPagedEntityByNamedQuery("Beneficiary.findByIds", pageIndex, pageSize, Arrays.asList(params));
    
            return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@GetMapping(path = "/findByPartnerId", produces = "application/json")
	public ResponseEntity<List<Beneficiaries>> getBeneficiariesByPartnerId(@RequestParam(name = "partnerId") int partnerId) {
		try {			
			List<Beneficiaries> beneficiaries = service.GetAllEntityByNamedQuery("Beneficiary.getBeneficiariesByPartnerId",partnerId);
		
			return new ResponseEntity<List<Beneficiaries>>(beneficiaries, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
