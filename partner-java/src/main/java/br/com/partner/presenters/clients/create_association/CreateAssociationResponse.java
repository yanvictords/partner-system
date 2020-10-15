package br.com.partner.presenters.clients.create_association;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssociationResponse {

    private List<BigInteger> associatedCampaignIds;

}
