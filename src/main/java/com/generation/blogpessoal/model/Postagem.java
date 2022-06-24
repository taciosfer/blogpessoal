package com.generation.blogpessoal.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//Criado um Objeto de Postagem!
@Entity //Indica p/ Spring que o Objeto criado vai ser tornar uma tabela no banco de dados.
@Table(name = "tb_postagem") //Indica o nome que a tabela vai ter.
public class Postagem
{
	@Id //Indica que o campo 'id' será uma chave primária.
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Equivale ao AUTO_INCREMENT do MySQL.
	private long id;
	@NotNull //Não pode ser campo vazio; @NotBlank, atualização, não pode ser vazio e nem espaços!
	@Size(min = 5, max = 500)
	private String titulo;
	@NotNull
	@Size(min = 10, max = 500)
	private String texto;
	@Temporal(TemporalType.TIMESTAMP)
	private Date data = new java.sql.Date(System.currentTimeMillis());
	@ManyToOne //'Várias (postagens) para Um (tema)'!
	@JsonIgnoreProperties("postagem") //Propriedade que será ignorada dentro do 'Tema'!
	private Tema tema; //31-05, Criação da Classe 'Tema' + Get/Set dela;
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	//Depois de todos os atributos definidos, Source->Generate Getters/Setters:
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getTitulo()
	{
		return titulo;
	}
	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}
	public String getTexto()
	{
		return texto;
	}
	public void setTexto(String texto)
	{
		this.texto = texto;
	}
	public Date getData()
	{
		return data;
	}
	public void setData(Date data)
	{
		this.data = data;
	}
	public Tema getTema() {
		return tema;
	}
	public void setTema(Tema tema) {
		this.tema = tema;
	}
}
//Tudo que é feito no Spring é feito por meio de Anotações/Annotations!