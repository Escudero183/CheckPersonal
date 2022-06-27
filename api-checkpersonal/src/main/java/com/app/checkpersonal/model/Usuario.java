/**
 * 
 */
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
import lombok.EqualsAndHashCode;

/**
 * @author Linygn Escudero
 *
 */
@Data
@Entity
@Table(name = "usuario", schema  = "seguridad")
@EqualsAndHashCode(callSuper = false)
@ApiModel(description = "Modelo que guarda los datos de un Usuario.")
public class Usuario {
	
	@Id
	@Column(name = "usuario_id")
	@SequenceGenerator(name = "usuario_generator_seq", sequenceName = "seguridad.usuario_usuario_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "usuario_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Usuario.")
	private Integer idUsuario;
	
	@ApiModelProperty(value = "Login del Usuario.")
	private String login;
	
	@ApiModelProperty(value = "Password del Usuario.")
	private String password;
	
	@ApiModelProperty(value = "Fecha de Alta del Usuario.")
	private Date fechaAlta;
	
	@ApiModelProperty(value = "Fecha de Baja del Usuario.")
	private Date fechaBaja;
	
	@ApiModelProperty(value = "Representa el estado de cuenta del Usuario.")
	private String estadoCuenta;
	
	@ApiModelProperty(value = "Email asociado a la cuenta del Usuario.")
	private String email;
	
	@ApiModelProperty(value = "Estado lógico del registro del Empresa.")
	private boolean estado;

}
