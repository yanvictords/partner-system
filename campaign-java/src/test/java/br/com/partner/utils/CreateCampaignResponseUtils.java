package br.com.partner.utils;

import br.com.partner.presenters.create_campaign.CreateCampaignDTO;
import br.com.partner.presenters.create_campaign.CreateCampaignResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateCampaignResponseUtils {

    public static CreateCampaignResponse create(final CreateCampaignDTO createdCampaign,
                                                final List<CreateCampaignDTO> updatedCampaigns) {
        return CreateCampaignResponse.builder()
                .createdCampaign(createdCampaign)
                .updatedCampaigns(updatedCampaigns)
                .build();
    }

}

