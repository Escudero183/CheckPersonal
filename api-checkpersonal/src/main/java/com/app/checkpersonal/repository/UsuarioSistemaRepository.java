/**
 * 
 */
package com.app.checkpersonal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.checkpersonal.model.UsuarioSistema;

/**
 * @author Linygn Escudero
 *
 */

@Repository
public interface UsuarioSistemaRepository extends JpaRepository<UsuarioSistema, Integer> {
	
	@Query("select us from UsuarioSistema us Where us.usuario.idUsuario = :idUsuario")
	public List<UsuarioSistema> findByUsuario(Integer idUsuario);

}
