package com.api.umalistaqualquersb.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.umalistaqualquersb.dtos.ItemDto;
import com.api.umalistaqualquersb.dtos.UsuarioDto;
import com.api.umalistaqualquersb.models.Item;
import com.api.umalistaqualquersb.models.Usuario;
import com.api.umalistaqualquersb.services.UsuarioService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/usuario")
public class UsuarioController {

	 final UsuarioService usuarioService;

	    public UsuarioController(UsuarioService usuarioService) {
	        this.usuarioService = usuarioService;
	    }
		
	    @GetMapping
	    public ResponseEntity<Page<Usuario>> getAllUsuarios(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
	    	return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll(pageable));
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Object> getUsuario(@PathVariable(value = "id") UUID id){
	        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
	        if (!usuarioOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
	        }
	        
	        UsuarioDto usuarioDto = new UsuarioDto();
	        usuarioDto.setId(usuarioOptional.get().toString());
	        usuarioDto.setLogin(usuarioOptional.get().getLogin());
	        usuarioDto.setSenha(usuarioOptional.get().getSenha());
	        usuarioDto.setNome(usuarioOptional.get().getNome());
	        usuarioDto.setListaItensDto(this.parseListaItemParaItemDto(usuarioOptional.get().getListaItens()));
	        
	        return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
	    }
	    
	    @PostMapping
	    public ResponseEntity<Object> saveUsuario(@RequestBody @Valid UsuarioDto usuarioDto){
	        var usuario = new Usuario();
	        BeanUtils.copyProperties(usuarioDto, usuario);
	        usuario.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuario));
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteUsuario(@PathVariable(value = "id") UUID id){
	        Optional<Usuario> itemOptional = usuarioService.findById(id);
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
	        }
	        usuarioService.delete(itemOptional.get());
	        return ResponseEntity.status(HttpStatus.OK).body("Usuário removido com sucesso.");
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Object> updateUsuario(@PathVariable(value = "id") UUID id,
	                                                    @RequestBody @Valid UsuarioDto usuarioDto){
	        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
	        if (!usuarioOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
	        }
	        var usuario = new Usuario();
	        BeanUtils.copyProperties(usuarioDto, usuario);
	        usuario.setId(usuarioOptional.get().getId());
	        usuario.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
	        usuario.setListaItens(this.parseListasItemDtoParaItem(usuarioDto.getListaItensDto()));
	        
	        for (Item item : usuario.getListaItens()) {
	        	if(item.getUsuario()==null) {
	    	        item.setUsuario(usuarioOptional.get());
	        	}
	        	
	        	if(item.getDataRegistro()==null) {
	        		item.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));	
	        	}
			}
	        
	        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.save(usuario));
	    }
	    
	    @GetMapping("/{login}/{senha}")
	    public ResponseEntity<Object> findByLoginAndSenha(@PathVariable String login, @PathVariable String senha) {
	    	Optional<Usuario> usuario = usuarioService.findByLoginAndSenha(login, senha);
	    	if (!usuario.isPresent()) {
	    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado. Login ou senha incorreto.");
	    	}
	    	
	    	UsuarioDto usuarioDto = new UsuarioDto();
	    	usuarioDto.setId(usuario.get().getId().toString());
	    	usuarioDto.setLogin(usuario.get().getLogin());
	    	usuarioDto.setSenha(usuario.get().getSenha());
	    	usuarioDto.setNome(usuario.get().getNome());
	    	usuarioDto.setListaItensDto(this.parseListaItemParaItemDto(usuario.get().getListaItens()));
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(usuarioDto);
	    }
	    
	    private List<Item> parseListasItemDtoParaItem(List<ItemDto> listaItemDto){
	    	List<Item> listaItem = new ArrayList<>();
	    	
	    	for (ItemDto itemDto : listaItemDto) {
	    		Item item = new Item();
	    		item.setId(UUID.fromString(itemDto.getId()));
	    		item.setConteudo(itemDto.getConteudo());
	    		item.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
	    		
	    		listaItem.add(item);
			}
	    
	    	return listaItem;
	    }
	    
	    private List<ItemDto> parseListaItemParaItemDto(List<Item> listaItens){
	    	List<ItemDto> listaItemDto = new ArrayList<>();
	    	
	    	for (Item item : listaItens) {
				ItemDto itemDto = new ItemDto();
		
				itemDto.setId(item.getId().toString());
				item.setConteudo(item.getConteudo());
				itemDto.setIdUsuario(item.getUsuario().getId().toString());
	    
				listaItemDto.add(itemDto);
			}
	    	
	    	return listaItemDto;
	    }
	
}