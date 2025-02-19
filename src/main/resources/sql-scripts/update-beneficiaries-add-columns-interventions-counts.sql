ALTER TABLE `dreams_db`.`beneficiaries` 
ADD COLUMN `clinical_interventions` INT NOT NULL DEFAULT 0 AFTER `status`,
ADD COLUMN `community_interventions` INT NOT NULL DEFAULT 0 AFTER `clinical_interventions`;