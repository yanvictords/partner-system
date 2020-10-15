package br.com.partner.controllers.impl;

import br.com.partner.controllers.TeamController;
import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignResponse;
import br.com.partner.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
public class TeamControllerImpl implements TeamController {

    private final TeamService teamService;

    public TeamControllerImpl(final TeamService teamService) {
        this.teamService = teamService;
    }

    @Override
    public ResponseEntity<ConsultTeamCampaignResponse> findAll(final BigInteger teamId) {
        return ResponseEntity.ok(teamService.findNotExpiredCampaignByTeamId(teamId));
    }

}
