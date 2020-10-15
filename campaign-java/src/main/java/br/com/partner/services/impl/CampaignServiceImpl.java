package br.com.partner.services.impl;

import br.com.partner.exceptions.NotFoundException;
import br.com.partner.exceptions.UnprocessableEntityException;
import br.com.partner.models.Campaign;
import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import br.com.partner.presenters.consult_campaign.ConsultCampaignResponse;
import br.com.partner.presenters.create_campaign.CreateCampaignResponse;
import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;
import br.com.partner.repositories.CampaignRepository;
import br.com.partner.services.CampaignService;
import br.com.partner.services.facades.CampaignValidityFacade;
import br.com.partner.utils.mappers.CampaignMapper;
import br.com.partner.utils.mappers.ConsultCampaignResponseMapper;
import br.com.partner.utils.mappers.CreateCampaignDTOMapper;
import br.com.partner.utils.mappers.UpdateCampaignResponseMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class CampaignServiceImpl implements CampaignService {

    private static final String NOT_FOUND_ERROR = "Campanha inexistente!";
    private static final String EXPIRED_CAMPAIGN_ERROR = "Campanha expirada!";

    private final CampaignRepository campaignRepository;

    private final CampaignValidityFacade campaignValidityFacade;

    public CampaignServiceImpl(final CampaignRepository campaignRepository,
                               final CampaignValidityFacade campaignValidityFacade) {
        this.campaignRepository = campaignRepository;
        this.campaignValidityFacade = campaignValidityFacade;
    }

    @Override
    public CreateCampaignResponse createCampaign(final CreateCampaignRequest request) {
        final var newCampaign = CampaignMapper.map(request);
        final var updatedCampaigns = campaignValidityFacade.processAllCampaignExpiryDates(newCampaign);
        updateCampaigns(updatedCampaigns);

        final var mappedUpdatedCampaigns = updatedCampaigns.stream()
                .map(CreateCampaignDTOMapper::map)
                .collect(toList());

        final var savedCampaign = campaignRepository.create(newCampaign);
        final var mappedSavedCampaign = CreateCampaignDTOMapper.map(savedCampaign);

        return CreateCampaignResponse.builder()
                .createdCampaign(mappedSavedCampaign)
                .updatedCampaigns(mappedUpdatedCampaigns)
                .build();
    }

    @Override
    public UpdateCampaignResponse updateCampaign(final BigInteger campaignId,
                                                 final UpdateCampaignRequest request) {
        findCampaign(campaignId);
        final var campaign = CampaignMapper.map(request);
        final var updatedCampaign = campaignRepository.update(campaignId, campaign);
        return UpdateCampaignResponseMapper.map(updatedCampaign);
    }

    @Override
    public ConsultCampaignResponse findCampaign(final BigInteger campaignId) {
        try {
            final var campaign = campaignRepository.findByID(campaignId);
            if (campaign.isExpired()) {
                throw new UnprocessableEntityException(EXPIRED_CAMPAIGN_ERROR);
            }

            return ConsultCampaignResponseMapper.map(campaign);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public void deleteCampaign(final BigInteger campaignId) {
        try {
            campaignRepository.deleteById(campaignId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(NOT_FOUND_ERROR);
        }
    }

    @Override
    public List<Campaign> findUnexpiredCampaignsByTeamId(final BigInteger teamId) {
        return campaignRepository.findByTeamIdAndNotExpired(teamId);
    }

    @Override
    public List<Campaign> findUnexpiredCampaignsByIds(final List<BigInteger> campaignIds) {
        if (isEmpty(campaignIds)) {
            return newArrayList();
        }
        return campaignRepository.findByIdsAndNotExpired(campaignIds);
    }

    private void updateCampaigns(final List<Campaign> campaigns) {
        campaigns.forEach(campaign -> {
            final var request = UpdateCampaignRequest.builder()
                    .name(campaign.getName())
                    .teamID(campaign.getTeamID())
                    .expiryDate(campaign.getExpiryDate())
                    .build();

            updateCampaign(campaign.getId(), request);
        });
    }

}
