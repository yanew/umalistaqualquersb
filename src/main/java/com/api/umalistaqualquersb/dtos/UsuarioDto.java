package com.api.umalistaqualquersb.dtos;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDto {

	private String id;
	
	@NotBlank
	@Size(max = 100)
    private String nome;
	
	@NotBlank
	@Size(max = 20)
    private String login;
	
	@NotBlank
	@Size(max = 8)
    private String senha;
	
	private List<ItemDto> listaItensDto;
	
	public UsuarioDto() {
		this.listaItensDto = new ArrayList<>();
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public List<ItemDto> getListaItensDto() {
		return listaItensDto;
	}
	
	public void setListaItensDto(List<ItemDto> listaItensDto) {
		this.listaItensDto = listaItensDto;
	}
	
}