package com.app.checkpersonal.controller.v1;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.checkpersonal.model.Personal;
import com.app.checkpersonal.service.CapacitacionService;
import com.app.checkpersonal.service.ExperienciaLaboralService;
import com.app.checkpersonal.service.FormacionAcademicaService;
import com.app.checkpersonal.service.HabilidadService;
import com.app.checkpersonal.service.PersonalService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/public", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PublicController {
	
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
	
	
	@ApiOperation(value = "Obtiene el expediente de un Personal"/*, authorizations = { @Authorization(value = "apiKey")}*/)
	@GetMapping(value = "/personal/{idTipoDocumento}/{numeroDocumento}")
	public ResponseEntity<?> findPersonal(
			@PathVariable(value = "idTipoDocumento") Integer idTipoDocumento,
			@PathVariable(value = "numeroDocumento") String numeroDocumento,
			HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		List<Personal> data = personalService.findByDocumento(idTipoDocumento, numeroDocumento);
		if(data.isEmpty()) {
			result.put("success", false);
			result.put("message", "No existe el Personal con número de documento: " + numeroDocumento);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		result.put("data_personal", data.get(0));
		result.put("data_fa", formacionAcademicaService.findAll(data.get(0).getIdPersonal(), ""));
		result.put("data_el", experienciaLaboralService.findAll(data.get(0).getIdPersonal(), ""));
		result.put("data_ha", habilidadService.findAll(data.get(0).getIdPersonal(), ""));
		result.put("data_ca", capacitacionService.findAll(data.get(0).getIdPersonal(), ""));
		
		result.put("success", true);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
