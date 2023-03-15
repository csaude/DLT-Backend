package dlt.dltbackendmaster.service;

import java.util.List;

import dlt.dltbackendmaster.domain.References;

public interface ReferenceService {
	List<References> findByStatus(Integer status);
}
