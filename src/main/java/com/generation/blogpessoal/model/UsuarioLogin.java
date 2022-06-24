package com.generation.blogpessoal.model; //Model que responde o cliente com infos que ele precisa sobre os usuários!

public class UsuarioLogin {
	
	private String nome;
	
	private String usuario;
	
	private String senha;
	
	private String token;
	
	private long id;
	
	private String foto;
//Método Construtor!!!
	public UsuarioLogin(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
		
	}
//Método Construtor Vazio Default!!!
	public UsuarioLogin() {
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
}