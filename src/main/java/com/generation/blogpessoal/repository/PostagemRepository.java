package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.blogpessoal.model.Postagem;

@Repository //Define que PostagemRepository é um repositório de queries dentro da tabela de postagens (tb_postagem).
public interface PostagemRepository extends JpaRepository<Postagem, Long> //A partir daqui se torna um repositório! Tudo passa por aqui pra fazer alterações no Banco de Dados. Traz todos os métodos que estão dentro do JPA!
{
	public List<Postagem> findAllByTituloContainingIgnoreCase(String titulo);
}
//Repository é uma Interface pra poder estender! Classe não dá pra estender!