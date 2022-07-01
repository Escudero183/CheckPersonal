/**
 * 
 */
package com.app.checkpersonal.controller.v1;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author Linygn Escudero
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/v1/multimedia", produces = { MediaType.APPLICATION_JSON_VALUE })
public class MultimediaController {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MultimediaController.class);
    // private static String rootPath = "/opt/GORESAMSUITE/GSENCUESTA";
    private static String rootPath = "D:\\\\RepositorioApps\\CheckPersonal\\";
	
	@GetMapping(value = "/byUrl")	
	public void findByUrl(
			@RequestParam(value = "url", required = false, defaultValue = "") String url,
			@RequestParam(value = "isthumb", required = false, defaultValue = "true") Boolean isthumb,
			HttpServletResponse response,
			HttpServletRequest request) {
		String ruta = rootPath;
		if(isthumb) {
			String[] parts = url.split("\\.");
			url = parts[0] + "_thumbnail." + parts[1];
		}
		try {
			File file = new File(ruta + url);
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			byte[] byteArray;
			byteArray = FileUtils.readFileToByteArray(file);
			response.setContentType(mimeType);
			response.setHeader("Content-Length", String.valueOf(file.length()));
			response.setHeader("Content-Disposition", "inline; filename=\"multimedia\"");
			response.getOutputStream().write(byteArray);
			response.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


    public static String randomString(int longitud) {
        // El banco de caracteres
        String banco = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        // La cadena en donde iremos agregando un carácter aleatorio
        String cadena = "";
        for (int x = 0; x < longitud; x++) {
            int indiceAleatorio = numberRandominRange(0, banco.length() - 1);
            char caracterAleatorio = banco.charAt(indiceAleatorio);
            cadena += caracterAleatorio;
        }
        return cadena;
    }

    public static int numberRandominRange(int minimo, int maximo) {
        // nextInt regresa en rango pero con límite superior exclusivo, por eso sumamos 1
        return ThreadLocalRandom.current().nextInt(minimo, maximo + 1);
    }
}
