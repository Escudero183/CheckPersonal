package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.Capacitacion;

@Repository
public interface CapacitacionRepository extends JpaRepository<Capacitacion, Integer>{
	
	@Query("select t1 from Capacitacion t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.capacitacion) like :query or lower(t1.centroCertifica) like :query)")
	public Page<Capacitacion> findAllParams(Integer idPersonal, String query, Pageable pageable);
	
	@Query("select t1 from Capacitacion t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.capacitacion) like :query or lower(t1.centroCertifica) like :query)")
	public List<Capacitacion> findAll(Integer idPersonal, String query);
	
	@Query("select t1 from Capacitacion t1 Where t1.estado = true and (:idPersonal = -1 or (:idPersonal != -1 and t1.idPersonal =:idPersonal)) and (lower(t1.capacitacion) like :query or lower(t1.centroCertifica) like :query)")
	public List<Capacitacion> findAll(Integer idPersonal, String query, Sort sort);

}
