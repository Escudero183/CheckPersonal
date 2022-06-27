package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.Habilidad;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidad, Integer>{
	
	@Query("select t1 from Habilidad t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.habilidad) like :query)")
	public Page<Habilidad> findAllParams(Integer idPersonal, String query, Pageable pageable);
	
	@Query("select t1 from Habilidad t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.habilidad) like :query)")
	public List<Habilidad> findAll(Integer idPersonal, String query);
	
	@Query("select t1 from Habilidad t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.habilidad) like :query)")
	public List<Habilidad> findAll(Integer idPersonal, String query, Sort sort);

}
