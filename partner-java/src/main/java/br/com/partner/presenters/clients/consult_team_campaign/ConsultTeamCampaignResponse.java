package br.com.partner.presenters.clients.consult_team_campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultTeamCampaignResponse {

    private List<ConsultTeamCampaignDTO> campaigns;

}
