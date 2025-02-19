ALTER TABLE `dreams_db`.`users_last_sync` 
ADD COLUMN `app_version` VARCHAR(10) NULL AFTER `user_id`;
