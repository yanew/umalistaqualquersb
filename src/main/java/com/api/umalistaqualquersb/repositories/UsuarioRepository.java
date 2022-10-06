package com.api.umalistaqualquersb.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.umalistaqualquersb.models.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

	
	boolean existsByLoginAndSenha(String login, String senha);
	
	
}
