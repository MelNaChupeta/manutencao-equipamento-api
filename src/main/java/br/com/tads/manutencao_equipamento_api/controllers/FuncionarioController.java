package br.com.tads.manutencao_equipamento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tads.manutencao_equipamento_api.commom.Response;
import br.com.tads.manutencao_equipamento_api.entities.dto.FuncionarioDTO;
import br.com.tads.manutencao_equipamento_api.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    @Autowired
	FuncionarioService service;

	@PostMapping("/registrar")
	@Operation(summary = "Registrar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Response> register(@RequestBody FuncionarioDTO funcionarioDTO) throws MessagingException {
		return ResponseEntity.ok().body(new Response(true,service.save(funcionarioDTO)));
	}
	
    @PutMapping("/alterar/{id}")
	@Operation(summary = "Alterar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Response> alterar(@RequestBody FuncionarioDTO funcionarioDTO , @PathParam("id") Long id) throws MessagingException {
		return ResponseEntity.ok().body(new Response(true,service.update(funcionarioDTO,id)));
	}
   
    @DeleteMapping("/deletar/{id}")
	@Operation(summary = "Alterar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Response> deletar(@PathParam("id") Long id) throws MessagingException {
		return ResponseEntity.ok().body(new Response(true,service.delete(id)));
	}
    
    @GetMapping("/buscar/{id}")
	@Operation(summary = "Buscar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Response> buscar(@PathParam("id") Long id) throws MessagingException {
		return ResponseEntity.ok().body(new Response(true,service.findById(id)));
	}
    
    @GetMapping("/buscar")
	@Operation(summary = "Buscar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Response> buscar() throws MessagingException {
		return ResponseEntity.ok().body(new Response(true,service.findAll()));
	}
}
