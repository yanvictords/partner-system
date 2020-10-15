package br.com.partner.services.clients;

import br.com.partner.parameters.clients.create_association.CreateAssociationRequest;
import br.com.partner.presenters.clients.consult_association.ConsultAssociationResponse;
import br.com.partner.presenters.clients.consult_team_campaign.ConsultTeamCampaignResponse;
import br.com.partner.presenters.clients.create_association.CreateAssociationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigInteger;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(value = "campaignClient", url = "${campaign-service.host}")
public interface CampaignClient {

    @GetMapping(value = "/teams/{id}", produces = {APPLICATION_JSON_VALUE})
    ConsultTeamCampaignResponse findAllCampaignByTeamId(@PathVariable("id") BigInteger teamId);

    @GetMapping(value = "/associations/partner/{id}", produces = {APPLICATION_JSON_VALUE})
    ConsultAssociationResponse findAllCampaignByPartnerId(@PathVariable("id") BigInteger partnerId);

    @PostMapping(value = "/associations/partner/{id}",
            consumes = {APPLICATION_JSON_VALUE},
            produces = {APPLICATION_JSON_VALUE})
    CreateAssociationResponse associateAllCampaign(@PathVariable("id") BigInteger partnerId,
                                                   @RequestBody CreateAssociationRequest request);

}