package br.com.partner.services.impl.facades;

import br.com.partner.models.Partner;
import br.com.partner.presenters.clients.consult_team_campaign.ConsultTeamCampaignDTO;

import java.util.List;

public interface AssociationFacade {

    List<ConsultTeamCampaignDTO> updateCampaignAssociations(Partner partner);

}
