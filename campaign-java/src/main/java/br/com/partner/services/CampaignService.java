package br.com.partner.services;

import br.com.partner.models.Campaign;
import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import br.com.partner.presenters.consult_campaign.ConsultCampaignResponse;
import br.com.partner.presenters.create_campaign.CreateCampaignResponse;
import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;

import java.math.BigInteger;
import java.util.List;

public interface CampaignService {

    CreateCampaignResponse createCampaign(CreateCampaignRequest request);

    UpdateCampaignResponse updateCampaign(BigInteger campaignId, UpdateCampaignRequest request);

    ConsultCampaignResponse findCampaign(BigInteger campaignId);

    void deleteCampaign(BigInteger campaignId);

    List<Campaign> findUnexpiredCampaignsByTeamId(BigInteger teamId);

    List<Campaign> findUnexpiredCampaignsByIds(List<BigInteger> campaignIds);

}
