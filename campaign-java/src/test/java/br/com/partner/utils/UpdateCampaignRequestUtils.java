package br.com.partner.utils;

import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateCampaignRequestUtils {

    public static UpdateCampaignRequest create(final LocalDate expiryDate) {
        return UpdateCampaignRequest.builder()
                .name("Campaign Name")
                .teamID(new BigInteger("1"))
                .expiryDate(expiryDate)
                .build();
    }

}
