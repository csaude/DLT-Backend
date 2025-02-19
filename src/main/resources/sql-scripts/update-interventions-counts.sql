update beneficiaries b
inner join 
(
	select b.id, b.nui,
		sum(case
				when s.service_type=1 then 1
				else 0
		end) servicos_clinico,
		sum(case
				when s.service_type=2 then 1
				else 0
		end) servicos_comunitarios
	from beneficiaries b
	left join beneficiaries_interventions bi on bi.beneficiary_id = b.id and bi.status=1
	inner join sub_services ss on bi.sub_service_id = ss.id
	inner join services s on ss.service_id = s.id
	group by b.id, b.nui
) s on s.id = b.id
set b.clinical_interventions = s.servicos_clinico,
b.community_interventions = s.servicos_comunitarios