package br.com.tads.manutencaoequipamentoapi.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.tads.manutencaoequipamentoapi.models.projection.VwReceitaCategoriaProjection;
import br.com.tads.manutencaoequipamentoapi.models.projection.VwReceitaPeriodoProjection;
import br.com.tads.manutencaoequipamentoapi.services.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/receita")
public class ReceitaController {
    
	@Autowired
	private ReceitaService service;

    @GetMapping("/categoria")
	@Operation(summary = "Buscar relatorio de receitas por categoria")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VwReceitaCategoriaProjection.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<List<VwReceitaCategoriaProjection>> buscarReceitaPorCategoria(
		@Parameter(required = false, description = "filtro de categoria") @RequestParam(required = false) Long idCategoria
	)  {
		return ResponseEntity.ok().body(service.findByCategoria(idCategoria));
	}
    
    @GetMapping("/periodo")
	@Operation(summary = "Buscar relatorio de receitas por categoria")
	@SecurityRequirement(name = "bearerAuth")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Sucesso na requisição", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VwReceitaPeriodoProjection.class)) }),
			@ApiResponse(responseCode = "400", description = "Parametros inv\u00E1lidos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "403", description = "Não Autorizado", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "500", description = "Erro Interno do Servidor", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }),
			@ApiResponse(responseCode = "504", description = "Timeout", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Exception.class)) }), })
	public ResponseEntity<List<VwReceitaPeriodoProjection>> buscarReceitaPorPeriodo(
		@Parameter(required = false, example = "2024-10-12") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dataFinal,
		@Parameter(required = false, example = "2024-10-12") @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(required = false) LocalDate dataInicial
	)  {
		return ResponseEntity.ok().body(service.findByPeriodo(dataInicial, dataFinal));
	}
}
