package br.com.partner.controllers;

import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import br.com.partner.presenters.consult_campaign.ConsultCampaignResponse;
import br.com.partner.presenters.create_campaign.CreateCampaignResponse;
import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Api(tags = "campaigns")
@RequestMapping("/campaigns")
public interface CampaignController {

    @ApiOperation(value = "Criação de Campanha", notes = "Endpoint responsável por criar campanhas.", code = 201)
    @ApiResponses({
            @ApiResponse(code = 201, message = "campaign created successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(CREATED)
    @PostMapping(value = "/", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<CreateCampaignResponse> createCampaign(@Valid @RequestBody CreateCampaignRequest request);

    @ApiOperation(value = "Atualização de Campanha", notes = "Endpoint responsável por atualizar campanhas.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "campaign updated successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(OK)
    @PutMapping(value = "/{id}", consumes = {APPLICATION_JSON_VALUE}, produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<UpdateCampaignResponse> updateCampaign(
            @ApiParam(example = "1", required = true) @PathVariable("id") final BigInteger campaignId,
            @Valid @RequestBody UpdateCampaignRequest request);

    @ApiOperation(value = "Consulta de Campanha", notes = "Endpoint responsável por consultar campanhas.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "campaign found successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(OK)
    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<ConsultCampaignResponse> findCampaign(
            @ApiParam(example = "1", required = true) @PathVariable("id") final BigInteger campaignId
    );

    @ApiOperation(value = "Deleção de Campanha", notes = "Endpoint responsável por deletar campanhas.", code = 204)
    @ApiResponses({
            @ApiResponse(code = 204, message = "campaign deleted successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<Void> deleteCampaign(
            @ApiParam(example = "1", required = true) @PathVariable("id") final BigInteger campaignId
    );
}