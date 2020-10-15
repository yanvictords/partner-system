package br.com.partner.services.impl;

import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignResponse;
import br.com.partner.services.CampaignService;
import br.com.partner.services.TeamService;
import br.com.partner.utils.mappers.ConsultTeamCampaignDTOMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

import static java.util.stream.Collectors.toList;

@Service
public class TeamServiceImpl implements TeamService {

    private final CampaignService campaignService;

    public TeamServiceImpl(final CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public ConsultTeamCampaignResponse findNotExpiredCampaignByTeamId(final BigInteger teamId) {
        final var campaigns = campaignService.findUnexpiredCampaignsByTeamId(teamId);
        final var mappedCampaigns = campaigns.stream()
                .map(ConsultTeamCampaignDTOMapper::map)
                .collect(toList());

        return new ConsultTeamCampaignResponse(mappedCampaigns);
    }

}
