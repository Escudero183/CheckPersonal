package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.FormacionAcademica;

@Repository
public interface FormacionAcademicaRepository extends JpaRepository<FormacionAcademica, Integer>{
	
	@Query("select t1 from FormacionAcademica t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroEstudio) like :query)")
	public Page<FormacionAcademica> findAllParams(Integer idPersonal, String query, Pageable pageable);
	
	@Query("select t1 from FormacionAcademica t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroEstudio) like :query)")
	public List<FormacionAcademica> findAll(Integer idPersonal, String query);
	
	@Query("select t1 from FormacionAcademica t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroEstudio) like :query)")
	public List<FormacionAcademica> findAll(Integer idPersonal, String query, Sort sort);

}
