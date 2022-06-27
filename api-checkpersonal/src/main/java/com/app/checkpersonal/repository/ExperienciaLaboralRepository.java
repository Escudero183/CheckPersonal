package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.ExperienciaLaboral;

@Repository
public interface ExperienciaLaboralRepository extends JpaRepository<ExperienciaLaboral, Integer>{
	
	@Query("select t1 from ExperienciaLaboral t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroTrabajo) like :query or lower(t1.cargo) like :query)")
	public Page<ExperienciaLaboral> findAllParams(Integer idPersonal, String query, Pageable pageable);
	
	@Query("select t1 from ExperienciaLaboral t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroTrabajo) like :query or lower(t1.cargo) like :query)")
	public List<ExperienciaLaboral> findAll(Integer idPersonal, String query);
	
	@Query("select t1 from ExperienciaLaboral t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.centroTrabajo) like :query or lower(t1.cargo) like :query)")
	public List<ExperienciaLaboral> findAll(Integer idPersonal, String query, Sort sort);

}
