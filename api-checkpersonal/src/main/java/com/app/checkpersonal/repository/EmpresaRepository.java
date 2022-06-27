package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Integer>{
	
	@Query("select t1 from Empresa t1 Where t1.estado = true and (lower(t1.ruc) like :query or lower(t1.razonSocial) like :query or lower(t1.direccion) like :query or lower(t1.departamento) like :query or lower(t1.provincia) like :query or lower(t1.distrito) like :query)")
	public Page<Empresa> findAllParams(String query, Pageable pageable);
	
	@Query("select t1 from Empresa t1 Where t1.estado = true and (lower(t1.ruc) like :query or lower(t1.razonSocial) like :query or lower(t1.direccion) like :query or lower(t1.departamento) like :query or lower(t1.provincia) like :query or lower(t1.distrito) like :query)")
	public List<Empresa> findAll(String query);
	
	@Query("select t1 from Empresa t1 Where t1.estado = true and (lower(t1.ruc) like :query or lower(t1.razonSocial) like :query or lower(t1.direccion) like :query or lower(t1.departamento) like :query or lower(t1.provincia) like :query or lower(t1.distrito) like :query)")
	public List<Empresa> findAll(String query, Sort sort);
	
	@Query("select t1 from Empresa t1 Where t1.estado = true and t1.ruc=:ruc")
	public List<Empresa> findByRuc(String ruc);

}
