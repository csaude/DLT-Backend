CREATE TABLE `REVINFO` (
  `REV` int NOT NULL AUTO_INCREMENT,
  `REVTSTMP` bigint DEFAULT NULL,
  PRIMARY KEY (`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
SELECT * FROM dreams_db.REVINFO;


CREATE TABLE `audit_beneficiaries_interventions` (
  `beneficiary_id` int NOT NULL,
  `date` date NOT NULL,
  `sub_service_id` int NOT NULL,
  `REV` int NOT NULL,
  `REVTYPE` tinyint DEFAULT NULL,
  `date_updated` datetime(6) DEFAULT NULL,
  `updated_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`beneficiary_id`,`date`,`sub_service_id`,`REV`),
  KEY `FKl3xyxfadgnfi8dcq8o76ns1ks` (`REV`),
  CONSTRAINT `FKl3xyxfadgnfi8dcq8o76ns1ks` FOREIGN KEY (`REV`) REFERENCES `REVINFO` (`REV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
