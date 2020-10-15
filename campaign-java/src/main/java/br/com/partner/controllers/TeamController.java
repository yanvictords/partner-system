package br.com.partner.controllers;

import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = "teams")
@RequestMapping("/teams")
public interface TeamController {

    @ApiOperation(value = "Consulta de Campanha por time",
            notes = "Endpoint responsável por consultar campanhas pelo identificador do time.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "campaigns found successfully"),
            @ApiResponse(code = 422, message = "cannot process request"),
            @ApiResponse(code = 502, message = "some service is not available")
    })
    @ResponseStatus(OK)
    @GetMapping(value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    ResponseEntity<ConsultTeamCampaignResponse> findAll(
            @ApiParam(example = "1", required = true) @PathVariable("id") final BigInteger teamId);

}