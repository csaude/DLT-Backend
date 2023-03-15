package dlt.dltbackendmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.References;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	@Autowired
	private DAOService service;

	@Override
	public List<References> findByStatus(Integer status) {
		List<References> references = service.GetAllEntityByNamedQuery("References.findAllByStatus", status);
		return references;
	}

}
