package com.generation.blogpessoal.model;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity //Para o JPA entender que se trata de uma tabela e mapeie;
@Table(name = "tb_usuario") //Para essa tabela ter o nome designado dentro do banco de dados;
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull //Para não receber nenhum nome nulo;
	@Size(min = 2, max = 100)
	private String nome;
	
	@Schema(example = "email@email.com.br")
	@NotNull(message = "Preenchimento obrigatório!")
	@Size(min = 14, max = 100)
	@Email(message = "Deve ser um email válido!")
	private String usuario; //O USUÁRIO É UM EMAIL!
	
	@NotBlank(message = "É obrigatória uma senha!")
	@Size(min = 6, max = 100)
	private String senha;//Usa string porque permite caracteres especiais ('array de caracteres')
	
	private String foto;
	
	//Junit #1, criar construtor nessa classe:
	@OneToMany(mappedBy = "usuario",cascade = CascadeType.REMOVE)
	@JsonIgnoreProperties("usuario")
	private List<Postagem> postagem;
	
	public Usuario(long id,String nome,String usuario,String senha,String foto) {
		this.id = id;
		this.nome = nome;
		this.usuario = usuario;
		this.senha = senha;
		this.foto = foto;
	}
	//Junit #2, criar construtor vazio (default de execução):
	public Usuario() {
		
	}
	//CTRL+3+GGAS: atalho para 'Generate Getters and Setters'!
	public long getId() { //O MALDITO DO 'Long'! > > > EXTREMA ATENÇÃO < < <
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public List<Postagem> getPostagem() {
		return postagem;
	}

	public void setPostagem(List<Postagem> postagem) {
		this.postagem = postagem;
	}
}