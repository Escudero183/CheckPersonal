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
@Table(name = "capacitacion", schema = "personal")
@ApiModel(description = "Modelo que guarda las capacitaciones de un Personal.")
public class Capacitacion {
	
	@Id
	@SequenceGenerator(name = "capacitacion_generator_seq", sequenceName = "personal.capacitacion_id_capacitacion_seq", allocationSize = 1)
	@GeneratedValue(generator = "capacitacion_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Capacitacion.")
	private Integer idCapacitacion;	
	
	private Integer idPersonal;
	
	private String centroCertifica;
	
	private String capacitacion;
	
	private Date fechaEmision;
	
	private Integer cantidadHoras;
	
	private Boolean estado;

}
