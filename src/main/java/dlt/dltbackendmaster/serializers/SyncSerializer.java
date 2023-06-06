package dlt.dltbackendmaster.serializers;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
import dlt.dltbackendmaster.domain.UsersSync;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryInterventionSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiarySyncModel;
import dlt.dltbackendmaster.domain.watermelondb.BeneficiaryVulnerabilitySyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceServicesSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.ReferenceSyncModel;
import dlt.dltbackendmaster.domain.watermelondb.SyncObject;
import dlt.dltbackendmaster.domain.watermelondb.UsersSyncModel;

public class SyncSerializer {

	public static String createSyncObject(SyncObject<UsersSync> users, SyncObject<Province> provinces, SyncObject<District> districts, SyncObject<Locality> localities,
			SyncObject<Profiles> profiles, SyncObject<Partners> partners, SyncObject<Us> us,
			SyncObject<Beneficiaries> beneficiaries, SyncObject<BeneficiariesInterventions> beneficiariesInterventions,
			SyncObject<Neighborhood> neighborhoods, SyncObject<Services> services, SyncObject<SubServices> subServices,
			SyncObject<References> references, SyncObject<ReferencesServices> referencesServices, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode changesNode = mapper.createObjectNode();
		changesNode.set("users", createUserSyncObject(users, lastPulledAt));
		changesNode.set("provinces", createProvinceSyncObject(provinces));
		changesNode.set("districts", createDistrictsSyncObject(districts));
		changesNode.set("localities", createLocalitySyncObject(localities));
		changesNode.set("partners", createPartnersSyncObject(partners));
		changesNode.set("profiles", createProfilesSyncObject(profiles));
		changesNode.set("us", createUsSyncObject(us));
		changesNode.set("beneficiaries", createBeneficiarySyncObject(beneficiaries, lastPulledAt));
		changesNode.set("beneficiaries_interventions",
				createBeneficiaryInterventionSyncObject(beneficiariesInterventions, lastPulledAt));
		changesNode.set("neighborhoods", createNeighborhoodSyncObject(neighborhoods));
		changesNode.set("services", createServiceSyncObject(services));
		changesNode.set("sub_services", createSubServiceSyncObject(subServices));
		changesNode.set("references", createReferencesSyncObject(references, lastPulledAt));
		changesNode.set("references_services", createReferenceServicesSyncObject(referencesServices, lastPulledAt));

		ObjectNode rootNode = mapper.createObjectNode();
		rootNode.set("changes", changesNode);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		rootNode.put("timestamp", timestamp.getTime());
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
	}

