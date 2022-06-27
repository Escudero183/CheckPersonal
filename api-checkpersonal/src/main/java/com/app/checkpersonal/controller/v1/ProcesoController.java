/**
 * 
 */
package com.app.checkpersonal.controller.v1;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.app.checkpersonal.config.exception.ApiMarathonException;
import com.app.checkpersonal.config.exception.RestException;
import com.app.checkpersonal.config.security.model.JwtUser;
import com.app.checkpersonal.model.Empresa;
import com.app.checkpersonal.service.EmpresaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author Linygn Escudero
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/proceso", produces = { MediaType.APPLICATION_JSON_VALUE })
public class ProcesoController {
	
	@Autowired
	private EmpresaService empresaService;
	
	/* Servicios para la Entidad Empresa */
	@ApiOperation(value = "Crea una Empresa", authorizations = {@Authorization(value = "apiKey") })	
	@PostMapping(value = "/empresa")
	public ResponseEntity<?> saveEmpresa(@RequestBody Empresa empresa, HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer idUser = 0;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			JwtUser userDetails = (JwtUser) auth.getPrincipal();
			idUser = userDetails.getId();

		} else {
			return new ResponseEntity<>(new RestException("No autorizado"), HttpStatus.UNAUTHORIZED);
		}
		
		HashMap<String, Object> result = new HashMap<>();
		
		/* VALIDACIONES */
		List<Empresa> existe = empresaService.findByRuc(empresa.getRuc());
		if(!existe.isEmpty()) {
			result.put("success", false);
			result.put("message", "Ya existe un registro con el RUC: " + empresa.getRuc());
			return new ResponseEntity<>(result, HttpStatus.CONFLICT); 
		}		
		
		empresa.setEstado(true);
		empresa.setCrAt(new java.sql.Timestamp(System.currentTimeMillis()));
		empresa.setUserCr(idUser);
		empresa.setUpAt(null);
		empresa.setDtAt(null);
		empresa.setUserUp(null);
		empresa.setUserDt(null);
		Empresa data = empresaService.insert(empresa);
		
		
		result.put("success", true);
		result.put("message", "Se ha registrado correctamente.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Lista todas las Empresas", authorizations = {@Authorization(value = "apiKey") })
	@GetMapping(value = "/empresa")	
	public ResponseEntity<?> findAllEmpresa(
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
			
			return new ResponseEntity<>(empresaService.findAll(query, page, limit, sortBy), HttpStatus.OK);
		}else {			
			return new ResponseEntity<>(empresaService.findAll(query, sortBy), HttpStatus.OK);
		}
	}
	
	@ApiOperation(value = "Obtiene datos de una Empresa", authorizations = { @Authorization(value = "apiKey")})
	@GetMapping(value = "/empresa/{idEmpresa}")
	public ResponseEntity<?> findEmpresa(@PathVariable(value = "idEmpresa") Integer idEmpresa, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<>();
		Empresa data = empresaService.findById(idEmpresa);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Empresa con código: " + idEmpresa);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		
		result.put("success", true);
		result.put("message", "Se ha encontrado el registro.");
		result.put("result", data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Actualiza una Empresa", authorizations = { @Authorization(value = "apiKey")})
	@PutMapping(value = "/empresa")
	public ResponseEntity<?> updateEmpresa (@RequestBody Empresa empresa, HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer idUser = 0;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			JwtUser userDetails = (JwtUser) auth.getPrincipal();
			idUser = userDetails.getId();

		} else {
			return new ResponseEntity<>(new RestException("No autorizado"), HttpStatus.UNAUTHORIZED);
		}
		
		HashMap<String, Object> result = new HashMap<>();
		Empresa data = empresaService.findById(empresa.getIdEmpresa());
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Empresa con código: " + empresa.getIdEmpresa());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			empresa.setEstado(true);
			empresa.setCrAt(data.getCrAt());
			empresa.setUserCr(data.getUserCr());
			empresa.setUpAt(new java.sql.Timestamp(System.currentTimeMillis()));
			empresa.setUserUp(idUser);
			empresa.setDtAt(data.getDtAt());
			empresa.setUserDt(data.getUserDt());
			empresaService.update(empresa);
			result.put("success", true);
			result.put("message", "Se ha actualizado los datos del registro.");
			result.put("result", empresa);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiMarathonException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}			
	}
	
	@ApiOperation(value = "Elimina una Empresa", authorizations = { @Authorization(value = "apiKey")})
	@DeleteMapping(value = "/empresa/{idEmpresa}")
	public ResponseEntity<?> deleteEmpresa (@PathVariable(value = "idEmpresa") Integer idEmpresa, HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Integer idUser = 0;
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			JwtUser userDetails = (JwtUser) auth.getPrincipal();
			idUser = userDetails.getId();

		} else {
			return new ResponseEntity<>(new RestException("No autorizado"), HttpStatus.UNAUTHORIZED);
		}
		
		HashMap<String, Object> result = new HashMap<>();
		Empresa data = empresaService.findById(idEmpresa);
		if(data == null) {
			result.put("success", false);
			result.put("message", "No existe la Empresa con código: " + idEmpresa);
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND); 
		}
		try {
			data.setEstado(false);
			data.setDtAt(new java.sql.Timestamp(System.currentTimeMillis()));
			data.setUserDt(idUser);
			empresaService.delete(data);
			result.put("success", true);
			result.put("message", "Se ha eliminado los datos del registro.");
			result.put("result", data);
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		} catch (Exception ex) {
			return new ResponseEntity<>(new ApiMarathonException(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	/* Fin Servicios para la Entidad Empresa */

}
