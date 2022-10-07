package com.api.umalistaqualquersb.services;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.umalistaqualquersb.models.Usuario;
import com.api.umalistaqualquersb.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	 final UsuarioRepository usuarioRepository;

	    public UsuarioService(UsuarioRepository usuarioRepository) {
	        this.usuarioRepository = usuarioRepository;
	    }

	    @Transactional
	    public Usuario save(Usuario usuario) {
	        return usuarioRepository.save(usuario);
	    }

	    public Page<Usuario> findAll(Pageable pageable) {
	        return usuarioRepository.findAll(pageable);
	    }

	    public Optional<Usuario> findById(UUID id) {
	        return usuarioRepository.findById(id);
	    }
	    
	    public Optional<Usuario> findByLoginAndSenha(String login, String senha) {
	    	return usuarioRepository.findByLoginAndSenha(login, senha);
	    }

	    @Transactional
	    public void delete(Usuario usuario) {
	    	usuarioRepository.delete(usuario);
	    }
	
}
