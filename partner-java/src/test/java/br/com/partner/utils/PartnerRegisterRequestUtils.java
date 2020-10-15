package br.com.partner.utils;

import br.com.partner.parameters.partner_register.PartnerRegisterRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerRegisterRequestUtils {

    public static PartnerRegisterRequest create(final String email) {
        return PartnerRegisterRequest.builder()
                .name("Partner name")
                .email(email)
                .teamId(valueOf(nextInt()))
                .birthDate(now().minusYears(20))
                .build();
    }
}
