package br.com.partner.presenters.create_campaign;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateCampaignResponse {

    private CreateCampaignDTO createdCampaign;

    private List<CreateCampaignDTO> updatedCampaigns;

}
