package com.generation.blogpessoal.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController //Define que se trata de uma Classe Controller da API (onde ficam os Endpoints).
@RequestMapping("/posts") //Caminho/URI a ser usado para acessar essa Classe (indica um Endpoint); sem espaços, tudo minúsculo.
@CrossOrigin("*") //Para que essa Classe aceite requisições de diferentes origens sejam aceitas na minha aplicação p.ex.: Angular, React.
public class PostagemController
{
	@Autowired //Garante que todos os serviços da Interface sejam acessados pela Controller; 'Injeção de Dependência': transferência de responsabilidade (não precisa preocupar em instanciar o repository toda vez que precisar usar ele).
	private PostagemRepository repository;//Objeto + Apelido; Usa private pra rodar só aqui dentro (encapsulamento); 
	private PostagemRepository postagemRepository;
	@GetMapping //Método que vai ser executado quando for dado o Request usando /posts; Indica o verbo a ser usado nessa requisição/endpoint.
	public ResponseEntity<List<Postagem>> buscaPostagem()//Usa list pra resposta poder ser uma ou mais postagens. buscaPostagem é só o nome, pode ser qualquer nome.
	{//pra pegar dados é GET... 
		return ResponseEntity.ok(repository.findAll()); //No geral vai ter um de cada, só o GET pode ter mais de um usando subrotas!
	}
	@GetMapping("/{id}") //Segundo método GET para fazer postagem por id! USA SUBROTA! Vai chamar /posts/id
	public ResponseEntity<Postagem> buscaPostagemPorId(@PathVariable long id) //Altera o apelido; Função preparada para receber qualquer valor!
	{ 
		return repository.findById(id) //Não é findAll porque só pode trazer um único resultado!!!
			.map(resposta -> ResponseEntity.ok(resposta)) //Lambda precisa de 'caso dê certo' e 'caso dê errado'; .map precisa de orElse!
			.orElse(ResponseEntity.notFound().build());
	}
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> buscaPostagemPorTitulo(@PathVariable String titulo)
	{
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	@PostMapping
	public ResponseEntity<Postagem> adicionaPostagem(@Valid @RequestBody Postagem postagem)//Instancia novo objeto postagem;
	{//CREATED = Status 201!
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));//Pode passar qlqr status, não precisa ser CREATED, mas usa cada status pra cada coisa que se quer executar!
	}
	@PutMapping
	public ResponseEntity<Postagem> colocaPostagem(@Valid @RequestBody Postagem postagem)//Instancia novo objeto postagem;
	{
		return postagemRepository.findById(postagem.getId()) //Pode passar qlqr status, não precisa ser CREATED, mas usa cada status pra cada coisa que se quer executar!
			.map(resposta -> ResponseEntity.ok().body(postagemRepository.save(postagem)))
			.orElse(ResponseEntity.notFound().build());
	}
	@DeleteMapping("/{id}")
	public void deletado(@PathVariable long id)
	{
		repository.deleteById(id);
	}
}