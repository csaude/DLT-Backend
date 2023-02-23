package dlt.dltbackendmaster.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import dlt.dltbackendmaster.domain.NumericSequence;
import dlt.dltbackendmaster.domain.VulnerabilityHistory;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;

@RestController
@RequestMapping("/api/beneficiaries")
public class BeneficiaryController
{
    private final DAOService service;

    private SequenceGenerator generator;

    public BeneficiaryController(DAOService service) {
        this.service = service;
        this.generator = new SequenceGenerator(service);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Beneficiaries>> get(
    		@RequestParam(name = "profile") Integer profile, 
    		@RequestParam(name = "level") String level, 
    		@RequestParam(name = "params",required = false) @Nullable Integer[] params,
    		@RequestParam(name = "pageIndex") int pageIndex,
    		@RequestParam(name = "pageSize") int pageSize
    		) {

        try {
            List<Beneficiaries> beneficiaries = null;

            if (level.equals("CENTRAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findAll", pageIndex, pageSize);
            } else if (level.equals("PROVINCIAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByProvinces", pageIndex, pageSize, Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByDistricts", pageIndex, pageSize, Arrays.asList(params));
            } else {
                beneficiaries = service.GetAllPagedEntityByNamedQuery("Beneficiary.findByLocalities", pageIndex, pageSize, Arrays.asList(params));
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
            beneficiary.setDateCreated(new Date());
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
            
            saveVulnerabilityHistory(createdBeneficiary);
            
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
            beneficiary.setDateUpdated(new Date());
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

			saveVulnerabilityHistory(updatedBeneficiary);

			return new ResponseEntity<>(updatedBeneficiary, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void saveVulnerabilityHistory(Beneficiaries beneficiary) {
		if(beneficiary.getVbltIsDeficient()!=null) {
			createVulnerability("IS_DEFICIENT", String.valueOf(beneficiary.getVbltIsDeficient()), beneficiary);
			if(beneficiary.getVbltIsDeficient()==1 && beneficiary.getVbltDeficiencyType()!=null) {
				createVulnerability("DEFICIENCY_TYPE", beneficiary.getVbltDeficiencyType(), beneficiary);
			}
		}			
		if(beneficiary.getVbltChildren() !=null) {
			createVulnerability("CHILDREN", String.valueOf(beneficiary.getVbltChildren()), beneficiary);
		}
		if(beneficiary.getVbltSexWorker() !=null) {
			createVulnerability("SEX_WORKER", String.valueOf(beneficiary.getVbltSexWorker()), beneficiary);
		}
		if(beneficiary.getVbltIsEmployed()!= null) {
			createVulnerability("IS_EMPLOYED", beneficiary.getVbltIsEmployed(), beneficiary);
		}
		if( beneficiary.getVbltLivesWith()!=null) {
			createVulnerability("LIVES_WITH", beneficiary.getVbltLivesWith(), beneficiary);
		}
		if(beneficiary.getVbltSchoolName()!=null) {
			createVulnerability("SCHOOL_NAME", beneficiary.getVbltSchoolName(), beneficiary);
		}
		if(beneficiary.getVbltSexploitationTime()!=null) {
			createVulnerability("SEXPLOITATION_TIME", beneficiary.getVbltSexploitationTime(), beneficiary);
		}
		if( beneficiary.getVbltTestedHiv()!=null) {
			createVulnerability("TESTED_HIV", beneficiary.getVbltTestedHiv(), beneficiary);
		}
		if(beneficiary.getVbltVbgTime()!=null) {
			createVulnerability("VBG_TIME", beneficiary.getVbltVbgTime(), beneficiary);
		}
		if(beneficiary.getVbltVbgType()!=null) {
			createVulnerability("VBG_TYPE", beneficiary.getVbltVbgType(), beneficiary);
		}
		if(beneficiary.getVbltAlcoholDrugsUse()!=null) {
			createVulnerability("ALCOHOL_DRUGS_USE", String.valueOf(beneficiary.getVbltAlcoholDrugsUse()), beneficiary);
		}
		if(beneficiary.getVbltAlcoholDrugsUse()!=null) {
			createVulnerability("HOUSE_SUSTAINER", String.valueOf(beneficiary.getVbltHouseSustainer()), beneficiary);
		}
		if(beneficiary.getVbltIsMigrant()!=null) {
			createVulnerability("IS_MIGRANT", String.valueOf(beneficiary.getVbltIsMigrant()), beneficiary);
		}
		if(beneficiary.getVbltIsOrphan()!=null) {
			createVulnerability("IS_ORPHAN", String.valueOf(beneficiary.getVbltIsOrphan()), beneficiary);
		}
		if(beneficiary.getVbltIsStudent()!=null) {
			createVulnerability("IS_STUDENT", String.valueOf(beneficiary.getVbltIsStudent()), beneficiary);
		}
		if(beneficiary.getVbltMarriedBefore()!=null) {
			createVulnerability("MARRIED_BEFORE", String.valueOf(beneficiary.getVbltMarriedBefore()), beneficiary);
		}
		if(beneficiary.getVbltMultiplePartners()!=null) {
			createVulnerability("MULTIPLE_PARTNERS", String.valueOf(beneficiary.getVbltMultiplePartners()), beneficiary);
		}
		if(beneficiary.getVbltPregnantBefore()!=null) {
			createVulnerability("PREGNANT_BEFORE", String.valueOf(beneficiary.getVbltPregnantBefore()), beneficiary);
		}
		if(beneficiary.getVbltPregnantOrBreastfeeding()!=null) {
			createVulnerability("PREGNANT_OR_BREASTFEEDING", String.valueOf(beneficiary.getVbltPregnantOrBreastfeeding()),
				beneficiary);
		}
		if(beneficiary.getVbltSchoolGrade()!=null) {
			createVulnerability("SCHOOL_GRADE", String.valueOf(beneficiary.getVbltSchoolGrade()), beneficiary);
		}
		if(beneficiary.getVbltSexualExploitation()!=null) {
			createVulnerability("SEXUAL_EXPLOITATION", String.valueOf(beneficiary.getVbltSexualExploitation()),
				beneficiary);
		}
		if(beneficiary.getVbltSexuallyActive()!=null) {
			createVulnerability("SEXUALLY_ACTIVE", String.valueOf(beneficiary.getVbltSexuallyActive()), beneficiary);
		}
		if(beneficiary.getVbltStiHistory()!=null) {
			createVulnerability("STI_HISTORY", String.valueOf(beneficiary.getVbltStiHistory()), beneficiary);}
		
		if(beneficiary.getVbltTraffickingVictim()!=null) {
			createVulnerability("TRAFFICKING_VICTIM", String.valueOf(beneficiary.getVbltTraffickingVictim()), beneficiary);
		}
		if(beneficiary.getVbltVbgVictim()!=null) {
			createVulnerability("VBG_VICTIM", String.valueOf(beneficiary.getVbltVbgVictim()), beneficiary);
		}
		if(beneficiary.getVbltHouseSustainer()!=null) {
			createVulnerability("HOUSE_SUSTAINER", String.valueOf(beneficiary.getVbltHouseSustainer()), beneficiary);
		}
	}

	private void createVulnerability(String vulnerabilityKey, String vulnerabilityValue, Beneficiaries beneficiary) {
		VulnerabilityHistory history = new VulnerabilityHistory();

		history.setBeneficiaries(beneficiary);
		history.setVulnerability(vulnerabilityKey);
		history.setValue(vulnerabilityValue);
		history.setStatus(beneficiary.getStatus());
		history.setCreatedBy(String.valueOf(beneficiary.getCreatedBy()));
		history.setDateCreated(new Date());

		try {
		
			List<VulnerabilityHistory> vulnerabilityHistoryOrdered = service.GetAllEntityByNamedQuery(
					"VulnerabilityHistory.findByBeneficiaryAndVulnerability", beneficiary.getId(), vulnerabilityKey);

			if (vulnerabilityValue != null  && (vulnerabilityHistoryOrdered.isEmpty() || !vulnerabilityValue.equals(vulnerabilityHistoryOrdered.get(0).getValue()))) 
			{
				service.Save(history);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping(path = "/count", produces = "application/json")
	public ResponseEntity<Long> countByBeneficiary(@RequestParam(name = "level") String level, @RequestParam(name = "params",
            required = false) @Nullable Integer[] params) {
		try {
			Long beneficiariesTotal;
			
			if (level.equals("CENTRAL")) {
				beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountAll");
            } else if (level.equals("PROVINCIAL")) {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByProvinces", Arrays.asList(params));
            } else if (level.equals("DISTRITAL")) {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistricts", Arrays.asList(params));
            } else {
            	beneficiariesTotal = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByLocalities", Arrays.asList(params));
            }
		
			return new ResponseEntity<>(beneficiariesTotal, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
