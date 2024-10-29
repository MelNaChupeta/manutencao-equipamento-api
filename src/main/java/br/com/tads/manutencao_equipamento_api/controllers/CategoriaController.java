package br.com.tads.manutencao_equipamento_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tads.manutencao_equipamento_api.commom.Response;
import br.com.tads.manutencao_equipamento_api.entities.dto.ClienteDTO;
import br.com.tads.manutencao_equipamento_api.repositories.ClienteRepository;
import br.com.tads.manutencao_equipamento_api.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    
	@Autowired
	ClienteService service;

	
}
