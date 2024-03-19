package dlt.dltbackendmaster.controller;

import static dlt.dltbackendmaster.util.ProfilesConstants.COUNSELOR;
import static dlt.dltbackendmaster.util.ProfilesConstants.MANAGER;
import static dlt.dltbackendmaster.util.ProfilesConstants.MENTOR;
import static dlt.dltbackendmaster.util.ProfilesConstants.NURSE;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.CountIntervention;
import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesCancel;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.ReferencesServicesId;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.ReferenceService;
import dlt.dltbackendmaster.util.ReferencesStatus;
import dlt.dltbackendmaster.util.ServiceCompletionRules;

@RestController
@RequestMapping("/api/references")
public class ReferencesController {
	private final DAOService service;
	private final ReferenceService referenceService;

	@Autowired
	public ReferencesController(DAOService service, ReferenceService referenceService) {
		this.service = service;
		this.referenceService = referenceService;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<References>> getall() {
		try {
			List<References> references = service.GetAllEntityByNamedQuery("References.findAll");
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
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

	@GetMapping(path = "/byUser/{userId}", produces = "application/json")
	public ResponseEntity<List<References>> getAllByUser(@PathVariable Integer userId,
			@RequestParam(name = "pageIndex") int pageIndex, @RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "searchNui", required = false) @Nullable String searchNui,
			@RequestParam(name = "searchUserCreator", required = false) @Nullable Integer searchUserCreator,
    		@RequestParam(name = "searchDistrict", required = false) @Nullable Integer searchDistrict
			) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			List<References> references = null;
			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId()) && user.getUs().size() > 0) {
				List<Integer> ussId = user.getUs().stream().map(Us::getId).collect(Collectors.toList());
				List<Users> users = service.GetAllEntityByNamedQuery("Users.findByUsId", ussId);
				List<Integer> usersIds = users.stream().map(Users::getId).collect(Collectors.toList());
				List<String> strUsersIds = usersIds.stream().map(String::valueOf).collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findAllByUserPermission", pageIndex,
						pageSize, searchNui,searchUserCreator, searchDistrict, strUsersIds, ussId, usersIds);
			} else if (user.getLocalities().size() > 0) {
				List<Integer> localitiesId = user.getLocalities().stream().map(Locality::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findByLocalities", pageIndex, pageSize,
						searchNui,searchUserCreator, searchDistrict, localitiesId);

			} else if (user.getDistricts().size() > 0) {

				List<Integer> districtsId = user.getDistricts().stream().map(District::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findByDistricts", pageIndex, pageSize,
						searchNui,searchUserCreator, searchDistrict, districtsId);

			} else if (user.getProvinces().size() > 0) {

				List<Integer> provincesId = user.getProvinces().stream().map(Province::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findByProvinces", pageIndex, pageSize,
						searchNui,searchUserCreator, searchDistrict, provincesId);

			} else {
				references = service.GetAllPagedEntityByNamedQuery("References.findAll", pageIndex, pageSize,
						searchNui,searchUserCreator, searchDistrict);
			}

			return new ResponseEntity<>(references, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/pendingByUser/{userId}", produces = "application/json")
	public ResponseEntity<List<References>> getAllPendingByUser(@PathVariable Integer userId,
			@RequestParam(name = "pageIndex") int pageIndex, @RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "searchStartDate", required = false) @Nullable Long searchStartDate,
			@RequestParam(name = "searchEndDate", required = false) @Nullable Long searchEndDate
			) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			List<References> references = null;
			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId()) && user.getUs().size() > 0) {
				List<Integer> ussId = user.getUs().stream().map(Us::getId).collect(Collectors.toList());
				
				List<Users> users = service.GetAllEntityByNamedQuery("Users.findByUsId", ussId);
				List<Integer> usersIds = users.stream().map(Users::getId).collect(Collectors.toList());
				List<String> strUsersIds = usersIds.stream().map(String::valueOf).collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findAllPendingByUserPermission", pageIndex,
						pageSize, new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L), strUsersIds, ussId, usersIds);
			} else if (user.getLocalities().size() > 0) {
				List<Integer> localitiesId = user.getLocalities().stream().map(Locality::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findPendingByLocalities", pageIndex, pageSize,
						new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L), localitiesId);

			} else if (user.getDistricts().size() > 0) {

				List<Integer> districtsId = user.getDistricts().stream().map(District::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findPendingByDistricts", pageIndex, pageSize,
						new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L), districtsId);

			} else if (user.getProvinces().size() > 0) {

				List<Integer> provincesId = user.getProvinces().stream().map(Province::getId)
						.collect(Collectors.toList());

				references = service.GetAllPagedEntityByNamedQuery("References.findPendingByProvinces", pageIndex, pageSize,
						new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L), provincesId);

			} else {
				references = service.GetAllPagedEntityByNamedQuery("References.findAllPending", pageIndex, pageSize,
						 new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L));
			}

			return new ResponseEntity<>(references, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<References> save(@RequestBody References reference) {

		if (reference == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			reference.setCancelReason(null);
			reference.setOtherReason(null);

			List<BeneficiariesInterventions> beneficiaryInterventions = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findAllByBeneficiaryAndDate",
					Instant.ofEpochMilli(reference.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
					reference.getBeneficiaries().getId());
			Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference, beneficiaryInterventions);
			reference.setStatus(referenceStatus);

			service.Save(reference);

			List<ReferencesServices> registeredServices = new ArrayList<ReferencesServices>();

			for (ReferencesServices services : reference.getReferencesServiceses()) {
				services.setId(new ReferencesServicesId());
				services.getId().setReferenceId(reference.getId());
				services.getId().setServiceId(services.getServices().getId());
				services.setDateCreated(new Date());

				Integer referenceServiceStatus = ServiceCompletionRules
						.getReferenceServiceStatus(beneficiaryInterventions, services.getServices().getId());
				services.setStatus(referenceServiceStatus);

				service.Save(services);
				registeredServices.add(services);
			}

			reference.setReferencesServiceses(Set.copyOf(registeredServices));

			return new ResponseEntity<>(reference, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<References> update(@RequestBody References reference) {

		if (reference == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			reference.setDateUpdated(new Date());

			service.update(reference);

			// remove all previous entries
			service.UpdateEntitiesByNamedQuery("ReferencesServices.removeByReferenceId", reference.getId());

			// save new ones
			List<ReferencesServices> registeredServices = new ArrayList<ReferencesServices>();

			for (ReferencesServices services : reference.getReferencesServiceses()) {
				services.setId(new ReferencesServicesId());
				services.getId().setReferenceId(reference.getId());
				services.getId().setServiceId(services.getServices().getId());
				services.setDateCreated(new Date());
				service.Save(services);
				registeredServices.add(services);
			}

			reference.setReferencesServiceses(Set.copyOf(registeredServices));

			return new ResponseEntity<>(reference, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/user/{Id}", produces = "application/json")
	public ResponseEntity<Long> getCountByUserCreated(@PathVariable String Id) {

		if (Id == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Long references = service.GetUniqueEntityByNamedQuery("References.findCountByUser", Id);
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(path = "/byUser/{userId}/countByFilters", produces = "application/json")
	public ResponseEntity<Long> getCountByUserPermission(
		@PathVariable Integer userId,
		@RequestParam(name = "searchNui", required = false) @Nullable String searchNui,
    	@RequestParam(name = "searchUserCreator", required = false) @Nullable Integer searchUserCreator,
    	@RequestParam(name = "searchDistrict", required = false) @Nullable Integer searchDistrict
		) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			Long referencesTotal;

			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId()) && user.getUs().size() > 0) {
				List<Integer> ussId = user.getUs().stream().map(Us::getId).collect(Collectors.toList());
				List<Users> users = service.GetAllEntityByNamedQuery("Users.findByUsId", ussId);
				List<Integer> usersIds = users.stream().map(Users::getId).collect(Collectors.toList());
				List<String> strUsersIds = usersIds.stream().map(String::valueOf).collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountByUserPermission",searchNui, searchUserCreator, searchDistrict
						,strUsersIds, ussId, usersIds);
			} else if (user.getLocalities().size() > 0) {
				List<Integer> localitiesId = user.getLocalities().stream().map(Locality::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountByLocalities",searchNui, searchUserCreator, searchDistrict, localitiesId);

			} else if (user.getDistricts().size() > 0) {

				List<Integer> districtsId = user.getDistricts().stream().map(District::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountByDistricts",searchNui, searchUserCreator, searchDistrict, districtsId);

			} else if (user.getProvinces().size() > 0) {

				List<Integer> provincesId = user.getProvinces().stream().map(Province::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountByProvinces",searchNui, searchUserCreator, searchDistrict, provincesId);

			} else {
				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountAll",searchNui, searchUserCreator, searchDistrict);
			}

			return new ResponseEntity<>(referencesTotal, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/byPeddingUser/{userId}/countByFilters", produces = "application/json")
	public ResponseEntity<Long> getCountPendingByUserPermission(
		@PathVariable Integer userId,
		@RequestParam(name = "searchStartDate", required = false) long searchStartDate,
    	@RequestParam(name = "searchEndDate", required = false) long searchEndDate
		) {

		if (userId == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			Users user = service.find(Users.class, userId);

			Long referencesTotal;

			if (Arrays.asList(MANAGER, MENTOR, NURSE, COUNSELOR).contains(user.getProfiles().getId()) && user.getUs().size() > 0) {
				List<Integer> ussId = user.getUs().stream().map(Us::getId).collect(Collectors.toList());
				List<Users> users = service.GetAllEntityByNamedQuery("Users.findByUsId", ussId);
				List<Integer> usersIds = users.stream().map(Users::getId).collect(Collectors.toList());
				List<String> strUsersIds = usersIds.stream().map(String::valueOf).collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountPendingByUserPermission",new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L),
						strUsersIds, ussId, usersIds);
			} else if (user.getLocalities().size() > 0) {
				List<Integer> localitiesId = user.getLocalities().stream().map(Locality::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountPendingByLocalities",new Date(searchStartDate * 1000L), localitiesId, new Date(searchEndDate * 1000L));

			} else if (user.getDistricts().size() > 0) {

				List<Integer> districtsId = user.getDistricts().stream().map(District::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountPendingByDistricts", new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L),  districtsId);

			} else if (user.getProvinces().size() > 0) {

				List<Integer> provincesId = user.getProvinces().stream().map(Province::getId)
						.collect(Collectors.toList());

				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountPendingByProvinces", new Date(searchStartDate * 1000L), provincesId, new Date(searchEndDate * 1000L));

			} else {
				referencesTotal = service.GetUniqueEntityByNamedQuery("References.findCountAllPending", new Date(searchStartDate * 1000L), new Date(searchEndDate * 1000L));
			}

			return new ResponseEntity<>(referencesTotal, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update-status")
	public ResponseEntity<List<References>> updateReferenceStatus() {

		List<References> references = referenceService.findByStatus(ReferencesStatus.ADDRESSED);

		try {
			for (References reference : references) {
				List<BeneficiariesInterventions> interventions = service.GetAllEntityByNamedQuery(
						"BeneficiaryIntervention.findAllByBeneficiaryAndDate",
						Instant.ofEpochMilli(reference.getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate(),
						reference.getBeneficiaries().getId());
				for (ReferencesServices referenceService : reference.getReferencesServiceses()) {

					Integer referenceServiceStatus = ServiceCompletionRules.getReferenceServiceStatus(interventions,
							referenceService.getServices().getId());
					if (referenceService.getStatus() != referenceServiceStatus) {
						referenceService.setStatus(referenceServiceStatus);
						service.update(referenceService);
					}
				}
				Integer referenceStatus = ServiceCompletionRules.getReferenceStatus(reference, interventions);
				if (reference.getStatus() != referenceStatus) {
					reference.setStatus(referenceStatus);
					service.update(reference);
				}
			}
			return new ResponseEntity<>(references, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(path = "/bulkCancel", consumes = "application/json")
	public ResponseEntity<ReferencesCancel> bulkCancel(@RequestBody  ReferencesCancel referencesCancel) {

		if (referencesCancel == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

		try {
			for(int referenceID : referencesCancel.gitIds()){

				References references = this.service.find(References.class, referenceID);

				references.setStatus(referencesCancel.getStatus());
				references.setCancelReason(referencesCancel.getCancelReason());
				references.setOtherReason(referencesCancel.getOtherReason());
				references.setUpdatedBy(referencesCancel.getUpdatedBy());
				references.setDateUpdated(new Date());

				service.update(references);
			}
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(path = "/countByBeneficiary/{beneficiaryId}", produces = "application/json")
	public ResponseEntity<List<CountIntervention>> countByBeneficiary(
			@PathVariable Integer beneficiaryId) {
		try {
			List<CountIntervention> interventions = referenceService
						.countByBeneficiary(beneficiaryId);
			
			return new ResponseEntity<>(interventions, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
