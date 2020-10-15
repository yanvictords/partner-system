package br.com.partner.utils;

import br.com.partner.presenters.consult_team_campaign.ConsultTeamCampaignDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

import static java.math.BigInteger.valueOf;
import static java.time.LocalDate.now;
import static org.apache.commons.lang.math.RandomUtils.nextInt;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsultTeamCampaignDTOUtils {

    public static ConsultTeamCampaignDTO create(final String id) {
        return ConsultTeamCampaignDTO.builder()
                .id(new BigInteger(id))
                .name("Campaign name")
                .expiryDate(now().plusDays(20))
                .startDate(now())
                .teamID(valueOf(nextInt()))
                .build();
    }

}
