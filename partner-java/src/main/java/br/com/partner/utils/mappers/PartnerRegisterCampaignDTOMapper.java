package br.com.partner.utils.mappers;

import br.com.partner.presenters.clients.consult_team_campaign.ConsultTeamCampaignDTO;
import br.com.partner.presenters.partner_register.PartnerRegisterCampaignDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.utils.BeanUtilsConfig.getBean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerRegisterCampaignDTOMapper {

    public static PartnerRegisterCampaignDTO map(final ConsultTeamCampaignDTO consultTeamCampaignDTO) {
        final var objectMapper = getBean(ObjectMapper.class);
        return objectMapper.convertValue(consultTeamCampaignDTO, PartnerRegisterCampaignDTO.class);
    }

}