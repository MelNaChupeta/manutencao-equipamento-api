package br.com.tads.manutencaoequipamentoapi.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import br.com.tads.manutencaoequipamentoapi.commom.Response;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.OrcamentoFormDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.RegistrarSolicitacaoDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.RejeitarDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.SolicitacaoDTO;
import br.com.tads.manutencaoequipamentoapi.entities.dto.solicitacao.SolicitacaoFormDTO;
import br.com.tads.manutencaoequipamentoapi.exceptions.ValidationException;
import br.com.tads.manutencaoequipamentoapi.services.SolicitacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitacao")
public class SolicitacaoController {
	
	@Autowired
	private SolicitacaoService service;

	@GetMapping
	@Operation(summary = "Buscar solicitações")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	public ResponseEntity<List<SolicitacaoDTO>> visualizar(
		@Parameter(required = false, description = "Data da Abertutea da solicitação", example = "2024-10-12") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dataAbertura,
		@Parameter(required = false, description = "Solicitações da data atual") @RequestParam(required = false) boolean hoje,
		@Parameter(required = false, description = "Todas as solicitações") @RequestParam(required = false) boolean todas)  {
		return ResponseEntity.ok().body(service.visualizar(todas, hoje, dataAbertura));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar uma solicitação")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	public ResponseEntity<SolicitacaoDTO> visualizar(@PathVariable("id") Long id)  {
		return ResponseEntity.ok().body(new SolicitacaoDTO(service.visualizar(id)));
	}

	@PostMapping("/registrar")
	@Operation(summary = "Registrar uma nova solicitação")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	@PreAuthorize("hasRole('CLIENTE')")                
	public ResponseEntity<SolicitacaoDTO> register(@RequestBody RegistrarSolicitacaoDTO dto)  {
		return ResponseEntity.status(201).body(new SolicitacaoDTO(service.registrar(dto)));
	}

	@PostMapping("/efetuarOrcamento/{id}")
	@Operation(summary = "Efetuar um orçamento")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	@PreAuthorize("hasRole('FUNCIONARIO')")                
	public ResponseEntity<SolicitacaoDTO> efetuarOrcamento(@Valid @RequestBody OrcamentoFormDTO solicitacaoDTO) throws ValidationException  {
		return ResponseEntity.ok().body(new SolicitacaoDTO(service.efetuarOrcamento(solicitacaoDTO)));
	}
	
	@PostMapping("/rejeitar")
	@Operation(summary = "Rejeitrar uma solicitação")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	@PreAuthorize("hasRole('CLIENTE')")                
	public ResponseEntity<SolicitacaoDTO> rejeitar(@Valid @RequestBody RejeitarDTO rejeitarDTO) throws ValidationException  {
		return ResponseEntity.ok().body(new SolicitacaoDTO(service.rejeitar(rejeitarDTO)));
	}

	@PostMapping("/aprovar/{id}")
	@Operation(summary = "Registrar uma nova solicitação")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	@PreAuthorize("hasRole('CLIENTE')")                
	public ResponseEntity<SolicitacaoDTO> aprovar(@PathVariable("id") Long id) throws ValidationException  {
		return ResponseEntity.ok().body(new SolicitacaoDTO(service.aprovar(id)));
	}
	
	@PostMapping("/resgatar/{id}")
	@Operation(summary = "Registrar uma nova solicitação")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SolicitacaoDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Response.class)) }), })
	@PreAuthorize("hasRole('CLIENTE')")                
	public ResponseEntity<SolicitacaoDTO> resgatar(@PathVariable("id") Long id) throws ValidationException  {
		return ResponseEntity.ok().body(new SolicitacaoDTO(service.resgatar(id)));
	}
}
