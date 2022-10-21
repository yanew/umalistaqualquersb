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
import org.springframework.data.domain.PageImpl;
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
	    public ResponseEntity<Page<ItemDto>> getAllItens(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
	        
	    	Page<Item> pageItem = itemService.findAll(pageable);
	    	
	    	List<Item> listaItem = pageItem.toList();
	    	
	    	List<ItemDto> listaItemDto = new ArrayList<>();
	    	for (Item item : listaItem) {
				ItemDto itemDto = new ItemDto();
				itemDto.setId(item.getId().toString());
				itemDto.setConteudo(item.getConteudo());
				itemDto.setIdUsuario(item.getUsuario().getId().toString());
				
				listaItemDto.add(itemDto);
			}
	    	
	    	final int start = (int)pageable.getOffset();
	    	final int end = Math.min((start + pageable.getPageSize()), listaItemDto.size());
	    	final Page<ItemDto> pageItemDto = new PageImpl<>(listaItemDto.subList(start, end), pageable, listaItemDto.size());
	    	
	    	return ResponseEntity.status(HttpStatus.OK).body(pageItemDto);
	    }
	    
	    @GetMapping("/{id}")
	    public ResponseEntity<Object> getItem(@PathVariable(value = "id") String id){
	        Optional<Item> itemOptional = itemService.findById(UUID.fromString(id));
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        
	        ItemDto itemDto = new ItemDto();
	        itemDto.setId(itemOptional.get().getId().toString());
	        itemDto.setConteudo(itemOptional.get().getConteudo());
	        itemDto.setIdUsuario(itemOptional.get().getUsuario().getId().toString());
	        
	        return ResponseEntity.status(HttpStatus.OK).body(itemDto);
	    }
		
	    @PostMapping
	    public ResponseEntity<Object> saveItem(@RequestBody @Valid ItemDto itemDto){
	        var item = new Item();
	        BeanUtils.copyProperties(itemDto, item);
	        item.setDataRegistro(LocalDateTime.now(ZoneId.of("UTC")));
	        
	        Item itemSave = itemService.save(item);
	        
	        ItemDto itemDtoRetorno = new ItemDto();
	        itemDtoRetorno.setId(itemSave.getId().toString());
	        itemDtoRetorno.setConteudo(itemSave.getConteudo());
	        itemDtoRetorno.setIdUsuario(itemSave.getUsuario().getId().toString());
	        
	        return ResponseEntity.status(HttpStatus.CREATED).body(itemDtoRetorno);
	    }
	    
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Object> deleteItem(@PathVariable(value = "id") String id){
	        Optional<Item> itemOptional = itemService.findById(UUID.fromString(id));
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        itemService.delete(itemOptional.get());
	        return ResponseEntity.status(HttpStatus.OK).body("Item removido com sucesso.");
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Object> updateItem(@PathVariable(value = "id") String id,
	                                                    @RequestBody @Valid ItemDto itemDto){
	        Optional<Item> itemOptional = itemService.findById(UUID.fromString(id));
	        if (!itemOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item não encontrado.");
	        }
	        var item = new Item();
	        BeanUtils.copyProperties(itemDto, item);
	        item.setUsuario(itemOptional.get().getUsuario());
	        item.setId(itemOptional.get().getId());
	        item.setDataRegistro(itemOptional.get().getDataRegistro());
	        
	        Item novoItem = itemService.save(item);
	        ItemDto novoItemDto = new ItemDto();
	        novoItemDto.setConteudo(novoItem.getConteudo());
	        novoItemDto.setIdUsuario(novoItem.getUsuario().getId().toString());
	        novoItemDto.setId(novoItem.getId().toString());
	        
	        return ResponseEntity.status(HttpStatus.OK).body(novoItemDto);
	    }
	    
	    
}
