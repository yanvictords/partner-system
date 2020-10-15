package br.com.partner.utils;

import br.com.partner.presenters.partner_register.PartnerRegisterCampaignDTO;
import br.com.partner.presenters.partner_register.PartnerRegisterResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.math.RandomUtils;

import java.util.List;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PartnerRegisterResponseUtils {

    public static PartnerRegisterResponse create(final String email,
                                                 final List<PartnerRegisterCampaignDTO> campaigns) {

        return PartnerRegisterResponse.builder()
                .name("Partner name")
                .email(email)
                .teamId(valueOf(RandomUtils.nextInt()))
                .birthDate(now().minusYears(20))
                .campaigns(campaigns)
                .build();
    }

}
