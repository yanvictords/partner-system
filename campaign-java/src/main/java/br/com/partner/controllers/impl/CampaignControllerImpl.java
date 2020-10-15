package br.com.partner.controllers.impl;

import br.com.partner.controllers.CampaignController;
import br.com.partner.parameters.create_campaign.CreateCampaignRequest;
import br.com.partner.parameters.update_campaign.UpdateCampaignRequest;
import br.com.partner.presenters.consult_campaign.ConsultCampaignResponse;
import br.com.partner.presenters.create_campaign.CreateCampaignResponse;
import br.com.partner.presenters.update_campaign.UpdateCampaignResponse;
import br.com.partner.services.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class CampaignControllerImpl implements CampaignController {

    private final CampaignService campaignService;

    public CampaignControllerImpl(final CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @Override
    public ResponseEntity<CreateCampaignResponse> createCampaign(final CreateCampaignRequest request) {
        return ResponseEntity.status(CREATED).body(campaignService.createCampaign(request));
    }

    @Override
    public ResponseEntity<UpdateCampaignResponse> updateCampaign(final BigInteger campaignId,
                                                                 final UpdateCampaignRequest request) {
        return ResponseEntity.ok(campaignService.updateCampaign(campaignId, request));
    }

    @Override
    public ResponseEntity<ConsultCampaignResponse> findCampaign(final BigInteger campaignId) {
        return ResponseEntity.ok(campaignService.findCampaign(campaignId));
    }

    @Override
    public ResponseEntity<Void> deleteCampaign(final BigInteger campaignId) {
        campaignService.deleteCampaign(campaignId);
        return ResponseEntity.noContent().build();
    }

}
