package dlt.dltbackendmaster.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

import dlt.dltbackendmaster.domain.AlphanumericSequence;
import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.BeneficiariesInterventionsId;
import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesId;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryInterventionSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceServicesSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;

@RestController
@RequestMapping("/sync")
public class SyncController {

	private final DAOService service;
	private SequenceGenerator generator;

	@Autowired
	public SyncController(DAOService service) {
		this.service = service;
		this.generator = new SequenceGenerator(service);
	}

	@SuppressWarnings("unchecked")
	@GetMapping(produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required = false) @Nullable String lastPulledAt,
			@RequestParam(name = "username") String username) throws ParseException {

		Date validatedDate;
		List<Users> usersCreated;
		List<Users> usersUpdated;
		List<Integer> listDeleted;

		List<Locality> localityCreated;
		List<Locality> localityUpdated;

		List<Province> provincesCreated = new ArrayList<Province>();
		List<District> districtsCreated = new ArrayList<District>(); 

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

		List<ReferencesServices> referenceServicesCreated;
		List<ReferencesServices> referenceServicesUpdated;
		
		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);

		if (lastPulledAt == null || lastPulledAt.equals("null")) {

			// provinces
			if (user.getProvinces().size() > 0) {
				provincesCreated = new ArrayList<Province>(user.getProvinces()); 
			} else {
				provincesCreated = service.GetAllEntityByNamedQuery("Province.findAll");
			}

			// districts
			if (user.getDistricts().size() > 0) {
				districtsCreated = new ArrayList<District>(user.getDistricts());
			} else if (user.getProvinces().size() > 0) {
				List<Integer> provIds = user.getProvinces().stream().map(Province::getId).collect(Collectors.toList());

				districtsCreated = service.GetAllEntityByNamedQuery("District.findByProvinces", provIds);
			} else {
				districtsCreated = service.GetAllEntityByNamedQuery("District.findAll");
			}

			// localities
			if (user.getLocalities().size() > 0) {
				localityCreated = new ArrayList<Locality>(user.getLocalities());
			} else if (user.getDistricts().size() > 0) {
				List<Integer> distIds = user.getDistricts().stream().map(District::getId).collect(Collectors.toList());
				localityCreated = service.GetAllEntityByNamedQuery("Locality.findByDistricts", distIds);
			} else {
				localityCreated = service.GetAllEntityByNamedQuery("Locality.findAll");
			}
			localityUpdated = new ArrayList<Locality>();

			// users
			usersCreated = service.GetAllEntityByNamedQuery("Users.findAll");
			usersUpdated = new ArrayList<Users>();
			listDeleted = new ArrayList<Integer>();

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

			referenceServicesCreated = service.GetAllEntityByNamedQuery("ReferencesServices.findAll");
			referenceServicesUpdated = new ArrayList<ReferencesServices>();

		} else {
			Long t = Long.valueOf(lastPulledAt);
			// validatedDate = new Date(t);

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(t);
			calendar.set(Calendar.MILLISECOND, 0); // remove milliseconds to avoid wrongly adding entities to created
													// list
			validatedDate = calendar.getTime();

			/*
			 * TODO: include provinces and district created after validatedDate
			 */
			
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
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findByDateCreated", validatedDate);
			profilesUpdated = service.GetAllEntityByNamedQuery("Profiles.findByDateUpdated", validatedDate);
			// us
			usCreated = service.GetAllEntityByNamedQuery("Us.findByDateCreated", validatedDate);
			usUpdated = service.GetAllEntityByNamedQuery("Us.findByDateUpdated", validatedDate);

			beneficiariesCreated = service.GetAllEntityByNamedQuery("Beneficiary.findByDateCreated", validatedDate);
			beneficiariesUpdated = service.GetAllEntityByNamedQuery("Beneficiary.findByDateUpdated", validatedDate);

			beneficiariesInterventionsCreated = service
					.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByDateCreated", validatedDate);
			beneficiariesInterventionsUpdated = service
					.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByDateUpdated", validatedDate);

			neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateCreated", validatedDate);
			neighborhoodUpdated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateUpdated", validatedDate);

			servicesCreated = service.GetAllEntityByNamedQuery("Service.findByDateCreated", validatedDate);
			servicesUpdated = service.GetAllEntityByNamedQuery("Service.findByDateUpdated", validatedDate);

			subServicesCreated = service.GetAllEntityByNamedQuery("SubService.findByDateCreated", validatedDate);
			subServicesUpdated = service.GetAllEntityByNamedQuery("SubService.findByDateUpdated", validatedDate);

			referencesCreated = service.GetAllEntityByNamedQuery("References.findByDateCreated", validatedDate);
			referencesUpdated = service.GetAllEntityByNamedQuery("References.findByDateUpdated", validatedDate);

			referenceServicesCreated = service.GetAllEntityByNamedQuery("ReferencesServices.findByDateCreated",
					validatedDate);
			referenceServicesUpdated = service.GetAllEntityByNamedQuery("ReferencesServices.findByDateUpdated",
					validatedDate);
		}

		try {
			SyncObject<Users> usersSO = new SyncObject<Users>(usersCreated, usersUpdated, listDeleted);
			SyncObject<Province> provinceSO = new SyncObject<Province>(provincesCreated, new ArrayList<Province>(),
					listDeleted);
			SyncObject<District> districtSO = new SyncObject<District>(districtsCreated, new ArrayList<District>(),
					listDeleted);
			SyncObject<Locality> localitySO = new SyncObject<Locality>(localityCreated, localityUpdated, listDeleted);
			SyncObject<Partners> partnersSO = new SyncObject<Partners>(partnersCreated, partnersUpdated, listDeleted);
			SyncObject<Profiles> profilesSO = new SyncObject<Profiles>(profilesCreated, profilesUpdated, listDeleted);
			SyncObject<Us> usSO = new SyncObject<Us>(usCreated, usUpdated, listDeleted);
			SyncObject<Beneficiaries> beneficiarySO = new SyncObject<Beneficiaries>(beneficiariesCreated,
					beneficiariesUpdated, listDeleted);
			SyncObject<BeneficiariesInterventions> beneficiaryInterventionSO = new SyncObject<BeneficiariesInterventions>(
					beneficiariesInterventionsCreated, beneficiariesInterventionsUpdated, listDeleted);
			SyncObject<Neighborhood> neighborhoodSO = new SyncObject<Neighborhood>(neighborhoodsCreated,
					neighborhoodUpdated, listDeleted);
			SyncObject<Services> serviceSO = new SyncObject<Services>(servicesCreated, servicesUpdated, listDeleted);
			SyncObject<SubServices> subServiceSO = new SyncObject<SubServices>(subServicesCreated, subServicesUpdated,
					listDeleted);
			SyncObject<References> referencesSO = new SyncObject<References>(referencesCreated, referencesUpdated,
					listDeleted);
			SyncObject<ReferencesServices> referencesServicesSO = new SyncObject<ReferencesServices>(
					referenceServicesCreated, referenceServicesUpdated, listDeleted);

			// String object = SyncSerializer.createUsersSyncObject(usersCreated,
			// usersUpdated, new ArrayList<Integer>());

			String object = SyncSerializer.createSyncObject(usersSO, provinceSO, districtSO, localitySO, profilesSO, partnersSO, usSO,
					beneficiarySO, beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO, referencesSO,
					referencesServicesSO, lastPulledAt);
			// System.out.println("PULLING " + object);

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/prefix", produces = "application/json")
	public ResponseEntity getPrefix(@RequestParam(name = "username") String username) throws ParseException {

		try {
			AlphanumericSequence seq = generator.getNextAlphanumericSequence(username);

			return new ResponseEntity<>(seq, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>("Processing Error!", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes, @RequestParam(name = "username") String username)
			throws ParseException, JsonMappingException, JsonProcessingException {

		String lastPulledAt = SyncSerializer.readLastPulledAt(changes);

		// System.out.println("PUSHING " + changes);
		HashMap<String, Integer> referenceIds = new HashMap<String, Integer>();

		Users user = (Users) service.GetAllEntityByNamedQuery("Users.findByUsername", username).get(0);

		ObjectMapper mapper = new ObjectMapper();
		SyncObject<UsersSyncModel> users;
		SyncObject<BeneficiarySyncModel> beneficiaries;
		SyncObject<BeneficiaryInterventionSyncModel> interventions;
		SyncObject<ReferenceSyncModel> references;
		SyncObject<ReferenceServicesSyncModel> referencesServices;

		try {
			users = SyncSerializer.readUsersSyncObject(changes);
			beneficiaries = SyncSerializer.readBeneficiariesSyncObject(changes);
			interventions = SyncSerializer.readInterventionsSyncObject(changes);
			references = SyncSerializer.readReferencesSyncObject(changes);
			referencesServices = SyncSerializer.readReferenceServicesSyncObject(changes);

			// created entities
			if (users != null && users.getCreated().size() > 0) {

				List<UsersSyncModel> createdList = mapper.convertValue(users.getCreated(),
						new TypeReference<List<UsersSyncModel>>() {
						});

				for (UsersSyncModel created : createdList) {

					if (created.getOnline_id() == null) {
						Users newUser = new Users(created, lastPulledAt);
						newUser.setCreatedBy(user.getId());
						Integer savedId = (Integer) service.Save(newUser);
					}
				}
			}
			if (beneficiaries != null && beneficiaries.getCreated().size() > 0) {
				List<BeneficiarySyncModel> createdList = mapper.convertValue(beneficiaries.getCreated(),
						new TypeReference<List<BeneficiarySyncModel>>() {
						});

				for (BeneficiarySyncModel created : createdList) {
					if (created.getOnline_id() == null) {
						Beneficiaries beneficiary = new Beneficiaries(created, lastPulledAt);
						beneficiary.setCreatedBy(user.getId());
						service.Save(beneficiary);
					}
				}
			}
			if (interventions != null && interventions.getCreated().size() > 0) {
				List<BeneficiaryInterventionSyncModel> createdList = mapper.convertValue(interventions.getCreated(),
						new TypeReference<List<BeneficiaryInterventionSyncModel>>() {
						});

				for (BeneficiaryInterventionSyncModel created : createdList) {
					if (created.getOnline_id() == null) {
						BeneficiariesInterventions intervention = new BeneficiariesInterventions(created, lastPulledAt);
						intervention.setCreatedBy(user.getId());
						service.Save(intervention);
						
						service.registerServiceCompletionStatus(intervention);
						
					}
				}
			}

			if (references != null && references.getCreated().size() > 0) {
				List<ReferenceSyncModel> createdList = mapper.convertValue(references.getCreated(),
						new TypeReference<List<ReferenceSyncModel>>() {
						});

				for (ReferenceSyncModel created : createdList) {
					if (created.getOnline_id() == null) {
						References reference = new References(created, lastPulledAt);
						reference.setUserCreated(user.getId()+"");
						service.Save(reference);
						referenceIds.put(reference.getOfflineId(), reference.getId());
					}
				}
			}
			if (referencesServices != null && referencesServices.getCreated().size() > 0) {
				List<ReferenceServicesSyncModel> createdList = mapper.convertValue(referencesServices.getCreated(),
						new TypeReference<List<ReferenceServicesSyncModel>>() {
						});

				for (ReferenceServicesSyncModel created : createdList) {
					if (created.getOnline_id() == null) {
						Integer refId = referenceIds.get(created.getReference_id());
						if (refId != null) {
							created.setReference_id("" + refId); // change mobileId with the server id
							ReferencesServices reference = new ReferencesServices(created, lastPulledAt);
							reference.setCreatedBy(user.getId());
							service.Save(reference);
						}
					}
				}
			}

			// updated entities
			if (users != null && users.getUpdated().size() > 0) {
				List<UsersSyncModel> updatedList = mapper.convertValue(users.getUpdated(),
						new TypeReference<List<UsersSyncModel>>() {
						});

				for (UsersSyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
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
			if (beneficiaries != null && beneficiaries.getUpdated().size() > 0) {
				List<BeneficiarySyncModel> updatedList = mapper.convertValue(users.getUpdated(),
						new TypeReference<List<BeneficiarySyncModel>>() {
						});

				for (BeneficiarySyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
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
			if (interventions != null && interventions.getUpdated().size() > 0) {
				List<BeneficiaryInterventionSyncModel> updatedList = mapper.convertValue(interventions.getUpdated(),
						new TypeReference<List<BeneficiaryInterventionSyncModel>>() {
						});

				for (BeneficiaryInterventionSyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
						BeneficiariesInterventions intervention = new BeneficiariesInterventions(updated, lastPulledAt);
						intervention.setCreatedBy(user.getId());
						service.Save(intervention);

					} else {
						String[] keys = updated.getOnline_id().split(",");
						BeneficiariesInterventionsId bId = new BeneficiariesInterventionsId(Integer.valueOf(keys[0]),
								Integer.valueOf(keys[1]), LocalDate.parse(keys[2]));
						BeneficiariesInterventions intervention = service.find(BeneficiariesInterventions.class, bId);
						if (intervention != null) {
							intervention.setUpdatedBy(String.valueOf(user.getId()));
							intervention.update(updated, lastPulledAt);
							service.update(intervention);
						}
					}
				}
			}
			if (references != null && references.getUpdated().size() > 0) {
				List<ReferenceSyncModel> updatedList = mapper.convertValue(references.getUpdated(),
						new TypeReference<List<ReferenceSyncModel>>() {
						});

				for (ReferenceSyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
						References reference = new References(updated, lastPulledAt);
						reference.setUserCreated(user.getId()+"");
						service.Save(reference);

					} else {
						References reference = service.find(References.class, updated.getOnline_id());
						reference.setUpdatedBy(user.getId());
						reference.update(updated, lastPulledAt);
						service.update(reference);
					}
				}
			}
			if (referencesServices != null && referencesServices.getUpdated().size() > 0) {
				List<ReferenceServicesSyncModel> updatedList = mapper.convertValue(referencesServices.getUpdated(),
						new TypeReference<List<ReferenceServicesSyncModel>>() {
						});

				for (ReferenceServicesSyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
						ReferencesServices referenceServices = new ReferencesServices(updated, lastPulledAt);
						referenceServices.setCreatedBy(user.getId());
						service.Save(referenceServices);

					} else {
						String[] keys = updated.getOnline_id().split(",");
						ReferencesServicesId bId = new ReferencesServicesId(Integer.valueOf(keys[0]),
								Integer.valueOf(keys[1]));
						ReferencesServices referenceServices = service.find(ReferencesServices.class, bId);
						if (referenceServices != null) {
							referenceServices.setUpdatedBy(user.getId());
							referenceServices.update(updated, lastPulledAt);
							service.update(referenceServices);
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
