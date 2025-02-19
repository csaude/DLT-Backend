CREATE TABLE `dreams_db`.`old_passwords` (
  `id` INT NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `user_id` INT NOT NULL,
  `date_created` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_old_passwords_1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_old_passwords_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `dreams_db`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `dreams_db`.`old_passwords` 
CHANGE COLUMN `id` `id` INT NOT NULL AUTO_INCREMENT ;
