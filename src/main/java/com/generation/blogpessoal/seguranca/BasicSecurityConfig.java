package com.generation.blogpessoal.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired //"Injeção de dependência";
	private UserDetailsService userDetailsService;
	
	@Override //Explicita que se trata de uma sobreescrita de método;
	protected void configure(AuthenticationManagerBuilder auth) throws Exception { //'throws' para tratativa de erros!
		auth.userDetailsService(userDetailsService);
		auth.inMemoryAuthentication().withUser("root").password(passwordEncoder().encode("root")).authorities("ROLE_USER");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/users/login").permitAll() //Endpoint liberado para clientes!
		.antMatchers("/users/register").permitAll() //Endpoint liberado para clientes!
		.anyRequest().authenticated() //Todas as demais requisições deverão serem autenticadas! A chave deverá ser passada no header!
		.and().httpBasic() //Usar o padrão 'Basic' para geração do Token!
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Não criará sessão http!!! É uma das funções de uma API Rest (??)
		.and().cors() //Habilita o 'cors' (??)
		.and().csrf().disable(); //'csrf' é uma arquitetura (??)
	}
}