package br.com.partner.utils;

import br.com.partner.presenters.partner_register.PartnerRegisterCampaignDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.time.LocalDate.now;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartnerRegisterCampaignDTOUtils {

    public static PartnerRegisterCampaignDTO create(final BigInteger id) {

        return PartnerRegisterCampaignDTO.builder()
                .id(id)
                .name("Campaign name")
                .expiryDate(now().plusDays(20))
                .startDate(now())
                .build();
    }

}
