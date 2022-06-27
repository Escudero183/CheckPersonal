/**
 * 
 */
package com.app.checkpersonal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "perfil", schema = "seguridad")
@ApiModel(description = "Modelo que guarda los datos de un Perfil.")
public class Perfil {
	
	@Id
	@Column(name = "perfil_id")
	@SequenceGenerator(name = "perfil_generator_seq", sequenceName = "seguridad.perfil_perfil_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "perfil_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Perfil.")
	private Integer idPerfil;
	
	@ApiModelProperty(value = "Nombre del Perfil")
	private String nombre;
	
	@ApiModelProperty(value = "Icono del Perfil")
	private String icono;
	
	@ApiModelProperty(value = "Estado lógico del registro del Perfil.")
	private boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "sistema_id")
	@ApiModelProperty(value = "Sistema asociado al módulo.")
	private Sistema sistema;

}
