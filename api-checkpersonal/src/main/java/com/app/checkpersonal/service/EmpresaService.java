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

import com.app.checkpersonal.model.Empresa;
import com.app.checkpersonal.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	public Empresa insert(Empresa tipoDocumento) {
		return empresaRepository.save(tipoDocumento);
	}
	
	public void update(Empresa item) {
		empresaRepository.save(item);
	}
	
	public void delete (Empresa item) {
		empresaRepository.save(item);		
	}
	
	public List<Empresa> findAll() {
		return (List<Empresa>) empresaRepository.findAll();
	}
	
	public List<Empresa> findByRuc(String ruc) {
		return (List<Empresa>) empresaRepository.findByRuc(ruc);
	}
	
	public Empresa findById(Integer id) {
		return empresaRepository.findById(id).orElse(null);
	}
	
	public List<Empresa> findAll(String query, String sortBy) {
		Sort sort;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();
			sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
		} else {
			sort = Sort.by(Direction.ASC, "idEmpresa");
		}
		return empresaRepository.findAll("%" + query.toLowerCase() + "%", sort);
	}

	public HashMap<String, Object> findAll(String query, int page, int limit, String sortBy) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Pageable pageable;
		if (!sortBy.equals("")) {
			String sortColumn = sortBy.split("\\|")[0];
			String sortDirection = sortBy.split("\\|")[1].toUpperCase();

			Sort sort = Sort.by(sortDirection.equals("DESC") ? Direction.DESC : Direction.ASC, sortColumn);
			pageable = PageRequest.of(page - 1, limit, sort);

		} else {
			Sort sort = Sort.by(Direction.ASC, "idEmpresa");
			pageable = PageRequest.of(page - 1, limit, sort);

		}
		Page<Empresa> data = empresaRepository.findAllParams("%" + query.toLowerCase() + "%", pageable);
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
