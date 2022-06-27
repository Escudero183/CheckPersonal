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
@Table(name = "habilidad", schema = "personal")
@ApiModel(description = "Modelo que guarda las habilidades de un Personal.")
public class Habilidad {
	
	@Id
	@SequenceGenerator(name = "habilidad_generator_seq", sequenceName = "personal.habilidad_id_habilidad_seq", allocationSize = 1)
	@GeneratedValue(generator = "habilidad_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Habilidad.")
	private Integer idHabilidad;
	
	private Integer idPersonal;
	
	private String habilidad;
	
	private Integer nivel;
	
	private Boolean estado;

}
