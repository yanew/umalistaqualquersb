package com.api.umalistaqualquersb.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ItemDto {

    private String id;
	
	@NotBlank
    @Size(max = 1000)
    private String conteudo;
	
	@NotBlank
	private String idUsuario;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
	public String getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	
}
