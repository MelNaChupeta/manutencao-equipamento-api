package br.com.tads.manutencaoequipamentoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario.FuncionarioDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.funcionario.FuncionarioFormDTO;
import br.com.tads.manutencaoequipamentoapi.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
					@Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<FuncionarioDTO> register(@RequestBody FuncionarioFormDTO funcionarioDTO)  {
		return ResponseEntity.ok().body(new FuncionarioDTO(service.save(funcionarioDTO)));
	}
	
    @PutMapping("/alterar/{id}")
	@Operation(summary = "Alterar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<FuncionarioDTO> alterar(@RequestBody FuncionarioFormDTO funcionarioDTO , @PathParam("id") Long id) {
		return ResponseEntity.ok().body(new FuncionarioDTO(service.update(funcionarioDTO,id)));
	}
   
    @DeleteMapping("/deletar/{id}")
	@Operation(summary = "Deletar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<Boolean> deletar(@PathParam("id") Long id) {
		return ResponseEntity.ok().body(service.delete(id));
	}
    
    @GetMapping("/buscar/{id}")
	@Operation(summary = "Buscar funcionario")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<FuncionarioDTO> buscar(@PathParam("id") Long id){
		return ResponseEntity.ok().body(new FuncionarioDTO(service.findById(id)));
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
	public ResponseEntity<Response> buscar() {
		return ResponseEntity.ok().body(new Response(true,service.findAll()));
	}
}
