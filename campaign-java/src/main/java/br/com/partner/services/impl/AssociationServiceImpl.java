package br.com.partner.services.impl;

import br.com.partner.models.Campaign;
import br.com.partner.models.PartnerCampaign;
import br.com.partner.parameters.create_association.CreateAssociationRequest;
import br.com.partner.presenters.consult_association.ConsultAssociationResponse;
import br.com.partner.presenters.create_association.CreateAssociationResponse;
import br.com.partner.repositories.PartnerCampaignRepository;
import br.com.partner.services.AssociationService;
import br.com.partner.services.CampaignService;
import br.com.partner.utils.mappers.ConsultAssociationDTOMapper;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AssociationServiceImpl implements AssociationService {

    private final CampaignService campaignService;

    private final PartnerCampaignRepository partnerCampaignRepository;

    public AssociationServiceImpl(final CampaignService campaignService,
                                  final PartnerCampaignRepository partnerCampaignRepository) {
        this.campaignService = campaignService;
        this.partnerCampaignRepository = partnerCampaignRepository;
    }

    @Override
    public ConsultAssociationResponse findAllPartnerCampaignAssociation(final BigInteger partnerId) {
        final var campaignIds = partnerCampaignRepository.findAllByPartnerId(partnerId)
                .stream()
                .map(PartnerCampaign::getCampaignId)
                .collect(toList());

        final var notExpiredCampaigns = campaignService.findUnexpiredCampaignsByIds(campaignIds);

        final var consultAssociationCampaigns = notExpiredCampaigns.stream()
                .map(ConsultAssociationDTOMapper::map)
                .collect(toList());

        return new ConsultAssociationResponse(consultAssociationCampaigns);
    }

    @Override
    public CreateAssociationResponse associate(final BigInteger partnerId,
                                               final CreateAssociationRequest request) {
        final var myExistingUnexpiredCampaigns = findAllPartnerCampaignAssociation(partnerId)
                .getCampaignIds();

        final var newCampaignIds = request.getCampaignIds()
                .stream()
                .filter(campaignId -> !myExistingUnexpiredCampaigns.contains(campaignId))
                .collect(toList());

        final var validCampaignIdsToAssociate = campaignService.findUnexpiredCampaignsByIds(newCampaignIds)
                .stream()
                .map(Campaign::getId)
                .collect(toList());

        createAssociations(partnerId, validCampaignIdsToAssociate);
        return new CreateAssociationResponse(validCampaignIdsToAssociate);
    }

    private void createAssociations(final BigInteger partnerId, final List<BigInteger> campaignIds) {
        campaignIds.forEach(campaignId -> {
            final var partnerCampaign = PartnerCampaign.builder()
                    .partnerId(partnerId)
                    .campaignId(campaignId)
                    .build();

            partnerCampaignRepository.create(partnerCampaign);
        });
    }

}
