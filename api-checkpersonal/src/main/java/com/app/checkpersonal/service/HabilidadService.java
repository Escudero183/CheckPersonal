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

import com.app.checkpersonal.model.Habilidad;
import com.app.checkpersonal.repository.HabilidadRepository;

@Service
public class HabilidadService {
	
	@Autowired
	private HabilidadRepository habilidadRepository;
	
	public Habilidad insert(Habilidad item) {
		return habilidadRepository.save(item);
	}
	
	public void update(Habilidad item) {
		habilidadRepository.save(item);
	}
	
	public void delete (Habilidad item) {
		habilidadRepository.save(item);		
	}
	
	public List<Habilidad> findAll() {
		return (List<Habilidad>) habilidadRepository.findAll();
	}
	
	public Habilidad findById(Integer id) {
		return habilidadRepository.findById(id).orElse(null);
	}
	
	public List<Habilidad> findAll(Integer idPersonal, String query, String sortBy) {
		Sort sort;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();
			sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
		} else {
			sort = Sort.by(Direction.ASC, "idHabilidad");
		}
		return habilidadRepository.findAll(idPersonal, "%" + query.toLowerCase() + "%", sort);
	}

	public HashMap<String, Object> findAll(Integer idPersonal, String query, int page, int limit, String sortBy) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Pageable pageable;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();

			Sort sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
			pageable = PageRequest.of(page - 1, limit, sort);

		} else {
			Sort sort = Sort.by(Direction.ASC, "idHabilidad");
			pageable = PageRequest.of(page - 1, limit, sort);

		}
		Page<Habilidad> data = habilidadRepository.findAllParams(idPersonal, "%" + query.toLowerCase() + "%", pageable);
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

}
