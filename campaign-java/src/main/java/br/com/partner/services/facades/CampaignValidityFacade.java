package br.com.partner.services.facades;

import br.com.partner.models.Campaign;

import java.util.List;

public interface CampaignValidityFacade {

    List<Campaign> processAllCampaignExpiryDates(final Campaign newCampaign);

}
