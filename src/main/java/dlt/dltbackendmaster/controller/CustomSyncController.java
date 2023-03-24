package dlt.dltbackendmaster.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dlt.dltbackendmaster.domain.Beneficiaries;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.District;
import dlt.dltbackendmaster.domain.Locality;
import dlt.dltbackendmaster.domain.Neighborhood;
import dlt.dltbackendmaster.domain.Partners;
import dlt.dltbackendmaster.domain.Profiles;
import dlt.dltbackendmaster.domain.Province;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.Services;
import dlt.dltbackendmaster.domain.SubServices;
import dlt.dltbackendmaster.domain.Us;
import dlt.dltbackendmaster.domain.Users;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.serializers.SyncSerializer;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.service.SequenceGenerator;

@RestController
@RequestMapping("/custom/sync")
public class CustomSyncController {

	private final DAOService service;

	@Autowired
	public CustomSyncController(DAOService service) {
		this.service = service;
		new SequenceGenerator(service);
	}

	@SuppressWarnings({ "rawtypes" })
	@GetMapping(path = "/beneficiaries", produces = "application/json")
	public ResponseEntity get(@RequestParam(name = "lastPulledAt", required = false) @Nullable String lastPulledAt,
			@RequestParam(name = "nui") String nui, @RequestParam(name = "userId") Integer userId)
			throws ParseException {

		List<Users> usersCreated = new ArrayList<Users>();
		List<Users> usersUpdated = new ArrayList<Users>();
		List<Integer> listDeleted;

		List<Locality> localityCreated = new ArrayList<Locality>();
		List<Locality> localityUpdated = new ArrayList<Locality>();

		List<Province> provincesCreated = new ArrayList<Province>();
		List<District> districtsCreated = new ArrayList<District>();

		List<Partners> partnersCreated = new ArrayList<Partners>();
		List<Partners> partnersUpdated = new ArrayList<Partners>();

		List<Profiles> profilesCreated = new ArrayList<Profiles>();
		List<Profiles> profilesUpdated = new ArrayList<Profiles>();

		List<Us> usCreated = new ArrayList<Us>();
		List<Us> usUpdated = new ArrayList<Us>();

		List<Beneficiaries> beneficiariesCreated = new ArrayList<Beneficiaries>();
		List<Beneficiaries> beneficiariesUpdated = new ArrayList<Beneficiaries>();

		List<BeneficiariesInterventions> beneficiariesInterventionsCreated = new ArrayList<BeneficiariesInterventions>();
		List<BeneficiariesInterventions> beneficiariesInterventionsUpdated = new ArrayList<BeneficiariesInterventions>();

		List<Neighborhood> neighborhoodsCreated = new ArrayList<>();
		List<Neighborhood> neighborhoodUpdated = new ArrayList<>();

		List<Services> servicesCreated = new ArrayList<>();
		List<Services> servicesUpdated = new ArrayList<>();

		List<SubServices> subServicesCreated = new ArrayList<>();
		List<SubServices> subServicesUpdated = new ArrayList<>();

		List<References> referencesCreated = new ArrayList<References>();
		List<References> referencesUpdated = new ArrayList<References>();

		List<ReferencesServices> referenceServicesCreated = new ArrayList<ReferencesServices>();
		List<ReferencesServices> referenceServicesUpdated = new ArrayList<ReferencesServices>();

		usersUpdated = new ArrayList<Users>();
		listDeleted = new ArrayList<Integer>();

		// Beneficiary
		List<Users> users = service.GetAllEntityByNamedQuery("Users.findById",userId);
		List <Integer> localitiesIds = new ArrayList<>();
		Set<Locality> localities = users.get(0).getLocalities();
		localities.forEach(locality->{
			localitiesIds.add(locality.getId());
			});
		
		beneficiariesCreated = service.GetAllEntityByNamedQuery("Beneficiary.getBeneficiariesByNui",nui, localitiesIds);

		if (beneficiariesCreated.size() > 0) {

			beneficiariesInterventionsCreated = service.GetAllEntityByNamedQuery(
					"BeneficiaryIntervention.findByBeneficiaryId", beneficiariesCreated.get(0).getId());

			referencesCreated = service.GetAllEntityByNamedQuery("References.findByBeneficiaryId",
					beneficiariesCreated.get(0).getId());

			for (References ref : referencesCreated) {
				List<ReferencesServices> refServicesByRef = new ArrayList<ReferencesServices>();
				refServicesByRef = service.GetAllEntityByNamedQuery("ReferencesServices.findByReference", ref.getId());
				referenceServicesCreated.addAll(refServicesByRef);
			}

			neighborhoodsCreated = service.GetAllEntityByNamedQuery("Neighborhood.findById",
					beneficiariesCreated.get(0).getNeighborhood().getId());

			partnersCreated = service.GetAllEntityByNamedQuery("Partners.findById",
					beneficiariesCreated.get(0).getPartners().getId());

			districtsCreated = service.GetAllEntityByNamedQuery("District.findById",
					beneficiariesCreated.get(0).getDistrict().getId());
			
			provincesCreated = service.GetAllEntityByNamedQuery("Province.findById",
					beneficiariesCreated.get(0).getDistrict().getProvince().getId());
			
			localityCreated = service.GetAllEntityByNamedQuery("Locality.findById",
					beneficiariesCreated.get(0).getLocality().getId());
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

			String object = SyncSerializer.createSyncObject(usersSO, provinceSO, districtSO, localitySO, profilesSO,
					partnersSO, usSO, beneficiarySO, beneficiaryInterventionSO, neighborhoodSO, serviceSO, subServiceSO,
					referencesSO, referencesServicesSO, lastPulledAt);
			// System.out.println("PULLING " + object);

			return new ResponseEntity<>(object, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			return new ResponseEntity<>("Parameter not present", HttpStatus.BAD_REQUEST);
		}
	}
}
