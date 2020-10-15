package br.com.partner.utils;

import br.com.partner.presenters.clients.consult_association.ConsultAssociationDTO;
import br.com.partner.presenters.clients.consult_association.ConsultAssociationResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsultAssociationResponseUtils {

    public static ConsultAssociationResponse create(final List<ConsultAssociationDTO> campaigns) {
        return ConsultAssociationResponse.builder()
                .campaigns(campaigns)
                .build();
    }

}
