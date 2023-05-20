package dlt.dltbackendmaster.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import dlt.dltbackendmaster.domain.UsersBeneficiariesCustomSync;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryInterventionSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceServicesSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;
import dlt.dltbackendmaster.service.UsersBeneficiariesCustomSyncService;
import dlt.dltbackendmaster.service.VulnerabilityHistoryService;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

@RestController
@RequestMapping("/sync")
public class SyncController {

	private final DAOService service;
	private SequenceGenerator generator;
	private String level;
	private Integer[] params;

	@Autowired
	private UsersBeneficiariesCustomSyncService usersBeneficiariesCustomSyncService;

	@Autowired
	private VulnerabilityHistoryService vulnerabilityHistoryService;

	@Autowired
	public SyncController(DAOService service) {
		this.service = service;
		this.generator = new SequenceGenerator(service);
	}

	@SuppressWarnings("rawtypes")
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

		List<BeneficiariesInterventions> beneficiariesInterventionsCreated = new ArrayList<>();
		List<BeneficiariesInterventions> beneficiariesInterventionsUpdated;

		List<Neighborhood> neighborhoodsCreated;
		List<Neighborhood> neighborhoodsUpdated;

		List<Services> servicesCreated;
		List<Services> servicesUpdated;

		List<SubServices> subServicesCreated;
		List<SubServices> subServicesUpdated;

		List<References> referencesCreated;
		List<References> referencesUpdated;

		List<ReferencesServices> referenceServicesCreated = new ArrayList<>();
		List<ReferencesServices> referenceServicesUpdated = new ArrayList<>();

		List<Beneficiaries> beneficiariesCreatedCustomized = new ArrayList<>();
		List<Beneficiaries> beneficiariesUpdatedCustomized = new ArrayList<>();

		List<BeneficiariesInterventions> beneficiariesInterventionsCreatedCustomized = new ArrayList<>();
		List<BeneficiariesInterventions> beneficiariesInterventionsUpdatedCustomized = new ArrayList<>();

		List<References> referencesCreatedCustomized = new ArrayList<>();
		List<References> referencesUpdatedCustomized = new ArrayList<>();

