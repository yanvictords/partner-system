package br.com.partner.repositories;

import br.com.partner.models.Campaign;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public interface CampaignRepository {

    Campaign create(Campaign campaign);

    Campaign update(BigInteger campaignID, Campaign campaign);

    Campaign findByID(BigInteger campaignId);

    void deleteById(BigInteger campaignId);

    List<Campaign> findByTeamIdAndNotExpired(BigInteger campaignId);

    List<Campaign> findByIdsAndNotExpired(List<BigInteger> campaignId);

    List<Campaign> findAllNotExpiredByLessOrEqualsExpiryDateAsc(LocalDate expiryDate);

}
