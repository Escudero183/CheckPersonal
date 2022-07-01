package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.Personal;

@Repository
public interface PersonalRepository extends JpaRepository<Personal, Integer>{
	
	@Query("select t1 from Personal t1 Where t1.estado = true and (lower(t1.nombres) like :query or lower(t1.apellidoPaterno) like :query or lower(t1.apellidoMaterno) like :query or lower(t1.numeroDocumento) like :query) and (:sexo='-1' or (:sexo!='-1' and t1.sexo=:sexo))")
	public Page<Personal> findAllParams(String sexo, String query, Pageable pageable);
	
	@Query("select t1 from Personal t1 Where t1.estado = true and (lower(t1.nombres) like :query or lower(t1.apellidoPaterno) like :query or lower(t1.apellidoMaterno) like :query or lower(t1.numeroDocumento) like :query)")
	public List<Personal> findAll(String query);
	
	@Query("select t1 from Personal t1 Where t1.estado = true and (lower(t1.nombres) like :query or lower(t1.apellidoPaterno) like :query or lower(t1.apellidoMaterno) like :query or lower(t1.numeroDocumento) like :query) and (:sexo='-1' or (:sexo!='-1' and t1.sexo=:sexo))")
	public List<Personal> findAll(String sexo, String query, Sort sort);
	
	@Query("select t1 from Personal t1 Where t1.estado = true and t1.tipoDocumento.idTipoDocumento=:idTipoDocumento and t1.numeroDocumento=:nroDocumento")
	public List<Personal> findByDocumento(Integer idTipoDocumento, String nroDocumento);

}
