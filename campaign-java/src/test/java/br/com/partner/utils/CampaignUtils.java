package br.com.partner.utils;

import br.com.partner.models.Campaign;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CampaignUtils {

    public static Campaign create() {
        return Campaign.builder()
                .name("Campaign name")
                .expiryDate(now().plusDays(20))
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

    public static Campaign create(final BigInteger id) {
        return Campaign.builder()
                .id(id)
                .name("Campaign name")
                .expiryDate(now().plusDays(20))
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

    public static Campaign create(final BigInteger id, final LocalDate expiryDate) {
        return Campaign.builder()
                .id(id)
                .name("Campaign name")
                .expiryDate(expiryDate)
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

}