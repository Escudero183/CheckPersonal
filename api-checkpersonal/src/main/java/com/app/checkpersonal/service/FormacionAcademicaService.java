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

import com.app.checkpersonal.model.FormacionAcademica;
import com.app.checkpersonal.repository.FormacionAcademicaRepository;

@Service
public class FormacionAcademicaService {
	
	@Autowired
	private FormacionAcademicaRepository formacionAcademicaRepository;
	
	public FormacionAcademica insert(FormacionAcademica item) {
		return formacionAcademicaRepository.save(item);
	}
	
	public void update(FormacionAcademica item) {
		formacionAcademicaRepository.save(item);
	}
	
	public void delete (FormacionAcademica item) {
		formacionAcademicaRepository.save(item);		
	}
	
	public List<FormacionAcademica> findAll(Integer idPersonal, String query) {
		return (List<FormacionAcademica>) formacionAcademicaRepository.findAll(idPersonal, "%" + query + "%");
	}
	
	public FormacionAcademica findById(Integer id) {
		return formacionAcademicaRepository.findById(id).orElse(null);
	}
	
	public List<FormacionAcademica> findAll(Integer idPersonal, String query, String sortBy) {
		Sort sort;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();
			sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
		} else {
			sort = Sort.by(Direction.ASC, "idFormacionAcademica");
		}
		return formacionAcademicaRepository.findAll(idPersonal, "%" + query.toLowerCase() + "%", sort);
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
			Sort sort = Sort.by(Direction.ASC, "idFormacionAcademica");
			pageable = PageRequest.of(page - 1, limit, sort);

		}
		Page<FormacionAcademica> data = formacionAcademicaRepository.findAllParams(idPersonal, "%" + query.toLowerCase() + "%", pageable);
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
