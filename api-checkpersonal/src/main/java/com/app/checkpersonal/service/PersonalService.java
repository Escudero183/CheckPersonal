package com.app.checkpersonal.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.app.checkpersonal.model.Personal;
import com.app.checkpersonal.repository.PersonalRepository;
import com.app.checkpersonal.repository.UbigeoRepository;

@Service
public class PersonalService {
	
	@Autowired
	private PersonalRepository personalRepository;
	
	@Autowired
	private UbigeoRepository ubigeoRepository;
	
	public Personal insert(Personal item) {
		return personalRepository.save(item);
	}
	
	public void update(Personal item) {
		personalRepository.save(item);
	}
	
	public void delete (Personal item) {
		personalRepository.save(item);		
	}
	
	public List<Personal> findAll() {
		return (List<Personal>) personalRepository.findAll();
	}
	
	public Personal findById(Integer id) {
		return personalRepository.findById(id).orElse(null);
	}
	
	public List<Personal> findAll(String sexo, String query, String sortBy) {
		Sort sort;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();
			sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
		} else {
			sort = Sort.by(Direction.ASC, "idPersonal");
		}
		return personalRepository.findAll(sexo, "%" + query.toLowerCase() + "%", sort);
	}

	public HashMap<String, Object> findAll(String sexo, String query, int page, int limit, String sortBy) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Pageable pageable;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();

			Sort sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
			pageable = PageRequest.of(page - 1, limit, sort);

		} else {
			Sort sort = Sort.by(Direction.ASC, "idPersonal");
			pageable = PageRequest.of(page - 1, limit, sort);

		}
		Page<Personal> data = personalRepository.findAllParams(sexo, "%" + query.toLowerCase() + "%", pageable);
		if (!data.getContent().isEmpty()) {
			result.put("items", data.getContent());
		} else {
			result.put("items", new ArrayList<>());
		}
		result.put("totalPage", data.getTotalPages());
		result.put("totalRows", data.getNumberOfElements());
		result.put("totalItems", data.getTotalElements());
		result.put("page", page);
		result.put("sizeRows", limit);
		return result;
	}
	
	public List<Personal> findByDocumento(Integer idTipoDocumento, String nroDocumento) {
		List<Personal> data = personalRepository.findByDocumento(idTipoDocumento, nroDocumento);
		for(Personal personal : data) {
			personal.setUbigeoAll(ubigeoRepository.getUbigeoFull(personal.getUbigeo().getIdUbigeo()));
		}
		return data;
	}

}
