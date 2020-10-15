package br.com.partner.services.impl.facades.impl;

import br.com.partner.exceptions.BadGatewayException;
import br.com.partner.exceptions.FeignClientException;
import br.com.partner.exceptions.UnprocessableEntityException;
import br.com.partner.models.Partner;
import br.com.partner.parameters.clients.create_association.CreateAssociationRequest;
import br.com.partner.presenters.clients.consult_team_campaign.ConsultTeamCampaignDTO;
import br.com.partner.services.clients.CampaignClient;
import br.com.partner.services.impl.facades.AssociationFacade;
import feign.FeignException;
import feign.RetryableException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AssociationFacadeImpl implements AssociationFacade {

    private static final String PROBLEMS_TO_CONNECT_ON_CAMPAIGN_SERVICE = "Problema ao conectar com o serviço de campanhas.";
    private static final String REGISTERED_PARTNER_ERROR = "Já existe cadastro para o e-mail informado.";

    private final CampaignClient campaignClient;

    public AssociationFacadeImpl(final CampaignClient campaignClient) {
        this.campaignClient = campaignClient;
    }

    @Override
    public List<ConsultTeamCampaignDTO> updateCampaignAssociations(final Partner partner) {
        final var teamCampaigns = findAllCampaignByTeamId(partner.getTeamId());
        final var myCampaignIds = findAllCampaignIdByPartnerId(partner.getId());

        final var campaignIdsToUpdate = teamCampaigns.stream()
                .filter(campaign -> !myCampaignIds.contains(campaign.getId()))
                .map(ConsultTeamCampaignDTO::getId)
                .collect(toList());

        if (!campaignIdsToUpdate.isEmpty()) {
            associateCampaigns(partner.getId(), campaignIdsToUpdate);
        }

        if (!myCampaignIds.isEmpty()) {
            throw new UnprocessableEntityException(REGISTERED_PARTNER_ERROR);
        }

        return teamCampaigns;
    }

    private List<ConsultTeamCampaignDTO> findAllCampaignByTeamId(final BigInteger teamId) {
        try {
            final var consultTeamCampaignResponse = campaignClient.findAllCampaignByTeamId(teamId);
            return consultTeamCampaignResponse.getCampaigns();
        } catch (RetryableException re) {
            throw new BadGatewayException(PROBLEMS_TO_CONNECT_ON_CAMPAIGN_SERVICE);
        } catch (FeignException fe) {
            throw new FeignClientException(fe);
        }
    }

    private List<BigInteger> findAllCampaignIdByPartnerId(final BigInteger partnerId) {
        try {
            final var consultAssociationResponse = campaignClient.findAllCampaignByPartnerId(partnerId);
            return consultAssociationResponse.getCampaignIds();
        } catch (RetryableException re) {
            throw new BadGatewayException(PROBLEMS_TO_CONNECT_ON_CAMPAIGN_SERVICE);
        } catch (FeignException fe) {
            throw new FeignClientException(fe);
        }
    }

    private void associateCampaigns(final BigInteger partnerId,
                                    final List<BigInteger> campaignIds) {
        final var request = CreateAssociationRequest.builder()
                .campaignIds(campaignIds)
                .build();
        try {
            campaignClient.associateAllCampaign(partnerId, request);
        } catch (RetryableException re) {
            throw new BadGatewayException(PROBLEMS_TO_CONNECT_ON_CAMPAIGN_SERVICE);
        } catch (FeignException fe) {
            throw new FeignClientException(fe);
        }
    }

}
