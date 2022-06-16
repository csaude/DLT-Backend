package dlt.dltbackendmaster.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.BeneficiariesInterventionsId;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryInterventionSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;

@RestController
@RequestMapping("/sync")
public class SyncController {

	private final DAOService service;
	
	@Autowired
    public SyncController(DAOService service) {
        this.service = service;
    }
	
	@GetMapping(produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required=false) @Nullable String lastPulledAt,
								@RequestParam(name = "username") String username) throws ParseException {

		Date validatedDate;
		List<Users> usersCreated;
		List<Users> usersUpdated;
		List<Integer> listDeleted;
		
		List<Locality> localityCreated;
		List<Locality> localityUpdated;
		List<Partners> partnersCreated;
		List<Partners> partnersUpdated;
		List<Profiles> profilesCreated;
		List<Profiles> profilesUpdated;
		List<Us> usCreated;
		List<Us> usUpdated;
		
		List<Beneficiaries> beneficiariesCreated;
		List<Beneficiaries> beneficiariesUpdated;
        
        List<BeneficiariesInterventions> beneficiariesInterventionsCreated;
        List<BeneficiariesInterventions> beneficiariesInterventionsUpdated;
        
        List<Neighborhood> neighborhoodsCreated;
        List<Neighborhood> neighborhoodUpdated;
        
        List<Services> servicesCreated;
        List<Services> servicesUpdated;
        
        List<SubServices> subServicesCreated;
        List<SubServices> subServicesUpdated;
        
        List<References> referencesCreated;
        List<References> referencesUpdated;
		
		if(lastPulledAt == null || lastPulledAt.equals("null") ) {

			//users
			usersCreated = service.GetAllEntityByNamedQuery("Users.findAll");
			usersUpdated = new ArrayList<Users>();
			listDeleted = new ArrayList<Integer>();
			
			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findAll");
			localityUpdated = new ArrayList<Locality>();
			
			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findAll");
			partnersUpdated = new ArrayList<Partners>();
			
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findAll");
			profilesUpdated = new ArrayList<Profiles>();
			
			usCreated = service.GetAllEntityByNamedQuery("Us.findAll");
			usUpdated = new ArrayList<Us>();
			
			beneficiariesCreated = service.GetAllEntityByNamedQuery("Beneficiary.findAll");
			beneficiariesUpdated = new ArrayList<Beneficiaries>();
            
            beneficiariesInterventionsCreated = service.GetAllEntityByNamedQuery("BeneficiaryIntervention.findAll");
            beneficiariesInterventionsUpdated = new ArrayList<BeneficiariesInterventions>();
            
            neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findAll");
            neighborhoodUpdated = new ArrayList<Neighborhood>();
            
            servicesCreated = service.GetAllEntityByNamedQuery("Service.findAll");
            servicesUpdated = new ArrayList<Services>();
            
            subServicesCreated = service.GetAllEntityByNamedQuery("SubService.findAll");
            subServicesUpdated = new ArrayList<SubServices>();
            
            referencesCreated = service.GetAllEntityByNamedQuery("References.findAll");
            referencesUpdated = new ArrayList<References>();
			
		}else {
			Long t = Long.valueOf(lastPulledAt);
			//validatedDate = new Date(t);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(t);
			calendar.set(Calendar.MILLISECOND, 0); // remove milliseconds to avoid wrongly adding entities to created list
			validatedDate = calendar.getTime();
			
			
			// users
			usersCreated = service.GetAllEntityByNamedQuery("Users.findByDateCreated", validatedDate);
			usersUpdated = service.GetAllEntityByNamedQuery("Users.findByDateUpdated", validatedDate);
			listDeleted = new ArrayList<Integer>();

			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findByDateCreated", validatedDate);
			localityUpdated = service.GetAllEntityByNamedQuery("Locality.findByDateUpdated", validatedDate);
			// partners			
			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findByDateCreated", validatedDate);
			partnersUpdated = service.GetAllEntityByNamedQuery("Partners.findByDateUpdated", validatedDate);
			// profiles
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findByDateCreated",validatedDate);
			profilesUpdated = service.GetAllEntityByNamedQuery("Profiles.findByDateUpdated",validatedDate);
			// us			
			usCreated = service.GetAllEntityByNamedQuery("Us.findByDateCreated",validatedDate);
			usUpdated = service.GetAllEntityByNamedQuery("Us.findByDateUpdated",validatedDate);
			
			beneficiariesCreated = service.GetAllEntityByNamedQuery("Beneficiary.findByDateCreated", validatedDate);
            beneficiariesUpdated = service.GetAllEntityByNamedQuery("Beneficiary.findByDateUpdated", validatedDate);
            
            beneficiariesInterventionsCreated = service.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByDateCreated", validatedDate);
            beneficiariesInterventionsUpdated = service.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByDateUpdated", validatedDate);
            
            neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateCreated", validatedDate);
            neighborhoodUpdated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateUpdated", validatedDate);
            
            servicesCreated = service.GetAllEntityByNamedQuery("Service.findByDateCreated", validatedDate);
            servicesUpdated = service.GetAllEntityByNamedQuery("Service.findByDateUpdated", validatedDate);
            
            subServicesCreated = service.GetAllEntityByNamedQuery("SubService.findByDateCreated", validatedDate); 
            subServicesUpdated = service.GetAllEntityByNamedQuery("SubService.findByDateUpdated", validatedDate);
            
            referencesCreated = service.GetAllEntityByNamedQuery("References.findByDateCreated", validatedDate);
            referencesUpdated = service.GetAllEntityByNamedQuery("References.findByDateUpdated", validatedDate);
		}
		
		try {
			SyncObject<Users> usersSO = new SyncObject<Users>(usersCreated, usersUpdated, listDeleted);
			SyncObject<Locality> localitySO = new SyncObject<Locality>(localityCreated, localityUpdated, listDeleted);
			SyncObject<Partners> partnersSO = new SyncObject<Partners>(partnersCreated, partnersUpdated, listDeleted);
			SyncObject<Profiles> profilesSO = new SyncObject<Profiles>(profilesCreated, profilesUpdated, listDeleted);
			SyncObject<Us> usSO = new SyncObject<Us>(usCreated, usUpdated, listDeleted);
			SyncObject<Beneficiaries> beneficiarySO = new SyncObject<Beneficiaries>(beneficiariesCreated, beneficiariesUpdated, listDeleted);
            SyncObject<BeneficiariesInterventions> beneficiaryInterventionSO = new SyncObject<BeneficiariesInterventions>(beneficiariesInterventionsCreated, beneficiariesInterventionsUpdated, listDeleted);
            SyncObject<Neighborhood> neighborhoodSO = new SyncObject<Neighborhood>(neighborhoodsCreated, neighborhoodUpdated, listDeleted);
            SyncObject<Services> serviceSO = new SyncObject<Services>(servicesCreated, servicesUpdated, listDeleted);
            SyncObject<SubServices> subServiceSO = new SyncObject<SubServices>(subServicesCreated, subServicesUpdated, listDeleted);
            SyncObject<References> referencesSO = new SyncObject<References>(referencesCreated, referencesUpdated, listDeleted);

			//String object = SyncSerializer.createUsersSyncObject(usersCreated, usersUpdated, new ArrayList<Integer>());

			String object = SyncSerializer.createSyncObject(usersSO, localitySO, profilesSO, partnersSO, usSO, beneficiarySO,  beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO, referencesSO, lastPulledAt);
			//System.out.println("PULLING " + object);

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes,
								@RequestParam(name = "username") String username) throws ParseException, JsonMappingException, JsonProcessingException {
		
		String lastPulledAt = SyncSerializer.readLastPulledAt(changes);
		//System.out.println("PUSHING " + changes);
		
		Users user = (Users) service.GetAllEntityByNamedQuery("Users.findByUsername", username).get(0);
		
		ObjectMapper mapper = new ObjectMapper();
		SyncObject<UsersSyncModel> users;
		SyncObject<BeneficiarySyncModel> beneficiaries;
		SyncObject<BeneficiaryInterventionSyncModel> interventions;

		try {
			users = SyncSerializer.readUsersSyncObject(changes);
			beneficiaries = SyncSerializer.readBeneficiariesSyncObject(changes);
			interventions = SyncSerializer.readInterventionsSyncObject(changes);
			
			// created entities
			if(users != null && users.getCreated().size() > 0) {
				
				List<UsersSyncModel> createdList = mapper.convertValue(users.getCreated(), new TypeReference<List<UsersSyncModel>>() {});
				
				for (UsersSyncModel created : createdList) {

					if(created.getOnline_id() == null) {
						Users newUser = new Users(created, lastPulledAt);
						newUser.setCreatedBy(user.getId());
						Integer savedId = (Integer) service.Save(newUser);
					}
				}
			}
			if(beneficiaries!=null && beneficiaries.getCreated().size()>0) {
			    List<BeneficiarySyncModel> createdList = mapper.convertValue(beneficiaries.getCreated(), new TypeReference<List<BeneficiarySyncModel>>() {});
			    
			    for (BeneficiarySyncModel created : createdList) {
                    if(created.getOnline_id() == null) {
                    	Beneficiaries beneficiary = new Beneficiaries(created, lastPulledAt);
                    	beneficiary.setCreatedBy(user.getId());
                        service.Save(beneficiary);
                    }
                }
			}
            if(interventions!=null && interventions.getCreated().size()>0) {
                List<BeneficiaryInterventionSyncModel> createdList = mapper.convertValue(interventions.getCreated(), new TypeReference<List<BeneficiaryInterventionSyncModel>>() {});
                
                for (BeneficiaryInterventionSyncModel created : createdList) {
                    if(created.getOnline_id() == null) {
                    	BeneficiariesInterventions intervention = new BeneficiariesInterventions(created, lastPulledAt);
                        intervention.setCreatedBy(user.getId());
                        service.Save(intervention);
                    } 
                }
            }

			// updated entities
			if(users != null && users.getUpdated().size() > 0) {
				List<UsersSyncModel> updatedList = mapper.convertValue(users.getUpdated(), new TypeReference<List<UsersSyncModel>>() {});

				for (UsersSyncModel updated : updatedList) {
					
					if(updated.getOnline_id() == null) {
						Users newUser = new Users(updated, lastPulledAt);
                        newUser.setCreatedBy(user.getId());
						Integer savedId = (Integer) service.Save(newUser);
						
					} else {
						Users updateu = service.find(Users.class, updated.getOnline_id());
						updateu.update(updated, lastPulledAt);
						updateu.setUpdatedBy(user.getId());
						service.update(updateu);
						
					} 
				}
			}
            if(beneficiaries != null && beneficiaries.getUpdated().size() > 0) {
                List<BeneficiarySyncModel> updatedList = mapper.convertValue(users.getUpdated(), new TypeReference<List<BeneficiarySyncModel>>() {});

                for (BeneficiarySyncModel updated : updatedList) {
                    
                    if(updated.getOnline_id() == null) {
                    	Beneficiaries beneficiary = new Beneficiaries(updated, lastPulledAt);
                        beneficiary.setCreatedBy(user.getId());
                        service.Save(beneficiary);
                        
                    } else {
                    	Beneficiaries beneficiary = service.find(Beneficiaries.class, updated.getOnline_id());
                        beneficiary.setUpdatedBy(user.getId());
                        beneficiary.update(updated, lastPulledAt);
                        service.update(beneficiary);
                    } 
                }
            }
            if(interventions != null && interventions.getUpdated().size() > 0) {
                List<BeneficiaryInterventionSyncModel> updatedList = mapper.convertValue(interventions.getUpdated(), new TypeReference<List<BeneficiaryInterventionSyncModel>>() {});

                for (BeneficiaryInterventionSyncModel updated : updatedList) {
                    
                    if(updated.getOnline_id() == null) {
                    	BeneficiariesInterventions intervention = new BeneficiariesInterventions(updated, lastPulledAt);
                        intervention.setCreatedBy(user.getId());
                        service.Save(intervention);
                        
                    } else {
                    	String[] keys = updated.getOnline_id().split(",");
                    	BeneficiariesInterventionsId bId = new BeneficiariesInterventionsId(Integer.valueOf(keys[0]), Integer.valueOf(keys[1]), LocalDate.parse(keys[2]));
                        BeneficiariesInterventions intervention = service.find(BeneficiariesInterventions.class, bId);
                        if(intervention != null) {
                        	intervention.setUpdatedBy(String.valueOf(user.getId()));
                        	intervention.update(updated, lastPulledAt);
                        	service.update(intervention);
                        }
                    } 
                }
            }
            
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>("Json Processing Error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
        return new ResponseEntity<>(HttpStatus.OK);
	}
}
