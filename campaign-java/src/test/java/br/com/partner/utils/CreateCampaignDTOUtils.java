package br.com.partner.utils;

import br.com.partner.presenters.create_campaign.CreateCampaignDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.time.LocalDate.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateCampaignDTOUtils {

    public static CreateCampaignDTO create(final BigInteger id) {
        return CreateCampaignDTO.builder()
                .id(id)
                .name("Campaign Name")
                .teamID(new BigInteger("1"))
                .startDate(now())
                .expiryDate(now().plusDays(30))
                .build();
    }

}
