DROP TABLE agyw_prev_mview;

CREATE TABLE agyw_prev_mview
(
   id int NOT NULL AUTO_INCREMENT,
   province_id int,
   district_id int DEFAULT 0 NOT NULL,
   neighborhood_id int DEFAULT 0,
   beneficiary_id int DEFAULT 0 NOT NULL,
   gender smallint,
   nui varchar(61),
   organization_id int,
   entry_point int NOT NULL,
   date_of_birth varchar(25),
   created_by int,
   date_created varchar(25),
   updated_by int,
   date_updated varchar(25),
   enrollment_date varchar(25),
   registration_age int,
   current_age int,
   vblt_lives_with varchar(150),
   vblt_is_orphan int,
   vblt_house_sustainer int,
   vblt_is_student int,
   vblt_is_deficient int,
   vblt_deficiency_type varchar(250),
   vblt_married_before int,
   vblt_pregnant_before int,
   vblt_children int,
   vblt_pregnant_or_breastfeeding int,
   vblt_is_employed int,
   vblt_tested_hiv int,
   vblt_sexual_exploitation int,
   vblt_is_migrant int,
   vblt_trafficking_victim int,
   vblt_sexually_active int,
   vblt_multiple_partners int,
   vblt_gbv_victim int,
   vblt_sex_worker int,
   vblt_alcohol_drugs_use int,
   vblt_sti_history int,
   service_type int,
   service_id int,
   sub_service_id int,
   service_entry_point int,
   us_id int,
   intervention_date varchar(25),
   provider varchar(250),
   remarks varchar(250),
   intervention_status int,
   registration_age_band int,
   current_age_band int,
   vulnerable int,
   level int,
   PRIMARY KEY (`id`)
);

SET @@SESSION.sql_mode='NO_ZERO_DATE,NO_ZERO_IN_DATE';

set @counter=0;

INSERT INTO agyw_prev_mview
select a.*,
   sab.level
from
(
   select a.*,
      if(registration_age between 9 and 14,1,if (registration_age between 15 and 19,2,if (registration_age between 20 and 24,3,if (registration_age between 25 and 29,4,5)))) registration_age_band,
      if(current_age between 9 and 14,1,if (current_age between 15 and 19,2,if (current_age between 20 and 24,3,if (current_age between 25 and 29,4,5)))) current_age_band,
      if (gender = 2 
            and((vblt_tested_hiv is not null and vblt_tested_hiv < 2 and date_created < '2022-01-01')
            or vblt_multiple_partners = 1 
            or vblt_is_deficient = 1
            or vblt_gbv_victim = 1
            or vblt_alcohol_drugs_use = 1
            or vblt_sti_history = 1
            or vblt_sexual_exploitation = 1
            or ((vblt_house_sustainer = 1 or vblt_pregnant_before = 1 or vblt_married_before = 1 or vblt_children = 1 or vblt_is_migrant = 1 or vblt_trafficking_victim = 1) and current_age < 20)
            or ((vblt_sexually_active = 1 or vblt_pregnant_or_breastfeeding= 1 or vblt_is_student = 0 or (vblt_is_employed is not null and vblt_is_employed <> 0) or vblt_is_orphan = 1) and current_age < 18)
            or (vblt_sex_worker = 1 and current_age > 17)),
            1,0) vulnerable
   from
   (
      select @counter:=@counter+1 id, 
         p.id as province_id,
         d.id as district_id,
         n.id as neighborhood_id,
         b.id as beneficiary_id,
         b.gender,
         concat(d.code,'/',b.nui) nui,
         b.organization_id,
         b.entry_point,
         b.date_of_birth,
         b.created_by,
         b.date_created,
         b.updated_by,
         b.date_updated,
         b.enrollment_date,
         floor(datediff(b.date_created,b.date_of_birth)/365) registration_age,
         floor(datediff(now(),b.date_of_birth)/365) current_age,
         b.vblt_lives_with,
         b.vblt_is_orphan,
         b.vblt_house_sustainer,
         b.vblt_is_student,
         b.vblt_is_deficient,
         b.vblt_deficiency_type,
         b.vblt_married_before,
         b.vblt_pregnant_before,
         b.vblt_children,
         b.vblt_pregnant_or_breastfeeding,
         b.vblt_is_employed,
         b.vblt_tested_hiv,
         b.vblt_sexual_exploitation,
         b.vblt_is_migrant,
         b.vblt_trafficking_victim,
         b.vblt_sexually_active,
         b.vblt_multiple_partners,
         b.vblt_vbg_victim vblt_gbv_victim,
         b.vblt_sex_worker,
         b.vblt_alcohol_drugs_use,
         b.vblt_sti_history,
         s.service_type,
         s.id service_id,
         ss.id sub_service_id,
         bi.entry_point service_entry_point,
         us.id us_id,
         bi.date intervention_date,
         bi.provider,
         bi.remarks,
         bi.status intervention_status
      from beneficiaries b
      inner join district d on b.district_id = d.id
      inner join province p on d.province_id = p.id
      left join neighborhood n on b.neighbourhood_id = n.id
      left join users u on b.created_by = u.id
      left join partners pa on u.partner_id = pa.id
      left join beneficiaries_interventions bi on bi.beneficiary_id = b.id
      left join sub_services ss on bi.sub_service_id = ss.id
      left join services s on ss.service_id = s.id
      left join us on bi.us_id = us.id
   ) a
) a
left join services_agebands sab on sab.service_id = a.service_id and sab.age_band = a.current_age_band;