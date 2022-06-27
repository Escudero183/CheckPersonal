/**
 * 
 */
package com.app.checkpersonal.model;

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

/**
 * @author Linygn Escudero
 *
 */
@Data
@Entity
@Table(name = "sistema", schema = "seguridad")
@ApiModel(description = "Modelo que guarda los datos de un Sistema.")
public class Sistema {
	
	@Id
	@Column(name = "sistema_id")
	@SequenceGenerator(name = "sistemas_generator_seq", sequenceName = "seguridad.sistemas_sistemas_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "sistemas_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Sistema.")
	private Integer idSistema;
	
	@ApiModelProperty(value = "Nombre del Sistema")
	private String nombre;
	
	@ApiModelProperty(value = "Path del Servicio asociado al Sistema")
	private String pathApi;
	
	@ApiModelProperty(value = "Icono del Sistema")
	private String icono;
	
	@ApiModelProperty(value = "Estado lógico del registro del Sistema.")
	private Boolean estado;

}
