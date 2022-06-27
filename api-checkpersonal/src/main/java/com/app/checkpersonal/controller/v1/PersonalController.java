package com.app.checkpersonal.controller.v1;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.checkpersonal.config.exception.ApiCheckPersonalException;
import com.app.checkpersonal.model.Capacitacion;
import com.app.checkpersonal.model.ExperienciaLaboral;
import com.app.checkpersonal.model.FormacionAcademica;
import com.app.checkpersonal.model.Habilidad;
import com.app.checkpersonal.model.Personal;
import com.app.checkpersonal.service.CapacitacionService;
import com.app.checkpersonal.service.ExperienciaLaboralService;
import com.app.checkpersonal.service.FormacionAcademicaService;
import com.app.checkpersonal.service.HabilidadService;
import com.app.checkpersonal.service.PersonalService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author Linygn Escudero
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/personal", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PersonalController {
	
	@Autowired
	private PersonalService personalService;
	
	@Autowired
	private HabilidadService habilidadService;
	
	@Autowired
	private FormacionAcademicaService formacionAcademicaService;
	
	@Autowired
	private CapacitacionService capacitacionService;
	
	@Autowired
	private ExperienciaLaboralService experienciaLaboralService;
	
	/* Servicios para la Entidad Personal */
	@ApiOperation(value = "Crea un Personal", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/personal")
	public ResponseEntity<?> savePersonal(@RequestBody Personal personal, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		personal.setEstado(true);
		Personal data = personalService.insert(personal);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas los Personales", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/personal")	
	public ResponseEntity<?> findAllPersonal(
			@RequestParam(value = "tipo", required = false, defaultValue = "grilla") String tipo,
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		if(tipo.equals("grilla")) {
			int maxPage = 10;
			
			if (page == -1 && limit == -1 && "".equals(sortBy)) {
				page = 1;
				limit = maxPage;		
			}else if (limit != -1 && page == -1) {
				page = 1;
			} else if (page != -1 && limit == -1) {
				limit = maxPage;
			}
			
			return new ResponseEntity<>(personalService.findAll(query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(personalService.findAll(query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de un Personal", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/personal/{idPersonal}")
	public ResponseEntity<?> findPersonal(@PathVariable(value = "idPersonal") Integer idPersonal, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Personal data = personalService.findById(idPersonal);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Personal con código: " + idPersonal);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza un Personal", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/personal")
	public ResponseEntity<?> updatePersonal (@RequestBody Personal personal, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Personal data = personalService.findById(personal.getIdPersonal());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Personal con código: " + personal.getIdPersonal());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			personal.setEstado(true);
			personalService.update(personal);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", personal);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina un Personal", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/personal/{idPersonal}")
	public ResponseEntity<?> deletePersonal (@PathVariable(value = "idPersonal") Integer idPersonal, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Personal data = personalService.findById(idPersonal);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe el Personal con código: " + idPersonal);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			personalService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad Personal */
	
	/* Servicios para la Entidad Habilidad */
	@ApiOperation(value = "Crea una Habilidad", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/habilidad")
	public ResponseEntity<?> saveHabilidad(@RequestBody Habilidad habilidad, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		habilidad.setEstado(true);
		Habilidad data = habilidadService.insert(habilidad);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas los Habilidades", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/habilidad")	
	public ResponseEntity<?> findAllHabilidad(
			@RequestParam(value = "idPersonal", required = false, defaultValue = "-1") Integer idPersonal,
			@RequestParam(value = "tipo", required = false, defaultValue = "grilla") String tipo,
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		if(tipo.equals("grilla")) {
			int maxPage = 10;
			
			if (page == -1 && limit == -1 && "".equals(sortBy)) {
				page = 1;
				limit = maxPage;		
			}else if (limit != -1 && page == -1) {
				page = 1;
			} else if (page != -1 && limit == -1) {
				limit = maxPage;
			}
			
			return new ResponseEntity<>(habilidadService.findAll(idPersonal, query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(habilidadService.findAll(idPersonal, query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de una Habilidad", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/habilidad/{idHabilidad}")
	public ResponseEntity<?> findHabilidad(@PathVariable(value = "idHabilidad") Integer idHabilidad, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Habilidad data = habilidadService.findById(idHabilidad);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Habilidad con código: " + idHabilidad);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza una Habilidad", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/habilidad")
	public ResponseEntity<?> updateHabilidad (@RequestBody Habilidad habilidad, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Habilidad data = habilidadService.findById(habilidad.getIdHabilidad());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Habilidad con código: " + habilidad.getIdHabilidad());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			habilidad.setEstado(true);
			habilidadService.update(habilidad);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", habilidad);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina una Habilidad", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/habilidad/{idHabilidad}")
	public ResponseEntity<?> deleteHabilidad (@PathVariable(value = "idHabilidad") Integer idHabilidad, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Habilidad data = habilidadService.findById(idHabilidad);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Habilidad con código: " + idHabilidad);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			habilidadService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad Habilidad */
	
	/* Servicios para la Entidad FormacionAcademica */
	@ApiOperation(value = "Crea un registro de Formacion Academica de un Personal", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/formacion_academica")
	public ResponseEntity<?> saveFormacionAcademica(@RequestBody FormacionAcademica formacionAcademica, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		formacionAcademica.setEstado(true);
		FormacionAcademica data = formacionAcademicaService.insert(formacionAcademica);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista toda la Formacion Academica de un personal", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/formacion_academica")	
	public ResponseEntity<?> findAllFormacionAcademica(
			@RequestParam(value = "idPersonal", required = false, defaultValue = "-1") Integer idPersonal,
			@RequestParam(value = "tipo", required = false, defaultValue = "grilla") String tipo,
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		if(tipo.equals("grilla")) {
			int maxPage = 10;
			
			if (page == -1 && limit == -1 && "".equals(sortBy)) {
				page = 1;
				limit = maxPage;		
			}else if (limit != -1 && page == -1) {
				page = 1;
			} else if (page != -1 && limit == -1) {
				limit = maxPage;
			}
			
			return new ResponseEntity<>(formacionAcademicaService.findAll(idPersonal, query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(formacionAcademicaService.findAll(idPersonal, query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de una Formacion Academica", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/formacion_academica/{idFormacionAcademica}")
	public ResponseEntity<?> findFormacionAcademica(@PathVariable(value = "idFormacionAcademica") Integer idFormacionAcademica, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		FormacionAcademica data = formacionAcademicaService.findById(idFormacionAcademica);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la FormacionAcademica con código: " + idFormacionAcademica);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza una Formacion Academica", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/formacion_academica")
	public ResponseEntity<?> updateFormacionAcademica (@RequestBody FormacionAcademica formacionAcademica, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		FormacionAcademica data = formacionAcademicaService.findById(formacionAcademica.getIdFormacionAcademica());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la FormacionAcademica con código: " + formacionAcademica.getIdFormacionAcademica());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			formacionAcademica.setEstado(true);
			formacionAcademicaService.update(formacionAcademica);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", formacionAcademica);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina una Formacion Academica", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/formacion_academica/{idFormacionAcademica}")
	public ResponseEntity<?> deleteFormacionAcademica (@PathVariable(value = "idFormacionAcademica") Integer idFormacionAcademica, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		FormacionAcademica data = formacionAcademicaService.findById(idFormacionAcademica);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la FormacionAcademica con código: " + idFormacionAcademica);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			formacionAcademicaService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad FormacionAcademica */
	
	/* Servicios para la Entidad Capacitacion */
	@ApiOperation(value = "Crea una Capacitacion", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/capacitacion")
	public ResponseEntity<?> saveCapacitacion(@RequestBody Capacitacion capacitacion, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		capacitacion.setEstado(true);
		Capacitacion data = capacitacionService.insert(capacitacion);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas las Capacitaciones", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/capacitacion")	
	public ResponseEntity<?> findAllCapacitacion(
			@RequestParam(value = "idPersonal", required = false, defaultValue = "-1") Integer idPersonal,
			@RequestParam(value = "tipo", required = false, defaultValue = "grilla") String tipo,
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		if(tipo.equals("grilla")) {
			int maxPage = 10;
			
			if (page == -1 && limit == -1 && "".equals(sortBy)) {
				page = 1;
				limit = maxPage;		
			}else if (limit != -1 && page == -1) {
				page = 1;
			} else if (page != -1 && limit == -1) {
				limit = maxPage;
			}
			
			return new ResponseEntity<>(capacitacionService.findAll(idPersonal, query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(capacitacionService.findAll(idPersonal, query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de una Capacitacion", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/capacitacion/{idCapacitacion}")
	public ResponseEntity<?> findCapacitacion(@PathVariable(value = "idCapacitacion") Integer idCapacitacion, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Capacitacion data = capacitacionService.findById(idCapacitacion);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Capacitacion con código: " + idCapacitacion);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza una Capacitacion", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/capacitacion")
	public ResponseEntity<?> updateCapacitacion (@RequestBody Capacitacion capacitacion, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Capacitacion data = capacitacionService.findById(capacitacion.getIdCapacitacion());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Capacitacion con código: " + capacitacion.getIdCapacitacion());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			capacitacion.setEstado(true);
			capacitacionService.update(capacitacion);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", capacitacion);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina una Capacitacion", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/capacitacion/{idCapacitacion}")
	public ResponseEntity<?> deleteCapacitacion (@PathVariable(value = "idCapacitacion") Integer idCapacitacion, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		Capacitacion data = capacitacionService.findById(idCapacitacion);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Capacitacion con código: " + idCapacitacion);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			capacitacionService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad Capacitacion */
	
	/* Servicios para la Entidad ExperienciaLaboral */
	@ApiOperation(value = "Crea una Experiencia Laboral", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/experiencia_laboral")
	public ResponseEntity<?> saveExperienciaLaboral(@RequestBody ExperienciaLaboral experienciaLaboral, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		experienciaLaboral.setEstado(true);
		ExperienciaLaboral data = experienciaLaboralService.insert(experienciaLaboral);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas las Experiencias Laborales de un personal", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/experiencia_laboral")	
	public ResponseEntity<?> findAllExperienciaLaboral(
			@RequestParam(value = "idPersonal", required = false, defaultValue = "-1") Integer idPersonal,
			@RequestParam(value = "tipo", required = false, defaultValue = "grilla") String tipo,
			@RequestParam(value = "query", required = false, defaultValue = "") String query,
			@RequestParam(value = "page", required = false, defaultValue = "-1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "-1") int limit,
			@RequestParam(value = "sortBy", required = false, defaultValue = "") String sortBy,
			HttpServletRequest request) {
		
		if(tipo.equals("grilla")) {
			int maxPage = 10;
			
			if (page == -1 && limit == -1 && "".equals(sortBy)) {
				page = 1;
				limit = maxPage;		
			}else if (limit != -1 && page == -1) {
				page = 1;
			} else if (page != -1 && limit == -1) {
				limit = maxPage;
			}
			
			return new ResponseEntity<>(experienciaLaboralService.findAll(idPersonal, query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(experienciaLaboralService.findAll(idPersonal, query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de una Experiencia Laboral", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/experiencia_laboral/{idExperienciaLaboral}")
	public ResponseEntity<?> findExperienciaLaboral(@PathVariable(value = "idExperienciaLaboral") Integer idExperienciaLaboral, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		ExperienciaLaboral data = experienciaLaboralService.findById(idExperienciaLaboral);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Experiencia Laboral con código: " + idExperienciaLaboral);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza una Experiencia Laboral", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/experiencia_laboral")
	public ResponseEntity<?> updateExperienciaLaboral (@RequestBody ExperienciaLaboral experienciaLaboral, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		ExperienciaLaboral data = experienciaLaboralService.findById(experienciaLaboral.getIdExperienciaLaboral());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Experiencia Laboral con código: " + experienciaLaboral.getIdExperienciaLaboral());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			experienciaLaboral.setEstado(true);
			experienciaLaboralService.update(experienciaLaboral);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", experienciaLaboral);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina una Experiencia Laboral", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/experiencia_laboral/{idExperienciaLaboral}")
	public ResponseEntity<?> deleteExperienciaLaboral (@PathVariable(value = "idExperienciaLaboral") Integer idExperienciaLaboral, HttpServletRequest request){
		HashMap<String, Object> result = new HashMap<>();
		ExperienciaLaboral data = experienciaLaboralService.findById(idExperienciaLaboral);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Experiencia Laboral con código: " + idExperienciaLaboral);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			experienciaLaboralService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiCheckPersonalException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad ExperienciaLaboral */

}
