package com.api.umalistaqualquersb.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
import com.api.umalistaqualquersb.models.Item;
import com.api.umalistaqualquersb.models.Usuario;
import com.api.umalistaqualquersb.services.ItemService;
import com.api.umalistaqualquersb.services.UsuarioService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/item")
public class ItemController {

	 final ItemService itemService;
	 final UsuarioService usuarioService;

	    public ItemController(ItemService itemService, UsuarioService usuarioService) {
	        this.itemService = itemService;
	        this.usuarioService = usuarioService;
	    }
	    
	    @GetMapping
	    public ResponseEntity<Page<Item>> getAllItens(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
	        return ResponseEntity.status(HttpStatus.OK).body(itemService.findAll(pageable));
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id){
	        Optional<Item> itemOptional = itemService.findById(id);
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        return ResponseEntity.status(HttpStatus.OK).body(itemOptional.get());
	    }
		
	    @PostMapping
	    public ResponseEntity<Object> saveItem(@RequestBody @Valid ItemDto itemDto){
	        var item = new Item();
	        BeanUtils.copyProperties(itemDto, item);
	        item.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.save(item));
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteItem(@PathVariable(value = "id") UUID id){
	        Optional<Item> itemOptional = itemService.findById(id);
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        itemService.delete(itemOptional.get());
	        return ResponseEntity.status(HttpStatus.OK).body("Item removido com sucesso.");
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Object> updateItem(@PathVariable(value = "id") UUID id,
	                                                    @RequestBody @Valid ItemDto itemDto){
	        Optional<Item> itemOptional = itemService.findById(id);
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        var item = new Item();
	        BeanUtils.copyProperties(itemDto, item);
	        item.setId(itemOptional.get().getId());
	        item.setDataRegistro(itemOptional.get().getDataRegistro());
	        
	        Optional<Usuario> usuarioOptional = usuarioService.findById(UUID.fromString(itemDto.getIdUsuario()));
	        item.setUsuario(usuarioOptional.get());
	        
	        Item novoItem = itemService.save(item);
	        ItemDto novoItemDto = new ItemDto();
	        novoItemDto.setConteudo(novoItem.getConteudo());
	        novoItemDto.setIdUsuario(novoItem.getUsuario().getId().toString());
	        novoItemDto.setId(novoItem.getId().toString());
	        
	        return ResponseEntity.status(HttpStatus.OK).body(novoItemDto);
	    }
	    
	    
}
