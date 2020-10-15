package br.com.partner.repositories;

import br.com.partner.models.PartnerCampaign;

import java.math.BigInteger;
import java.util.List;

public interface PartnerCampaignRepository {

    PartnerCampaign create(PartnerCampaign partnerCampaign);

    List<PartnerCampaign> findAllByPartnerId(BigInteger partnerID);

}