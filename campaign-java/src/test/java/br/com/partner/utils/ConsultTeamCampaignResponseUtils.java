package br.com.partner.utils;

import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignDTO;
import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsultTeamCampaignResponseUtils {

    public static ConsultTeamCampaignResponse create(final List<ConsultTeamCampaignDTO> campaigns) {
        return ConsultTeamCampaignResponse.builder()
                .campaigns(campaigns)
                .build();

    }
}