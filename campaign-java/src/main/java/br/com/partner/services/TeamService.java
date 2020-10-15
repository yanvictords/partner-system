package br.com.partner.services;

import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignResponse;

import java.math.BigInteger;

public interface TeamService {

    ConsultTeamCampaignResponse findNotExpiredCampaignByTeamId(BigInteger teamId);

}
