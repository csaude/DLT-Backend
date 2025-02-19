ALTER TABLE `dreams_db`.`beneficiaries` 
ADD COLUMN `vblt_idp` TINYINT NULL AFTER `vblt_is_employed`,
ADD COLUMN `vblt_pregnant_or_has_children` TINYINT NULL AFTER `vblt_sexually_active`;

update beneficiaries
set vblt_pregnant_or_has_children = if (vblt_pregnant_before is not null or vblt_children is not null or vblt_pregnant_or_breastfeeding is not null,if (vblt_pregnant_before=1 or vblt_children=1 or vblt_pregnant_or_breastfeeding=1,1,0),null);