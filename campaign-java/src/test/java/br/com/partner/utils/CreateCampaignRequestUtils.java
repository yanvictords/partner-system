package br.com.partner.utils;

import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateCampaignRequestUtils {

    public static CreateCampaignRequest create(final LocalDate expiryDate) {
        return CreateCampaignRequest.builder()
                .name("Campaign Name")
                .teamID(new BigInteger("1"))
                .expiryDate(expiryDate)
                .build();
    }

}
