package br.com.partner.utils;

import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UpdateCampaignResponseUtils {

    public static UpdateCampaignResponse create(final BigInteger id, final LocalDate expiryDate) {
        return UpdateCampaignResponse.builder()
                .id(id)
                .name("Campaign name")
                .expiryDate(expiryDate)
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

}
