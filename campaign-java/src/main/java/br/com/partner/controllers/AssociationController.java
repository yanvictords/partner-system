package br.com.partner.controllers;

import br.com.partner.parameters.create_association.CreateAssociationRequest;
import br.com.partner.presenters.consult_association.ConsultAssociationResponse;
import br.com.partner.presenters.create_association.CreateAssociationResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "associations")
@RequestMapping("/associations")
public interface AssociationController {

    @ApiOperation(value = "Consulta de associações de campanhas para um sócio",
            notes = "Endpoint responsável por consultar associações para um sócio.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "campaigns was found successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(OK)
    @GetMapping(value = "/partner/{id}", produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<ConsultAssociationResponse> findAllPartnerCampaignAssociation(
            @ApiParam(example = "123", required = true) @PathVariable("id") final BigInteger partnerId
    );

    @ApiOperation(value = "Associação de sócios a campanhas",
            notes = "Endpoint responsável por associar sócio a uma lista de campanhas de um time.",
            code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "link done successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/partner/{id}", produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<CreateAssociationResponse> associate(
            @ApiParam(example = "123", required = true) @PathVariable("id") final BigInteger partnerId,
            @Valid @RequestBody CreateAssociationRequest request);

}
