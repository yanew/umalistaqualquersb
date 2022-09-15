package com.api.umalistaqualquersb.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ItemDto {

	@NotBlank
    @Size(max = 1000)
    private String conteudo;
	
	public String getConteudo() {
		return conteudo;
	}
	
	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	
}
