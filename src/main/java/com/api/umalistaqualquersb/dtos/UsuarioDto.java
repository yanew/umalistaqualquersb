package com.api.umalistaqualquersb.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UsuarioDto {

	@NotBlank
	@Size(max = 100)
    private String nome;
	
	@NotBlank
	@Size(max = 20)
    private String login;
	
	@NotBlank
	@Size(max = 8)
    private String senha;

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
	
}