package com.app.checkpersonal.model;

import java.util.Date;

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

@Data
@Entity
@Table(name = "personal", schema = "personal")
@ApiModel(description = "Modelo que guarda los datos de un Personal o Trabajador.")
public class Personal {
	
	@Id
	@SequenceGenerator(name = "personal_generator_seq", sequenceName = "personal.personal_id_personal_seq", allocationSize = 1)
	@GeneratedValue(generator = "personal_generator_seq", strategy = GenerationType.SEQUENCE)
	@ApiModelProperty(value = "Clave primaria del Modelo Personal.")
	private Integer idPersonal;	
	
	private String numeroDocumento;
	
	private String nombres;
	
	private String apellidoPaterno;
	
	private String apellidoMaterno;
	
	private String idUbigeo;
	
	private String direccion;
	
	private String sexo;
	
	private String celular;
	
	private String correo;
	
	private Date fechaNacimiento;
	
	private String presentacion;
	
	private String foto;
	
	private String banner;
	
	private Boolean estado;
	
	@ManyToOne
	@JoinColumn(name = "id_tipo_documento")
	private TipoDocumento tipoDocumento;

}
