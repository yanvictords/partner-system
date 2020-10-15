package br.com.partner.utils;

import br.com.partner.models.PartnerCampaign;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerCampaignUtils {

    public static PartnerCampaign create(final BigInteger campaignId, final BigInteger partnerId) {
        return PartnerCampaign.builder()
                .id(valueOf(nextInt()))
                .campaignId(campaignId)
                .partnerId(partnerId)
                .build();
    }

}
