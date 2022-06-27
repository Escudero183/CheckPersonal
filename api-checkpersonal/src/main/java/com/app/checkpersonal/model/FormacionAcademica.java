package com.app.checkpersonal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Entity
@Table(name = "formacion_academica", schema = "personal")
@ApiModel(description = "Modelo que guarda la formación académica de un Personal.")
public class FormacionAcademica {
	
	@Id
	@SequenceGenerator(name = "formacion_academica_generator_seq", sequenceName = "personal.formacion_academica_id_formacion_academica_seq", allocationSize = 1)
	@GeneratedValue(generator = "formacion_academica_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo FormacionAcademica.")
	private Integer idFormacionAcademica;
	
	private Integer idPersonal;
	
	private String centroEstudio;
	
	private Integer anio;
	
	private String tipo;
	
	private Boolean estado;

}
