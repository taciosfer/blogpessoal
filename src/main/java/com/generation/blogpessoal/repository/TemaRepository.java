package com.generation.blogpessoal.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.generation.blogpessoal.model.Tema;

@Repository //ATENÇÃO: NÃO ESQUECER DE ESPECIFICAR COM ESSA ANNOTATION!!!
public interface TemaRepository extends JpaRepository<Tema,Long> { //Entidade: Tema; Tipo do Id: Long.
	public List<Tema> findAllByDescricaoContainingIgnoreCase(String descricao);//Método personalizado; Retorna lista do tipo tema; Pega o que o cliente digitar ignorando maiúsculo/minúsculo;
}