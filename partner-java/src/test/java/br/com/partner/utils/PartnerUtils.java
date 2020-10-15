package br.com.partner.utils;

import br.com.partner.models.Partner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerUtils {

    public static Partner create(final BigInteger id, final String email) {
        return Partner.builder()
                .id(id)
                .name("Partner name")
                .email(email)
                .teamId(valueOf(nextInt()))
                .birthDate(now().minusYears(20))
                .build();
    }
}
