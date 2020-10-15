package br.com.partner.utils;

import br.com.partner.presenters.consult_campaign.ConsultCampaignResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsultCampaignResponseUtils {

    public static ConsultCampaignResponse create(final BigInteger id) {
        return ConsultCampaignResponse.builder()
                .id(id)
                .name("Campaign name")
                .expiryDate(now().plusDays(30))
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

}