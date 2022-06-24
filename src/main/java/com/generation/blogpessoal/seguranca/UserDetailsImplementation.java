package com.generation.blogpessoal.seguranca;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImplementation implements UserDetails {
	
	private static final long serialVersionUID = 1L; //Apenas para controle interno!
	
	private String username;
	private String password;
	private List<GrantedAuthority> authorities; //Estava AUSENTE NOS VÍDEOS!!! ATENÇÃO!!!
	
	public UserDetailsImplementation(Usuario user) {
		this.username = user.getUsuario();
		this.password = user.getSenha();
	}
	
	public UserDetailsImplementation() {
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities; //> > > ATENÇÃO!
	}

	@Override
	public String getPassword() {
		return password; //Assim como na linha 14!
	}

	@Override
	public String getUsername() {
		return username; //Assim como na linha 13!
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //Quer dizer que a 'conta' não expirou!
	}

	@Override
	public boolean isAccountNonLocked() {
		return true; //Quer dizer que a 'conta' não está bloqueada!
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true; //Quer dizer que as 'credenciais' não expiraram!
	}

	@Override
	public boolean isEnabled() {
		return true; //Quer dizer que a 'conta' está ativa!
	}
}