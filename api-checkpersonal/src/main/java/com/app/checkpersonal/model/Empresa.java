package com.app.checkpersonal.model;

import java.util.Date;

import javax.persistence.Column;
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
@Table(name = "empresa", schema = "proceso")
@ApiModel(description = "Modelo que guarda los datos de una Empresa.")
public class Empresa {

	@Id
	@SequenceGenerator(name = "empresa_generator_seq", sequenceName = "proceso.empresa_id_empresa_seq", allocationSize = 1)
	@GeneratedValue(generator = "empresa_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Empresa.")
	private Integer idEmpresa;
	
	@ApiModelProperty(value = "Ruc de la Empresa.")
	private String ruc;
	
	@ApiModelProperty(value = "Razón Social de la Empresa.")
	private String razonSocial;
	
	@ApiModelProperty(value = "Dirección de la Empresa.")
	private String direccion;
	
	@ApiModelProperty(value = "Ubigeo de la Empresa.")
	private String ubigeo;

	@ApiModelProperty(value = "Departamento de ubicación de la Empresa.")
	private String departamento;

	@ApiModelProperty(value = "Provincia de ubicación de la Empresa.")
	private String provincia;

	@ApiModelProperty(value = "Distrito de ubicación de la Empresa.")
	private String distrito;

	@ApiModelProperty(value = "Estado en Sunat de la Empresa.")
	private String estadoSunat;

	@ApiModelProperty(value = "Estado lógico del registro del Empresa.")
	private Boolean estado;

	@ApiModelProperty(value = "Fecha y hora del registro de la Empresa.")
	@Column(name = "created_at")
	private Date crAt;

	@ApiModelProperty(value = "Fecha y hora de la última actualización del registro de la Empresa.")
	@Column(name = "updated_at")
	private Date upAt;

	@ApiModelProperty(value = "Fecha y hora del borrado lógico del registro de la Empresa.")
	@Column(name = "deleted_at")
	private Date dtAt;

	@ApiModelProperty(value = "ID del Usuario que registra la Empresa.")
	@Column(name = "user_id_created")
	private Integer userCr;

	@ApiModelProperty(value = "ID del Usuario que realiza la última actualización de la Empresa.")
	@Column(name = "user_id_mod")
	private Integer userUp;

	@ApiModelProperty(value = "ID del Usuario que realiza el borrado lógico de la Empresa.")
	@Column(name = "user_id_delete")
	private Integer userDt;
}
