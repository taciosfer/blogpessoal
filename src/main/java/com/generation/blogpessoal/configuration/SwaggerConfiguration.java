package com.generation.blogpessoal.configuration;

import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration //Classe do tipo 'configuração';
public class SwaggerConfiguration {
	
	@Bean //Indica a invocação desse método e o gerenciamento do objeto retornado por ele (injeção em qualquer ponto da aplicação!)
	public OpenAPI springBlogPessoalOpenAPI() { //Cria Objeto da Classe 'OpenAPI', que gera documentação no Swagger seguindo a spec 'OpenAPI';
		return new OpenAPI().info(new Info().title("Projeto para Blog Pessoal").description("Generation Brasil") //Nome/Título, Descrição e Versão da API;
			.version("v0.0.1").license(new License().name("Generation Brasil").url("https://brazil.generation.org/")) //Nome e Link da licença da API;
			.contact(new Contact().name("Tacio S Ferreira").url("https://github.com/taciosfer").email("taciosfer@gmail.com"))) //Nome, Site e Email do dev;
			.externalDocs(new ExternalDocumentation().description("GitHub").url("https://github.com/taciosfer/")); //Ref. para docum. externas!
	}
	
	@Bean
	public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() { //Personalização das Http Responses do Swagger;
		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> { //Loop de leitura de recursos/paths que retorna o caminho de cada endpoint;
				ApiResponses apiResponses = operation.getResponses(); //Loop que identifica qual método Http está sendo executado em cada endpoint;
				//Acima: cria Obj. Classe ApiResponses; Abaixo: adição novas respostas no endpoint;
				apiResponses.addApiResponse("200",createApiResponse("Sucesso."));
				apiResponses.addApiResponse("201",createApiResponse("Objeto Persistido!"));
				apiResponses.addApiResponse("204",createApiResponse("Objeto Excluído!!!!"));
				apiResponses.addApiResponse("400",createApiResponse("Erro na Requisição."));
				apiResponses.addApiResponse("401",createApiResponse("Acesso Não Autorizado!"));
				apiResponses.addApiResponse("404",createApiResponse("Objeto Não Encontrado!!!!"));
				apiResponses.addApiResponse("500",createApiResponse("Erro na Aplicação."));
			}));
		};
	}
	
	private ApiResponse createApiResponse(String message) { //Método 'create' add uma msg em cada resposta Http!
		return new ApiResponse().description(message);
	}
}