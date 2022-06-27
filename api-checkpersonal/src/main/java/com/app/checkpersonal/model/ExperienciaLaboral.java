package com.app.checkpersonal.model;

import java.util.Date;

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
@Table(name = "experiencia_laboral", schema = "personal")
@ApiModel(description = "Modelo que guarda la experiencia laboral de un Personal.")
public class ExperienciaLaboral {
	
	@Id
	@SequenceGenerator(name = "experiencia_laboral_generator_seq", sequenceName = "personal.experiencia_laboral_id_experiencia_laboral_seq", allocationSize = 1)
	@GeneratedValue(generator = "experiencia_laboral_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo ExperienciaLaboral.")
	private Integer idExperienciaLaboral;
	
	private Integer idPersonal;
	
	private String centroTrabajo;
	
	private String cargo;
	
	private Date fechaAlta;
	
	private Date fechaBaja;
	
	private Boolean estado;

}
