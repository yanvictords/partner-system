package br.com.partner.services.facades.impl;

import br.com.partner.models.Campaign;
import br.com.partner.repositories.CampaignRepository;
import br.com.partner.services.facades.CampaignValidityFacade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignValidityFacadeImpl implements CampaignValidityFacade {

    private final CampaignRepository campaignRepository;

    public CampaignValidityFacadeImpl(final CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public List<Campaign> processAllCampaignExpiryDates(final Campaign newCampaign) {
        var campaignsAsc =
                campaignRepository.findAllNotExpiredByLessOrEqualsExpiryDateAsc(newCampaign.getExpiryDate());

        campaignsAsc.stream()
                .map(campaign -> plusExpiryDateAndPlusOnNewCampaignExpiryDateEquals(campaign, newCampaign))
                .reduce(this::compareExpiryDateAndPlusOnEquals);

        return campaignsAsc;
    }

    private Campaign plusExpiryDateAndPlusOnNewCampaignExpiryDateEquals(final Campaign campaign,
                                                                        final Campaign newCampaign) {
        campaign.plusOneDayExpiryDate();
        if (campaign.expiresEqual(newCampaign)) {
            campaign.plusOneDayExpiryDate();
        }

        return campaign;
    }

    private Campaign compareExpiryDateAndPlusOnEquals(final Campaign firstCampaign,
                                                      final Campaign secondCampaign) {

        if (firstCampaign.expiresEqual(secondCampaign)) {
            secondCampaign.plusOneDayExpiryDate();
        }

        return secondCampaign;
    }
}
