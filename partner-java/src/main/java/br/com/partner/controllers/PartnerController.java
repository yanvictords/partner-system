package br.com.partner.controllers;

import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "partners")
@RequestMapping("/partners")
public interface PartnerController {

    @ApiOperation(value = "Cadastro de sócio torcedor",
            notes = "Endpoint responsável por cadastrar novos sócio torcedores.",
            code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "socio created successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available"),
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<PartnerRegisterResponse> register(
            @Valid @RequestBody PartnerRegisterRequest request);

}