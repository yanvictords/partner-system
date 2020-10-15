package br.com.partner.utils;

import br.com.partner.parameters.clients.create_association.CreateAssociationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateAssociationRequestUtils {

    public static CreateAssociationRequest create(final List<BigInteger> campaignIds) {
        return CreateAssociationRequest.builder()
                .campaignIds(campaignIds)
                .build();
    }

}