		List<ReferencesServices> referenceServicesCreatedCustomized = new ArrayList<>();
		List<ReferencesServices> referenceServicesUpdatedCustomized = new ArrayList<>();

		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);
		defineLevelAndParms(user);

		Set<Integer> localitiesIds = new TreeSet<>();
		List<Integer> userLocalitiesIds = user.getLocalities().stream().map(Locality::getId)
				.collect(Collectors.toList());
		localitiesIds.addAll(userLocalitiesIds);

		/** Sync Customized, previous fetched by NUI **/
		List<UsersBeneficiariesCustomSync> userBeneficiariesSync = usersBeneficiariesCustomSyncService
				.getUserBeneficiariesSync(user.getId());

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

			// Partners
			if (level.equals("CENTRAL")) {
				partnersCreated = service.GetAllEntityByNamedQuery("Partners.findAll");
			} else if (level.equals("PROVINCIAL")) {
				partnersCreated = service.GetAllEntityByNamedQuery("Partners.findBySyncProvinces",
						Arrays.asList(params));
			} else if (level.equals("DISTRITAL")) {
				partnersCreated = service.GetAllEntityByNamedQuery("Partners.findBySyncDistricts",
						Arrays.asList(params));
			} else {
				partnersCreated = service.GetAllEntityByNamedQuery("Partners.findBySyncLocalities",
						Arrays.asList(params));
			}
			partnersUpdated = new ArrayList<Partners>();

			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findAll");
			profilesUpdated = new ArrayList<Profiles>();

			// Beneficiary
			beneficiariesCreated = service.GetAllEntityByNamedQuery(
					"Beneficiary.findByReferenceNotifyToOrBeneficiaryCreatedBy", user.getId());
			beneficiariesUpdated = new ArrayList<Beneficiaries>();

			// References
			referencesCreated = service.GetAllEntityByNamedQuery("References.findByReferenceNotifyToOrReferredBy",
					user.getId());
			referencesUpdated = new ArrayList<References>();

			List<Beneficiaries> refBeneficiaries = referencesCreated.stream().map(References::getBeneficiaries)
					.collect(Collectors.toList());
			beneficiariesCreated.addAll(refBeneficiaries);

			for (Beneficiaries beneficiary : beneficiariesCreated) {
				if (beneficiary.getLocality() != null) {
					localitiesIds.add(beneficiary.getLocality().getId());
				}
				beneficiariesInterventionsCreated.addAll(beneficiary.getBeneficiariesInterventionses());
			}

			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findByIds",
					Arrays.asList(localitiesIds.toArray()));
			localityUpdated = new ArrayList<Locality>();

			// users
			usersCreated = service.GetAllEntityByNamedNativeQuery("Users.findByLocalities",
					Arrays.asList(localitiesIds.toArray()));
			usersUpdated = new ArrayList<Users>();
			listDeleted = new ArrayList<Integer>();

			// Us
			usUpdated = new ArrayList<Us>();
			usCreated = service.GetAllEntityByNamedQuery("Us.findBySyncLocalities",
					Arrays.asList(localitiesIds.toArray()));

			beneficiariesInterventionsUpdated = new ArrayList<BeneficiariesInterventions>();

			// Neighborhood
			neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findBySyncLocalities",
					Arrays.asList(localitiesIds.toArray()));
			neighborhoodsUpdated = new ArrayList<Neighborhood>();

			servicesCreated = service.GetAllEntityByNamedQuery("Service.findAll");
			servicesUpdated = new ArrayList<Services>();

			subServicesCreated = service.GetAllEntityByNamedQuery("SubService.findAll");
			subServicesUpdated = new ArrayList<SubServices>();

			// ReferencesServices
			referenceServicesCreated = service
					.GetAllEntityByNamedQuery("ReferencesServices.findByReferenceNotifyToOrReferredBy", user.getId());
			referenceServicesUpdated = new ArrayList<ReferencesServices>();

			beneficiariesCreatedCustomized = userBeneficiariesSync.stream()
					.map(UsersBeneficiariesCustomSync::getBeneficiary).collect(Collectors.toList());

			List<Integer> beneficiariesIds = beneficiariesCreatedCustomized.stream().map(Beneficiaries::getId)
					.collect(Collectors.toList());

			if (beneficiariesCreatedCustomized.size() > 0) {

				beneficiariesInterventionsCreatedCustomized = service
						.GetAllEntityByNamedQuery("BeneficiaryIntervention.findByBeneficiariesIds", beneficiariesIds);

				referencesCreatedCustomized = service.GetAllEntityByNamedQuery("References.findByBeneficiariesIds",
						beneficiariesIds);

				for (References ref : referencesCreatedCustomized) {
					List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
					refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference",
							ref.getId());
					referenceServicesCreatedCustomized.addAll(refServicesByRef);
				}
			}
			
			beneficiariesCreated.addAll(beneficiariesCreatedCustomized);
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

			beneficiariesCreated = service.GetAllEntityByNamedQuery(
					"Beneficiary.findByReferenceNotifyToOrBeneficiaryCreatedByAndDateCreated", user.getId(),
					validatedDate);
			beneficiariesUpdated = service.GetAllEntityByNamedQuery(
					"Beneficiary.findByReferenceNotifyToOrBeneficiaryCreatedByAndDateUpdated", user.getId(),
					validatedDate);

			for (Beneficiaries beneficiary : beneficiariesCreated) {
				localitiesIds.add(beneficiary.getLocality().getId());
			}
			for (Beneficiaries beneficiary : beneficiariesUpdated) {
				localitiesIds.add(beneficiary.getLocality().getId());
			}

			// localities
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findByIdsAndDateCreated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			localityUpdated = service.GetAllEntityByNamedQuery("Locality.findByIdsAndDateUpdated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);

			usersCreated = service.GetAllEntityByNamedQuery("Users.findByDateCreated", validatedDate);
			usersUpdated = service.GetAllEntityByNamedQuery("Users.findByDateUpdated", validatedDate);

			// users
			usersCreated = service.GetAllEntityByNamedNativeQuery("Users.findByLocalitiesAndDateCreated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			usersUpdated = service.GetAllEntityByNamedNativeQuery("Users.findByLocalitiesAndDateUpdated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			listDeleted = new ArrayList<Integer>();
			// partners
			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findByLocalitiesAndDateCreated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			partnersUpdated = service.GetAllEntityByNamedQuery("Partners.findByLocalitiesAndDateCreated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			// profiles
			profilesCreated = service.GetAllEntityByNamedQuery("Profiles.findByDateCreated", validatedDate);
			profilesUpdated = service.GetAllEntityByNamedQuery("Profiles.findByDateUpdated", validatedDate);
			// us
			usCreated = service.GetAllEntityByNamedQuery("Us.findByLocalitiesAndDateCreated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);
			usUpdated = service.GetAllEntityByNamedQuery("Us.findByLocalitiesAndDateUpdated",
					Arrays.asList(localitiesIds.toArray()), validatedDate);

			beneficiariesInterventionsCreated = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findByReferenceNotifyToOrBeneficiaryCreatedByAndDateCreated", user.getId(),
					validatedDate);
			beneficiariesInterventionsUpdated = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findByReferenceNotifyToOrBeneficiaryCreatedByAndDateUpdated", user.getId(),
					validatedDate);

			neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateCreated", validatedDate);
			neighborhoodsUpdated = service.GetAllEntityByNamedQuery("Neighborhood.findByDateUpdated", validatedDate);

			servicesCreated = service.GetAllEntityByNamedQuery("Service.findByDateCreated", validatedDate);
			servicesUpdated = service.GetAllEntityByNamedQuery("Service.findByDateUpdated", validatedDate);

			subServicesCreated = service.GetAllEntityByNamedQuery("SubService.findByDateCreated", validatedDate);
			subServicesUpdated = service.GetAllEntityByNamedQuery("SubService.findByDateUpdated", validatedDate);

			referencesCreated = service.GetAllEntityByNamedQuery(
					"References.findByReferenceNotifyToOrReferredByAndDateCreated", user.getId(), validatedDate);
			referencesUpdated = service.GetAllEntityByNamedQuery(
					"References.findByReferenceNotifyToOrReferredByAndDateUpdated", user.getId(), validatedDate);

			for (References ref : referencesCreated) {
				List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
				refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference", ref.getId());
				referenceServicesCreated.addAll(refServicesByRef);
			}

			for (References ref : referencesUpdated) {
				List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
				refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference", ref.getId());
				referenceServicesUpdated.addAll(refServicesByRef);
			}

			beneficiariesCreatedCustomized = userBeneficiariesSync.stream()
					.map(UsersBeneficiariesCustomSync::getBeneficiary).collect(Collectors.toList());

			List<Integer> beneficiariesCreateIds = beneficiariesCreatedCustomized.stream().map(Beneficiaries::getId)
					.collect(Collectors.toList());

			if (beneficiariesCreatedCustomized.size() > 0) {

				beneficiariesUpdatedCustomized = service.GetAllEntityByNamedQuery("Beneficiary.findByIdsAndDateUpdated",
						beneficiariesCreateIds, validatedDate);

				beneficiariesInterventionsCreatedCustomized = service.GetAllEntityByNamedQuery(
						"BeneficiaryIntervention.findByBeneficiariesIdsAndDateCreated", beneficiariesCreateIds,
						validatedDate);
				beneficiariesInterventionsUpdatedCustomized = service.GetAllEntityByNamedQuery(
						"BeneficiaryIntervention.findByBeneficiariesIdsAndDateUpdated", beneficiariesCreateIds,
						validatedDate);

				referencesCreatedCustomized = service.GetAllEntityByNamedQuery(
						"References.findByBeneficiariesIdsAndDateCreated", beneficiariesCreateIds, validatedDate);
				referencesUpdatedCustomized = service.GetAllEntityByNamedQuery(
						"References.findByBeneficiariesIdsAndDateUpdated", beneficiariesCreateIds, validatedDate);

				for (References ref : referencesCreatedCustomized) {
					List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
					refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference",
							ref.getId());
					referenceServicesCreatedCustomized.addAll(refServicesByRef);
				}

				for (References ref : referencesUpdatedCustomized) {
					List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
					refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference",
							ref.getId());
					referenceServicesUpdatedCustomized.addAll(refServicesByRef);
				}
			}
			
			beneficiariesUpdated.addAll(beneficiariesUpdatedCustomized);
		}

		List<Beneficiaries> uniqueBeneficiariesCreated = beneficiariesCreated.stream().distinct()
				.collect(Collectors.toList());
		List<Beneficiaries> uniqueBeneficiariesUpdated = beneficiariesUpdated.stream().distinct()
				.collect(Collectors.toList());

		referencesCreated.addAll(referencesCreatedCustomized);
		referencesUpdated.addAll(referencesUpdatedCustomized);

		List<References> uniqueReferencesCreated = referencesCreated.stream().distinct().collect(Collectors.toList());
		List<References> uniqueReferencesUpdated = referencesUpdated.stream().distinct().collect(Collectors.toList());

		referenceServicesCreated.addAll(referenceServicesCreatedCustomized);
		referenceServicesUpdated.addAll(referenceServicesUpdatedCustomized);

		List<ReferencesServices> uniqueReferenceServicesCreated = referenceServicesCreated.stream().distinct()
				.collect(Collectors.toList());
		List<ReferencesServices> uniqueReferenceServicesUpdated = referenceServicesUpdated.stream().distinct()
				.collect(Collectors.toList());

		beneficiariesInterventionsCreated.addAll(beneficiariesInterventionsCreatedCustomized);
		beneficiariesInterventionsUpdated.addAll(beneficiariesInterventionsUpdatedCustomized);

		List<BeneficiariesInterventions> uniqueBeneficiariesIntervetnionsCreated = beneficiariesInterventionsCreated
				.stream().distinct().collect(Collectors.toList());
		List<BeneficiariesInterventions> uniqueBeneficiariesIntervetnionsUpdated = beneficiariesInterventionsUpdated
				.stream().distinct().collect(Collectors.toList());

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
			SyncObject<Beneficiaries> beneficiarySO = new SyncObject<Beneficiaries>(uniqueBeneficiariesCreated,
					uniqueBeneficiariesUpdated, listDeleted);
			SyncObject<BeneficiariesInterventions> beneficiaryInterventionSO = new SyncObject<BeneficiariesInterventions>(
					uniqueBeneficiariesIntervetnionsCreated, uniqueBeneficiariesIntervetnionsUpdated, listDeleted);
			SyncObject<Neighborhood> neighborhoodSO = new SyncObject<Neighborhood>(neighborhoodsCreated,
					neighborhoodsUpdated, listDeleted);
			SyncObject<Services> serviceSO = new SyncObject<Services>(servicesCreated, servicesUpdated, listDeleted);
			SyncObject<SubServices> subServiceSO = new SyncObject<SubServices>(subServicesCreated, subServicesUpdated,
					listDeleted);
			SyncObject<References> referencesSO = new SyncObject<References>(uniqueReferencesCreated,
					uniqueReferencesUpdated, listDeleted);
			SyncObject<ReferencesServices> referencesServicesSO = new SyncObject<ReferencesServices>(
					uniqueReferenceServicesCreated, uniqueReferenceServicesUpdated, listDeleted);

			String object = SyncSerializer.createSyncObject(usersSO, provinceSO, districtSO, localitySO, profilesSO,
					partnersSO, usSO, beneficiarySO, beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO,
					referencesSO, referencesServicesSO, lastPulledAt);

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping(consumes = "application/json")
	public ResponseEntity post(@RequestBody String changes, @RequestParam(name = "username") String username)
			throws ParseException, JsonMappingException, JsonProcessingException {

		String lastPulledAt = SyncSerializer.readLastPulledAt(changes);

		// System.out.println("PUSHING " + changes);
		Map<String, Integer> beneficiariesIds = new HashMap<>();

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
						service.Save(newUser);
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
						setPartner(created, beneficiary);
						beneficiary.setCreatedBy(user.getId());
						beneficiary.setDateUpdated(new Date());
						Integer beneficiaryId = (Integer) service.Save(beneficiary);
						vulnerabilityHistoryService.saveVulnerabilityHistory(beneficiary);
						beneficiariesIds.put(created.getId(), beneficiaryId);
					} else {
						beneficiariesIds.put(created.getId(), created.getOnline_id());
					}
				}
			}
			if (beneficiaries != null && beneficiaries.getUpdated().size() > 0) {
				List<BeneficiarySyncModel> updatedList = mapper.convertValue(beneficiaries.getUpdated(),
						new TypeReference<List<BeneficiarySyncModel>>() {
						});

				for (BeneficiarySyncModel updated : updatedList) {

					if (updated.getOnline_id() == null) {
						Beneficiaries beneficiary = new Beneficiaries(updated, lastPulledAt);
						beneficiary.setCreatedBy(user.getId());
						setPartner(updated, beneficiary);
						Integer beneficiaryId = (Integer) service.Save(beneficiary);
						vulnerabilityHistoryService.saveVulnerabilityHistory(beneficiary);
						beneficiariesIds.put(updated.getId(), beneficiaryId);

					} else {
						Beneficiaries beneficiary = service.find(Beneficiaries.class, updated.getOnline_id());
						beneficiary.setUpdatedBy(user.getId());
						setPartner(updated, beneficiary);
						beneficiary.update(updated, lastPulledAt);
						service.update(beneficiary);
						vulnerabilityHistoryService.saveVulnerabilityHistory(beneficiary);
						beneficiariesIds.put(updated.getId(), updated.getOnline_id());
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
						if (created.getBeneficiary_id() == 0) {
							Integer beneficiaryId = beneficiariesIds.get(created.getBeneficiary_offline_id());
							if (beneficiaryId == null) {
								Beneficiaries beneficiary = service.GetUniqueEntityByNamedQuery(
										"Beneficiary.findByOfflineId", created.getBeneficiary_offline_id());
								beneficiaryId = beneficiary.getId();
							}
							intervention.setBeneficiaries(new Beneficiaries(beneficiaryId));
							intervention.getId().setBeneficiaryId(beneficiaryId);
							intervention.setDateUpdated(new Date());
							intervention.setUpdatedBy(user.getId().toString());
						}
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

				List<ReferenceServicesSyncModel> refServices = mapper.convertValue(referencesServices.getCreated(),
						new TypeReference<List<ReferenceServicesSyncModel>>() {
						});

				for (ReferenceSyncModel created : createdList) {
					if (created.getOnline_id() == null) {
						References reference = new References(created, lastPulledAt);
						if (created.getBeneficiary_id() == 0) {
							Integer beneficiaryId = beneficiariesIds.get(created.getBeneficiary_offline_id());
							if (beneficiaryId == null) {
								Beneficiaries beneficiary = service.GetUniqueEntityByNamedQuery(
										"Beneficiary.findByOfflineId", created.getBeneficiary_offline_id());
								beneficiaryId = beneficiary.getId();
							}
							reference.setBeneficiaries(new Beneficiaries(beneficiaryId));
						}
						reference.setUserCreated(user.getId() + "");
						service.Save(reference);

						for (ReferenceServicesSyncModel refService : refServices) {
							if (refService.getReference_id().equals(reference.getOfflineId())) {
								refService.setReference_id("" + reference.getId());
								ReferencesServices referenceService = new ReferencesServices(refService, lastPulledAt);
								reference.getReferencesServiceses().add(referenceService);
								referenceService.setCreatedBy(user.getId());
								referenceService.setDateUpdated(new Date());
								List<BeneficiariesInterventions> beneficiaryInterventions = service
										.GetAllEntityByNamedQuery("BeneficiaryIntervention.findAllByBeneficiaryAndDate",
												reference.getDate().toInstant().atZone(ZoneId.systemDefault())
														.toLocalDate(),
												reference.getBeneficiaries().getId());

								Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(
										beneficiaryInterventions, referenceService.getServices().getId());
								referenceService.setStatus(referenceServiceStatus);
								service.Save(referenceService);
							}
						}
						List<BeneficiariesInterventions> beneficiaryInterventions = service.GetAllEntityByNamedQuery(
								"BeneficiaryIntervention.findAllByBeneficiaryAndDate",
								reference.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
								reference.getBeneficiaries().getId());
						Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference,
								beneficiaryInterventions);
						reference.setStatus(referenceStatus);
						reference.setDateUpdated(new Date());

						service.update(reference);
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
						service.Save(newUser);

					} else {
						Users updatedUser = service.find(Users.class, updated.getOnline_id());
						updatedUser.update(updated, lastPulledAt);
						updatedUser.setUpdatedBy(user.getId());
						service.update(updatedUser);

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
						if (updated.getBeneficiary_id() == 0) {
							Integer beneficiaryId = beneficiariesIds.get(updated.getBeneficiary_offline_id());
							if (beneficiaryId == null) {
								Beneficiaries beneficiary = service.GetUniqueEntityByNamedQuery(
										"Beneficiary.findByOfflineId", updated.getBeneficiary_offline_id());
								beneficiaryId = beneficiary.getId();
							}
							intervention.setBeneficiaries(new Beneficiaries(beneficiaryId));
							intervention.getId().setBeneficiaryId(beneficiaryId);
							intervention.setDateUpdated(new Date());
							intervention.setUpdatedBy(user.getId().toString());
						}
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
						if (updated.getBeneficiary_id() == 0) {
							Integer beneficiaryId = beneficiariesIds.get(updated.getBeneficiary_offline_id());
							if (beneficiaryId == null) {
								Beneficiaries beneficiary = service.GetUniqueEntityByNamedQuery(
										"Beneficiary.findByOfflineId", updated.getBeneficiary_offline_id());
								beneficiaryId = beneficiary.getId();
							}
							reference.setBeneficiaries(new Beneficiaries(beneficiaryId));
							reference.setDateUpdated(new Date());
							reference.setUpdatedBy(user.getId());
						}
						reference.setUserCreated(user.getId() + "");
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

	private void setPartner(BeneficiarySyncModel syncModel, Beneficiaries beneficiary) {
		String partner_id = syncModel.getPartner_id();
		if (partner_id != null) {
			Beneficiaries benPartner = StringUtils.isNumeric(partner_id)
					? service.GetUniqueEntityByNamedQuery("Beneficiary.findById", Integer.valueOf(partner_id))
					: service.GetUniqueEntityByNamedQuery("Beneficiary.findByOfflineId", partner_id);
			if (benPartner != null) {
				beneficiary.setPartnerId(benPartner.getId());
			}
		}
	}

	private void defineLevelAndParms(Users user) {
		if (user.getProvinces().isEmpty()) {
			this.level = "CENTRAL";
			this.params = new Integer[0];
		} else if (user.getDistricts().isEmpty()) {
			this.level = "PROVINCIAL";
			this.params = user.getProvinces().stream().map(Province::getId).collect(Collectors.toList()).stream()
					.toArray(Integer[]::new);
		} else if (user.getLocalities().isEmpty()) {
			this.level = "DISTRITAL";
			this.params = user.getDistricts().stream().map(District::getId).collect(Collectors.toList()).stream()
					.toArray(Integer[]::new);
		} else {
			this.level = "LOCAL";
			this.params = user.getLocalities().stream().map(Locality::getId).collect(Collectors.toList()).stream()
					.toArray(Integer[]::new);
		}
	}
}
