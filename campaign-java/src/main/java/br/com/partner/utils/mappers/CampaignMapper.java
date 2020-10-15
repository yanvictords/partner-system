package br.com.partner.utils.mappers;

import br.com.partner.models.Campaign;
import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static br.com.partner.utils.BeanUtilsConfig.getBean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CampaignMapper {

    public static Campaign map(final CreateCampaignRequest request) {
        final var objectMapper = getBean(ObjectMapper.class);
        final var campaign = objectMapper.convertValue(request, Campaign.class);
        campaign.setStartDate(LocalDate.now());
        return campaign;
    }

    public static Campaign map(final UpdateCampaignRequest request) {
        final var objectMapper = getBean(ObjectMapper.class);
        return objectMapper.convertValue(request, Campaign.class);
    }
}
