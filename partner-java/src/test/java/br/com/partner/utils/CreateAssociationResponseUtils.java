package br.com.partner.utils;

import br.com.partner.presenters.clients.create_association.CreateAssociationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CreateAssociationResponseUtils {

    public static CreateAssociationResponse create(final List<BigInteger> associatedCampaignIds) {
        return CreateAssociationResponse.builder()
                .associatedCampaignIds(associatedCampaignIds)
                .build();
    }

}
