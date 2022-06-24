package com.generation.blogpessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest {
	
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {
		usuarioRepository.deleteAll();
	}
//Para TODOS: REQUEST depois RESPONSE (padrão!)
	@Test
	@Order(1)
	@DisplayName("Cadastrar um usuário!")
	public void deveCriarUmUsuario() { //Precisa ser exatamente a mesma sequência do construtor!!! 
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,"Paulo Antunes","paulo_antunes@email.com.br","12345678","https://i.imgur.com/JR7kUFU.jpg"));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/users/register",HttpMethod.POST,corpoRequisicao,Usuario.class); //CADASTROS=POST
		assertEquals(HttpStatus.CREATED,corpoResposta.getStatusCode()); //"Foi criado ou não?"
		assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome()); //"Nome está igual?"
		assertEquals(corpoRequisicao.getBody().getUsuario(),corpoResposta.getBody().getUsuario()); //"Usuário está igual?"
	}
	
	@Test
	@Order(2)
	@DisplayName("Não deve permitir usuários duplicados!!")
	public void naoDeveDuplicarUsuario() { //Atenção com Nome e Usuário! //Chama UsuarioService, chama método 'Cadastra'... 
		usuarioService.cadastrarUsuario(new Usuario(0L,"Maria da Silva","maria_silva@email.com.br","12345678","https://i.imgur.com/T12NIp9.jpg"));
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,"Maria da Silva","maria_silva@email.com.br","12345678","https://i.imgur.com/T12NIp9.jpg"));
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/users/register",HttpMethod.POST,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.BAD_REQUEST,corpoResposta.getStatusCode()); //Se der BAD_REQUEST, significa que não deixou cadastrar USUÁRIO duplicado!
	}
	
	@Test
	@Order(3)
	@DisplayName("Atualizar um usuário!!!")
	public void deveAtualizarUmUsuario() { //Pegar usuário que já existe ("persistência" de dados)
		Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,"Juliana Andrews","juliana_andrews@email.com.br","juliana123","https://i.imgur.com/yDRVeK7.jpg"));
		Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),"Juliana Andrews Ramos","juliana_ramos@email.com.br","juliana123","https://i.imgur.com/yDRVeK7.jpg");
		HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate); //Já feita a solicitação, enviou atualização e responde com usuário atualizado! Lembrar que é o BD H2 (volátil!)
		ResponseEntity<Usuario> corpoResposta = testRestTemplate.withBasicAuth("root","root").exchange("/users/update",HttpMethod.PUT,corpoRequisicao,Usuario.class);
		assertEquals(HttpStatus.OK,corpoResposta.getStatusCode());
		assertEquals(corpoRequisicao.getBody().getNome(),corpoResposta.getBody().getNome());
		assertEquals(corpoRequisicao.getBody().getUsuario(),corpoResposta.getBody().getUsuario());
	} //PRECISA CADASTRAR USUÁRIO EM TODOS OS MÉTODOS PORQUE O BANCO DE DADOS QUE ESTÁ SENDO USADO É O H2 - O VOLÁTIL!!! 
	
	@Test
	@Order(4)
	@DisplayName("Listar todos os Usuários!!!!")
	public void deveListarTodosUsuarios() {
		usuarioService.cadastrarUsuario(new Usuario(0L,"Sabrina Sanches","sabrina_sanches@email.com.br","sabrina123","https://i.imgur.com/5M2p5Wb.jpg"));
		usuarioService.cadastrarUsuario(new Usuario(0L,"Ricardo Marques","ricardo_marques@email.com.br","ricardo123","https://i.imgur.com/Sk5SjWE.jpg"));
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root","root").exchange("/users/all",HttpMethod.GET,null,String.class);
		assertEquals(HttpStatus.OK,resposta.getStatusCode()); //Se conseguir listar = Status de 'ok'!
	}

	@Test
	@Order(5)
	@DisplayName("Listar Um Usuário Específico")
	public void deveListarApenasUmUsuario() {
		Optional<Usuario> usuarioBusca = usuarioService.cadastrarUsuario(new Usuario(0L,"Laura Santolia","laura_santolia@email.com.br","laura12345","https://i.imgur.com/EcJG8kB.jpg"));
		ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("root","root").exchange("/users/"+usuarioBusca.get().getId(),HttpMethod.GET,null,String.class);
		assertEquals(HttpStatus.OK,resposta.getStatusCode());
	}
		
	@Test
	@Order(6)
	@DisplayName("Login do Usuário")
	public void deveAutenticarUsuario() {
		usuarioService.cadastrarUsuario(new Usuario(0L,"Marisa Souza","marisa_souza@email.com.br","12345678","https://i.imgur.com/T12NIp9.jpg"));
		HttpEntity<UsuarioLogin> corpoRequisicao = new HttpEntity<UsuarioLogin>(new UsuarioLogin("marisa_souza@email.com.br","12345678"));
		ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate.exchange("/users/login",HttpMethod.POST,corpoRequisicao,UsuarioLogin.class);
		assertEquals(HttpStatus.OK,corpoResposta.getStatusCode());
	}
}