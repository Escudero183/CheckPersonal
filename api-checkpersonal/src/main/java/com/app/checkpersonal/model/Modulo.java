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
@Table(name = "modulo", schema = "seguridad")
@ApiModel(description = "Modelo que guarda los datos de un Módulo.")
public class Modulo {
	
	@Id
	@Column(name = "modulo_id")
	@SequenceGenerator(name = "modulo_generator_seq", sequenceName = "seguridad.modulo_modulo_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "modulo_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Módulo.")
	private Integer idModulo;
	
	@ApiModelProperty(value = "Nombre del Módulo")
	private String nombre;
	
	@ApiModelProperty(value = "Descripción del Módulo")
	private String descripcion;
	
	@ApiModelProperty(value = "Abreviatura del nombre del Módulo")
	private String abreviatura;
	
	@ApiModelProperty(value = "Url del Módulo")
	private String url;
	
	@ApiModelProperty(value = "Icono del Módulo")
	private String icono;
	
	@ApiModelProperty(value = "Número de orden para listar los Módulos")
	private Integer nroOrden;
	
	@ApiModelProperty(value = "ID del módulo Padre si así lo requiere")
	private Integer moduloPadre;
	
	@ApiModelProperty(value = "Determina si es un Módulo es destacado")
	private boolean destacadoFlag;
	
	@ApiModelProperty(value = "Determina el color del Módulo")
	private String destacadoColor;
	
	@ApiModelProperty(value = "Determina el esquema del Módulo")
	private String esquema;
	
	@ApiModelProperty(value = "Estado lógico del registro del Módulo.")
	private boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "sistema_id")
	@ApiModelProperty(value = "Sistema asociado al módulo.")
	private Sistema sistema;

}
