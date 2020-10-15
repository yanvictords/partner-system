package br.com.partner.utils.mappers;

import br.com.partner.models.Campaign;
import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static br.com.partner.utils.BeanUtilsConfig.getBean;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateCampaignResponseMapper {

    public static UpdateCampaignResponse map(final Campaign campaign) {
        final var objectMapper = getBean(ObjectMapper.class);
        return objectMapper.convertValue(campaign, UpdateCampaignResponse.class);
    }
}