	public static ObjectNode createUserSyncObject(SyncObject<UsersSync> users, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created users
		List<UsersSync> createdUsers = mapper.convertValue(users.getCreated(), new TypeReference<List<UsersSync>>() {
		});
		List<ObjectNode> createdlist = createdUsers.stream().map((UsersSync element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated users
		List<UsersSync> updatedUsers = mapper.convertValue(users.getUpdated(), new TypeReference<List<UsersSync>>() {
		});
		List<ObjectNode> updatedlist = updatedUsers.stream().map((UsersSync element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted users
		// List<Integer> deletedUsers = mapper.convertValue(users.getDeleted(), new
		// TypeReference<List<Integer>>() {});
		ArrayNode arrayDeleted = mapper.createArrayNode();
		// arrayDeleted.addAll(deletedUsers.toArray());
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}

	public static ObjectNode createLocalitySyncObject(SyncObject<Locality> locality) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<Locality> createdObjs = mapper.convertValue(locality.getCreated(), new TypeReference<List<Locality>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((Locality element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<Locality> updatedObjs = mapper.convertValue(locality.getUpdated(), new TypeReference<List<Locality>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((Locality element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(locality.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}
	
	public static ObjectNode createProvinceSyncObject(SyncObject<Province> provinces) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<Province> createdObjs = mapper.convertValue(provinces.getCreated(), new TypeReference<List<Province>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((Province element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<Province> updatedObjs = mapper.convertValue(provinces.getUpdated(), new TypeReference<List<Province>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((Province element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(provinces.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}
	
	public static ObjectNode createDistrictsSyncObject(SyncObject<District> districts) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<District> createdObjs = mapper.convertValue(districts.getCreated(), new TypeReference<List<District>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((District element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<District> updatedObjs = mapper.convertValue(districts.getUpdated(), new TypeReference<List<District>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((District element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(districts.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}

	public static ObjectNode createPartnersSyncObject(SyncObject<Partners> partners) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<Partners> createdObjs = mapper.convertValue(partners.getCreated(), new TypeReference<List<Partners>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((Partners element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<Partners> updatedObjs = mapper.convertValue(partners.getUpdated(), new TypeReference<List<Partners>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((Partners element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(partners.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}

	public static ObjectNode createProfilesSyncObject(SyncObject<Profiles> profiles) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<Profiles> createdObjs = mapper.convertValue(profiles.getCreated(), new TypeReference<List<Profiles>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((Profiles element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<Profiles> updatedObjs = mapper.convertValue(profiles.getUpdated(), new TypeReference<List<Profiles>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((Profiles element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(profiles.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}

	public static ObjectNode createUsSyncObject(SyncObject<Us> us) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		// created
		List<Us> createdObjs = mapper.convertValue(us.getCreated(), new TypeReference<List<Us>>() {
		});
		List<ObjectNode> createdlist = createdObjs.stream().map((Us element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdlist);
		// updated
		List<Us> updatedObjs = mapper.convertValue(us.getUpdated(), new TypeReference<List<Us>>() {
		});
		List<ObjectNode> updatedlist = updatedObjs.stream().map((Us element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedlist);
		// deleted
		List<Integer> deletedObjs = mapper.convertValue(us.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode userNode = mapper.createObjectNode();
		userNode.set("created", arrayCreated);
		userNode.set("updated", arrayUpdated);
		userNode.set("deleted", arrayDeleted);
		return userNode;
	}

	private static ObjectNode createBeneficiarySyncObject(SyncObject<Beneficiaries> beneficiaries, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		List<Beneficiaries> createdObjs = mapper.convertValue(beneficiaries.getCreated(),
				new TypeReference<List<Beneficiaries>>() {
				});
		List<ObjectNode> createdList = createdObjs.stream().map((Beneficiaries element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);
		List<Beneficiaries> updatedObjs = mapper.convertValue(beneficiaries.getUpdated(),
				new TypeReference<List<Beneficiaries>>() {
				});
		List<ObjectNode> updatedList = updatedObjs.stream().map((Beneficiaries element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(beneficiaries.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode beneficiaryNode = mapper.createObjectNode();
		beneficiaryNode.set("created", arrayCreated);
		beneficiaryNode.set("updated", arrayUpdated);
		beneficiaryNode.set("deleted", arrayDeleted);
		return beneficiaryNode;
	}

	private static ObjectNode createBeneficiaryInterventionSyncObject(
			SyncObject<BeneficiariesInterventions> beneficiariesInterventions, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		// FIXME: This method is setting beneficiary to null... do we really need to use
		// it???
		// List<BeneficiaryIntervention> createdObjs =
		// mapper.convertValue(beneficiariesInterventions.getCreated(),
		// new TypeReference<List<BeneficiaryIntervention>>() {});
		List<BeneficiariesInterventions> createdObjs = beneficiariesInterventions.getCreated();
		List<ObjectNode> createdList = createdObjs.stream()
				.map((BeneficiariesInterventions element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);

		// FIXME: This method is setting beneficiary to null... do we really need to use
		// it???
		// List<BeneficiaryIntervention> updatedObjs =
		// mapper.convertValue(beneficiariesInterventions.getUpdated(),
		// new TypeReference<List<BeneficiaryIntervention>>() {});
		List<BeneficiariesInterventions> updatedObjs = beneficiariesInterventions.getUpdated();
		List<ObjectNode> updatedList = updatedObjs.stream()
				.map((BeneficiariesInterventions element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(beneficiariesInterventions.getDeleted(),
				new TypeReference<List<Integer>>() {
				});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode beneficiaryInterventionNode = mapper.createObjectNode();
		beneficiaryInterventionNode.set("created", arrayCreated);
		beneficiaryInterventionNode.set("updated", arrayUpdated);
		beneficiaryInterventionNode.set("deleted", arrayDeleted);
		return beneficiaryInterventionNode;
	}

	/*
	 * private static ObjectNode
	 * createBeneficiaryVulnerabilitySyncObject(SyncObject<BeneficiaryVulnerability>
	 * beneficiariesVulnerabilities) throws JsonProcessingException { ObjectMapper
	 * mapper = new ObjectMapper();
	 * 
	 * // FIXME: This method is setting beneficiary to null... do we really need to
	 * use it??? // List<BeneficiaryVulnerability> createdObjs =
	 * mapper.convertValue(beneficiariesVulnerabilities.getCreated(), // new
	 * TypeReference<List<BeneficiaryVulnerability>>() {});
	 * List<BeneficiaryVulnerability> createdObjs =
	 * beneficiariesVulnerabilities.getCreated(); List<ObjectNode> createdList =
	 * createdObjs.stream() .map((BeneficiaryVulnerability element) ->
	 * element.toObjectNode()) .collect(Collectors.toList()); ArrayNode arrayCreated
	 * = mapper.createArrayNode(); arrayCreated.addAll(createdList);
	 * 
	 * // FIXME: This method is setting beneficiary to null... do we really need to
	 * use it??? // List<BeneficiaryVulnerability> updatedObjs =
	 * mapper.convertValue(beneficiariesVulnerabilities.getUpdated(), // new
	 * TypeReference<List<BeneficiaryVulnerability>>() {});
	 * List<BeneficiaryVulnerability> updatedObjs =
	 * beneficiariesVulnerabilities.getUpdated(); List<ObjectNode> updatedList =
	 * updatedObjs.stream() .map((BeneficiaryVulnerability element) ->
	 * element.toObjectNode()) .collect(Collectors.toList()); ArrayNode arrayUpdated
	 * = mapper.createArrayNode(); arrayUpdated.addAll(updatedList); List<Integer>
	 * deletedObjs = mapper.convertValue(beneficiariesVulnerabilities.getDeleted(),
	 * new TypeReference<List<Integer>>() {}); ArrayNode arrayDeleted =
	 * mapper.valueToTree(deletedObjs); ObjectNode beneficiaryVulnerabilityNode =
	 * mapper.createObjectNode(); beneficiaryVulnerabilityNode.set("created",
	 * arrayCreated); beneficiaryVulnerabilityNode.set("updated", arrayUpdated);
	 * beneficiaryVulnerabilityNode.set("deleted", arrayDeleted); return
	 * beneficiaryVulnerabilityNode; }
	 */

	private static ObjectNode createNeighborhoodSyncObject(SyncObject<Neighborhood> neighborhood)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<Neighborhood> createdObjs = mapper.convertValue(neighborhood.getCreated(),
				new TypeReference<List<Neighborhood>>() {
				});
		List<ObjectNode> createdList = createdObjs.stream().map((Neighborhood element) -> element.toObjectNode())
				.collect(Collectors.toList());

		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);
		List<Neighborhood> updatedObjs = mapper.convertValue(neighborhood.getUpdated(),
				new TypeReference<List<Neighborhood>>() {
				});
		List<ObjectNode> updatedList = updatedObjs.stream().map((Neighborhood element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(neighborhood.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode neighborhoodNode = mapper.createObjectNode();
		neighborhoodNode.set("created", arrayCreated);
		neighborhoodNode.set("updated", arrayUpdated);
		neighborhoodNode.set("deleted", arrayDeleted);
		return neighborhoodNode;
	}

	private static ObjectNode createServiceSyncObject(SyncObject<Services> service) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<Services> createdObjs = mapper.convertValue(service.getCreated(), new TypeReference<List<Services>>() {
		});
		List<ObjectNode> createdList = createdObjs.stream().map((Services element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);
		List<Services> updatedObjs = mapper.convertValue(service.getUpdated(), new TypeReference<List<Services>>() {
		});
		List<ObjectNode> updatedList = updatedObjs.stream().map((Services element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(service.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode serviceNode = mapper.createObjectNode();
		serviceNode.set("created", arrayCreated);
		serviceNode.set("updated", arrayUpdated);
		serviceNode.set("deleted", arrayDeleted);
		return serviceNode;
	}

	private static ObjectNode createSubServiceSyncObject(SyncObject<SubServices> subService)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<SubServices> createdObjs = mapper.convertValue(subService.getCreated(),
				new TypeReference<List<SubServices>>() {
				});
		List<ObjectNode> createdList = createdObjs.stream().map((SubServices element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);
		List<SubServices> updatedObjs = mapper.convertValue(subService.getUpdated(),
				new TypeReference<List<SubServices>>() {
				});
		List<ObjectNode> updatedList = updatedObjs.stream().map((SubServices element) -> element.toObjectNode())
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(subService.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode subServiceNode = mapper.createObjectNode();
		subServiceNode.set("created", arrayCreated);
		subServiceNode.set("updated", arrayUpdated);
		subServiceNode.set("deleted", arrayDeleted);
		return subServiceNode;
	}

	private static ObjectNode createReferencesSyncObject(SyncObject<References> references, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<References> createdObjects = mapper.convertValue(references.getCreated(),
				new TypeReference<List<References>>() {
				});

		List<ObjectNode> createdList = createdObjects.stream().map((References element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);
		List<References> updatedObjs = mapper.convertValue(references.getUpdated(),
				new TypeReference<List<References>>() {
				});
		List<ObjectNode> updatedList = updatedObjs.stream().map((References element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(references.getDeleted(), new TypeReference<List<Integer>>() {
		});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode referencesNode = mapper.createObjectNode();
		referencesNode.set("created", arrayCreated);
		referencesNode.set("updated", arrayUpdated);
		referencesNode.set("deleted", arrayDeleted);
		return referencesNode;
	}

	private static ObjectNode createReferenceServicesSyncObject(
			SyncObject<ReferencesServices> refencesServices, String lastPulledAt)
			throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		List<ReferencesServices> createdObjs = refencesServices.getCreated();
		List<ObjectNode> createdList = createdObjs.stream()
				.map((ReferencesServices element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayCreated = mapper.createArrayNode();
		arrayCreated.addAll(createdList);

		List<ReferencesServices> updatedObjs = refencesServices.getUpdated();
		List<ObjectNode> updatedList = updatedObjs.stream()
				.map((ReferencesServices element) -> element.toObjectNode(lastPulledAt))
				.collect(Collectors.toList());
		ArrayNode arrayUpdated = mapper.createArrayNode();
		arrayUpdated.addAll(updatedList);
		List<Integer> deletedObjs = mapper.convertValue(refencesServices.getDeleted(),
				new TypeReference<List<Integer>>() {
				});
		ArrayNode arrayDeleted = mapper.valueToTree(deletedObjs);
		ObjectNode refencesServicesNode = mapper.createObjectNode();
		refencesServicesNode.set("created", arrayCreated);
		refencesServicesNode.set("updated", arrayUpdated);
		refencesServicesNode.set("deleted", arrayDeleted);
		return refencesServicesNode;
	}

	public static SyncObject readUsersSyncObject(String changes) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode usersNode = changesNode.path("users");
			@SuppressWarnings("unchecked")
			SyncObject<UsersSyncModel> users = mapper.treeToValue(usersNode,
					(Class<SyncObject<UsersSyncModel>>) (Object) SyncObject.class);
			return users;
		}
		return null;
	}

	public static SyncObject<BeneficiarySyncModel> readBeneficiariesSyncObject(String changes)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode beneficiariesNode = changesNode.path("beneficiaries");
			SyncObject<BeneficiarySyncModel> beneficiaries = mapper.treeToValue(beneficiariesNode,
					(Class<SyncObject<BeneficiarySyncModel>>) (Object) SyncObject.class);
			return beneficiaries;
		}

		return null;
	}

	public static SyncObject<BeneficiaryInterventionSyncModel> readInterventionsSyncObject(String changes)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode beneficiaries_interventionsNode = changesNode.path("beneficiaries_interventions");
			if (!beneficiaries_interventionsNode.isMissingNode()) {
				SyncObject<BeneficiaryInterventionSyncModel> beneficiaries_interventions = mapper.treeToValue(
						beneficiaries_interventionsNode,
						(Class<SyncObject<BeneficiaryInterventionSyncModel>>) (Object) SyncObject.class);
				return beneficiaries_interventions;
			}

		}

		return null;
	}

	public static SyncObject<BeneficiaryVulnerabilitySyncModel> readVulnerabilitiesSyncObject(String changes)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode vulnerabilitiesNode = changesNode.path("vulnerabilities");
			SyncObject<BeneficiaryVulnerabilitySyncModel> vulnerabilities = mapper.treeToValue(vulnerabilitiesNode,
					(Class<SyncObject<BeneficiaryVulnerabilitySyncModel>>) (Object) SyncObject.class);
			return vulnerabilities;
		}

		return null;
	}

	public static String readLastPulledAt(String changes) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode lastPulledAtNode = root.path("lastPulledAt");

		if (!lastPulledAtNode.isMissingNode()) {
			return mapper.treeToValue(lastPulledAtNode, String.class);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static SyncObject<ReferenceServicesSyncModel> readReferenceServicesSyncObject(String changes)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode referenceServicesNode = changesNode.path("references_services");
			SyncObject<ReferenceServicesSyncModel> referenceServices = mapper.treeToValue(referenceServicesNode,
					(Class<SyncObject<ReferenceServicesSyncModel>>) (Object) SyncObject.class);
			return referenceServices;
		}

		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static SyncObject<ReferenceSyncModel> readReferencesSyncObject(String changes)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(changes);
		JsonNode changesNode = root.path("changes");

		if (!changesNode.isMissingNode()) {
			JsonNode referenceNode = changesNode.path("references");
			SyncObject<ReferenceSyncModel> references = mapper.treeToValue(referenceNode,
					(Class<SyncObject<ReferenceSyncModel>>) (Object) SyncObject.class);
			return references;
		}

		return null;
	}

}
